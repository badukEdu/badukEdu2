package org.choongang.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.repositories.BoardRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardScheduler {

    private final BoardRepository boardRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void changeDate() {

        // postingType을 expectedPostingDate -> immediately로 변경
        // scheduledDate -> Null로 변경
        log.error("==== scheduler: start");
        log.error("==== now: {}", LocalDate.now().minusDays(1));
        for(Notice_ target: boardRepository.findByPostingTypeAndScheduledDateBefore("expectedPostingDate", LocalDate.now().plusDays(1))) {
            log.error("==== target Id: {}", target.getNum());

            target.setPostingType("immediately");
            target.setScheduledDate(null);
            boardRepository.save(target);
        }
        log.error("==== scheduler: end");

    }
}
