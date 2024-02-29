package org.choongang.staticstic;

import org.choongang.statistic.service.MemberStatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Map;

@SpringBootTest
public class MemberStatisticTest {

  @Autowired
  private MemberStatisticService memberService;

  @Test
  void test1() {
    Map<String, Long> data = memberService.getData(LocalDate.now().minusDays(100), "DAY");
    System.out.println(data);
  }
  @Test
  void test2() {
    Map<String, Long> data2 = memberService.getData(LocalDate.now().minusDays(100), "MONTH");
    System.out.println(data2);
  }
}
