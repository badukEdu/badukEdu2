package org.choongang.statistic.controllers;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RequestSearch {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate sDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate eDate;

  private String type = "DAY";

}
