package com.brobrown.soccerservice.controller;

import java.util.Enumeration;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brobrown.soccerservice.common.Messenger;
import com.brobrown.soccerservice.service.SoccerSearchFacade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/soccer/search")
@CrossOrigin(origins = "*") // CORS 설정 추가
public class SoccerSearchController {

    private final SoccerSearchFacade soccerSearchFacade;

    /**
     * 통합 검색 엔드포인트 (GET 방식)
     * 리액트에서 axios로 호출할 때 사용
     * 
     * 예시: GET /soccer/search/findByKeyword?type=player&keyword=손흥민
     * 
     * @param request HttpServletRequest (디버깅용)
     * @param type    검색할 엔티티 타입 (player, team, stadium, schedule)
     * @param keyword 검색어
     * @return 검색 결과
     */
    @GetMapping("/findByKeyword")
    public ResponseEntity<Messenger> findByKeyword(
            HttpServletRequest request,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "keyword", required = false) String keyword) {

        System.out.println("========================================");
        System.out.println("=== SoccerSearchController: 검색 요청 수신 (GET) ===");
        System.out.println("요청 URL: " + request.getRequestURL());
        System.out.println("Query String: " + request.getQueryString());
        System.out.println("요청 Method: " + request.getMethod());

        // 모든 파라미터 출력
        System.out.println("--- 모든 요청 파라미터 ---");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println("  " + paramName + " = " + paramValue);
        }

        System.out.println("--- @RequestParam으로 받은 값 ---");
        System.out.println("type: [" + type + "]");
        System.out.println("keyword: [" + keyword + "]");
        System.out.println("========================================");

        // 파라미터 검증
        if (type == null || type.isEmpty()) {
            System.out.println("!!! 오류: type 파라미터가 없습니다 !!!");
            return ResponseEntity.badRequest().body(
                    Messenger.builder()
                            .code(400)
                            .message("type parameter is required")
                            .build());
        }

        if (keyword == null || keyword.isEmpty()) {
            System.out.println("!!! 오류: keyword 파라미터가 없습니다 !!!");
            return ResponseEntity.badRequest().body(
                    Messenger.builder()
                            .code(400)
                            .message("keyword parameter is required")
                            .build());
        }

        Messenger result = soccerSearchFacade.searchByKeyword(type, keyword);

        System.out.println("=== SoccerSearchController: 검색 결과 반환 ===");
        return ResponseEntity.ok(result);
    }

    /**
     * 통합 검색 엔드포인트 (POST 방식)
     * 리액트에서 axios.post()로 호출할 때 사용
     * 
     * @param request     HttpServletRequest (디버깅용)
     * @param requestBody 요청 본문 (JSON)
     * @return 검색 결과
     */
    @PostMapping("/findByKeyword")
    public ResponseEntity<Messenger> findByKeywordPost(
            HttpServletRequest request,
            @RequestBody(required = false) Map<String, String> requestBody) {

        System.out.println("========================================");
        System.out.println("=== SoccerSearchController: 검색 요청 수신 (POST) ===");
        System.out.println("요청 URL: " + request.getRequestURL());
        System.out.println("요청 Method: " + request.getMethod());
        System.out.println("Content-Type: " + request.getContentType());

        if (requestBody != null) {
            System.out.println("--- Request Body ---");
            requestBody.forEach((key, value) -> {
                System.out.println("  " + key + " = " + value);
            });
        } else {
            System.out.println("!!! Request Body가 null입니다 !!!");
        }

        // Query String도 확인
        System.out.println("Query String: " + request.getQueryString());
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println("  " + paramName + " = " + paramValue);
        }
        System.out.println("========================================");

        String type = null;
        String keyword = null;

        // Request Body에서 파라미터 추출
        if (requestBody != null) {
            type = requestBody.get("type");
            keyword = requestBody.get("keyword");
        }

        // Query String에서도 확인
        if (type == null || type.isEmpty()) {
            type = request.getParameter("type");
        }
        if (keyword == null || keyword.isEmpty()) {
            keyword = request.getParameter("keyword");
        }

        System.out.println("최종 추출된 값 - type: [" + type + "], keyword: [" + keyword + "]");

        // 파라미터 검증
        if (type == null || type.isEmpty()) {
            System.out.println("!!! 오류: type 파라미터가 없습니다 !!!");
            return ResponseEntity.badRequest().body(
                    Messenger.builder()
                            .code(400)
                            .message("type parameter is required")
                            .build());
        }

        if (keyword == null || keyword.isEmpty()) {
            System.out.println("!!! 오류: keyword 파라미터가 없습니다 !!!");
            return ResponseEntity.badRequest().body(
                    Messenger.builder()
                            .code(400)
                            .message("keyword parameter is required")
                            .build());
        }

        Messenger result = soccerSearchFacade.searchByKeyword(type, keyword);

        System.out.println("=== SoccerSearchController: 검색 결과 반환 ===");
        return ResponseEntity.ok(result);
    }

    /**
     * 테스트용 엔드포인트 - 요청이 도달하는지 확인
     */
    @GetMapping("/test")
    public ResponseEntity<Messenger> test() {
        System.out.println("========================================");
        System.out.println("!!! TEST 엔드포인트 호출됨 !!!");
        System.out.println("========================================");
        return ResponseEntity.ok(
                Messenger.builder()
                        .code(200)
                        .message("Test endpoint is working")
                        .build());
    }
}
