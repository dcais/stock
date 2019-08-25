package org.dcais.stock.stock.biz;

import org.dcais.stock.stock.dao.mybatis.BaseDao;
import org.dcais.stock.stock.entity.BaseEntity;
import org.dcais.stock.stock.entity.IdEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseServiceImpl implements BaseService {


  public <T extends IdEntity> List<T> getAll(BaseDao<T> baseDao) {
    return baseDao.select(null);
  }

  public <T extends IdEntity> T getById(BaseDao<T> baseDao, Object id) {
    return baseDao.selectById(id);
  }

  @Transactional
  public <T extends IdEntity> boolean save(BaseDao<T> baseDao, T entity) {
    if (entity instanceof BaseEntity) {
      ((BaseEntity) entity).setDefaultBizValue();
    }
    if (entity.getId() == null) {
      return baseDao.insert(entity) > 0;
    } else {
      return baseDao.updateById(entity) > 0;
    }
  }

  @Transactional
  public <T extends IdEntity> boolean deleteById(BaseDao<T> baseDao, Object id) {
    T t = baseDao.selectById(id);
    if (t == null)
      return false;

    return baseDao.deleteById(id) > 0;
  }
 
  public <T extends IdEntity> int deleteByIds(BaseDao<T> baseDao, Object[] ids) {
    if (ids.length == 0) {
      return 0;
    }
    return baseDao.deleteByIds(ids);
  }

}
