package org.dcais.stock.stock.config.mysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.ClientFactory;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({XDevApiPoolingProperties.class})
public class XDevApiConfig {
  @Value("${mysql.x-dev-api.url}")
  private String url;

  @Bean(destroyMethod = "close")
  public Client xDevApiClient(XDevApiPoolingProperties poolingProperties) throws Exception {
    ClientFactory cf = new ClientFactory();
    ObjectMapper objectMapper = new ObjectMapper();
    XDevApiPoolingProperties properties = null;

    if ( AopUtils.isAopProxy(poolingProperties)){
      poolingProperties = (XDevApiPoolingProperties) ((Advised)poolingProperties).getTargetSource().getTarget();
    }
    String jsonStrProps = JsonUtil.getGsonObj().toJson(poolingProperties);

    return cf.getClient(url, jsonStrProps);
  }
}
