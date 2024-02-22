package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.email.service.EmailMessage;
import org.choongang.email.service.EmailSendService;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FindIdService {
    private final MemberRepository repository;
    private final EmailSendService sendService;

    public void sendUserId(String name, String email) {
        Member member = repository.findByNameAndEmail(name, email).orElse(null);
        if (member == null) {
            return;
        }

        String subject = "아이디 찾기";
        String message = "아이디...";
        EmailMessage emailMessage = new EmailMessage(email, subject, message);
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("userId", member.getUserId());
        sendService.sendMail(emailMessage, "send_id", tplData);
    }
}
