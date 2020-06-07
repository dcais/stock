package org.dcais.stock.stock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

  public class Father{

  }
  public class Son extends Father{

  }

  @Test
  public void contextLoads() {
    Boolean a = Father.class.isAssignableFrom(Son.class);
    Assert.assertTrue(a);
    Boolean b = Son.class.isAssignableFrom(Father.class);
    Assert.assertFalse(b);
  }

}
