package org.choongang.staticstic;

import org.choongang.statistic.service.OrderStatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class OrderStatisticTest {

  @Autowired
  private OrderStatisticService orderService;

  @Test
  void test1() {
    LocalDate eDate = LocalDate.now();
    LocalDate sDate = eDate.minusDays(100);
    orderService.getData(sDate, eDate, "MONTH");
  }
}
