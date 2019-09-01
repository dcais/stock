package org.dcais.stock.stock;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.utils.CommonUtils;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.dao.xdriver.meta.XStockMetaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class StockMetaTests extends AbstractTestNGSpringContextTests {

  @Autowired
  private XStockMetaDao xStockMetaDao;

  @Test
  public void add() {
    Date now = new Date();
    xStockMetaDao.setStockMeta("123","lll","123.45");
    xStockMetaDao.setStockMeta("123","rrr","123.46");
  }

  @Test
  public void find() {
    String v= xStockMetaDao.getStockMeta("123","lll");
    Assert.assertEquals(v,"123.45");
    v= xStockMetaDao.getStockMeta("123","rrr");
    Assert.assertEquals(v,"123.46");
  }

}
