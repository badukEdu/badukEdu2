package org.choongang.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("BoardViewService")
@RequiredArgsConstructor
public class BoardService {




    //    public ListData<BoardData> getList(BoardDataSearch search) {
//
//        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
//        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);
//        String sopt = search.getSopt();
//        String skey = search.getSkey();
//        sopt = StringUtils.hasText(sopt) ? sopt : "ALL";
//
//        QBoardData boardData = QBoardData.boardData;
//
//        /* 검색 조건 처리 S */
//        BooleanBuilder andBuilder = new BooleanBuilder();
//        if (StringUtils.hasText(skey)) {
//            skey = skey.trim();
//            BooleanExpression nameConds = boardData.name.contains(skey);
//            BooleanExpression contentConds = boardData.content.contains(skey);
//            if (sopt.equals("ALL")) {
//                BooleanBuilder orBuilder = new BooleanBuilder();
//                andBuilder.and(orBuilder.or(nameConds).or(contentConds));
//
//            } else if (sopt.equals("name")) {
//                andBuilder.and(nameConds);
//            } else if (sopt.equals("content")) {
//                andBuilder.and(contentConds);
//            }
//        }
//
//        /* 검색 조건 처리 E */
//
//        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
//
//        Page<EduData> data = BoardDataRepository.findAll(andBuilder, pageable);
//        int total = (int)BoardDataRepository.count(andBuilder);
//
//        data.getContent().forEach(this::addInfo);
//
//        Pagination pagination = new Pagination(page, total, 10, limit ,request);
//
//        return new ListData<>(data.getContent(), pagination);
//    }
}
