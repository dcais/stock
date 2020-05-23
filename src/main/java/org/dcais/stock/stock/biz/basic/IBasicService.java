package org.dcais.stock.stock.biz.basic;


import com.baomidou.mybatisplus.extension.service.IService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.basic.Basic;

import java.util.List;

/**
 * <p>
 * 股票基本信息 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-23
 */
public interface IBasicService extends IService<Basic> {
  Result sync();
  List<Basic> getAllList();
  Basic getBySymbol(String symbol);
  Basic getByTsCode(String tsCode);
}
