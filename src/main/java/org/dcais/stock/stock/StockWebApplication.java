package org.dcais.stock.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.yuanpin","org.dcais.stock.stock"})
public class StockWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockWebApplication.class, args);
  }

}
