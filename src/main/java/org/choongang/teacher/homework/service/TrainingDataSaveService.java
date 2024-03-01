package org.choongang.teacher.homework.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.email.service.EmailMessage;
import org.choongang.email.service.EmailSendService;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.teacher.homework.entities.Homework;
import org.choongang.teacher.homework.entities.QTrainingData;
import org.choongang.teacher.homework.entities.TrainingData;
import org.choongang.teacher.homework.repositories.HomeworkRepository;
import org.choongang.teacher.homework.repositories.TrainingDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingDataSaveService {

    private final HomeworkInfoService homeworkInfoService;
    private final TrainingDataRepository trainingDataRepository;
    private final MemberRepository memberRepository;
    private final MemberUtil memberUtil;
    private final HomeworkRepository homeworkRepository;
    private final EmailSendService emailSendService;


    /** 다수의 숙제를 다수의 학습자에게 배포
     *
     * @param checkedHomeworks -> 배포할 숙제 list
     * @param checkedMembers -> 배포받을 학습자 list
     */
    public void distribute(List<Long> checkedHomeworks, List<Long> checkedMembers) {

        QTrainingData trainingData = QTrainingData.trainingData;
        BooleanBuilder andBuilder = new BooleanBuilder();

        // 없다면 new
        TrainingData form = null;
        Homework homework = null;
        Member member = null;
        // 체크된 숙제를 // 각 체크된 그룹 멤버에게 배포
        for (Long chkHW : checkedHomeworks) {

            // trainingData 중에서 나에게 배포된 것들 탐색
            andBuilder.and(trainingData.member.eq(memberUtil.getMember()));
            // 그 중 현재 선택된 homework와 동일한 것 탐색
            andBuilder.and(trainingData.homework.num.eq(chkHW));

            List<TrainingData> trainingDataList= (List<TrainingData>) trainingDataRepository.findAll(andBuilder);
            System.out.println(trainingDataList);
            // 학습자가 가진 trainingData 중 homework 정보 중에서 동일한 homework num이 있는 것은 패스
            if (!trainingDataList.isEmpty()) {
                continue;
            }

            homework = homeworkRepository.findById(chkHW).orElseThrow();
            EmailMessage emailMessage;
            for (Long chkMB : checkedMembers) {
                emailMessage = null;

                form = new TrainingData();

                member = memberRepository.findById(chkMB).orElseThrow();

                form.setHomework(homework);
                form.setMember(member);

                trainingDataRepository.save(form);

                // 숙제 배포되었다고 이메일 전송
                String subject = "숙제가 전송되었습니다.";
                String message = member.getName() + "님(" + member.getUserId() + ")\n"
                        + homework.getName() + "  숙제가 등록되었습니다.\n" +
                        "제출 마감 일자는 " + homework.getDeadLine() + "입니다.";
                emailMessage = new EmailMessage(member.getEmail(), subject, message);
                emailSendService.sendMail(emailMessage);
            }
        }

        trainingDataRepository.flush();
    }


    /** 다수의 학습자 trainingData 평가
     *
     * @param chks -> trainingData num
     * @param scores -> 각각의 score
     */
    public void saveScore(List<Long> chks, List<Long> scores) {

        for (int i = 0; i < chks.size(); i++) {
            TrainingData trainingData = trainingDataRepository.findById(chks.get(i)).orElseThrow();
            if (scores.get(i) < 0) {
                continue;
            }
            trainingData.setScore(scores.get(i));
            trainingData.setADate(LocalDateTime.now());

            if (scores.get(i) >= 1) { // 보통 이상의 평가를 받았을 때
                Member member = memberRepository.findById(trainingData.getMember().getNum()).orElseThrow();
                // 학습자의 레벨 < 숙제레벨일 때
                if (member.getLevels() < trainingData.getHomework().getStudyLevel()) {
                    // 학습자의 레벨 = 숙제의 레벨 입력
                    member.setLevels(trainingData.getHomework().getStudyLevel());
                    memberRepository.save(member);
                }
            }

            trainingDataRepository.save(trainingData);
        }
        trainingDataRepository.flush();
        memberRepository.flush();
    }

    /** 개별 trainingData 평가
     *
     * @param form : 숙제에 대한 답변, 점수 담고 있는 form
     */
    public void saveQuestionAnswer(TrainingData form) {

        TrainingData trainingData = trainingDataRepository.findById(form.getNum()).orElseThrow();

        trainingData.setQuestionAnswer(form.getQuestionAnswer());
        trainingData.setScore(form.getScore());

        trainingDataRepository.saveAndFlush(trainingData);
    }
}
