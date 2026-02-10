
# 🚀 Spring Boot CoC & REST API 실습 정리

> **Spring Boot → CoC → REST API → 확장앱 테스트**  
> 이 흐름을 한 번 제대로 잡아두면, 이후 **백엔드 이해도가 급상승**합니다.

---

## 🎯 학습 목표

- Spring Boot의 **CoC (Convention over Configuration)** 개념 이해
- **Spring Data JPA 기반 자동 REST API 생성 구조** 파악
- REST API 테스트 툴(Postman, REST Client, 확장앱) 실습
- 실무에서 자주 발생하는 **에러 유형과 해결 방법 정리**

---

# 1️⃣ Spring Boot의 CoC (Convention over Configuration)

Spring Data JPA에서 아래와 같이 **Repository 인터페이스만 작성해도**  
👉 **REST API가 자동 생성됩니다.**

```java
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
}
````

### 📌 자동 생성 URI 규칙

| 엔티티     | 자동 생성 URI   |
| ------- | ----------- |
| User    | `/users`    |
| Order   | `/orders`   |
| Product | `/products` |

### 규칙 요약

* **Entity 클래스명 → 소문자 → 복수형(s)**
* 별도의 Controller 작성 없이 **자동 REST API 제공**

> 💡 **COC 철학**
> “설정하지 않으면, 관례대로 처리한다”

---

# 2️⃣ 자동 생성 REST API 구조

### Entity 예제

```java
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
```

```java
public interface UserRepository extends JpaRepository<User, Long> {}
```

### 자동 생성 REST API

| Method | URI           | 기능    |
| ------ | ------------- | ----- |
| GET    | `/users`      | 전체 조회 |
| GET    | `/users/{id}` | 단건 조회 |
| POST   | `/users`      | 생성    |
| PUT    | `/users/{id}` | 수정    |
| DELETE | `/users/{id}` | 삭제    |

---

# 3️⃣ REST API 테스트 방법

### POST 요청 예시

```
POST http://localhost:8080/users
Content-Type: application/json
```

```json
{
  "name": "Hyerim"
}
```

---

### GET 요청 예시

```
GET http://localhost:8080/users
```

---

# 4️⃣ POST / GET 실패 원인 TOP 6

> 👉 **실무에서 90% 이상 여기서 문제 발생**

---

### ① Content-Type 누락 (⭐⭐⭐⭐⭐)

```
Content-Type: application/json
```

없으면 → **415 Unsupported Media Type**

---

### ② URI 오타 (복수형 s 빠짐)

❌ `/user`
⭕ `/users`

---

### ③ 포트 번호 오류

기본:

```
http://localhost:8080
```

설정 변경 여부 확인:

```yaml
server:
  port: 9090
```

---

### ④ Spring Security 인증 차단

401 / 403 발생 시 → **보안 설정 문제**

테스트용 임시 해제:

```java
@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    return http.build();
}
```

---

### ⑤ Entity 필드명과 JSON 키 불일치

```java
private String userName;
```

```json
{
  "name": "test"
}
```

→ **400 Bad Request**

---

### ⑥ JPA 테이블 자동 생성 안 됨

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

없으면 → **DB 테이블 미생성**

---

# 5️⃣ 빠른 디버깅 체크리스트 🔥

문제 발생 시 아래 4가지만 확인하면 **원인 90% 해결**

* Entity 클래스
* Repository 코드
* 요청 JSON
* 콘솔 에러 로그

---

# 6️⃣ 자동 REST vs Controller 직접 구현

| 방식              | 특징                    |
| --------------- | --------------------- |
| Repository 자동   | 빠른 개발 / 테스트 / 내부 관리툴  |
| @RestController | 실무 표준 / 보안 / DTO / 검증 |

### 실무 적용 기준

* **내부 관리 시스템** → Repository 자동
* **외부 공개 API** → Controller 직접 구현

---

# 🔥 핵심 요약

> Spring Boot의 **CoC + 자동 REST API 구조**는
> 👉 **Spring 생산성의 핵심**

---

## ✨ 정리 목표

* Spring Data REST 구조 완전 이해
* REST API 설계 감각 향상
* 실무형 디버깅 루틴 습득

---

📌 **Practice Repository**
Spring Boot + JPA + REST API 실습용 예제 프로젝트

```

---


