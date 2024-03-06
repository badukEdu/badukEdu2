# GO(바둑 교육 사이트) 

# 📢프로젝트 소개 

<details>
  <summary>프로젝트 소개</summary>
  
  ### 🔎개요
  - 바둑에 대해 단계별로 학습 할 수 있는 사이트를 만들기 위함

  ### 🔎개발 목적
  - 바둑을 모르는 사람도 최도한의 도움과 재미를 통해 바둑을 배우게 하는 것
  - 국내뿐 아니라 해외에서도 온라인을 통해 체계적인 바둑 교육이 가능케 하기 위함
  - 단기적으로 학원 학생을 대상으로 기원이 서비스하는 각종 교육 프로그램의 사용을 확산시키고, 바둑에 관심이 있는 일반인들이 많이 찾게 하기 위함

  ### 🔎개발기간
  - 2024.2.5 ~ 2024.3.5

  ### 🔎개발 업무
  - 학원의 교육자가 **게임콘텐츠 구매**를 해서 본인이 수업 할 **학습 그룹을 개설**하고 학생들은 교육자가 개설한 **그룹을 선택하여 가입 신청**을 한다
    교육자는 자신이 만든 그룹에 가입 신청한 학생이 있으면 **가입 승인**을 하여 학습 그룹을 완성
    
  - 이후 교육자가 숙제를 만들어 **학생들에게 배포하면** 학생은 본인에게 부여된 숙제가 있는지를 확인하고 학습한 내용을 **기술하여 등록하고**, 교육자는 학생이 제출한 숙제 수행 내용을 **평가한다.**
  - 게임콘텐츠를 구매하면 유료 학습자료(튜툐리얼, 교육 영상)를 시청 할 수 있으며, 운영관리자는 본 업무가 이루어지도록 게임콘텐츠/학습자료 및 게시판 내용을 **수시로 등록한다.**

  ### 🔎기대효과
  - **전문가와의 상호작용:** 프로 바둑 선수나 전문가들과의 온라인 강의, 실시간 질문과 답변 등을 통해 학습자들은 전문가들의 경험과 지식을 배울 수 있습니다.

  - **평가와 피드백:** 학습자의 실력을 정량적으로 평가하고 개선 방향을 제시할 수 있습니다. 학습자는 자신의 성장을 실시간으로 확인하고 피드백을 받아 개선할 수 있습니다.
  
</details>

# 👪💻개발자 소개 & 역할

- **박범수(조장)** : 숙제생성, 숙제배포, 숙제 학습진도 조회
- **고원일** : 회원가입, 로그인, 로그아웃, 회원정보수정, 매출조회
- **이기흥** : 게임콘텐츠CRUD, 교육자료CRUD, 구독신청, 결제, 내 게임콘텐츠 조회, 상품소개
- **전표찬** : 학습그룹 등록/수정/삭제/조회, 학습그룹 가입 승인
- **박호범** : 공지, FaQ, QnA
- **장하영** : 헤더, 푸터, 메인, 사이트소개, 이용가이드

# ⚙ERD&개발환경

<details>
  <summary>ERD&개발환경</summary>

![erd](https://github.com/badukEdu/badukEdu2/assets/148045978/c54bf55f-f03c-4149-9129-8d9ff94e0b13)

## :wrench:DB : ![dbeaver](https://img.shields.io/badge/dbeaver-F80000?style=for-the-badge&logo=dbeaver&logoColor=white)   , ![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)   
## :wrench:백엔드 : ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white), ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white),<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">, <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">, ![json](https://img.shields.io/badge/json-%23ED8B00.svg?style=for-the-badge&logo=json&logoColor=white)
## :wrench:프론트엔드 : ![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white), ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white), ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E), ![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white), <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white">
## :wrench:웹서버 : ![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
## :wrench:협업 : ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white),![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white), ![sourcetree](https://img.shields.io/badge/sourcetree-%23121011.svg?style=for-the-badge&logo=sourcetree&logoColor=bule)
## :wrench:Tool : ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white), ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
## :wrench:CI/CD : ![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white), ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white), ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
## :wrench:기타 : ![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white) ![Google Chrome](https://img.shields.io/badge/Google%20Chrome-4285F4?style=for-the-badge&logo=GoogleChrome&logoColor=white)


</details>

# 📓개발목표&상세기능

<details>
  <summary>개발목표&상세기능</summary>

  ### 🔎개발목표

  회원
  회원가입, 로그인, 아이디 찾기, 비밀번호 찾기, 회원탈퇴, 회원정보수정
  
  이용안내
  사이트 소개, 이용 가이드, 상품 소개, Notice & FaQ, Q&A
  
  구독서비스
  게임콘텐츠 구독신청, 결제, 내 게임콘텐츠 조회
  
  학습서비스
  학습그룹 가입 신청, 가입승인 대기 목록, 숙제 목록
  
  교육자마당
  학습그룹 등록/조회, 학습 그룹 상세, 학습그룹 가입 승인,
  숙제 생성/조회, 숙제 배포, 숙제 학습 진도 조회
  
  운영마당
  게임콘텐츠 등록/조회, 교육자료 등록/조회, 공지등록, 매출조회, 회원관리

  ### 🔎상세기능
  
  
</details>



# ✏PPT 
https://www.canva.com/design/DAF-KUS-Wyo/OOqtIS-pJINXR2BklAe5dA/view?utm_content=DAF-KUS-Wyo&utm_campaign=designshare&utm_medium=link&utm_source=editor


