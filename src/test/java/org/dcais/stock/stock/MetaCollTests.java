package org.dcais.stock.stock;

import com.google.gson.Gson;
import com.mysql.cj.xdevapi.*;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.utils.CommonUtils;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class MetaCollTests extends AbstractTestNGSpringContextTests {

  @Autowired
  private XMetaCollDao xMetaCollDao;

  @Test
  public void add() {
    Date now = new Date();
    xMetaCollDao.put("123", now);
  }

  @Test
  public void find() {
    Object a = xMetaCollDao.get("123");
    Date date = CommonUtils.getValue(a, Date.class);
    log.info(date.toString());
  }

}
