package org.choongang.education.homework.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.teacher.homework.entities.QTrainingData;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class TrainingDataInfoService {
    private final TrainingDataRepository trainingDataRepository;
    private final HomeworkRepository homeworkRepository;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;

        public ListData<TrainingData> getlist() {

        // int page = Utils.onlyPositiveNumber(search.getP, 1)

        Member member = memberUtil.getMember();

        BooleanBuilder andBuilder = new BooleanBuilder();
        QTrainingData trainingData = QTrainingData.trainingData;

        andBuilder.and(trainingData.member.eq(member));

        Pageable pageable = PageRequest.of(0, 20, Sort.by(desc("createdAt")));

        Page<TrainingData> data = trainingDataRepository.findAll(andBuilder, pageable);
        int total = (int)trainingDataRepository.count(andBuilder);

            Pagination pagination = new Pagination(1, total, 10, 20 ,request);


        return new ListData<>(data.getContent(), pagination);
    }

    public TrainingData getOne(Long num) {

        return trainingDataRepository.findById(num)
                .orElseThrow(() -> new NoSuchElementException("TrainingData with id " + num + " not found"));
    }


}
