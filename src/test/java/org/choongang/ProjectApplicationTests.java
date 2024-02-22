package org.choongang;

import org.choongang.member.Authority;
import org.choongang.member.entities.Member;
import org.choongang.member.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootTest
class ProjectApplicationTests {
        @Autowired
        private JoinService joinService;
        @Autowired
        private PasswordEncoder encoder;
        @Test
        void member(){
            Member member = new Member();

            member.setName("홍길동");
            member.setAgree(true);
            member.setAgree2(true);
            member.setAgree3(true);
            member.setAgree4(true);
            member.setAgree5(true);
            member.setEmailAgree(true);
            member.setEnable(true);
            member.setSMSAgree(true);
            member.setLevels(100L);
            member.setUserId("user02");
            member.setEmail("cbmfja@naver.com");
            member.setPassword("_aA123456");
            member.setAuthority(Authority.USER);
            member.setBirth("1999-01-01");
            member.setGender("M");
            member.setTel("01001010101");




            joinService.process(member);

        }

}
