## 과제 1
#### MultipartFile 업로드 및 조회하기
##### 관련 코드
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/file">me.hamtom.redblue.assignment.file</a>
- Controller:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/file/UploadController.java">UploadController</a>: 파일 업로드 Controller
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/file/LookupController.java">LookupController</a>: 파일 조회 Controller
- Service:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/file/FileService.java">FileService</a>: 파일 업로드, 조회 Service
- DTO:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/file/UploadController.java#L63">UploadController.UploadRespData</a>: 파일 업로드 응답 객체
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/file/LookupController.java#L67">LookupController.LookupRespData</a>: 파일 조회 응답 객체

##### 공통 코드
- 아래 [공통](#공통) 부분에서 확인 가능    

## 과제 2
#### HashMap 이용한 String 글자 수 카운트하기
##### 관련 코드
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/hashmap">me.hamtom.redblue.assignment.hashmap</a>
- Class:
  - <a href="">HashMapCountString</a>: HashMap을 이용하여 String의 각 글자 수를 카운트하는 Method 구현
    
## 과제 3
#### 입력 데이터 500개씩 Bulk Insert 하기
> 전달 받을 정보: 사용자 기본 정보 (username)  
> 저장 테이블: Users (Entity: User)
##### 관련 코드
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/bulk">me.hamtom.redblue.assignment.bulk</a>
- Controller:
  - <a href="">UserController</a>: 사용자 정보 저장 Controller 
- Service:
  - <a href="">UserService</a>: 사용자 정보 저장 Service
- DTO:
  - <a href="">BulkInsertReq</a>: Client에게 받는 사용자 정보 객체(UserInfoDto) 리스트
  - <a href="">UserInfoDto</a>: 사용자 정보 객체, UserService에서 User(Entity)로 변환
  - <a href="">BulkInsertResultDto</a>: 파일 업로드 응답 객체
- Repository:
  - <a href="">UserRepository</a>: User Entity Repository
- Entity:
  - <a href="">User</a>: User Entity

##### 공통 코드
- 아래 [공통](#공통) 부분에서 확인 가능

## 과제 4
#### 클래스 다이어그램

## 과제 5
#### RestTemplate, WebClient API 호출하기
##### 관련 코드
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/callapi">me.hamtom.redblue.assignment.callapi</a>
- Service:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java">SmsService</a>: SMS 전송 Service
  - - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java#L19">Request 정보 정의</a>: URL, URI, HttpMethod, Header, Body
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java#L40">sendSmsWithRestTemplateCall()</a>: RestTemplate Method 사용
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java#L70">sendSmsWithWebClientCall()</a>: WebClient Method 사용
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/CallApiService.java">CallApiService</a>: API Call Service
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/CallApiService.java#L22">CallApiService</a>: RestTemplate Method
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/CallApiService.java#L47">CallApiService</a>: WebClient Method
- DTO:
  - <a href="">BulkInsertReq</a>: Client에게 받는 사용자 정보 객체(UserInfoDto) 리스트
  - <a href="">UserInfoDto</a>: 사용자 정보 객체, UserService에서 User(Entity)로 변환
  - <a href="">BulkInsertResultDto</a>: 파일 업로드 응답 객체
- Repository:
  - <a href="">UserRepository</a>: User Entity Repository
- Entity:
  - <a href="">User</a>: User Entity

##### 공통 코드
- 아래 [공통](#공통) 부분에서 확인 가능

### 공통
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/common">me.hamtom.redblue.assignment.common</a>
- response:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/common/response/SuccessResult.java">SuccessResult</a>: 성공 응답 객체
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/common/response/FailResult.java">FailResult</a>: 실패 응답 객체
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/common/response/ErrorResult.java">ErrorResult</a>: 에러 응답 객체
- exception:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/common/exception/PredictableRuntimeException.java">PredictableRuntimeException</a>: 로직 수행 중 예상할 수 있는 예외
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/common/ControllerExceptionHandler.java">ControllerExceptionHandler</a>: Exception 발생 시 응답 핸들링
- config:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/resources/config.yml">config.yml</a>: 파일 저장 경로 설정, SMS API URL 설정

