package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.biz.tec.MAService;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.thread.SaunaThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SMATask {
  @Autowired
  private BasicService basicService;
  @Autowired
  private MAService maService;
  @Autowired
  private XMetaCollDao xMetaCollDao;
  @Value("${stock.calc-thread-pool.size:10}")
  private Integer calcThreadPoolSize;

  public void startCalc(){
    log.info("SMATask Start");

    List<Double> tmps = (List<Double>) xMetaCollDao.get("SmaPeriods");
    List<Integer> periods = tmps.stream().map(Double::intValue).collect(Collectors.toList());

    SaunaThreadPoolExecutor service = new SaunaThreadPoolExecutor(calcThreadPoolSize, calcThreadPoolSize,
      0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    List<Basic> list = basicService.getAllList();
    log.info(String.format("SMATask count %d", list.size()));
    CountDownLatch latch = new CountDownLatch(list.size());
    list.forEach( basic -> {
      service.execute(new Runnable() {
        @Override
        public void run() {
          log.info( "SMATask on [tsCode]"+ basic.getTsCode());
          try{
            maService.calcSMA(basic.getTsCode(),periods);
          }catch (Exception e){
          }finally {
            long count = latch.getCount();
            latch.countDown();
            log.debug("SMATask done [latch count]" + count + "[tsCode]" + basic.getTsCode());
          }
        }
      });
    });

    try {
      latch.await();
    } catch (InterruptedException e) {
      log.error("",e);
    } finally {
      service.destroy();
    }
    log.info("SMATask Done");
  }

}
