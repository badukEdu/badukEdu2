package org.choongang;

import org.choongang.member.Authority;
import org.choongang.member.entities.Member;
import org.choongang.member.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ProjectApplicationTests {
        @Autowired
        private JoinService joinService;
        @Autowired
        private PasswordEncoder encoder;


        @Test
        void member(){

            /*Member admin = new Member();
            admin.setName("운영자");
            admin.setAgree(true);
            admin.setAgree2(true);
            admin.setAgree3(true);
            admin.setAgree4(true);
            admin.setAgree5(true);
            admin.setEmailAgree(true);
            admin.setEnable(true);
            admin.setSMSAgree(true);
            admin.setLevels(100L);
            admin.setUserId("admin");
            admin.setEmail("admin@test.org");
            admin.setPassword(encoder.encode("_aA123456"));
            admin.setAuthority(Authority.ADMIN);
            admin.setBirth("1999-01-01");
            admin.setGender("M");
            admin.setTel("01001010101");

            joinService.process(admin);



            Member member1 = new Member();
            member1.setName("교육자01");
            member1.setAgree(true);
            member1.setAgree2(true);
            member1.setAgree3(true);
            member1.setAgree4(true);
            member1.setAgree5(true);
            member1.setEmailAgree(true);
            member1.setEnable(true);
            member1.setSMSAgree(true);
            member1.setLevels(100L);
            member1.setUserId("teacher01");
            member1.setEmail("teacher01@naver.com");
            member1.setPassword(encoder.encode("_aA123456"));
            member1.setAuthority(Authority.TEACHER);
            member1.setBirth("1999-01-01");
            member1.setGender("M");
            member1.setTel("01001010101");

            joinService.process(member1);

            Member member2 = new Member();
            member2.setName("교육자02");
            member2.setAgree(true);
            member2.setAgree2(true);
            member2.setAgree3(true);
            member2.setAgree4(true);
            member2.setAgree5(true);
            member2.setEmailAgree(true);
            member2.setEnable(true);
            member2.setSMSAgree(true);
            member2.setLevels(100L);
            member2.setUserId("teacher02");
            member2.setEmail("teacher02@naver.com");
            member2.setPassword(encoder.encode("_aA123456"));
            member2.setAuthority(Authority.TEACHER);
            member2.setBirth("1999-01-01");
            member2.setGender("M");
            member2.setTel("01001010101");

            joinService.process(member2);

            Member member3 = new Member();
            member3.setName("학습자01");
            member3.setAgree(true);
            member3.setAgree2(true);
            member3.setAgree3(true);
            member3.setAgree4(true);
            member3.setAgree5(true);
            member3.setEmailAgree(true);
            member3.setEnable(true);
            member3.setSMSAgree(true);
            member3.setLevels(100L);
            member3.setUserId("student01");
            member3.setEmail("student01@naver.com");
            member3.setPassword(encoder.encode("_aA123456"));
            member3.setAuthority(Authority.STUDENT);
            member3.setBirth("1999-01-01");
            member3.setGender("M");
            member3.setTel("01001010101");

            joinService.process(member3);

            Member member4 = new Member();
            member4.setName("학습자02");
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
            member4.setEmail("student02@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.STUDENT);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member4);




            Member user01 = new Member();
            user01.setName("사용자01");
            user01.setAgree(true);
            user01.setAgree2(true);
            user01.setAgree3(true);
            user01.setAgree4(true);
            user01.setAgree5(true);
            user01.setEmailAgree(true);
            user01.setEnable(true);
            user01.setSMSAgree(true);
            user01.setLevels(100L);
            user01.setUserId("user01");
            user01.setEmail("user01@naver.com");
            user01.setPassword(encoder.encode("_aA123456"));
            user01.setAuthority(Authority.USER);
            user01.setBirth("1999-01-01");
            user01.setGender("M");
            user01.setTel("01001010101");

            joinService.process(user01);

*/



            Member member4 = new Member();
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
            member4.setEmail("cbmffjjtgghgㅎgha@naver.com");
            member4.setPassword(encoder.encode("_aA123456"));
            member4.setAuthority(Authority.ADMIN);
            member4.setBirth("1999-01-01");
            member4.setGender("M");
            member4.setTel("01001010101");

            joinService.process(member4);
        }

}
