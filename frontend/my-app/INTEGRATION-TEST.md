# 통합 테스트 가이드

## 🧪 테스트 시나리오

### 테스트 1: API Gateway 라우팅 확인

**목적**: API Gateway가 soccerservice로 올바르게 라우팅하는지 확인

**테스트 방법**:
```bash
# Docker 컨테이너 내부에서 테스트
docker compose exec discoveryclient curl -s "http://localhost:8080/soccer/search/findByKeyword?keyword=손흥민&type=player"

# 또는 브라우저에서 직접 테스트
http://localhost:8080/soccer/search/findByKeyword?keyword=손흥민&type=player
```

**예상 결과**:
- HTTP 200 OK
- JSON 응답 반환
- soccerservice 로그에 요청 기록

---

### 테스트 2: Eureka Discovery 확인

**목적**: soccerservice가 Eureka에 등록되어 있는지 확인

**테스트 방법**:
1. 브라우저에서 http://localhost:8761 접속
2. "Instances currently registered with Eureka" 섹션 확인
3. `SOCCERSERVICE` 서비스가 UP 상태인지 확인

**예상 결과**:
- SOCCERSERVICE가 등록되어 있음
- 상태: UP
- 인스턴스 수: 1

---

### 테스트 3: 프론트엔드에서 API 호출

**목적**: Next.js 앱에서 API Gateway를 통해 데이터를 가져오는지 확인

**테스트 방법**:
1. 브라우저에서 http://localhost:3000 접속
2. 개발자 도구(F12) 열기
3. Network 탭 확인
4. 검색어 입력 (예: "손흥민")
5. Enter 키 또는 전송 버튼 클릭

**확인 사항**:
- Network 탭에서 `/soccer/search/findByKeyword?keyword=손흥민&type=player` 요청 확인
- 요청 상태: 200 OK
- 응답 데이터 확인
- Console 탭에서 "백엔드 응답:" 로그 확인

**예상 결과**:
- 요청 성공 (200 OK)
- CORS 오류 없음
- 응답 데이터 수신
- 메시지에 검색 결과 표시

---

### 테스트 4: PlayerController sysout 확인

**목적**: PlayerController에서 sysout으로 데이터 확인

**테스트 방법**:
1. 프론트엔드에서 검색 요청
2. soccerservice 로그 확인:
   ```bash
   docker compose logs -f soccerservice
   ```

**확인 사항**:
- `=== PlayerController: 검색 요청 수신 ===` 로그 확인
- 검색어 출력 확인
- 검색 결과 출력 확인

**예상 결과**:
```
========================================
=== PlayerController: 검색 요청 수신 ===
검색어: 손흥민
요청 경로: /player/search
========================================
=== PlayerController: 검색 결과 ===
결과 코드: 200
결과 메시지: ...
결과 데이터: ...
========================================
```

---

## 🔍 문제 해결

### 404 오류 발생 시

1. **라우팅 설정 확인**:
   - `server/discoveryclient/src/main/resources/application.yaml` 확인
   - `/soccer/**` 라우팅이 설정되어 있는지 확인

2. **서비스 재시작**:
   ```bash
   docker compose restart discoveryclient
   docker compose restart soccerservice
   ```

3. **Eureka 등록 확인**:
   - http://localhost:8761 에서 soccerservice 등록 확인

### CORS 오류 발생 시

1. **API Gateway CORS 설정 확인**:
   - `application.yaml`의 `globalcors` 설정 확인
   - `localhost:3000`이 허용 목록에 있는지 확인

2. **서비스 CORS 설정 확인**:
   - soccerservice의 `@CrossOrigin` 어노테이션 확인

### 데이터가 이동하지 않는 경우

1. **로그 확인**:
   ```bash
   # discoveryclient 로그
   docker compose logs -f discoveryclient
   
   # soccerservice 로그
   docker compose logs -f soccerservice
   ```

2. **경로 확인**:
   - 프론트엔드 요청 경로: `/soccer/search/findByKeyword`
   - Gateway 라우팅: `/soccer/**` → `lb://soccerservice`
   - StripPrefix=1: `/soccer` 제거 → `/search/findByKeyword`
   - 컨트롤러 경로: `@RequestMapping("/search")` + `@GetMapping("/findByKeyword")`

---

## ✅ 성공 기준

- [ ] API Gateway가 요청을 받음
- [ ] Eureka Discovery를 통해 soccerservice 찾기
- [ ] SoccerSearchController에서 요청 수신
- [ ] SoccerSearchFacade에서 type="player" 확인
- [ ] PlayerService.findByKeyword() 호출
- [ ] PlayerController sysout에 데이터 출력
- [ ] 프론트엔드에 응답 반환
- [ ] 브라우저에 검색 결과 표시

---

## 📝 테스트 체크리스트

- [ ] 모든 서비스 실행 중 (docker compose ps)
- [ ] Eureka Server 접근 가능 (http://localhost:8761)
- [ ] API Gateway 접근 가능 (http://localhost:8080)
- [ ] soccerservice Eureka 등록 확인
- [ ] API Gateway 라우팅 테스트 성공
- [ ] 프론트엔드에서 API 호출 성공
- [ ] PlayerController sysout 로그 확인
- [ ] CORS 오류 없음
- [ ] 검색 결과 정상 표시

