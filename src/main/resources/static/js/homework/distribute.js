window.addEventListener("DOMContentLoaded", function() {
/*
    const checkHomeworks = document.getElementsByName("checkHomework");
    for (const checkHomework of checkHomeworks) {
        checkHomework.addEventListener("change", function() {
            console.log(checkHomework);
            // 변화한 숙제의 학습레벨을 아래 출력된 멤버들 전부와 비교하여 멤버의 레벨이 높다면 멤버 check disabled 처리
            const homeworkLevel = checkHomework.getAttribute('data-homeworkLevel');
            const checkMembers = document.getElementsByName("checkMember");
            for (const checkMember of checkMembers) {
                const studyLevel = checkMember.getAttribute("data-studyLevel");
                if (studyLevel < homeworkLevel) {
                    checkMember.disabled = true;
                }
            }
        });
    }
*/
    const checkHomeworks = document.getElementsByName("checkHomework");
    const checkMembers = document.getElementsByName("checkMember");

    checkHomeworks.forEach(function(checkHomework) {
        checkHomework.addEventListener('change', function () {
            if (this.checked) {
                const homeworkLevel = parseInt(this.getAttribute('data-homeworkLevel'));
                checkMembers.forEach(function(checkMember) {
                    const studyLevel = parseInt(checkMember.getAttribute('data-studyLevel'));
                    if (homeworkLevel < studyLevel) {
                        checkMember.disabled = true;
                    } else {
                        checkMember.disabled = false;
                    }
                });
            } else {
                checkMembers.forEach(function(checkMember) {
                    checkMembers.disabled = true;
                });
            }
        });
    });


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
    });
    /* Homework 전체 선택 기능 E */

    /* 배포 버튼 클릭시 S */
    const distributeBtn = document.getElementById("submitBtn");
    distributeBtn.addEventListener("click", function(e) {
        e.preventDefault();
        // 숙제 선택 항목 체크
        const checkedHomeworks = document.querySelectorAll("input[name='checkHomework']:checked")
//        console.log(checkedHomeworks.length);
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
//    console.log("Selected Option Value: " + selectedOption);
//    console.log("Selected Option Index: " + selectedIndex);

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
//    console.log("Selected Option Value: " + selectedOption);
//    console.log("Selected Option Index: " + selectedIndex);

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
//    console.log(checkHomeworks);
    for (const checkHomework of checkHomeworks) {
//        console.log(checkHomework);
        const deadLine = checkHomework.getAttribute('data-deadLine');

        if (new Date(deadLine) < new Date()) {
            checkHomework.disabled = true;
        }
    }
}
/* 마감일(deadLine)이 오늘 이전일 때 disabled */


function checkLevels() {
    const checkHomeworks = document.getElementsByName("checkHomework");
    const checkMembers = document.getElementsByName("checkMember");

    checkHomeworks.forEach(function(checkHomework) {
        checkHomework.addEventListener('change', function () {
            if (this.checked) {
                const homeworkLevel = parseInt(this.getAttribute('data-homeworkLevel'));
                checkMembers.forEach(function(checkMember) {
                    const studyLevel = parseInt(checkMember.getAttribute('data-studyLevel'));
                    if (homeworkLevel <= studyLevel) {
                        checkMember.disabled = true;
                    } else {
                        checkMember.disabled = false;
                    }
                });
            } else {
                checkMembers.forEach(function(checkMember) {
                    checkMember.disabled = false;
                });
            }
        });
    });
}
/*
function checkLevels() {
    const checkHomeworks = document.querySelectorAll('input[name="checkHomework"]:checked');
    const checkMembers = document.getElementsByName("checkMember");

    checkMembers.forEach(function(checkMember) {
        let shouldBeDisabled = false;
        checkHomeworks.forEach(function(checkHomework) {
            const homeworkLevel = parseInt(checkHomework.getAttribute('data-homeworkLevel'));
            const studyLevel = parseInt(checkMember.getAttribute('data-studyLevel'));
            if (homeworkLevel > studyLevel) {
                shouldBeDisabled = true;
                return; // 중단하여 다음 체크홈워크를 검사하지 않음
            }
        });
        checkMember.disabled = shouldBeDisabled;
    });
}*/