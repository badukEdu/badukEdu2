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
            member4.setUserId("teacher01");
            member4.setEmail("cbmffjghggha@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.TEACHER);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member4);

            Member member3 = new Member();
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
            member4.setUserId("teacher02");
            member4.setEmail("cbmffjghgg3ha@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.TEACHER);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member3);
            Member member45 = new Member();
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
            member4.setUserId("student01");
            member4.setEmail("cbmffjjghggha@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.STUDENT);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member45);
            Member member44 = new Member();
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
            member4.setEmail("cbmffjjtghggha@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.STUDENT);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member44);

            Member member00 = new Member();
            member4.setName("관리자");
            member4.setAgree(true);
            member4.setAgree2(true);
            member4.setAgree3(true);
            member4.setAgree4(true);
            member4.setAgree5(true);
            member4.setEmailAgree(true);
            member4.setEnable(true);
            member4.setSMSAgree(true);
            member4.setLevels(100L);
            member4.setUserId("admin0101");
            member4.setEmail("cbmffjjtghgㅎgha@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.ADMIN);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member00);




        }

}
