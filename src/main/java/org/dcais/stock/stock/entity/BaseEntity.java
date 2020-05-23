package org.dcais.stock.stock.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEntity extends IdEntity implements Serializable {
    @TableLogic
    protected String isDeleted;

    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime gmtCreate;
    @TableField(fill = FieldFill.INSERT)
    protected Long creator;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime gmtModified;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long modifier;



  public void setDefaultBizValue() {
    if (id == null) {
      // 创建
      if (gmtCreate == null) {
        gmtCreate = LocalDateTime.now();
      }
      if (creator == null) {
        creator = 1L;
      }
      if (isDeleted == null) {
        isDeleted = "N";
      }
    }
    // 修改
    if (modifier == null) {
      modifier = 1L;
    }
    gmtModified = LocalDateTime.now();
  }
}
