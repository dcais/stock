package org.dcais.stock.stock.biz.info.impl;

import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.info.IFinIndicatorRateService;
import org.dcais.stock.stock.dao.mybatisplus.fin.FinIndicatorRateMapper;
import org.dcais.stock.stock.entity.info.FinIndicatorRate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 财务指标数据 服务实现类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-29
 */
@Service
public class FinIndicatorRateServiceImpl extends IBaseServiceImpl<FinIndicatorRateMapper, FinIndicatorRate> implements IFinIndicatorRateService {

}
