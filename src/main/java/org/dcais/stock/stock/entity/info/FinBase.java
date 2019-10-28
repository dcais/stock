package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class FinBase extends BaseEntity {
  private String tsCode;
  private String annDate;
  private String fAnnDate;
  private String endDate;


  public void setEndDate(String endDate) {
    this.endDate = endDate;
    Date dateEndDate = DateUtils.smartFormat(endDate);
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateEndDate);
    int month = cal.get(Calendar.MONTH);
    if(month == 2){
      this.setReportSeason(1);
    }else if (month == 5) {
      this.setReportSeason(2);
    } else if (month == 8) {
      this.setReportSeason(3);
    } else if (month == 11) {
      this.setReportSeason(4);
    }
    int year = cal.get(Calendar.YEAR);
    this.setReportYear(year);
  }

  //更新标识
  private String updateFlag;
  private int reportSeason;
  private int reportYear;
}
