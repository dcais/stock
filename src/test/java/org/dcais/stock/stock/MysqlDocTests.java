package org.dcais.stock.stock;

import com.mysql.cj.xdevapi.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class MysqlDocTests extends AbstractTestNGSpringContextTests {

  private Session mySession;
  private Schema stockDB;
  private String collectionName = "alalei";

  @Autowired
  private Client xDevApiClient;

  @BeforeClass
  public void init() {
    Session sess = xDevApiClient.getSession();
    mySession = sess;

    Schema stock = mySession.getSchema("stock");
    stockDB = stock;
    Collection myColl = stockDB.getCollection(collectionName);
    if (myColl.existsInDatabase().equals(DatabaseObject.DbObjectStatus.EXISTS)) {
      stockDB.dropCollection(collectionName);
    }
    stockDB.createCollection(collectionName);
  }

  @Test
  public void add() {
    Collection myColl = stockDB.getCollection(collectionName);
    myColl.add(
      "{\"_id\":\"s-3\",\"name\":\"Sakila\", \"age\":17}"
    ).execute();
//    myColl.add(
//      "{\"name\":\"Sakila\", \"age\":17}",
//      "{\"name\":\"Sakila\", \"age\":18}"
//    ).execute();

  }

  //  @Ignore
  @Test
  public void find() {
    Collection myColl = stockDB.getCollection(collectionName);
    Map<String, Object> params = new HashMap<>();
    params.put("age", 12);
    DocResult docs = myColl.find("age > :age").sort("age asc").bind(params).execute();

    List<DbDoc> dbDocs = docs.fetchAll();
    log.info(dbDocs.toString());


  }

  public Schema getStockDB() {
    return stockDB;
  }
}
