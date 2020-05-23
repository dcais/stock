package org.dcais.stock.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class IdEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;
}
