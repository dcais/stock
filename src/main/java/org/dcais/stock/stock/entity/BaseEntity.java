package org.dcais.stock.stock.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static org.dcais.stock.stock.entity.BaseEntityICons.IS_DELETE_UNENABLE;

@Data
public abstract class BaseEntity extends IdEntity implements Serializable {
  protected String isDeleted;
  protected Date gmtCreate;
  protected Long creator;
  protected Date gmtModified;
  protected Long modifier;

  public void setDefaultBizValue() {
    if (id == null) {
      // 创建
      if (gmtCreate == null) {
        gmtCreate = new Date();
      }
      if (creator == null) {
        creator = 1L;
      }
      if (isDeleted == null) {
        isDeleted = IS_DELETE_UNENABLE;
      }
    }
    // 修改
    if (modifier == null) {
      modifier = 1L;
    }
    gmtModified = new Date();
  }

  public void setDefaultBizValue(Long userId) {

    if (userId == null) {
      setDefaultBizValue();
      return;
    }

    if (id == null) {
      // 创建
      if (gmtCreate == null) {
        gmtCreate = new Date();
      }
      if (creator == null) {
        creator = userId;
      }
      if (isDeleted == null) {
        isDeleted = IS_DELETE_UNENABLE;
      }
    }
    // 修改
    modifier = userId;
    gmtModified = new Date();
  }
}
