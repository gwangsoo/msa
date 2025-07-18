# 쇼핑몰 MSA 프로젝트

MSA(Microservice Architecture) 기반의 쇼핑몰을 구축하는 프로젝트입니다.

## 아키텍처

*   **MSA (Microservice Architecture)**
*   **F/E:** React
*   **BFF (Backend for Frontend):** 모바일, 사용자 사이트, 관리자 사이트 각각 구성
*   **API Gateway:** Spring Cloud Gateway
*   **Event Driven Architecture:** Kafka
*   **인증:** OAuth2 (Keycloak)
*   **B/E:** Spring Boot
*   **Database:** PostgreSQL
*   **Cache:** Redis
*   **File Storage:** Minio

## 실행 방법

### 1. 인프라 실행

프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 Docker Compose로 필요한 인프라(Keycloak, Kafka, PostgreSQL 등)를 실행합니다.

```bash
docker-compose up -d
```

### 2. Keycloak 설정

1.  브라우저에서 `http://localhost:8080`으로 접속하여 Keycloak 관리자 콘솔에 로그인합니다. (ID: `admin`, PW: `admin`)
2.  `master` realm 위에서 'Create Realm' 버튼을 클릭합니다.
3.  Realm 이름으로 `shopping-mall`을 입력하고 생성합니다.
4.  `shopping-mall` realm에서 클라이언트, 역할, 사용자 등을 필요에 맞게 설정합니다.

### 3. 서비스 실행

각 서비스는 Gradle을 통해 실행할 수 있습니다. 예를 들어, `user-service`를 실행하려면 다음 명령어를 사용합니다.

```bash
./gradlew :services:user-service:bootRun
```

API Gateway를 실행하려면 다음 명령어를 사용합니다.

```bash
./gradlew :gateway:bootRun
```

## 서비스 목록

*   **API Gateway:** 포트 8000
*   **User Service:** 포트 8081
*   ... (나머지 서비스)
