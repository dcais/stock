package org.dcais.stock.stock.config.thread;

import org.dcais.stock.stock.thread.SaunaThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadConfig {

  @Bean(destroyMethod = "destroy")
  public ThreadPoolExecutor calcThreadPoolExecutor (){
    SaunaThreadPoolExecutor service = new SaunaThreadPoolExecutor(10, 10,
      0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    return service;
  }
}
