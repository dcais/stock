package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.biz.tec.MAService;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SMATask {
  @Autowired
  private BasicService basicService;
  @Autowired
  private ThreadPoolExecutor threadPoolExecutor;
  @Autowired
  private MAService maService;
  @Autowired
  private XMetaCollDao xMetaCollDao;

  public void startCalc(){
    log.info("SMATask Start");

    List<Double> tmps = (List<Double>) xMetaCollDao.get("SmaPeriods");
    List<Integer> periods = tmps.stream().map(Double::intValue).collect(Collectors.toList());

    List<Basic> list = basicService.getAllList();
    log.info(String.format("SplitAdjustTask count %d", list.size()));
    CountDownLatch latch = new CountDownLatch(list.size());
    list.forEach( basic -> {
      threadPoolExecutor.execute(new Runnable() {
        @Override
        public void run() {
          log.info( "SMATask on [tsCode]"+ basic.getTsCode());
          try{
            maService.calcSMA(basic.getTsCode(),periods);
          }catch (Exception e){
          }finally {
            long count = latch.getCount();
            latch.countDown();
            log.debug("SMATask [latch count]" + count + "[tsCode]" + basic.getTsCode());
          }
        }
      });
    });

    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error("",e);
    }
    log.info("SMATask Done");
  }

}
