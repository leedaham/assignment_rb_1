

# 2023 Coding Test

## 요구사항

### 1. 파일 업로드 및 조회 API

- **환경**: Java + Spring Boot
- **형태**: RESTful API
- **기능**:
    - POST Multipart로 이미지 파일(jpg, png 등)을 서버 로컬 파일 스토리지에 업로드
    - 파일명을 기반으로 이미지를 조회하여 `<img src="...">` 태그로 브라우저에서 표시 가능하게 구현
- **구조**: Controller, Service, Dto, VO 체계

---

### 2. 문자열 글자 수 세기

- HashMap을 이용하여 문자열(String)의 글자 수를 카운트하는 Java Method 작성

---

### 3. JSON 입력값을 Bulk Insert

- **입력**: JSON으로 전달받은 데이터
- **요구사항**:
    - 500건마다 bulk로 Insert 처리
    - DB: MySQL
    - ORM: MyBatis 또는 JPA 중 선택 가능

---

### 4. 동시접속 예약 처리 API 설계

#### 예약 프로세스

1. 사용 가능한 티켓 보유 확인 (없으면 에러)
2. Queue에 적재
3. Queue에서 읽어 예약 처리 (트랜잭션 보장)
4. 티켓 사용 수 차감
5. 그룹수업 좌석수 차감
6. 예약 내역 기록

- **사용 Queue**: AWS SQS 또는 RabbitMQ

---

### 5. 데이터 모델 (클래스 다이어그램 제출 대상)

#### 예약 티켓 테이블 (ticket)

| 필드명       | 타입    | 설명                         |
|--------------|---------|------------------------------|
| seq_ticket   | bigint  | 순번 예약번호                |
| reservation_unit | int(3) | 1~999, 예약가능 좌석 수     |
| ower_id      | bigint  | 소유자 ID                    |
| used_number  | int(3)  | 예약에 사용된 좌석 수        |

#### 그룹수업 내역 (group_lesson)

| 필드명             | 타입    | 설명                 |
|--------------------|---------|----------------------|
| seq_group_lesson   | bigint  | 그룹수업 순번        |
| total_seat_number  | int     | 좌석번호 (1~99999)   |
| occupied_number    | bigint  | 예약 완료된 좌석 수  |

#### 예약 내역 (reservation)

| 필드명       | 타입      | 설명                       |
|--------------|-----------|----------------------------|
| seq_schedule | bigint    | 예약 순번                  |
| owner_id     | bigint    | 예약자 고유번호            |
| seat_number  | seat_number | 예약 좌석 번호           |
| reg_dtm      | datetime  | 예약 일시                  |
| seq_ticket   | bigint    | 예약 시 사용한 티켓 번호   |

---

### 6. 외부 REST API 호출 - SMS 전송

- **Endpoint**: `https://{hostname}/api/sendSMS`
- **Method**: POST
- **Content-Type**: JSON
- **Authorization**: Bearer Token (Authorization Header 사용)

#### Request 예시

```json
{
  "title": "SMS Title 샘플",
  "content": "안녕하세요! SMS 샘플 테스트입니다.",
  "targetPhoneNumber": "+82-10-1234-1234"
}
```

#### Response

- **HTTP Status**: 200 OK
- **Body**:
```json
{
  "message": "Successfully sent"
}
```

#### 예외 상태

- HTTP Status: 403, 404, 500

#### 구현 요구

- RestTemplate, WebClient를 각각 사용한 구현
- 두 방식의 차이점 설명 포함

---

# 과제 풀이

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

#### HashMap 이용한 String 글자 수 카운트하기
##### 관련 코드
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/hashmap">me.hamtom.redblue.assignment.hashmap</a>
- Class:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/hashmap/HashMapCountString.java">HashMapCountString</a>: HashMap을 이용하여 String의 각 글자 수를 카운트하는 Method 구현

#### 입력 데이터 500개씩 Bulk Insert 하기
> 전달 받을 정보: 사용자 기본 정보 (username)  
> 저장 테이블: Users (Entity: User)
##### 관련 코드
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/bulk">me.hamtom.redblue.assignment.bulk</a>
- Controller:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/bulk/UserController.java">UserController</a>: 사용자 정보 저장 Controller 
- Service:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/bulk/UserService.java">UserService</a>: 사용자 정보 저장 Service
- DTO:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/bulk/UserController.java#L35">BulkInsertReq</a>: Client에게 받는 사용자 정보 객체(UserInfoDto) 리스트
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/bulk/dto/UserInfoDto.java">UserInfoDto</a>: 사용자 정보 객체, UserService에서 User(Entity)로 변환
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/bulk/dto/BulkInsertResultDto.java">BulkInsertResultDto</a>: 파일 업로드 응답 객체
- Repository:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/bulk/repository/UserBatchRepository.java">UserBatchRepository</a>: User 엔티티 Bulk insert Repository
- Entity:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/bulk/entity/User.java">User</a>: User 엔티티

##### 공통 코드
- 아래 [공통](#공통) 부분에서 확인 가능


#### RestTemplate, WebClient API 호출하기
##### 관련 코드
- package:
  - <a href="https://github.com/leedaham/assignment_rb_1/tree/master/src/main/java/me/hamtom/redblue/assignment/callapi">me.hamtom.redblue.assignment.callapi</a>
- Service:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java">SmsService</a>: SMS 전송 Service
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java#L22">Request 정보 정의</a>: URL, URI, HttpMethod, Header, Body
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java#L45">sendSmsWithRestTemplateCall()</a>: RestTemplate Method 사용
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/SmsService.java#L83">sendSmsWithWebClientCall()</a>: WebClient Method 사용
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/CallApiService.java">CallApiService</a>: API Call Service
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/CallApiService.java#L22">callWithRestTemplate()</a>: RestTemplate Method
    - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/CallApiService.java#L47">callWithWebClient()</a>: WebClient Method
- DTO:
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/dto/RequestSMS.java">RequestSMS</a>: API 요청 객체
  - <a href="https://github.com/leedaham/assignment_rb_1/blob/master/src/main/java/me/hamtom/redblue/assignment/callapi/dto/ResponseSMS.java">ResponseSMS</a>: API 응답 객체

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

