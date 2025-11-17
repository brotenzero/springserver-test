# API Gateway 패턴 구현 가이드

## 📋 개요

이 프로젝트는 **API Gateway 패턴**을 사용하여 Next.js 프론트엔드와 Spring Boot 마이크로서비스를 연동합니다.

## 🏗️ 아키텍처

```
Next.js App (3000)
    ↓
API Gateway / Discovery Client (8080)
    ↓
Eureka Server (8761) - 서비스 탐색
    ↓
마이크로서비스들 (8081-8085)
```

## 📁 파일 구조

```
frontend/my-app/
├── src/
│   ├── lib/
│   │   └── api-client.ts          # API 클라이언트 (싱글톤)
│   ├── services/
│   │   ├── user.service.ts        # 사용자 서비스 API
│   │   ├── soccer.service.ts      # 축구 서비스 API
│   │   ├── common.service.ts      # 공통 서비스 API
│   │   ├── zone.service.ts        # Zone 서비스 API
│   │   ├── zthree.service.ts      # ZThree 서비스 API
│   │   └── eureka.service.ts      # Eureka 서비스 API
│   └── app/
│       ├── page.tsx                # 메인 페이지 (검색 기능)
│       ├── users/page.tsx          # 사용자 목록 페이지
│       └── services/page.tsx      # 서비스 상태 페이지
└── docker-compose.yaml             # 환경 변수 설정
```

## 🔧 환경 변수

### 개발 환경 (로컬)
- `NEXT_PUBLIC_API_GATEWAY=http://localhost:8080`
- `NEXT_PUBLIC_EUREKA_SERVER=http://localhost:8761`

### 프로덕션 환경 (Docker)
- `API_GATEWAY=http://discoveryclient:8080` (서버 사이드)
- `NEXT_PUBLIC_API_GATEWAY=http://localhost:8080` (클라이언트 사이드)
- `EUREKA_SERVER=http://eurekaserver:8761` (서버 사이드)
- `NEXT_PUBLIC_EUREKA_SERVER=http://localhost:8761` (클라이언트 사이드)

## 💻 사용 방법

### 1. API 클라이언트 직접 사용

```typescript
import { apiClient } from '@/lib/api-client';

// GET 요청
const data = await apiClient.get('/user/api/users');

// POST 요청
const newUser = await apiClient.post('/user/api/users', { name: 'John' });

// Query Parameter 포함 GET 요청
const results = await apiClient.getWithQuery('/soccer/search/findByKeyword', {
  keyword: 'test',
  type: 'chat'
});
```

### 2. 서비스별 API 사용 (권장)

```typescript
import { userService } from '@/services/user.service';
import { soccerService } from '@/services/soccer.service';

// 사용자 조회
const users = await userService.getUsers();
const user = await userService.getUser('123');

// 축구 경기 검색
const matches = await soccerService.searchByKeyword({
  keyword: 'premier league',
  type: 'match'
});
```

### 3. 컴포넌트에서 사용

```typescript
'use client';

import { useEffect, useState } from 'react';
import { userService } from '@/services/user.service';

export default function MyComponent() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    userService.getUsers()
      .then(setUsers)
      .catch(console.error);
  }, []);

  return <div>{/* ... */}</div>;
}
```

## 🚀 실행 방법

### 1. 모든 서비스 시작
```bash
docker compose up -d
```

### 2. 프론트엔드만 재시작
```bash
docker compose restart frontend
```

### 3. 로그 확인
```bash
docker compose logs -f frontend
```

## 📍 접근 가능한 페이지

- **메인 페이지**: http://localhost:3000
- **사용자 목록**: http://localhost:3000/users
- **서비스 상태**: http://localhost:3000/services
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080

## 🔍 API 엔드포인트 예시

### 사용자 서비스
- `GET /user/api/users` - 모든 사용자 조회
- `GET /user/api/users/{id}` - 특정 사용자 조회
- `POST /user/api/users` - 사용자 생성
- `PUT /user/api/users/{id}` - 사용자 업데이트
- `DELETE /user/api/users/{id}` - 사용자 삭제

### 축구 서비스
- `GET /soccer/api/matches` - 모든 경기 조회
- `GET /soccer/api/matches/{id}` - 특정 경기 조회
- `GET /soccer/search/findByKeyword?keyword={keyword}&type={type}` - 키워드 검색

### Eureka 서비스
- `GET /eureka/apps` - 모든 등록된 애플리케이션 조회
- `GET /eureka/apps/{appName}` - 특정 애플리케이션 조회

## ⚠️ 주의사항

1. **CORS 설정**: Spring Boot 서비스에서 CORS를 허용해야 합니다.
2. **환경 변수**: `NEXT_PUBLIC_` 접두사가 있는 변수만 클라이언트 사이드에서 사용 가능합니다.
3. **서버/클라이언트 구분**: API 클라이언트는 자동으로 서버/클라이언트 환경을 감지합니다.
4. **에러 처리**: 모든 API 호출은 try-catch로 감싸서 에러를 처리해야 합니다.

## 🐛 문제 해결

### CORS 오류
- Spring Boot 서비스에 CORS 설정 추가 필요
- `@CrossOrigin` 어노테이션 또는 `CorsFilter` 사용

### 연결 오류
- Docker 네트워크 확인: `docker network ls`
- 서비스 상태 확인: `docker compose ps`
- 로그 확인: `docker compose logs [service-name]`

### 환경 변수 미적용
- Docker 컨테이너 재시작: `docker compose restart frontend`
- 환경 변수 확인: `docker compose exec frontend env`

