package org.dcais.stock.stock.common.xdevapi;

import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XDriverSession {
  @Autowired
  private Client xDevApiClient;

  public Session getSession() {
    return xDevApiClient.getSession();
  }
}
