package org.dcais.stock.stock.dao;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.daily.XSplitAdjustedDailyDao;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class XSplitAdjustDailyDaoTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private XSplitAdjustedDailyDao xSplitAdjustedDailyDao;

  @Test(enabled = false)
  public void add() {
    Date now = new Date();
    SplitAdjustedDaily splitAdjustedDaily = new SplitAdjustedDaily();
    splitAdjustedDaily.setTsCode("123546");
    splitAdjustedDaily.setTradeDate(LocalDateTime.now());
    splitAdjustedDaily.setOpen(new BigDecimal(12.34));
    List<SplitAdjustedDaily> list = new ArrayList<>();
    list.add(splitAdjustedDaily);
    xSplitAdjustedDailyDao.insertList(list);
  }

  @Test(enabled = false)
  void remove() {
    xSplitAdjustedDailyDao.remove("123546");
  }

  @Test(enabled = false)
  public void find() {
    SplitAdjustedDaily splitAdjustedDaily = xSplitAdjustedDailyDao.getLatest("123546");
    log.info(""+ JsonUtil.toJson(splitAdjustedDaily));
  }

  @Test
  public void getFromDate(){
    List<SplitAdjustedDaily> list = xSplitAdjustedDailyDao.getFromDate("000001.SZ", DateUtils.smartFormat("2001-01-03"),null);
    log.info(JsonUtil.toJson(list.get(0)));

  }

}
