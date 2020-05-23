package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.IBasicService;
import org.dcais.stock.stock.biz.tec.SARService;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.thread.SaunaThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SARTask {
  @Autowired
  private IBasicService basicService;
  @Autowired
  private SARService sarService;
  @Autowired
  private XMetaCollDao xMetaCollDao;

  @Value("${stock.calc-thread-pool.size:10}")
  private Integer calcThreadPoolSize;


  public void startCalc(){
    log.info("SARTask Start");
    SaunaThreadPoolExecutor service = new SaunaThreadPoolExecutor(calcThreadPoolSize, calcThreadPoolSize,
      0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    List<Basic> list = basicService.getAllList();

    log.info(String.format("SARTask count %d", list.size()));
    CountDownLatch latch = new CountDownLatch(list.size());
    list.forEach( basic -> {
      service.execute(new Runnable() {
        @Override
        public void run() {
          log.info( "SARTask on [tsCode]"+ basic.getTsCode());
          try{
            sarService.calc(basic.getTsCode());
          }catch (Exception e){
          }finally {
            long count = latch.getCount();
            latch.countDown();
            log.debug("SARTask done [latch count]" + count + "[tsCode]" + basic.getTsCode());
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

    log.info("SARTask Done");
  }

}
