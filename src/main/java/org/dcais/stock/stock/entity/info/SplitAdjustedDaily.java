package org.dcais.stock.stock.entity.info;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@TableName("stock_split_adjusted_daily")
public class SplitAdjustedDaily extends Daily{
}
