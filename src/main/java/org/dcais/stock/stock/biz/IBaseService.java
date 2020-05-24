package org.dcais.stock.stock.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IBaseService <T> extends IService<T> {

  Page<T> selectPage(Page<T> page, Wrapper<T> queryWrapper);
}
