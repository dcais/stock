package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.ana.AnaTagService;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.basic.TradeCalService;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

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
    list.forEach( basic -> {
      threadPoolExecutor.execute(new Runnable() {
        @Override
        public void run() {
          log.info( "AnaTagTask on [tsCode]"+ basic.getTsCode());
          try{
            anaTagService.ana(basic.getTsCode());
          }catch (Exception e){
          }finally {
            long count = latch.getCount();
            latch.countDown();
            log.debug("AnaTagTask done [latch count]" + count + "[tsCode]" + basic.getTsCode());
          }
        }
      });
    });

    Date lastTradeDate = tradeCalService.getLastTradeDate();
    anaTagService.anaRS50(lastTradeDate);


    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error("",e);
    }
    log.info("AnaTagTask Done");
  }
}
