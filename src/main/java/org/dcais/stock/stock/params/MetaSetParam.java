package org.dcais.stock.stock.params;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MetaSetParam {
  @NotEmpty
  private String key;
  @NotNull
  private Object value;
}
