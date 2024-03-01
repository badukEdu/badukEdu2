package org.choongang.statistic;

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
        LocalDate sDate = LocalDate.now().minusDays(100);
        orderService.getData(sDate, null, "MONTH");
    }
}
