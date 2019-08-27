package org.dcais.stock.stock.common.xdevapi.aop;

import com.mysql.cj.xdevapi.Client;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dcais.stock.stock.common.xdevapi.SessionHolder;
import org.dcais.stock.stock.common.xdevapi.XDriveSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class XDaoAspect {
  @Autowired
  private Client xDevApiClient;

  @Pointcut("execution(* org.dcais.stock.stock.dao.xdriver..*Dao.*(..))")
  public void pointcut() {
  }

  @Around("pointcut()")
  public Object log(ProceedingJoinPoint joinpoint) throws Throwable{
    SessionHolder sessionHolder = XDriveSessionManager.getSessionHolder(xDevApiClient);
    sessionHolder.requested();
    if( log.isTraceEnabled()){
      log.trace("Dao method begin: " + joinpoint.getSignature());
    }
    try{
      //実行
      Object result = joinpoint.proceed();
      if(log.isTraceEnabled()){
        log.trace("Dao method "+joinpoint.getSignature() + " ends with result "+result);
      }
      return result;
    } catch (Exception ex) {
      log.error("Dao End with Exception: " ,ex);
      throw ex;
    } finally {
      sessionHolder.released();
      sessionHolder.close();
    }
  }

}
