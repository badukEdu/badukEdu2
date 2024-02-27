package org.choongang.teacher.group.services.stGroup;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.entities.GameContent;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.education.group.services.joinStG.JoinSTGInfoService;
import org.choongang.member.Authority;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.controllers.RequestStGroup;
import org.choongang.teacher.group.controllers.StGroupSearch;
import org.choongang.teacher.group.entities.QStudyGroup;
import org.choongang.teacher.group.entities.StudyGroup;
import org.choongang.teacher.group.repositories.StGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SGInfoService {

    private final StGroupRepository stGroupRepository;
    public final JoinSTGInfoService joinSTGInfoService;
    private final EntityManager em;
    private final HttpServletRequest request;
    private final HttpSession session;
    private final GameContentInfoService gameContentInfoService ;
    private final MemberUtil memberUtil;

    /**
     * 교육자는 본인이 생성한 스터디 그룹 목록만 가져옴.
     * 나머지는 모든 스터디 그ㅜㅂ 목록 가져옴
     * @param search
     * @return
     */
    public ListData<StudyGroup> getList(StGroupSearch search){

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        /*페이지 블럭 수*/
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 4);
        int offset = (page - 1) * limit; // 레코드 시작 위치

        QStudyGroup studyGroup = QStudyGroup.studyGroup;
        // QGameContent gameTitle = QGameContent.gameTitle;
        BooleanBuilder andBuilder = new BooleanBuilder();

        //교육자는 본인 스터디그룹만 볼 수 있음
        if(memberUtil.isTeacher()){
            andBuilder.and(studyGroup.member.eq((Member) session.getAttribute("member")));
        }

        String sopt = search.getSopt();
        String skey = search.getSkey().trim();

        if(StringUtils.hasText(skey)){
            BooleanExpression groupNameCond = studyGroup.name.contains(skey);
            BooleanExpression gameNameCond = studyGroup.gameTitle.contains(skey);

            if(memberUtil.isStudent()){
                BooleanExpression teacherNameCond = studyGroup.teacherName.contains(skey);

                if(sopt.equals("groupName")){
                    andBuilder.and(groupNameCond);
                } else if (sopt.equals("gameName")) {
                    andBuilder.and(gameNameCond);
                } else if (sopt.equals("teacherName")) {
                    andBuilder.and(teacherNameCond);
                }else if (sopt.equals("ALL")) {
                    BooleanBuilder orBuilder = new BooleanBuilder();
                    orBuilder.or(groupNameCond)
                            .or(gameNameCond)
                            .or(teacherNameCond);

                    andBuilder.and(orBuilder);
                }
            } else {

                if(sopt.equals("groupName")){
                    andBuilder.and(groupNameCond);
                } else if (sopt.equals("gameName")) {
                    andBuilder.and(gameNameCond);
                } else if (sopt.equals("ALL")) {
                    BooleanBuilder orBuilder = new BooleanBuilder();
                    orBuilder.or(groupNameCond)
                            .or(gameNameCond);

                    andBuilder.and(orBuilder);
                }
            }

            /////////

        }
    PathBuilder<StudyGroup> pathBuilder = new PathBuilder<>(StudyGroup.class, "stGroup");

        List<StudyGroup> items = new JPAQueryFactory(em)
                .selectFrom(studyGroup)
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .fetch();
        long total = stGroupRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, (int)total, 5, limit, request);

        for(StudyGroup s : items){
            gameContentInfoService.addInfo(s.getGameContent());
        }


        return new ListData <> (items , pagination);
    }

    public StudyGroup getById(Long num){

        return stGroupRepository.getById(num);
    }

    /**
     * 해당 스터디그룹에 가입한(가입 승인 된) 멤버 목록 리턴
     * @param num -> 스터디그룹 num
     * @return
     */
    public List<Member> getJoinMember(Long num){

        List<Member> members = new ArrayList<>();
        List<JoinStudyGroup> jstgList = joinSTGInfoService.getAll();
        StudyGroup stg = stGroupRepository.getById(num);
        for(JoinStudyGroup j : jstgList){
            if(stg.equals(j.getStudyGroup()) && j.isAccept()){
                members.add(j.getMember());
            }
        }
        return members;
    }


    public RequestStGroup getForm(Long num) {
        StudyGroup data = getById(num);
        RequestStGroup form = new ModelMapper().map(data, RequestStGroup.class);
        form.setGameEndDate(data.getGameContent().getEndDate());
        form.setGameStartDate(data.getGameContent().getStartDate());
        form.setNum(data.getNum());
        return form;
    }

    /**
     * 게임 컨텐츠 num -> 해당 게임 컨텐츠로 개설한 스터디 그룹 개수 리턴
     * @param num
     * @return
     */
    public int count(Long num){
        int count =0;
        List<StudyGroup> list = stGroupRepository.findAll();
        GameContent g = gameContentInfoService.getById(num);

        for(StudyGroup s : list){
            if(s.getGameContent().equals(g)){
                count++;
            }
        }
        return count;
    }

}
