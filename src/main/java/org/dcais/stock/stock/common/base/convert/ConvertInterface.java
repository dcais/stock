package org.dcais.stock.stock.common.base.convert;

public interface ConvertInterface<R, P> {

  R convert(P param);
}