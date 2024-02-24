window.addEventListener("DOMContentLoaded", function() {
    /* Member 전체 선택 기능 S */
    const checkAllMember = document.getElementById("checkAllMember");
    checkAllMember.addEventListener("change", function() {
        const checkMembers = document.getElementsByName("checkMember");
        const checkedMembers = [];
        for (const chk of checkMembers) {
            chk.checked = checkAllMember.checked;
            const num = chk.value;
//            console.log(num);
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
            chk.checked = checkAllHomework.checked;
            const num = chk.value;
//            console.log(num);
            checkedHomeworks.push(num);
        }
    });
    /* Homework 전체 선택 기능 E */

    /* 배포 버튼 클릭시 S */
    const distributeBtn = document.getElementById("submitBtn");
    distributeBtn.addEventListener("click", function(e) {
        e.preventDefault();
        // 숙제 선택 항목 체크
        const checkedHomeworks = document.querySelectorAll("input[name='checkHomework']:checked")
        console.log(checkedHomeworks.length);
        if (checkedHomeworks.length == 0) {
            alert("학습자에게 전송할 숙제를 선택하세요.");
            return;
        }

        // 학습자 선택 항목 체크
        const checkedMembers = document.querySelectorAll("input[name='checkMember']:checked")
        console.log(checkedMembers.length);
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
    console.log("Selected Option Value: " + selectedOption);
    console.log("Selected Option Index: " + selectedIndex);

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
    console.log("Selected Option Value: " + selectedOption);
    console.log("Selected Option Index: " + selectedIndex);

    // 선택 옵션이 없으면
    if (selectedOption == "") {
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            // 서버로부터 응답을 받으면 테이블 업데이트

            document.getElementById("group_homework").innerHTML = this.responseText;
        }
    };

    // 선택한 값에 따라 새로운 내용을 생성합니다.
    // 서버로 요청 보내기
    xhr.open("GET", "/get_table_data/homework?option=" + selectedOption, true);
    xhr.send();
}