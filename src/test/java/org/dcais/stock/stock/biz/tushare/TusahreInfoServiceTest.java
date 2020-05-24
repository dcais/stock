package org.dcais.stock.stock.biz.tushare;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.entity.basic.FutBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
@Test
public class TusahreInfoServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private FutureInfoService futureInfoService;

  @Test
  public void test(){
    Result<List<FutBasic>> r = futureInfoService.futBasic("SHFE");
    log.info(""+ JsonUtil.toJson(r.getData()));

  }


}
