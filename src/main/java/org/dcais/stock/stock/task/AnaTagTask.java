package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.ana.IDailyAnaTagService;
import org.dcais.stock.stock.biz.basic.IBasicService;
import org.dcais.stock.stock.biz.basic.ITradeCalService;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
@Slf4j
public class AnaTagTask {
  @Autowired
  private IBasicService basicService;
  @Autowired
  private IDailyAnaTagService dailyAnaTagService;
  @Autowired
  private ThreadPoolExecutor threadPoolExecutor;
  @Autowired
  private ITradeCalService tradeCalService;

  public void startCalc(LocalDateTime tradeDate){
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
            Map r = dailyAnaTagService.ana(basic.getTsCode(),tradeDate);
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

    LocalDateTime lastTradeDate = tradeCalService.getLastTradeDate();
    dailyAnaTagService.anaCompare(lastTradeDate,items);
    log.info("AnaTagTask Done");
  }
}
