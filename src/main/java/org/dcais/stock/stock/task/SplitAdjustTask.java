package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.IBasicService;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Slf4j
public class SplitAdjustTask {
  @Autowired
  private IBasicService basicService;
  @Autowired
  private ThreadPoolExecutor threadPoolExecutor;
  @Autowired
  private SplitAdjustService splitAdjustService;

  public void startCalc(){
    log.info("SplitAdjustTask Start");
    List<Basic> list = basicService.getAllList();
    log.info(String.format("SplitAdjustTask count %d", list.size()));
    CountDownLatch latch = new CountDownLatch(list.size());
    list.forEach( basic -> {
      threadPoolExecutor.execute(new Runnable() {
        @Override
        public void run() {
          log.info( "SplitAdjustTask on [tsCode]"+ basic.getTsCode());
          try{
            splitAdjustService.calcSplitAdjust(basic.getTsCode());
          }catch (Exception e){
          }finally {
            long count = latch.getCount();
            latch.countDown();
            log.debug("SplitAdjustTask done [latch count]" + count + "[tsCode]" + basic.getTsCode());
          }
        }
      });
    });

    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error("",e);
    }
    log.info("SplitAdjustTask Done");
  }

}
