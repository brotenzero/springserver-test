package com.example.controller;

import com.example.dto.SearchRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/soccer/search")
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정
public class SearchController {

    @PostMapping("/findByKeyword")
    public ResponseEntity<?> findByKeyword(@RequestBody SearchRequest request) {
        // 즉시 sysout으로 확인
        System.out.println("==========================================");
        System.out.println("요청이 도착했습니다!");
        System.out.println("==========================================");
        
        // 검색어 출력
        System.out.println("입력한 검색어: " + request.getKeyword());
        
        // 메시지 출력
        if (request.getMessages() != null) {
            System.out.println("전체 메시지 개수: " + request.getMessages().size());
            System.out.println("전체 메시지 내용:");
            
            request.getMessages().forEach(message -> {
                System.out.println("  - ID: " + message.getId());
                System.out.println("    Role: " + message.getRole());
                System.out.println("    Content: " + message.getContent());
                System.out.println("    ---");
            });
        } else {
            System.out.println("메시지가 없습니다.");
        }
        
        System.out.println("==========================================");
        
        // 응답 반환
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "검색어를 받았습니다: " + request.getKeyword()
        ));
    }
}

