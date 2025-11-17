# 데이터 이동 플로우 상세 설명

## 📝 시나리오: 사용자가 '해결됨' 입력

---

## 🔄 전체 데이터 이동 경로

```
[브라우저] → [Next.js Frontend] → [API Gateway] → [Eureka Discovery] → [soccerservice] → [SoccerSearchController] → [SoccerSearchFacade] → [PlayerService] → [PlayerController]
```

---

## 📍 단계별 상세 설명

### **1단계: 프론트엔드 입력 처리** (`page.tsx`)

**위치**: `frontend/my-app/src/app/page.tsx`

**사용자 액션**: 
- 사용자가 텍스트 입력창에 "해결됨" 입력
- Enter 키 또는 전송 버튼 클릭

**코드 실행**:
```typescript
// 17-27줄: handleSend() 함수 실행
const handleSend = async () => {
  if (!input.trim()) return;
  
  // input = "해결됨"
  const newMessage: Message = { 
    id: messages.length + 1, 
    role: "user", 
    content: input  // "해결됨"
  };
  setMessages([...messages, newMessage]);
  setInput("");
```

**다음 단계**: API 호출 준비

---

### **2단계: API 서비스 호출** (`soccer.service.ts`)

**위치**: `frontend/my-app/src/services/soccer.service.ts`

**코드 실행**:
```typescript
// 32-34줄: searchByKeyword 호출
const { soccerService } = await import('@/services/soccer.service');
const data = await soccerService.searchByKeyword({
  keyword: "해결됨",  // input 값
  type: "player"      // 하드코딩된 값
});
```

**실제 호출**:
```typescript
// soccer.service.ts 32-34줄
searchByKeyword: (params: SearchParams): Promise<SoccerMatch[]> => {
  return apiClient.getWithQuery<SoccerMatch[]>(
    '/soccer/search/findByKeyword', 
    { keyword: "해결됨", type: "player" }
  );
}
```

**결과**: 
- URL 생성: `http://localhost:8080/soccer/search/findByKeyword?keyword=해결됨&type=player`

---

### **3단계: API Client 요청 생성** (`api-client.ts`)

**위치**: `frontend/my-app/src/lib/api-client.ts`

**코드 실행**:
```typescript
// 155-166줄: getWithQuery 메서드
async getWithQuery<T>(path: string, params?: Record<string, string | number | boolean>): Promise<T> {
  let url = `${this.baseURL}${path}`;  // http://localhost:8080/soccer/search/findByKeyword
  
  // Query Parameter 추가
  const searchParams = new URLSearchParams();
  searchParams.append('keyword', '해결됨');
  searchParams.append('type', 'player');
  url += `?${searchParams.toString()}`;
  
  // 최종 URL: http://localhost:8080/soccer/search/findByKeyword?keyword=%ED%95%B4%EA%B2%B0%EB%90%A8&type=player
  return this.get<T>(url);
}
```

**HTTP 요청**:
```
GET http://localhost:8080/soccer/search/findByKeyword?keyword=%ED%95%B4%EA%B2%B0%EB%90%A8&type=player
Headers:
  Content-Type: application/json
  Origin: http://localhost:3000
```

---

### **4단계: API Gateway 라우팅** (`discoveryclient`)

**위치**: `server/discoveryclient/src/main/resources/application.yaml`

**라우팅 규칙**:
```yaml
# 11-19줄
spring:
  cloud:
    gateway:
      routes:
        - id: soccerservice
          uri: lb://soccerservice  # Eureka를 통한 로드 밸런싱
          predicates:
            - Path=/soccer/**
          filters:
            - StripPrefix=1  # /soccer 제거
```

**처리 과정**:
1. **요청 수신**: `GET /soccer/search/findByKeyword?keyword=해결됨&type=player`
2. **경로 매칭**: `/soccer/**` 패턴 매칭 성공
3. **Eureka Discovery**: `lb://soccerservice` → Eureka에서 soccerservice 인스턴스 조회
4. **StripPrefix 적용**: `/soccer` 제거 → `/search/findByKeyword`
5. **요청 전달**: `http://soccerservice:8080/search/findByKeyword?keyword=해결됨&type=player`

**CORS 처리**:
```yaml
# 48-64줄
globalcors:
  cors-configurations:
    '[/**]':
      allowedOrigins:
        - "http://localhost:3000"
      allowedMethods: [GET, POST, PUT, DELETE, OPTIONS, PATCH]
      allowedHeaders: ["*"]
      allowCredentials: true
```

**응답 헤더 추가**:
```
Access-Control-Allow-Origin: http://localhost:3000
```

---

### **5단계: soccerservice 컨트롤러 수신** (`SoccerSearchController.java`)

**위치**: `service/soccerservice/src/main/java/com/brobrown/soccerservice/controller/SoccerSearchController.java`

**요청 매핑**:
```java
@RestController
@RequestMapping("/search")  // StripPrefix 후 경로: /search
public class SoccerSearchController {
  
  @GetMapping("/findByKeyword")  // 최종 경로: /search/findByKeyword
  public ResponseEntity<Messenger> findByKeyword(
      HttpServletRequest request,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "keyword", required = false) String keyword) {
```

**sysout 출력 (44-62줄)**:
```
========================================
=== SoccerSearchController: 검색 요청 수신 (GET) ===
요청 URL: http://soccerservice:8080/search/findByKeyword
Query String: keyword=해결됨&type=player
요청 Method: GET
--- 모든 요청 파라미터 ---
  keyword = 해결됨
  type = player
--- @RequestParam으로 받은 값 ---
type: [player]
keyword: [해결됨]
========================================
```

**파라미터 검증 (64-81줄)**:
```java
if (type == null || type.isEmpty()) {
  // 오류 처리
}
if (keyword == null || keyword.isEmpty()) {
  // 오류 처리
}
// ✅ 검증 통과: type="player", keyword="해결됨"
```

**Facade 호출 (83줄)**:
```java
Messenger result = soccerSearchFacade.searchByKeyword(type, keyword);
// type = "player", keyword = "해결됨"
```

---

### **6단계: SoccerSearchFacade 라우팅** (`SoccerSearchFacade.java`)

**위치**: `service/soccerservice/src/main/java/com/brobrown/soccerservice/service/SoccerSearchFacade.java`

**sysout 출력**:
```
=== SoccerSearchFacade: 통합 검색 시작 ===
검색 타입: player
검색어: 해결됨
>>> Player 검색 실행
```

**타입별 라우팅 (37-40줄)**:
```java
switch (type.toLowerCase()) {  // "player"
  case "player":
    System.out.println(">>> Player 검색 실행");
    result = playerService.findByKeyword(keyword);  // keyword = "해결됨"
    break;
  // ...
}
```

**sysout 출력 (62줄)**:
```
=== SoccerSearchFacade: 통합 검색 완료 ===
```

---

### **7단계: PlayerService 검색 실행** (`PlayerServiceImpl.java`)

**위치**: `service/soccerservice/src/main/java/com/brobrown/soccerservice/player/PlayerServiceImpl.java`

**실행 내용**:
- `PlayerRepository`를 통해 데이터베이스에서 "해결됨" 키워드로 선수 검색
- QueryDSL을 사용한 동적 쿼리 실행
- 검색 결과를 `Messenger` 객체로 래핑하여 반환

**반환 데이터**:
```java
Messenger result = Messenger.builder()
    .code(200)
    .message("검색 성공")
    .data(검색된_선수_리스트)
    .build();
```

---

### **8단계: 응답 반환** (`SoccerSearchController.java`)

**위치**: `SoccerSearchController.java` (85-86줄)

**sysout 출력**:
```
=== SoccerSearchController: 검색 결과 반환 ===
```

**응답 생성**:
```java
return ResponseEntity.ok(result);
// HTTP 200 OK
// Body: { "code": 200, "message": "...", "data": [...] }
```

---

### **9단계: API Gateway 응답 전달**

**처리 과정**:
1. soccerservice에서 응답 수신
2. CORS 헤더 추가 (이미 Gateway에서 처리됨)
3. 프론트엔드로 응답 전달

**HTTP 응답**:
```
HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://localhost:3000
Content-Type: application/json

{
  "code": 200,
  "message": "검색 성공",
  "data": [...]
}
```

---

### **10단계: 프론트엔드 응답 처리** (`page.tsx`)

**위치**: `page.tsx` (37-47줄)

**코드 실행**:
```typescript
console.log("백엔드 응답:", data);

// 검색 결과를 메시지로 표시
if (data && data.length > 0) {
  const searchResult: Message = {
    id: updatedMessages.length + 1,
    role: "assistant",
    content: `검색 결과를 찾았습니다: ${data.length}개의 결과가 있습니다.`,
  };
  setMessages((prev: Message[]) => [...prev, searchResult]);
}
```

**브라우저 콘솔 출력**:
```
백엔드 응답: { code: 200, message: "...", data: [...] }
```

**UI 업데이트**:
- 채팅 화면에 검색 결과 메시지 표시
- "검색 결과를 찾았습니다: X개의 결과가 있습니다."

---

## 🔍 확인 가능한 sysout 로그

### **SoccerSearchController에서 확인 가능한 로그**:

```
========================================
=== SoccerSearchController: 검색 요청 수신 (GET) ===
요청 URL: http://soccerservice:8080/search/findByKeyword
Query String: keyword=해결됨&type=player
요청 Method: GET
--- 모든 요청 파라미터 ---
  keyword = 해결됨
  type = player
--- @RequestParam으로 받은 값 ---
type: [player]
keyword: [해결됨]
========================================
=== SoccerSearchController: 검색 결과 반환 ===
```

### **SoccerSearchFacade에서 확인 가능한 로그**:

```
=== SoccerSearchFacade: 통합 검색 시작 ===
검색 타입: player
검색어: 해결됨
>>> Player 검색 실행
=== SoccerSearchFacade: 통합 검색 완료 ===
```

### **로그 확인 방법**:

```bash
# soccerservice 로그 실시간 확인
docker compose logs -f soccerservice

# 특정 키워드로 필터링
docker compose logs -f soccerservice | grep "해결됨"
```

---

## 📊 데이터 변환 과정

| 단계 | 데이터 형태 | 값 |
|------|------------|-----|
| 1. 사용자 입력 | String | `"해결됨"` |
| 2. API 호출 | URL Query Parameter | `keyword=해결됨&type=player` |
| 3. URL 인코딩 | URL Encoded | `keyword=%ED%95%B4%EA%B2%B0%EB%90%A8&type=player` |
| 4. API Gateway | HTTP Request | `GET /soccer/search/findByKeyword?...` |
| 5. StripPrefix | 경로 변환 | `/search/findByKeyword` |
| 6. Controller | Java String | `type="player"`, `keyword="해결됨"` |
| 7. Facade | Java Method Call | `playerService.findByKeyword("해결됨")` |
| 8. Service | Database Query | SQL 쿼리 실행 |
| 9. Response | JSON | `{ "code": 200, "data": [...] }` |
| 10. Frontend | JavaScript Object | `{ code: 200, data: [...] }` |

---

## ✅ 요약

1. **프론트엔드**: 사용자 입력 "해결됨" → API 호출 준비
2. **API Gateway**: `/soccer/**` → `lb://soccerservice` 라우팅, CORS 처리
3. **Eureka Discovery**: soccerservice 인스턴스 찾기
4. **SoccerSearchController**: 요청 수신 및 sysout 로그 출력 ✅
5. **SoccerSearchFacade**: `type="player"` 확인 → PlayerService 호출
6. **PlayerService**: 데이터베이스에서 "해결됨" 검색
7. **응답 반환**: 검색 결과를 JSON으로 반환
8. **프론트엔드**: 결과를 UI에 표시

**핵심 확인 포인트**: `SoccerSearchController`의 sysout 로그에서 다음을 확인할 수 있습니다:
- ✅ 요청 URL
- ✅ Query String (keyword=해결됨&type=player)
- ✅ 모든 파라미터 값
- ✅ @RequestParam으로 받은 값

