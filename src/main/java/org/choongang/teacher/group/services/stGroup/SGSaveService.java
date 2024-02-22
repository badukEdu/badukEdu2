package org.choongang.teacher.group.services.stGroup;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.gamecontent.service.GameContentInfoService;
import org.choongang.member.entities.Member;
import org.choongang.teacher.stGrooup.controllers.RequestStGroup;
import org.choongang.teacher.stGrooup.entities.StudyGroup;
import org.choongang.teacher.stGrooup.repositories.StGroupRepository;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class SGSaveService {

    private final StGroupRepository stGroupRepository;
    private final HttpSession session;
    private final SGInfoService sgInfoService;
    //private final vetaGameInfo vetaGameInfo;
    private final GameContentInfoService gameContentInfoService;


    public void save(RequestStGroup form){
        StudyGroup s = null;
        if(form.getMode().equals("add")){
            s = new StudyGroup();

            s.setName(form.getName());
            s.setStartDate(form.getStartDate());
            s.setEndDate(form.getEndDate());
            s.setMaxSubscriber(form.getMaxSubscriber());
            s.setMaxLevel(form.getMaxLevel());
            s.setText(form.getText());
            s.setMonth(form.getMonth());
            s.setGameTitle(form.getGameTitle());
            s.setTeacherName(form.getTeacherName());
            s.setMember((Member) session.getAttribute("member"));
            //s.setGameContent(vetaGameInfo.getById(form.getGameContentNum()));
            s.setGameContent(gameContentInfoService.getById(form.getGameContentNum()));
        }else{
            s = sgInfoService.getById(form.getNum());

            s.setName(form.getName());
            s.setStartDate(form.getStartDate());
            s.setEndDate(form.getEndDate());
            s.setMaxSubscriber(form.getMaxSubscriber());
            s.setMaxLevel(form.getMaxLevel());
            s.setText(form.getText());
            s.setMonth(form.getMonth());

        }
        /*
        s.setMember(session.member);
        s.setGameContent(gameRepository.findbyId(form.getGameContentNum()));
        */
        stGroupRepository.saveAndFlush(s);

    }
}
