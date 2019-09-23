package org.dcais.stock.stock.dao.xdriver.tec;

import lombok.Getter;
import org.dcais.stock.stock.entity.tec.TecMa;
import org.springframework.stereotype.Component;

@Component
public class XTecSARDao extends XTecBaseDao<TecMa> {
  @Getter
  private String collName = "stock_x_tec_sar";


}
