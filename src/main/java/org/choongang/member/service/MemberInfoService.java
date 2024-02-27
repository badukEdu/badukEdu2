package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpServletRequest request;
    private final EntityManager em;

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

        /* 검색 조건 처리 S */
        String userId = search.getUserId();
        String name = search.getName();
        String tel = search.getTel();
        String email = search.getEmail();

        String sopt = search.getSopt();
        sopt = StringUtils.hasText(sopt) ? sopt.trim() : "ALL"; // 검색 항목
        String skey = search.getSkey(); // 검색 키워드

        if (StringUtils.hasText(userId)) {
            andBuilder.and(member.userId.contains(userId.trim()));
        }
        if (StringUtils.hasText(name)) {
            andBuilder.and(member.name.contains(name.trim()));
        }
        if (StringUtils.hasText(tel)) {
            andBuilder.and(member.tel.contains(tel.trim()));
        }
        if (StringUtils.hasText(email)) {
            andBuilder.and(member.email.contains(email.trim()));
        }

        // 조건별 키워드 검색
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();

            BooleanExpression cond1 = member.userId.contains(skey);
            BooleanExpression cond2 = member.name.contains(skey);
            BooleanExpression cond3 = member.tel.contains(skey);
            BooleanExpression cond4 = member.email.contains(skey);

            if (sopt.equals("userId")) {
                andBuilder.and(cond1);
            } else if (sopt.equals("name")) {
                andBuilder.and(cond2);
            } else if (sopt.equals("tel")) {
                andBuilder.and(cond3);
            } else if (sopt.equals("email")) {
                andBuilder.and(cond4);
            } else { // 통합검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder
                    .or(cond1)
                    .or(cond2)
                    .or(cond3)
                    .or(cond4);
                andBuilder.and(orBuilder);
            }
        }

        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");

        List<Member> items = new JPAQueryFactory(em)
            .selectFrom(member)
            .leftJoin(member)
            .fetchJoin()
            .where(andBuilder)
            .limit(limit)
            .offset(offset)
            .fetch();


        /* 페이징 처리 S */
        int total = (int)memberRepository.count(andBuilder); // 총 레코드 갯수

        Pagination pagination = new Pagination(page, total, 10, limit, request);
        /* 페이징 처리 E */

        return new ListData<>(items, pagination);
    }

    /**
     * 회원 추가 정보 처리
     *
     * @param member
     */
    public void addMemberInfo(Member member) {
    }

}