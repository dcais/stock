package org.dcais.stock.stock.dao.xdriver.concept;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DocResult;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexDef;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInfo;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInterface;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.dcais.stock.stock.entity.info.Concept;
import org.dcais.stock.stock.entity.info.ConceptDetail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class XConceptDetailDao extends XCommon {
  @Getter
  private String collName = "stock_x_concept_detail";

  @Override
  public CollectionIndexInterface getCollectionIndexInterface() {
    return new CollectionIndexInterface() {
      @Override
      public List<CollectionIndexInfo> indexInfo() {
        List<CollectionIndexInfo> collectionIndexInfos = new ArrayList<>();

        CollectionIndexInfo collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_code");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.code", "TEXT(16)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_name");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.conceptName", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_ts_code");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tsCode", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);

        return collectionIndexInfos;
      }
    };
  }

  public void deleteAll(){
    Collection col = getCollection();
    col.remove("true").execute();
  }

  public void insertList(List<ConceptDetail> concepts){
    String[] arrayJson = concepts.stream().map(
      JsonUtil::toJson
    ).toArray(String[]::new);
    Collection col = getCollection();
    col.add(arrayJson).execute();
  }

}
