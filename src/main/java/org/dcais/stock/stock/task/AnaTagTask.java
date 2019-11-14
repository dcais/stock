package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.ana.AnaTagService;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.basic.TradeCalService;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

@Component
@Slf4j
public class AnaTagTask {
  @Autowired
  private BasicService basicService;
  @Autowired
  private AnaTagService anaTagService;
  @Autowired
  private ThreadPoolExecutor threadPoolExecutor;
  @Autowired
  private TradeCalService tradeCalService;

  public void startCalc(){
    log.info("AnaTagTask Start");

    List<Basic> list = basicService.getAllList();
    log.info(String.format("SplitAdjustTask count %d", list.size()));
    CountDownLatch latch = new CountDownLatch(list.size());
    List<Map> items = new ArrayList<>();
    for (Basic basic : list) {
      Future<Map> future = threadPoolExecutor.submit(new Callable<Map>() {
        @Override
        public Map call() {
          log.info("AnaTagTask on [tsCode]" + basic.getTsCode());
          try {
            Map r = anaTagService.ana(basic.getTsCode());
            return r;
          } catch (Exception e) {
            return null;
          } finally {
            long count = latch.getCount();
            latch.countDown();
            log.debug("AnaTagTask done [latch count]" + count + "[tsCode]" + basic.getTsCode());
          }
        }
      });
      try {
        Map map = future.get();
        if (map == null ){
          continue;
        }
        items.add(map);

      } catch (InterruptedException | ExecutionException e) {
        log.error("",e);
      }
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error("",e);
    }

    Date lastTradeDate = tradeCalService.getLastTradeDate();
    anaTagService.anaCompare(lastTradeDate,items);
    log.info("AnaTagTask Done");
  }
}
