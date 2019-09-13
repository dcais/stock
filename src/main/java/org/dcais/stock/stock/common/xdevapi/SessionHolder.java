package org.dcais.stock.stock.common.xdevapi;

import com.mysql.cj.xdevapi.Session;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;


public class SessionHolder {
  @Nullable
  private Session curSession;

  private boolean transactionActive = false;
  private int referenceCount = 0;
  @Getter
  private boolean closed = false;

  public SessionHolder(Session session) {
    Assert.notNull(session, "Active Session is required");
    this.curSession = session;
  }

  protected boolean hasSession() {
    return (this.curSession != null);
  }
  protected boolean isTransactionActive() {
    return this.transactionActive;
  }

  public Session getSession(){
    return this.curSession;
  }

  public void close() {
    if(curSession == null ){
      return;
    }
    if(curSession.isOpen()){
      if( this.referenceCount <= 0 ){
        curSession.close();
        this.closed= true;
        curSession = null;
      }
    }
  }

  public void requested() {
    this.referenceCount++;
  }

  public void released() {
    this.referenceCount--;
  }

}
