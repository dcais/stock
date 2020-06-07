package org.dcais.stock.stock.entity.ana;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 分析打标
 * </p>
 *
 * @author david-cai
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("stock_daily_ana_tag")
public class DailyAnaTag extends BaseEntity {


    /**
     * 股票代码
     */
    private String tsCode;

    /**
     * 交易日期
     */
    private LocalDateTime tradeDate;

    /**
     * tag key
     */
    private String tagKey;

    /**
     * 数字值
     */
    private BigDecimal value;

    /**
     * 字符串值
     */
    private String strValue;


  public void setValue(BigDecimal value) {
    this.value = value;
    value = value.setScale(6, BigDecimal.ROUND_FLOOR);
    this.setStrValue(value.toString());
  }
}
