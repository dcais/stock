package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.entity.info.Concept;

import java.util.List;

public interface ConceptService {
  void sync();

  List<Concept> getAll();
}
