package org.dcais.stock.stock.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class IBaseServiceImpl <M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IBaseService<T> {
  @Autowired
  protected M baseMapper;

  @Override
  public Page<T> selectPage(Page<T> page, Wrapper<T> queryWrapper){
    return baseMapper.selectPage(page, queryWrapper);
  }
}

