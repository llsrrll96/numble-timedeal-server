<div align="center">
 <img src="https://user-images.githubusercontent.com/58140426/225539172-962d18c0-4b52-44bc-8a36-507de8693b89.png" width="600">
</div>

# 타임딜 서버 구축, 넘블챌린지
> 정해진 시간에 제한된 개수의 물건을 파는 쇼핑몰 기능인 타임딜 서버 구축

<br/>

## 1️⃣ 기술 스택
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=JAVA&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/></a>&nbsp;
<img src="https://img.shields.io/badge/Gradle-231F20?style=flat-square&logo=Gradle&logoColor=white"/></a><br/>

<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/></a><br/>

<img src="https://img.shields.io/badge/Centos-FCC624?style=flat-square&logo=Centos&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Naver Cloud-03C75A?style=flat-square&logo=Naver&logoColor=white">

<br>

## 2️⃣ [와이어프레임](https://www.figma.com/file/FBkZCZnPz998sVdtifR8Nk/%EB%84%98%EB%B8%94%EC%B1%8C%EB%A6%B0%EC%A7%80_%ED%83%80%EC%9E%84%EB%94%9C_%EA%B9%80%EC%A3%BC%ED%98%84?node-id=0%3A1&t=PK9FNx0bXXq8LG8X-1)
> 피그마를 사용하여 추가적인 화면을 디자인하고 추가하였습니다.
<img src="https://user-images.githubusercontent.com/58140426/225499104-c47b8a30-0fec-4f34-b160-f168f1b09119.jpg" width="720">

<br>

## 3️⃣ 아키텍처 | 지속적 통합/지속적 배포(CI/CD)
> Jenkins와 Naver Cloud Server를 사용해 CI/CD를 구현했습니다. 구조는 다음과 같습니다.
<img src="https://user-images.githubusercontent.com/58140426/225501229-b75395c6-be9e-4208-867c-1f01b72a35fe.jpg" width="720">

- 젠킨스 서버와 배포 서버를 하나씩 운영(인스턴스 2개)
- 젠킨스 서버에서 GitHub 코드를 pull 하여 코드를 가져오고 빌드시켜 DockerHub에 Push
- 배포 서버에서 젠킨스를 통해 원격으로 DockerHub의 jar 를 pull & run 하여 도커 컨테이너로 Spring Boot 를 실행

<br>

## 4️⃣ API 명세
[📑API 명세](https://savory-hollyhock-d1e.notion.site/API-0b8217f528da43ada908b1216557eeed)

<br>

## 5️⃣ ER-Diagram
> 핵심기능을 구현할 수 있도록 설계 
<img src="https://user-images.githubusercontent.com/58140426/225508842-24ebcddf-bf41-43ac-929f-d94eea92a718.png" width="480">

- users(유저), products(상품), category, timedeal(타임딜), purchase(구매) 총 5개 테이블로 구성
- 타임딜 상품은 일반상품과 별개 테이블로 관리하도록 함

<br>

## 6️⃣ 동시성처리
> 동시성 문제 : 변경되는 데이터에 의해 발생한다고 생각(변경되기 전의 데이터에 대한 접근과 변경된 후에 데이터에 대한 접근에 대한 데이터 정합성 문제)
>> Race condition을 해결하는 여러 방법이 있다.
synchronized, MySql Lock, 메시지 브로커(Redis pub/sub, kafka)

<br>

<b>JPA의 낙관적 락과 비관적 락을 통해 엔티티에 대한 동시성 제어 </b>
> Pessimistic Lock && Optimistic Lock
>> 여러 서버에서 동시에 같은 레코드를 업데이트하려고 할 때 발생하는 동시성 문제를 해결하는 방법
>> Pessimistic Lock: 데이터베이스의 락을 사용하여 동시성 
```java
@Lock(value = LockModeType.PESSIMISTIC_WRITE)
@Query("select t from Timedeal t where t.timedealId = :timedealId")
Timedeal findByIdWithPessimisticLock(@Param("timedealId") Long timedealId);
```

<br>

- synchronized는 서버가 여러 대 일때 동시성 처리가 불가하기 때문에 제외
- 현재 상황에서 가장 적합한 해결책을 제시하고 실행하는 것이 좋다고 생각했기 때문에 <br/>
  Redis pub/sub을 사용하여 여러 서버가 있다고 가정하여 처리를 하는 것도 좋다고 생각하지만 외부 시스템을 사용하지 않음

<br>

## 7️⃣ 성능 측정 및 개선내용

### 성능 측정
#### 서버 상태를 모니터링할 수 있는 APM 툴인 `pinpoint` 도입
<img src="https://user-images.githubusercontent.com/58140426/225526216-18e2ed78-8a58-4e20-9413-ff557b23c314.png" width="100">

#### nGrinder 테스트
<img src="https://user-images.githubusercontent.com/58140426/225541355-951dbc88-c2f5-44c9-b2ec-d10ec4a801e3.png" width="100">

- 1만개의 재고가 있다고 가정
- Pessimistic Lock을 걸어 동시성 처리

<img src="https://user-images.githubusercontent.com/58140426/225526851-e18c008f-c22d-4b2c-99a0-6587552558bf.png" width="720">
threads-max = 100 으로 설정 <br>
TPS : 127.4

<span style="color:red">Error 도 발생</span>

### 개선내용

핀포인트 확인
![image](https://user-images.githubusercontent.com/58140426/225526997-2ba6ea06-8292-474f-a4bc-f87d1a4c8ae6.png)
실패한 부분이 있었고 추적결과 약 30초간 기다렸다가 실패한 것으로 보인다.


#### 커넥션 에러 줄이기
```
    hikari:
      connection-timeout: 300000

server:
  tomcat:
    threads:
      max: 200
```
connection-timeout을 대폭 늘렸다.
* pool에서 커넥션을 얻어오기전까지 기다리는 최대 시간, 허용가능한 wait time을 초과하면 SQLException을 던짐

#### 구매기능 테스트
<img src="https://user-images.githubusercontent.com/58140426/225527611-b3efa8e9-5d20-458b-a898-b6a1a57eeae2.png" width="720">

TPS: 160.5으로 올랐다.

<br>

쓰레드 max를 300으로 올렸는데, TPS가 120으로 나왔다.<br>
쓰레드 간 경합으로 쓰레드 최대값을 늘리면 쓰레드 간 경합이 발생할 가능성이 높아지고 여러 쓰레드가 같은 자원에 접근하면 경합이 발생하여 cpu 느리게 처리되는 것 같다.

<br>

## 8️⃣ Test(JUnit5)
> 구매 기능에 대해 멀티쓰레드를 이용하여 비동기로 테스트
```java
@Test
    @DisplayName("낙관적 락으로 구매")
    void manyPplPurchaseWithOptimisticLock() throws InterruptedException{
        /**
         * 여러 유저 생성
         * 쓰레드 생성해서
         * 구매 러시
         * */
        // given
        int threadCount = 500;
        String userId = userService.findUserIdByNickname("client");
        ReqPurchase reqPurchase = new ReqPurchase();
        reqPurchase.setUser_id(userId);
        reqPurchase.setTimedeal_id(1L);
        reqPurchase.setCount(2);

        // 멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있또록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 다른 스레드에서 수행이 완료될 때 까지 대기할 수 있도록
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for(int i = 0; i< threadCount; i++){
            executorService.submit(()->{
                try{
                    // 로직
                    purchaseService.purchaseTimedealWithOptimisticLock(reqPurchase);
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Timedeal timedeal = timedealService.findById(1L);
        assertEquals(0,timedeal.getLimitedAmount());
    }
```

<br>

## 9️⃣ 트러블 슈팅과 회고
[[Numble] Spring으로 타임딜 서버 구축하기 - 트러블 슈팅과 회고](https://javapp.tistory.com/312)
