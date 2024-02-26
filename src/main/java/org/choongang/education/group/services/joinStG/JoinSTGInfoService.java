package org.choongang.education.group.services.joinStG;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.education.group.controllers.JoinStGroupSearch;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.education.group.entities.QJoinStudyGroup;
import org.choongang.education.group.repositories.JoinStGroupRepository;
import org.choongang.member.Authority;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinSTGInfoService {


    private final JoinStGroupRepository joinStGroupRepository;
    private final HttpSession session;
    private final EntityManager em;
    private final HttpServletRequest request;

    public JoinStudyGroup findById(Long num){

        return joinStGroupRepository.findById(num).orElse(null);
    }

    public List<JoinStudyGroup> getAll(){

        return joinStGroupRepository.findAll();
    }





    /**
     * 교육자일 경우 본인이 개설한 스터디그룹 신청 명단만 보이도록
     * 관리자는 전체 열람 가능
     * 일반, 학생 회원은 열람 불가
     * @return
     */
    public ListData<JoinStudyGroup> getList(JoinStGroupSearch search){

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        /*페이지 블럭 수*/
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 4);
        int offset = (page - 1) * limit; // 레코드 시작 위치

        QJoinStudyGroup joinStudyGroup = QJoinStudyGroup.joinStudyGroup;

        BooleanBuilder andBuilder = new BooleanBuilder();

        //교육자는 본인 스터디그룹만 볼 수 있음
        if(((Member)session.getAttribute("member")).getAuthority() == Authority.TEACHER){
            andBuilder.and(joinStudyGroup.studyGroup.member.eq((Member) session.getAttribute("member")));
        }

        //승인 대기 목록일 경우에는 내가 신청한 스터디 그룹만 보이도록
        if(search.getType().equals("wait")){
            andBuilder.and(joinStudyGroup.member.eq((Member) session.getAttribute("member")));
        }

        String sopt = search.getSopt();
        String skey = search.getSkey().trim();

        if(StringUtils.hasText(skey)){
            BooleanExpression groupNameCond = joinStudyGroup.studyGroup.name.contains(skey);
            BooleanExpression teacherNameCond = joinStudyGroup.studyGroup.teacherName.contains(skey);
            BooleanExpression memberNameCond = joinStudyGroup.member.name.contains(skey);
            if(sopt.equals("groupName")){
                andBuilder.and(groupNameCond);
            } else if (sopt.equals("memberName")) {
                andBuilder.and(memberNameCond);
            }else if (sopt.equals("teacherName")) {
                andBuilder.and(teacherNameCond);
            }else if (sopt.equals("ALL")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(groupNameCond)
                        .or(memberNameCond);

                andBuilder.and(orBuilder);
            }
        }

        List<JoinStudyGroup> items = new JPAQueryFactory(em)
                .selectFrom(joinStudyGroup)
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .fetch();
        long total = joinStGroupRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, (int)total, 5, limit, request);


        return new ListData <> (items , pagination);
    }


}

