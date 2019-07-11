package org.dcais.stock.stock.entity;

import lombok.Data;

@Data
public abstract class IdEntity implements java.io.Serializable {
    protected Long id;
}