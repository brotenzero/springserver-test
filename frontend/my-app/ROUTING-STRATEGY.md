# Eureka Discovery를 통한 PlayerController 데이터 이동 전략

## 📋 현재 아키텍처

```
Next.js (page.tsx)
    ↓ axios 요청
API Gateway (discoveryclient:8080)
    ↓ Eureka Discovery
soccerservice
    ↓
SoccerSearchController
    ↓
SoccerSearchFacade
    ↓ (type="player"일 때)
PlayerService.findByKeyword()
    ↓
PlayerRepository
    ↓
Database
```

## 🎯 목표

**page.tsx에서 axios 요청 → Eureka Discovery → PlayerController로 직접 데이터 이동**

## 🔄 두 가지 전략

### 전략 1: 기존 구조 활용 (현재 구조 - 권장)

**현재 구조가 이미 올바르게 작동합니다:**

1. **프론트엔드 (page.tsx)**
   ```typescript
   const data = await soccerService.searchByKeyword({
     keyword: input,
     type: "player",  // "player"로 변경 필요
   });
   ```

2. **API Gateway 라우팅**
   - 요청: `GET /soccer/search/findByKeyword?type=player&keyword=손흥민`
   - Gateway: `/soccer/**` → `lb://soccerservice` (Eureka Discovery)
   - StripPrefix=1: `/soccer` 제거 → `/search/findByKeyword`로 전달

3. **soccerservice 내부**
   - `SoccerSearchController.findByKeyword()` 수신
   - `SoccerSearchFacade.searchByKeyword(type="player", keyword)` 호출
   - `PlayerService.findByKeyword(keyword)` 호출
   - 데이터 반환

**장점:**
- ✅ 이미 구현되어 있음
- ✅ 통합 검색 인터페이스 제공 (player, team, stadium, schedule)
- ✅ Facade 패턴으로 확장성 좋음

**단점:**
- ❌ PlayerController를 직접 호출하지 않음 (Service 레이어를 거침)

---

### 전략 2: PlayerController 직접 호출 (새로운 구조)

**PlayerController에 직접 접근하는 경로 추가:**

1. **프론트엔드 (page.tsx)**
   ```typescript
   // PlayerController 직접 호출
   const data = await apiClient.getWithQuery('/soccer/player/search', {
     keyword: input
   });
   ```

2. **API Gateway 라우팅** (현재 설정 유지)
   - 요청: `GET /soccer/player/search?keyword=손흥민`
   - Gateway: `/soccer/**` → `lb://soccerservice`
   - StripPrefix=1: `/soccer` 제거 → `/player/search`로 전달

3. **PlayerController에 새 엔드포인트 추가**
   ```java
   @GetMapping("/search")
   public ResponseEntity<Messenger> searchByKeyword(
       @RequestParam String keyword) {
       return ResponseEntity.ok(playerService.findByKeyword(keyword));
   }
   ```

**장점:**
- ✅ PlayerController를 직접 호출
- ✅ RESTful API 설계 원칙 준수
- ✅ 각 컨트롤러가 독립적으로 동작

**단점:**
- ❌ 추가 구현 필요
- ❌ 통합 검색 기능과 분리됨

---

## 🚀 권장 구현 전략

### 옵션 A: 기존 구조 개선 (권장)

**page.tsx 수정:**
```typescript
// type을 "player"로 변경
const data = await soccerService.searchByKeyword({
  keyword: input,
  type: "player",  // "chat" → "player"로 변경
});
```

**현재 흐름:**
1. page.tsx → `soccerService.searchByKeyword({ type: "player", keyword })`
2. API Gateway → Eureka Discovery → soccerservice
3. SoccerSearchController → SoccerSearchFacade
4. SoccerSearchFacade → PlayerService.findByKeyword()
5. PlayerService → PlayerRepository → Database
6. 결과 반환

**이미 Eureka Discovery를 거치고 있으며, PlayerService를 통해 데이터를 가져옵니다.**

---

### 옵션 B: PlayerController 직접 호출 추가

**1. PlayerController에 검색 엔드포인트 추가:**

```java
@GetMapping("/search")
public ResponseEntity<Messenger> searchByKeyword(
    @RequestParam String keyword) {
    System.out.println("=== PlayerController: 검색 요청 수신 ===");
    System.out.println("검색어: " + keyword);
    return ResponseEntity.ok(playerService.findByKeyword(keyword));
}
```

**2. 프론트엔드 서비스 추가:**

```typescript
// frontend/my-app/src/services/soccer.service.ts
export const soccerService = {
  // ... 기존 코드 ...
  
  /**
   * PlayerController 직접 호출
   */
  searchPlayer: (keyword: string): Promise<any> => {
    return apiClient.getWithQuery('/soccer/player/search', { keyword });
  },
};
```

**3. page.tsx에서 사용:**

```typescript
// PlayerController 직접 호출
const data = await soccerService.searchPlayer(input);
```

---

## 📊 비교표

| 항목 | 전략 1 (기존) | 전략 2 (직접 호출) |
|------|--------------|-------------------|
| Eureka Discovery | ✅ 사용 | ✅ 사용 |
| PlayerController 직접 호출 | ❌ Service 거침 | ✅ 직접 호출 |
| 구현 복잡도 | 낮음 (이미 구현됨) | 중간 (추가 구현 필요) |
| 확장성 | 높음 (통합 검색) | 중간 (개별 엔드포인트) |
| RESTful 설계 | 중간 | 높음 |

---

## 🎯 최종 권장사항

**현재 구조(전략 1)를 유지하되, page.tsx의 type 파라미터만 수정:**

```typescript
// page.tsx 수정
const data = await soccerService.searchByKeyword({
  keyword: input,
  type: "player",  // "chat" → "player"로 변경
});
```

**이미 Eureka Discovery를 거쳐 PlayerService로 데이터가 이동하고 있습니다.**

만약 PlayerController를 직접 호출하고 싶다면, 전략 2를 추가로 구현할 수 있습니다.

