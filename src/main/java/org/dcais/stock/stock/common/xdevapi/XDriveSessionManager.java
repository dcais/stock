package org.dcais.stock.stock.common.xdevapi;

import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class XDriveSessionManager {

  private static final ThreadLocal<Map<Object, SessionHolder>> resources =
    new NamedThreadLocal<>("Transactional resources");

  public static SessionHolder getSessionHolder(Client client) {
    SessionHolder sessionHolder = doGetSessionHoleder(client);
    if(sessionHolder == null ){
      Session session = client.getSession();
      sessionHolder = new SessionHolder(session);
      bindSession(client,sessionHolder);
    }
    return sessionHolder;
  }

  private static SessionHolder doGetSessionHoleder(Client client){
    Map<Object, SessionHolder> map = resources.get();
    if (map == null) {
      return null;
    }
    SessionHolder sessionHolder = map.get(client);
    if(
      sessionHolder != null
        && ( sessionHolder.getSession()==null
        || sessionHolder.isClosed()
        || !sessionHolder.getSession().isOpen()
      )){
      map.remove(client);
      if(map.isEmpty()){
        resources.remove();
      }
      sessionHolder = null;
    }
    return sessionHolder;
  }

  private static void bindSession (Client client,SessionHolder sessionHolder){
    Map<Object,SessionHolder> map = resources.get();
    if(map == null ){
      map = new HashMap<>();
      resources.set(map);
    }
    SessionHolder oldSessionHolder = map.put(client,sessionHolder);
    if(
      oldSessionHolder != null
      && ( oldSessionHolder.getSession()==null
           || oldSessionHolder.isClosed()
           || !oldSessionHolder.getSession().isOpen()
       )){
      oldSessionHolder = null;
    }
    if(oldSessionHolder != null){
      throw new IllegalStateException("Already value [" + oldSessionHolder + "] for tecName [" +
        client + "] bound to thread [" + Thread.currentThread().getName() + "]");
    }
    if (log.isTraceEnabled()) {
      log.trace("Bound value [" + sessionHolder + "] for tecName [" + client + "] to thread [" +
        Thread.currentThread().getName() + "]");
    }

  }
  public static Object unbindResource(Client key) throws IllegalStateException {
    SessionHolder value = doUnbindSession(key);
    if (value == null) {
      throw new IllegalStateException(
        "No value for tecName [" + key + "] bound to thread [" + Thread.currentThread().getName() + "]");
    }
    return value;
  }


  @Nullable
  private static SessionHolder doUnbindSession(Client actualKey) {
    Map<Object, SessionHolder> map = resources.get();
    if (map == null) {
      return null;
    }
    SessionHolder value = map.remove(actualKey);
    // Remove entire ThreadLocal if empty...
    if (map.isEmpty()) {
      resources.remove();
    }
    // Transparently suppress a ResourceHolder that was marked as void...
    if(
      value != null
        && ( value.getSession()==null
        || !value.getSession().isOpen()
      )){
      value= null;
    }

    if (value != null && log.isTraceEnabled()) {
      log.trace("Removed value [" + value + "] for tecName [" + actualKey + "] from thread [" +
        Thread.currentThread().getName() + "]");
    }
    return value;
  }

}
