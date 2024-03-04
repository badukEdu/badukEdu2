package org.choongang.admin.board.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.choongang.admin.board.controllers.RequestBoardPosts;
import org.choongang.admin.board.entities.BoardFileInfo;
import org.choongang.admin.board.entities.NoticeSearch;
import org.choongang.admin.board.entities.Notice_;
import org.choongang.admin.board.entities.QNotice_;
import org.choongang.admin.board.repositories.BoardFileRepository;
import org.choongang.admin.board.repositories.BoardRepository;
import org.choongang.admin.board.repositories.NoticeCommentRepository;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.configs.FileProperties;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.MemberUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final HttpServletRequest request;
    private final FileInfoService fileInfoService;
    private final NoticeCommentRepository noticeCommentRepository;
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberUtil memberUtil;
    private final FileProperties fileProperties;

    /* 게시글(Notice, FaQ) 등록(등록 즉시 게시) 및 수정 서비스 S */

    public void save(RequestBoardPosts form) throws IOException {

        Long num = form.getNum();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Notice_ notice;

        if (mode.equals("edit") && num != null) {
            // 수정 모드이고 게시물의 번호가 있을 때 이미 존재하는 게시글을 불러옴
            notice = boardRepository.findById(num).orElseThrow(BoardNotFoundException::new);
        } else {
            // 등록 모드일 때 새로운 게시글 생성
            notice = new Notice_();
        }

        notice.setType(form.getType());
        notice.setOnTop(form.isOnTop());
        notice.setTitle(form.getTitle());
        notice.setPostingType(form.getPostingType());
        notice.setQuestion(form.getQuestion());
        notice.setAnswer(form.getAnswer());
        notice.setContent(form.getContent());
        notice.setMember(memberUtil.getMember());
        notice.setScheduledDate(form.getScheduledDate());

        // 게시글 저장 전에 조회수를 증가시킴
        increaseVisitCount(notice);

        boardRepository.saveAndFlush(notice);

        log.error("form.getUploadFile(): {}", form.getUploadFile());
        log.error("form.getUploadFile() size: {}", form.getUploadFile().size());
        if (form.getUploadFile() != null && !form.getUploadFile().isEmpty()) {
            List<BoardFileInfo> boardFileInfoList = new ArrayList<>();
            String path = fileProperties.getPath();
            for (MultipartFile fileInfo : form.getUploadFile()) {
                String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                        + "_" + fileInfo.getOriginalFilename();

                // 강사님 file upload 적용

                File fileDir = new File(path);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }

                fileInfo.transferTo(new File(path + fileName));
                // boardFileService

                log.error("file info {}", fileInfo.getResource().getFilename());
                // BoardFileInfo DB Save
                boardFileRepository.save(BoardFileInfo.builder()
                        .notice(notice)
                        .fileName(fileName)
                        .originFileName(fileInfo.getOriginalFilename())
                        .filePath(path + fileName)
                        .build());

            }
            notice.setFile(boardFileInfoList);
        }
    }

    private void increaseVisitCount(Notice_ notice) {
        // 현재 조회수를 가져와서 1 증가시킴
        Long currentVisit = notice.getVisit() != null ? notice.getVisit() : 0;
        notice.setVisit(currentVisit + 1);
    }
    /* 게시글(Notice, FaQ) 등록 및 수정 서비스 E */

    /* 조회수 증가 서비스 S */
    public void increaseVisitCount(Long num) {
        Optional<Notice_> optionalNotice = boardRepository.findById(num);
        if (optionalNotice.isPresent()) {
            Notice_ notice = optionalNotice.get();
            Long currentVisit = notice.getVisit() != null ? notice.getVisit() : 0;
            notice.setVisit(currentVisit + 1);
            boardRepository.saveAndFlush(notice);
        } else {
            throw new BoardNotFoundException();
        }
    }
    /* 조회수 증가 서비스 S */


    /* 등록된 게시글 조회(정렬 기준 X, 등록 순) S */

    public List<Notice_> getList() {
        List<Notice_> noticeList = boardRepository.findAll();

        return noticeList;
    }

    /* 등록된 게시글 조회(정렬 기준 X, 등록 순) E */



    /* 노출 여부를 기준으로 게시물 조회 S */

    public ListData<Notice_> getListOrderByOnTop(NoticeSearch search, String postingTypeFlag) {

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";

        QNotice_ notice = QNotice_.notice_;

        /* 검색 조건 처리 S */

        BooleanBuilder andBuilder = new BooleanBuilder();
        if (!"ALL".equals(postingTypeFlag)) {
            andBuilder.and(notice.postingType.eq(postingTypeFlag));
        }

        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            BooleanExpression titleConds = notice.title.contains(skey);
            BooleanExpression contentConds = notice.content.contains(skey);
            BooleanExpression typeConds = notice.type.contains(skey);

            if (sopt.equals("ALL")) {
                BooleanBuilder orBuilder = new BooleanBuilder();
                andBuilder.and(orBuilder.or(titleConds).or(contentConds).or(typeConds));

            } else if (sopt.equals("title")) {
                andBuilder.and(titleConds);
            } else if (sopt.equals("content")) {
                andBuilder.and(contentConds);
            } else if (sopt.equals("type")) {
                andBuilder.and(typeConds);
            }
        }

        // 게시 타입이 immediately인 것만 출력
        // andBuilder.and(notice.postingType.eq("immediately"));

        Pageable pageable;
        if (StringUtils.hasText(search.getOnTop()) && search.getOnTop().equals("O")) {
            // onTop이 'O'인 경우, 가장 최근 게시물을 제일 위에 출력하도록 설정
            pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        } else {
            // onTop이 'O'가 아닌 경우, 일반적인 등록일 기준으로 정렬
            pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "onTop", "createdAt"));
        }
        Page<Notice_> data = boardRepository.findAll(andBuilder, pageable);
        int total = (int) boardRepository.count(andBuilder);

        data.getContent().forEach(this::addInfo);

        // 결과 반환

        Pagination pagination = new Pagination(page, total, 10, limit, request);

        return new ListData<>(data.getContent(), pagination);
    }

    /* 노출 여부를 기준으로 게시물 조회 E */



    /* 게시글 번호로 상세 페이지 조회 S */
    public Optional<Notice_> findByNum(Long num) {
        return boardRepository.findById(num);
    }

    /* 게시글 번호로 상세 페이지 조회 E */

    /* 게시글 삭제 S */

    @Transactional
    public void deleteById(Long num) {

        noticeCommentRepository.deleteById(num);

        boardRepository.deleteById(num);
    }

    /* 게시글 삭제 E */

    public Notice_ getById(Long num) {

        Notice_ data = boardRepository.getById(num);
        addInfo(data);

        return data;
    }

    public RequestBoardPosts getForm(Long num) {
        Notice_ data = getById(num);
        RequestBoardPosts form = new ModelMapper().map(data, RequestBoardPosts.class);
        form.setNum(data.getNum());
        return form;
    }



    private void addInfo(Notice_ data) {
        List<FileInfo> items = fileInfoService.getListDone(data.getGid());
        //if(items != null && !items.isEmpty()) data.setThumbnail(items.get(0));

    }


    public List<Notice_> getTop5Notice() {

        return boardRepository.findTop5ByScheduledDateIsNullAndTypeOrderByCreatedAtDesc("notice");
    }

    /* 선택한 게시글 삭제 API S */

    public int deleteNotices(String[] nums) {
        try {
            for (String num : nums) {
                Notice_ notice = boardRepository.findById(Long.valueOf(num)).orElseThrow();
                boardRepository.delete(notice);
            }

        } catch (Exception e) {
            return 0;
        }

        return 1;
    }

    /* 선택한 게시글 삭제 API E */
}
