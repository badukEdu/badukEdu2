package org.choongang;

import org.choongang.member.Authority;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Member;
import org.choongang.member.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootTest
class ProjectApplicationTests {
        @Autowired
        private JoinService joinService;
        @Autowired
        private PasswordEncoder encoder;


        @Test
        void member(){

            Member member4 = new Member();
            member4.setName("홍길동");
            member4.setAgree(true);
            member4.setAgree2(true);
            member4.setAgree3(true);
            member4.setAgree4(true);
            member4.setAgree5(true);
            member4.setEmailAgree(true);
            member4.setEnable(true);
            member4.setSMSAgree(true);
            member4.setLevels(100L);
            member4.setUserId("student02");
            member4.setEmail("cbmffjghggha@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.TEACHER);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member4);





        }

}
