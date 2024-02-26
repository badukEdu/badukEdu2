package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.member.controllers.MemberSearch;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.member.Authority;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpServletRequest request;

    /**
     * 아이디 조회읽기
     * @param userId the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Member member = memberRepository.findByUserId(userId) // 아이디 조회
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        Authority authority = Objects.requireNonNullElse(member.getAuthority(), Authority.USER);

        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(authority.name()));



        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .enable(member.isEnable())
                .build();
    }


    /**
     * 회원 목록 (나중에 리스트 뽑을때 쓰는 용도)
     *
     * @param search
     * @return
     */
    public ListData<Member> getList(MemberSearch search) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1); // 페이지 번호
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20); // 1페이지당 레코드 갯수
        int offset = (page - 1) * limit; // 레코드 시작 위치 번호

        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Member> data = memberRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);
        /* 페이징 처리 E */

        return new ListData<>(data.getContent(), pagination);
    }

    /**
     * 회원 추가 정보 처리
     *
     * @param member
     */
    public void addMemberInfo(Member member) {
    }

}