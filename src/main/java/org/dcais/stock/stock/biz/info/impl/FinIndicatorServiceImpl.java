package org.dcais.stock.stock.biz.info.impl;

import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.info.IFinIndicatorService;
import org.dcais.stock.stock.dao.mybatisplus.fin.FinIndicatorMapper;
import org.dcais.stock.stock.entity.info.FinIndicator;
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
public class FinIndicatorServiceImpl extends IBaseServiceImpl<FinIndicatorMapper, FinIndicator> implements IFinIndicatorService {

}
