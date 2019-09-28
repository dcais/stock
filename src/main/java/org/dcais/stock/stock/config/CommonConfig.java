package org.dcais.stock.stock.config;

import org.dcais.stock.stock.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class CommonConfig {

  @Bean
  public Date tecGteDate(@Value("${stock.tec.gte-date}")String strTecGetDate ){
    return DateUtils.smartFormat(strTecGetDate);
  }

}
