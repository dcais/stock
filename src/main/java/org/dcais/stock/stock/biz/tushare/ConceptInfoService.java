package org.dcais.stock.stock.biz.tushare;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.google.common.util.concurrent.RateLimiter;
import org.dcais.stock.stock.biz.tushare.entity.TushareConceptDetail;
import org.dcais.stock.stock.biz.tushare.parser.TushareDataParser;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.Concept;
import org.dcais.stock.stock.entity.info.ConceptDetail;
import org.dcais.stock.stock.http.tushare.param.TushareParam;
import org.dcais.stock.stock.http.tushare.result.TushareData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConceptInfoService extends StockInfoService{
  private RateLimiter rateLimiter;
  @Override
  public RateLimiter getRateLimiter(){
    return this.rateLimiter;
  }

  @Autowired
  private TushareParamGem tushareParamGem;

  @PostConstruct
  private void init() {
    rateLimiter = RateLimiter.create(50);
  }

  public Result concept() {
    TushareParam tushareParam = tushareParamGem.getParam("concept");
    Map<String, Object> param = new HashMap<>();
    param.put("src", "ts");
    tushareParam.setParams(param);
    Result<TushareData> tushareResult = this.request(tushareParam);
    if (!tushareResult.isSuccess()) {
      return tushareResult;
    }
    List<Concept> concepts = TushareDataParser.parse(tushareResult.getData(), Concept.class);
    return Result.wrapSuccessfulResult(concepts);
  }

  public Result conceptDetail(String code) {
    TushareParam tushareParam = tushareParamGem.getParam("concept_detail");
    Map<String, Object> param = new HashMap<>();
    param.put("id", code);
    tushareParam.setParams(param);
    Result<TushareData> tushareResult = this.request(tushareParam);
    if (!tushareResult.isSuccess()) {
      return tushareResult;
    }
    List<TushareConceptDetail> concepts = TushareDataParser.parse(tushareResult.getData(), TushareConceptDetail.class);
    List<ConceptDetail> conceptDetails = concepts.stream().map( t->{
      ConceptDetail detail = new ConceptDetail();
      detail.setConceptCode(t.getId());
      detail.setConceptName(t.getConceptName());
      detail.setName(t.getName());
      detail.setTsCode(t.getTsCode());
      return detail;
    }).collect(Collectors.toList());

    return Result.wrapSuccessfulResult(conceptDetails);
  }
}
