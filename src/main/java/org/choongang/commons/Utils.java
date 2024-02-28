package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.basic.controllers.BasicConfig;
import org.choongang.basic.service.ConfigInfoService;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session;
    private final FileInfoService fileInfoService;
    private final ConfigInfoService infoService;

    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }



    public static String getMessage(String code, String type) {
        try {
            type = StringUtils.hasText(type) ? type : "validations";

            ResourceBundle bundle = null;
            if (type.equals("commons")) {
                bundle = commonsBundle;
            } else if (type.equals("errors")) {
                bundle = errorsBundle;
            } else {
                bundle = validationsBundle;
            }

            return bundle.getString(code);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * \n 또는 \r\n -> <br>
     * @param str
     * @return
     */
    public String nl2br(String str) {
        str = Objects.requireNonNullElse(str, "");

        str = str.replaceAll("\\n", "<br>")
                .replaceAll("\\r", "");

        return str;
    }

    /**
     * 썸네일 이미지 사이즈 설정
     *
     * @return
     */
    public List<int[]> getThumbSize() {
        BasicConfig config = (BasicConfig)request.getAttribute("siteConfig");
        String thumbSize = config.getThumbSize(); // \r\n
        String[] thumbsSize = thumbSize.split("\\n");

        List<int[]> data = Arrays.stream(thumbsSize)
                .filter(StringUtils::hasText)
                .map(s -> s.replaceAll("\\s+", ""))
                .map(this::toConvert).toList();


        return data;
    }

    private int[] toConvert(String size) {
        size = size.trim();
        int[] data = Arrays.stream(size.replaceAll("\\r", "").toUpperCase().split("X"))
                .mapToInt(Integer::parseInt).toArray();

        return data;
    }

    public String printThumb(long seq, int width, int height, String className) {
        try {
            String[] data = fileInfoService.getThumb(seq, width, height);
            if (data != null) {
                String cls = StringUtils.hasText(className) ? " class='" + className + "'" : "";
                String image = String.format("<img src='%s'%s>", data[1], cls);
                return image;
            }
        } catch (Exception e) {}

        return "";
    }

    public String printThumb(long seq, int width, int height) {
        return printThumb(seq, width, height, null);
    }

    public String backgroundStyle(FileInfo file, int width, int height) {

        try {

            String[] data = fileInfoService.getThumb(file.getSeq(), width, height);
            String imageUrl = data[1];

            String style = String.format("background:url('%s') no-repeat center center; background-size:cover;", imageUrl);

            return style;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 0이하 정수 인 경우 1이상 정수로 대체
     *
     * @param num
     * @param replace
     * @return
     */
    public static int onlyPositiveNumber(int num, int replace) {
        return num < 1 ? replace : num;
    }

    /**
     * 요청 데이터 단일 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 요청 데이터 복수개 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }


    /**
     * 비회원 UID(Unique ID)
     *          IP + 브라우저 정보
     *
     * @return
     */
    public int guestUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }

    /**
     * 삭제 버튼 클릭시 "정말 삭제하시겠습니까?" confirm 대화상자
     *
     * @return
     */
    public String confirmDelete() {
        String message = Utils.getMessage("Confirm.delete.message", "commons");

        return String.format("return confirm('%s');", message);
    }

    /**
     * 알파벳, 숫자, 특수문자 조합 랜덤 문자열 생성 (비밀번호 초기화 사용 외)
     * @param length
     * @return
     */
    public String randomChars(int length) {
        // 알파벳 생성
        Stream<String> alphas = IntStream.concat(IntStream.rangeClosed((int)'a', (int)'z'), IntStream.rangeClosed((int)'A', (int)'Z')).mapToObj(s -> String.valueOf((char)s));

        // 숫자 생성
        Stream<String> nums = IntStream.range(0, 10).mapToObj(String::valueOf);

        // 특수문자 생성
        Stream<String> specials = Stream.of("~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "-", "=", "[", "{", "}", "]", ";", ":");

        List<String> chars = Stream.concat(Stream.concat(alphas, nums), specials).collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(chars);

        return chars.stream().limit(length).collect(Collectors.joining());
    }


    public boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}
