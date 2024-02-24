window.addEventListener("DOMContentLoaded", function() {
    /* Member 전체 선택 기능 S */
    const checkAllMember = document.getElementById("checkAllMember");
    checkAllMember.addEventListener("change", function() {
        const checkMembers = document.getElementsByName("checkMember");
        const checkedMembers = [];
        for (const chk of checkedMembers) {
            chk.checked = checkAllMember.checked;
            const num = chk.value;
            console.log(num);
            checkedMembers.push(num);
        }
    });
    /* 전체 선택 기능 E */

    /* Homework 전체 선택 기능 S */
    const checkAllHomework = document.getElementById("checkAllHomework");
    checkAllHomework.addEventListener("change", function() {
        const checkMembers = document.getElementsByName("checkHomework");
        const checkedHomeworks = [];
        for (const chk of checkedHomeworks) {
            chk.checked = checkAllHomework.checked;
            const num = chk.value;
            console.log(num);
            checkedHomeworks.push(num);
        }
    });
    /* 전체 선택 기능 E */


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
    xhr.open("GET", "/get_table_data?option=" + selectedOption, true);
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

            document.getElementById("group_member").innerHTML = this.responseText;
        }
    };

    // 선택한 값에 따라 새로운 내용을 생성합니다.
    // 서버로 요청 보내기
    xhr.open("GET", "/get_table_data?option=" + selectedOption, true);
    xhr.send();
}