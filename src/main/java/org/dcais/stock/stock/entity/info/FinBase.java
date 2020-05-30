package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class FinBase extends BaseEntity {

  /**
   * ts code
   */
  private String tsCode;

  /**
   * 季度
   */
  private Integer reportSeason;

  /**
   * 年份
   */
  private Integer reportYear;

  /**
   * 公告日期 (二选一)
   */
  private LocalDateTime annDate;

  /**
   * 公告开始日期
   */
  private LocalDateTime fAnnDate;

  /**
   * 报告期
   */
  private LocalDateTime endDate;

  /**
   * 更新标识
   */
  private String updateFlag;


  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
    if(endDate == null ){
      return;
    }

    Month month = endDate.getMonth();
    if(month == Month.MARCH){
      this.setReportSeason(1);
    }else if (month == Month.JUNE) {
      this.setReportSeason(2);
    } else if (month == Month.SEPTEMBER) {
      this.setReportSeason(3);
    } else if (month == Month.DECEMBER) {
      this.setReportSeason(4);
    }
    int year = endDate.getYear();
    this.setReportYear(year);
  }

}
