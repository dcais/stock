package org.dcais.stock.stock.dao;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.tec.XSMADao;
import org.dcais.stock.stock.entity.tec.TecMa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class XSMADaoTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private XSMADao xsmaDao;

  @Test(enabled = true)
  public void add() {
    Date now = new Date();
    TecMa tecMa = new TecMa();
    tecMa.setTsCode("123456");
    tecMa.setTradeDate(DateUtils.smartFormat("2019-01-01"));
    tecMa.setValue(new BigDecimal(13.00));
    tecMa.setTecName("sma8");
    List<TecMa> list = new ArrayList<>();
    list.add(tecMa);
    xsmaDao.insertList(list);
  }

  @Test(enabled = true)
  void remove() {
    xsmaDao.remove("123456");
  }

  @Test
  public void getFromDate(){
    List<TecMa> list = xsmaDao.getFromDate("123456", "sma8",DateUtils.smartFormat("2001-01-03"));

    log.info(JsonUtil.toJson(list.get(0)));
  }

}
