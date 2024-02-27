function updateTrainingData(el) {
    var num = el.getAttribute('value');

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            // 서버로부터 응답을 받으면 테이블 업데이트

            document.getElementById("homework_trainingData").innerHTML = this.responseText;
        }
    };

    // 선택한 값에 따라 새로운 내용을 생성합니다.
    // 서버로 요청 보내기
    xhr.open("GET", "/get_table_data/trainingData?option=" + num, true);
    xhr.send();
}

function answerPopup(num) {
    const url = "/teacher/homework/answerPopup/" + num; // 컨트롤러로 연결
    const { popup } = commonLib;

    popup.open(url, 700, 700);
}