package org.dcais.stock.stock.biz.basic.impl;

import java.util.List;
import java.util.Map;

import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.dao.basic.BasicDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.dcais.stock.stock.biz.BaseService;


@Service
public class BasicServiceImpl extends BaseServiceImpl implements BasicService {

  @Autowired
  private BasicDao basicDao;

  public List<Basic> getAll() {
    return super.getAll(basicDao);
  }

  public List<Basic> select(Map<String,Object> param){
    return basicDao.select(param);
  }

  public Integer selectCount(Map<String,Object> param){
    return basicDao.selectCount(param);
  }

  public Basic getById(Long id) {
    return super.getById(basicDao, id);
  }

  public boolean save(Basic basic) {
    return super.save(basicDao, basic);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(basicDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(basicDao, ids);
  }
}
