package org.dcais.stock.stock.biz.info.impl;

import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.info.IFinIncomeService;
import org.dcais.stock.stock.dao.mybatisplus.fin.FinIncomeMapper;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 利润表 服务实现类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-31
 */
@Service
public class FinIncomeServiceImpl extends IBaseServiceImpl<FinIncomeMapper, FinIncome> implements IFinIncomeService {

}
