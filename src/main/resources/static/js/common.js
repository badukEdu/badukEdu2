var commonLib = commonLib || {};

/**
* ajax 요청,응답 편의 함수
*
* @param method : 요청 방식 - GET, POST, PUT, PATCH, DELETE ...
* @param url : 요청 URL
* @param params : 요청 데이터(POST, PUT, PATCH ... )
* @param responseType :  json : javascript 객체로 변환
*/
commonLib.ajaxLoad = function(method, url, params, responseType, headers) {
    method = method || "GET";
    params = params || null;

    const token = document.querySelector("meta[name='_csrf']").content;
    const tokenHeader = document.querySelector("meta[name='_csrf_header']").content;

    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();

        xhr.open(method, url);
        xhr.setRequestHeader(tokenHeader, token);

        if (headers) {
            for (const key in headers) {
                xhr.setRequestHeader(key, headers[key]);
            }
        }

       xhr.send(params); // 요청 body에 실릴 데이터 키=값&키=값& .... FormData 객체 (POST, PATCH, PUT)


        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                const resData = (xhr.responseText.trim() && responseType && responseType.toLowerCase() == 'json') ? JSON.parse(xhr.responseText) : xhr.responseText;

                if (xhr.status == 200 || xhr.status == 201) {

                    resolve(resData); // 성공시 응답 데이터
                } else {
                    reject(resData);
                }
            }
        };

        xhr.onabort = function(err) {
            reject(err); // 중단 시
        };

        xhr.onerror = function(err) {
            reject(err); // 요청 또는 응답시 오류 발생
        };
    });
};

/**
* 위지윅 에디터 로드
*
*/
commonLib.loadEditor = function(id, height) {
    if (!id) {
        return;
    }

    height = height || 450;

    // ClassicEditor
    return ClassicEditor.create(document.getElementById(id), {
        height
    });
}

/**
* 이메일 인증 메일 보내기
*
* @param email : 인증할 이메일
*/
commonLib.sendEmailVerify = function(email) {
    const { ajaxLoad } = commonLib;

    const url = `/api/email/verify?email=${email}`;

    ajaxLoad("GET", url, null, "json")
        .then(data => {
            if (typeof callbackEmailVerify == 'function') { // 이메일 승인 코드 메일 전송 완료 후 처리 콜백
                callbackEmailVerify(data);
            }
        })
        .catch(err => console.error(err));
};

/**
* 인증 메일 코드 검증 처리
*
*/
commonLib.sendEmailVerifyCheck = function(authNum) {
    const { ajaxLoad } = commonLib;
    const url = `/api/email/auth_check?authNum=${authNum}`;

    ajaxLoad("GET", url, null, "json")
        .then(data => {
            if (typeof callbackEmailVerifyCheck == 'function') { // 인증 메일 코드 검증 요청 완료 후 처리 콜백
                callbackEmailVerifyCheck(data);
            }
        })
        .catch(err => console.error(err));
};

/* 최근 본 게시글 기록 저장 S */
if (location.pathname.indexOf("/board/view/") != -1) {
    const v = /\/board\/view\/(\d*)/.exec(location.pathname);
    const seq = Number(v[1]);
    const key = "viewPosts";
    let viewPosts = localStorage.getItem(key);
    viewPosts = JSON.parse(viewPosts) || [];

    // 이미 열람 기록이 있으면 기존 기록 삭제
    const index = viewPosts.findIndex(p => p.seq == seq);
    if (index != -1) viewPosts.splice(index, 1);

    viewPosts.push({seq, timestamp: Date.now()});

    viewPosts.sort((a, b) => b.timestamp - a.timestamp); // 내림차순 정렬

    localStorage.setItem(key, JSON.stringify(viewPosts));
}
/* 최근 본 게시글 기록 저장 E */

window.addEventListener("DOMContentLoaded", function() {
    /* 양식 공통 처리 S */
    const formActions = document.getElementsByClassName("form_action");
    for (const el of formActions) {
        el.addEventListener("click", function() {
            const dataset = this.dataset;
            const mode = dataset.mode || "edit";
            const formName = dataset.formName;
            if (!formName || !document[formName]) {
                return;
            }

            const formEl = document[formName];
           formEl._method.value = mode == 'delete' ? 'DELETE' : 'PATCH';

           const modeTitle = mode == 'delete' ? '삭제' : '수정';

           const chks = formEl.querySelectorAll("input[name='chk']:checked");

           if (chks.length == 0) { // 체크가 안된 경우
                alert(`${modeTitle}할 항목을 선택하세요.`);
                return;
           }

            if (confirm(`정말 ${modeTitle} 하겠습니까?`)) {
                formEl.submit();
            }

        });
    }
    /* 양식 공통 처리 E */

    /* 전체 선택 토글 기능 S */
    const checkAlls = document.getElementsByClassName("checkall");
    for (const el of checkAlls) {
        el.addEventListener("click", function() {
            const targetName = this.dataset.targetName;
            const chks = document.getElementsByName(targetName);
            for (const el of chks) {
                el.checked = this.checked;
            }
        });
    }
    /* 전체 선택 토글 기능 E */
});