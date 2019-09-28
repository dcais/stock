package org.dcais.stock.stock.biz.tec;

import joinery.DataFrame;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.tec.impl.MAServiceImpl;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class MAServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private MAServiceImpl maServiceImpl;

  @Test
  public void test() throws Exception {
    maServiceImpl.calcSMA("000001.SZ", Arrays.asList(8));
  }


}
