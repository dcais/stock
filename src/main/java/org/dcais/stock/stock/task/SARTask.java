package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.tec.MAService;
import org.dcais.stock.stock.biz.tec.SARService;
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
public class SARTask {
  @Autowired
  private BasicService basicService;
  @Autowired
  private ThreadPoolExecutor threadPoolExecutor;
  @Autowired
  private SARService sarService;
  @Autowired
  private XMetaCollDao xMetaCollDao;

  public void startCalc(){
    log.info("SARTask Start");

    List<Basic> list = basicService.getAllList();

    log.info(String.format("SARTask count %d", list.size()));
    CountDownLatch latch = new CountDownLatch(list.size());
    list.forEach( basic -> {
      threadPoolExecutor.execute(new Runnable() {
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
    }
    log.info("SARTask Done");
  }

}
