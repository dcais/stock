package org.dcais.stock.stock.biz.info.impl;

import org.dcais.stock.stock.biz.info.AdjFactorService;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.common.cons.StockMetaConstant;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.MathUtil;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.dao.xdriver.meta.XStockMetaDao;
import org.dcais.stock.stock.entity.info.AdjFactor;
import org.springframework.beans.factory.annotation.Autowired;

public class SplitAdjustServiceImpl implements SplitAdjustService {
  @Autowired
  private AdjFactorService adjFactorService;
  @Autowired
  private XStockMetaDao xStockMetaDao;

  public Result calcSplitAdjust(String tsCode){
    Result<AdjFactor> rAdjFactorLatest = adjFactorService.getMaxDaily(tsCode);
    if(!rAdjFactorLatest.isSuccess()){
      return Result.wrapErrorResult("","can not get latest adjust factor");
    }
    AdjFactor adjFactor = rAdjFactorLatest.getData();
    if( isRecaculate(adjFactor)){
      //todo: delete this ts code adFactor records
    }




    return Result.wrapSuccessfulResult("OK");
  }

  public boolean isRecaculate(AdjFactor adjFactor){
    String tsCode = adjFactor.getTsCode();
    String lastAdjFactor = xStockMetaDao.getStockMeta(tsCode, StockMetaConstant.LAST_ADJFACTOR_STR);
    if(StringUtil.isBlank(lastAdjFactor)){
      return true;
    }
    if(!MathUtil.toRateFormat(adjFactor.getAdjFactor()).equals(lastAdjFactor)){
      return true;
    }
    return false;
  }

}
