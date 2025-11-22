package com.kiwoom.application.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StockInfoResponse {

  private String dt;
  private String totBuyAmt;
  private String totEvltAmt;
  private String totEvltvPrft;
  private String totPrftRt;
  private String dbstBal;
  private String dayStkAsst;
  private String buyWght;
  private List<StockDetailResponse> dayBalRt;

  private Integer returnCode;
  private String returnMsg;
}
