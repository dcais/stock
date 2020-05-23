package org.dcais.stock.stock.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 填充器
 *
 * @author nieqiurong 2018-08-10 22:59:23.
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("creator", 1l, metaObject);
        this.setFieldValByName("modifier", 1l, metaObject);
        this.setFieldValByName("gmtCreate", now ,metaObject);
        this.setFieldValByName("gmtModified", now ,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("modifier", 1l, metaObject);
        this.setFieldValByName("gmtModified", now ,metaObject);
    }
}
