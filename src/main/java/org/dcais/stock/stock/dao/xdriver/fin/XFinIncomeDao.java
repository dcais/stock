package org.dcais.stock.stock.dao.xdriver.fin;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DocResult;
import lombok.Getter;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexDef;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInfo;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInterface;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.dcais.stock.stock.entity.info.FinIndicator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class XFinIncomeDao extends XCommon {
  @Getter
  private String collName = "stock_x_fin_income";

  @Override
  public CollectionIndexInterface getCollectionIndexInterface() {
    return new CollectionIndexInterface() {
      @Override
      public List<CollectionIndexInfo> indexInfo() {
        List<CollectionIndexInfo> collectionIndexInfos = new ArrayList<>();

        CollectionIndexInfo collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_ts_code");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tsCode", "TEXT(16)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_end_date");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.endDate", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_report_season");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.reportSeason", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_report_year");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.reportYear", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);

        return collectionIndexInfos;
      }
    };
  }

  public void insertList(List<FinIncome> finIncomes) {
    String[] arrayDaily = finIncomes.stream().map(
      JsonUtil::toJson
    ).toArray(String[]::new);
    Collection col = getCollection();
    col.add(arrayDaily).execute();
  }

  public void remove(String tsCode) {
    Collection col = getCollection();
    col.remove("tsCode=:tsCode").bind("tsCode",tsCode).execute();
  }
  public FinIncome getLast(String tsCode) {
    Collection col = getCollection();
    Map<String, Object> params = new HashMap<>();
    params.put("tsCode",tsCode);
    DocResult docs = col.find("tsCode=:tsCode").bind(params).orderBy("reportYear desc,reportSeason desc").execute();
    if (!docs.hasData()) {
      return null;
    }
    DbDoc dbDoc = docs.fetchOne();
    if (dbDoc == null) {
      return null;
    }
    FinIncome finIncome = JsonUtil.getGsonObj().fromJson(dbDoc.toString(), FinIncome.class);
    return finIncome;
  }
}
