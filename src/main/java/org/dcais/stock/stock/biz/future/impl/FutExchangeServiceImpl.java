package org.dcais.stock.stock.biz.future.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.future.FutExchangeService;
import org.dcais.stock.stock.dao.mybatis.basic.FutExchangeDao;
import org.dcais.stock.stock.entity.basic.FutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FutExchangeServiceImpl extends BaseServiceImpl implements FutExchangeService {
  @Autowired
  private FutExchangeDao futExchangeDao;

  @Override
  public List<FutExchange> getAll() {
    return super.getAll(futExchangeDao);
  }

}
