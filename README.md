# 🕹️ HangMan Game


- Git : https://github.com/bovob/bitcamp-project4
- Connect
  <br>

  |    IP     | Port |     비고      |
  |:---------:|:----:|:-----------:|
  | localhost | 8888 |    local    |
  | 127.0.0.1 | 8888 | LoopBack IP |

  <br>

## 1. 프로젝트 소개

### 프로젝트 명 : HangMan

### 프로젝트 개발 기간 : 2024-07-31 ~ 2024-08-05

### 소개

- HangMan 서버에 접속하여 클라이언트가 게임을 진행한다.
- Socket 을 이용한 서버 - 클라이언트 구성
- app-server의 ServerApp 실행을 통한 서버 실행
- app-client의 ClientApp 실행을 통한 클라이언트 실행
- Thread Pool을 통하여 각 클라이언트와 동시에 게임이 진행된다.
  <br>

## 2. 팀원 구성

<div align="center">

| **이재욱** |                                                **권기윤**                                                |                                                **김주연**                                                 |
| :--------: |:-----------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/66761864?v=4" height=150 width=150> <br/> [@bovob] | <img src="https://avatars.githubusercontent.com/u/147612945?v=4" height=150 width=150> <br/> [@Homez96] | <img src="https://avatars.githubusercontent.com/u/148393841?v=4" height=150 width=150> <br/> [@uripup] |

</div>
<br>

## 3. 개발 환경

<img src="https://img.shields.io/badge/Java-007396?style=flastic&logo=OpenJDK&logoColor=white"/>  <img src="https://img.shields.io/badge/IntelliJ-000000?style=flastic&logo=intellijidea&logoColor=white"/>
<br>

## 4. 역할분담

### 이재욱

* **Game** : 행맨 게임 구성
* **Server-Client** : 소켓 프로그래밍을 통한 서버-클라이언트 연결 구성

### 권기윤

- **UI** : 게임TUI 구성
- **Code** : 에러처리 수정

### 이선아

- **PM** : 아이디어 제출, 게임 구성방안
- **Code** : API 구성 및 리팩터링
  <br>

## 5. 프로젝트 구조

``````
📦bitcamp-project4
 ┣📦app-client
 ┃ ┣📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┗ 📂bitcamp
 ┃ ┃ ┃ ┗ 📂project4
 ┃ ┃ ┃ ┃ ┣ 📂command
 ┃ ┃ ┃ ┃ ┃ ┗ 📂quiz
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜QuizAddCommand.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜QuizDeleteCommand.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜QuizListCommand.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜QuizUpdateCommand.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜QuizViewCommand.java
 ┃ ┃ ┃ ┃ ┣ 📂listener
 ┃ ┃ ┃ ┃ ┃ ┗ 📜InitApplicationListener.java
 ┃ ┃ ┃ ┃ ┗ 📜ClientApp.java
 ┣📦app-common
 ┃ ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂bitcamp
 ┃ ┃ ┃ ┗ 📂project4
 ┃ ┃ ┃ ┃ ┣ 📂command
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Command.java
 ┃ ┃ ┃ ┃ ┣ 📂context
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ApplicationContext.java
 ┃ ┃ ┃ ┃ ┣ 📂listener
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ApplicationListener.java
 ┃ ┃ ┃ ┃ ┣ 📂menu
 ┃ ┃ ┃ ┃ ┃ ┣ 📜AbstractMenu.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Menu.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜MenuGroup.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜MenuItem.java
 ┃ ┃ ┃ ┃ ┣ 📂myapp
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂stub
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜QuizDaoStub.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜QuizDao.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📂vo
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Quiz.java
 ┃ ┃ ┃ ┃ ┣ 📂net
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ResponseStatus.java
 ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Prompt.java
 ┣📦app-server
 ┃ ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂bitcamp
 ┃ ┃ ┃ ┗ 📂project4
 ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┣ 📂skel
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜QuizDaoSkel.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ListQuizDao.java
 ┃ ┃ ┃ ┃ ┣ 📂listener
 ┃ ┃ ┃ ┃ ┃ ┗ 📜InitApplicationListener.java
 ┃ ┃ ┃ ┃ ┣ 📜Hangman.java
 ┃ ┃ ┃ ┃ ┗ 📜ServerApp.java
 ┃ ┣📜data.xlsx
``````
<br>

## 6. 기능

### 1. Server
- HangMan 게임을 시작하기 위한 Server
- ThreadPool 을 이용하여 여러명의 Client 가 이용할 수 있다.
- Client의 접속과 종료를 출력한다.
![server](https://github.com/user-attachments/assets/0b90977c-60fa-45a0-af3f-d047b70b5bac)



### 2. Client
- Server의 IP와 Port 를 받아 HangMan 게임에 접속한다.
- 게임시작을 통하여 게임이 진행된다.
![client_login](https://github.com/user-attachments/assets/74ba9ee6-24b0-4a94-8a9c-f11c0a3860dc)



### 3. HangMan Game
- Server에 저장되어 있는 data.xlsx 을 통하여 문제가 진행된다. 
- Topic을 통하여 단어를 추론한다.
  1) 중복 알파벳은 에러처리
  2) 시도 횟수는 글자수 +4
  3) 일정 횟수 이상 틀릴 시 Hint 제공
- 단어 추론에 성공할 시 승리
- 단어 추론에 실패할 시 패배
  
<br>

![client_success](https://github.com/user-attachments/assets/60215dc2-e455-4c05-9f72-b6fcb5ee46f8)
![client_fail](https://github.com/user-attachments/assets/2c5bc2c0-7707-4489-9e78-0ec841f02187)

<br>

## 7. 후기
Data Stream을 통하여 data.xlsx에 Sheet [quizs] 를 만들어 HangMAN Game에 사용할 데이터를 만들고 
Socket을 통해 Server-Client의 통신을 하여 Server에 구현되어 있는 Thread Pool을 통해 
각각 Client는 랜덤한 Game을 진행하는데 처음 데이터 sheet를 구현할 때 어떻게 데이터를 구성해둘지
Thread 구성 전 계속 동일한 퀴즈가 나오는 등 여러가지 문제, Server와 Client가 계속 Socket 연결이 끊어지는 등 
여러가지 문제들이 많았지만 다행히 발표 전 StateLess 방식으로 변경하여 해결하였습니다.
