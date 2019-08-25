package org.dcais.stock.stock.config.mysql;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties(value = "mysql.x-dev-api.client")
@Data
public class XDevApiPoolingProperties {
  private Pooling pooling;
  @Data
  public static class Pooling {
    @NotNull
    private boolean enabled;
    @NotNull
    private Integer maxSize;
    @NotNull
    private Integer maxIdleTime = 30000;
    @NotNull
    private Integer queueTimeout = 10000;
  }
}
