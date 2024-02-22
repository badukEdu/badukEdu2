
package org.choongang.education.group.services.joinStG;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.education.group.entities.JoinStudyGroup;
import org.choongang.education.group.repositories.JoinStGroupRepository;
import org.choongang.member.entities.Member;
import org.choongang.teacher.group.services.stGroup.SGInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinSTGSaveService {


    private final JoinStGroupRepository joinStGroupRepository;
    private final SGInfoService sgInfoService;
    private final JoinSTGInfoService joinSTGInfoService;
    private final HttpSession session;


    /**
     * (학생)
     * 여러 스터디그룹 동시 가입 신청
     * @param chks
     */
    public void save(List<Long> chks){

        for(Long num : chks){
            JoinStudyGroup jsg = new JoinStudyGroup();

            jsg.setMember((Member) session.getAttribute("member"));
            jsg.setStudyGroup(sgInfoService.getById(num));
            joinStGroupRepository.saveAndFlush(jsg);
        }

    }

    public void accept(List<Long> chks ){

        for(Long num : chks){
            JoinStudyGroup jsg = joinStGroupRepository.getById(num);
            if(jsg != null){
                jsg.setAccept(true);
                joinStGroupRepository.saveAndFlush(jsg);
                System.out.println("승인완료");
            }
        }

    }

}
