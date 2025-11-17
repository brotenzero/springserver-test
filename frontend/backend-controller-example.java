package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/soccer/search")
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정
public class SearchController {

    @PostMapping("/findByKeyword")
    public ResponseEntity<?> findByKeyword(@RequestBody Map<String, Object> request) {
        // 즉시 sysout으로 확인
        System.out.println("==========================================");
        System.out.println("요청이 도착했습니다!");
        System.out.println("==========================================");
        
        // 검색어 추출
        String keyword = (String) request.get("keyword");
        System.out.println("입력한 검색어: " + keyword);
        
        // 메시지 배열 추출
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> messages = (List<Map<String, Object>>) request.get("messages");
        
        System.out.println("전체 메시지 개수: " + (messages != null ? messages.size() : 0));
        System.out.println("전체 메시지 내용:");
        
        if (messages != null) {
            for (Map<String, Object> message : messages) {
                System.out.println("  - ID: " + message.get("id"));
                System.out.println("    Role: " + message.get("role"));
                System.out.println("    Content: " + message.get("content"));
                System.out.println("    ---");
            }
        }
        
        System.out.println("==========================================");
        
        // 응답 반환
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "검색어를 받았습니다: " + keyword,
            "receivedMessages", messages != null ? messages.size() : 0
        ));
    }
}

