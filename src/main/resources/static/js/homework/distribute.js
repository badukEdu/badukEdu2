window.addEventListener("DOMContentLoaded", function() {

    /* Member 전체 선택 기능 S */
    const checkAllMember = document.getElementById("checkAllMember");
    checkAllMember.addEventListener("change", function() {
        const checkMembers = document.getElementsByName("checkMember");
        const checkedMembers = [];
        for (const chk of checkMembers) {
            if (chk.disabled == true){
                continue;
            }
            chk.checked = checkAllMember.checked;
            const num = chk.value;
            checkedMembers.push(num);
        }
    });
    /* Member 전체 선택 기능 E */

    /* Homework 전체 선택 기능 S */
    const checkAllHomework = document.getElementById("checkAllHomework");
    checkAllHomework.addEventListener("change", function() {
        const checkHomeworks = document.getElementsByName("checkHomework");
        const checkedHomeworks = [];
        for (const chk of checkHomeworks) {
            if (chk.disabled == true){
                continue;
            }
            chk.checked = checkAllHomework.checked;
            const num = chk.value;
            checkedHomeworks.push(num);
        }
        checkLevels();
    });
    /* Homework 전체 선택 기능 E */

    /* 배포 버튼 클릭시 S */
    const distributeBtn = document.getElementById("submitBtn");
    distributeBtn.addEventListener("click", function(e) {
        e.preventDefault();
        // 숙제 선택 항목 체크
        const checkedHomeworks = document.querySelectorAll("input[name='checkHomework']:checked")

        if (checkedHomeworks.length == 0) {
            alert("학습자에게 전송할 숙제를 선택하세요.");
            return;
        }

        // 학습자 선택 항목 체크
        const checkedMembers = document.querySelectorAll("input[name='checkMember']:checked")

        if (checkedMembers.length == 0) {
            alert("숙제를 전송할 학생을 선택하세요.");
            return;
        }
        const distributeFrm = document.getElementById('distributeFrm');
        distributeFrm.submit();
    });
    /* 배포 버튼 클릭시 E */
});

function updateMember() {
    const selectedOption = document.getElementById("options").value;

    let selectedIndex = document.getElementById("options").getAttribute("data-index");

    // 선택 옵션이 없으면
    if (selectedOption == "") {
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            // 서버로부터 응답을 받으면 테이블 업데이트

            document.getElementById("group_member").innerHTML = this.responseText;
        }
    };

    // 선택한 값에 따라 새로운 내용을 생성합니다.
    // 서버로 요청 보내기
    xhr.open("GET", "/get_table_data/member?option=" + selectedOption, true);
    xhr.send();
}

function updateHomework() {
    const selectedOption = document.getElementById("options").value;

    let selectedIndex = document.getElementById("options").getAttribute("data-index");

    // 선택 옵션이 없으면
    if (selectedOption == "") {
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            // 서버로부터 응답을 받으면 테이블 업데이트

            document.getElementById("group_homework").innerHTML = this.responseText;
            updateMember();
            checkDeadLine();
        }
    };

    // 선택한 값에 따라 새로운 내용을 생성합니다.
    // 서버로 요청 보내기
    xhr.open("GET", "/get_table_data/homework?option=" + selectedOption, true);
    xhr.send();
}


/* 마감일(deadLine)이 오늘 이전일 때 disabled */
function checkDeadLine() {
    const checkHomeworks = document.getElementsByName("checkHomework");

    for (const checkHomework of checkHomeworks) {

        const deadLine = checkHomework.getAttribute('data-deadLine');

        if (new Date(deadLine) < new Date()) {
            checkHomework.disabled = true;
        }
    }
}
/* 마감일(deadLine)이 오늘 이전일 때 disabled */

/* 숙제 체크될 때마다 숙제벨과 멤버들의 학습레벨과 비교하여 학습자 disabled 처리 */
function checkLevels() {
    const checkHomeworks = document.querySelectorAll('input[name="checkHomework"]:checked');
    const checkMembers = document.getElementsByName("checkMember");

    checkMembers.forEach(function(checkMember) {
        let shouldBeDisabled = false;
        checkHomeworks.forEach(function(checkHomework) {
            const homeworkLevel = parseInt(checkHomework.getAttribute('data-homeworkLevel'));
            const studyLevel = parseInt(checkMember.getAttribute('data-studyLevel'));
            if (homeworkLevel <= studyLevel) {
                shouldBeDisabled = true;
                return; // 중단하여 다음 체크레벨를 검사하지 않음
            }
        });
        checkMember.disabled = shouldBeDisabled;
    });
}
