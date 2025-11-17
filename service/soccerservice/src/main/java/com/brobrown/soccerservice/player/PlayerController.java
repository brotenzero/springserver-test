package com.brobrown.soccerservice.player;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brobrown.soccerservice.common.Messenger;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")

public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody PlayerModel playerDTO) {
        return ResponseEntity.ok(playerService.save(playerDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<Messenger> saveAll(@RequestBody List<PlayerModel> playerDTOs) {
        return ResponseEntity.ok(playerService.saveAll(playerDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Messenger> update(@PathVariable String id, @RequestBody PlayerModel playerDTO) {
        return ResponseEntity.ok(playerService.update(playerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Messenger> delete(@PathVariable String id) {
        return ResponseEntity.ok(playerService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messenger> findById(@PathVariable String id) {
        return ResponseEntity.ok(playerService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Messenger> findAll() {
        return ResponseEntity.ok(playerService.findAll());
    }

    /**
     * 키워드로 선수 검색
     * Eureka Discovery를 거쳐 이 컨트롤러로 도달한 데이터를 sysout으로 확인
     * 
     * @param keyword 검색어
     * @return 검색 결과
     */
    @GetMapping("/search")
    public ResponseEntity<Messenger> searchByKeyword(@RequestParam String keyword) {
        System.out.println("========================================");
        System.out.println("=== PlayerController: 검색 요청 수신 ===");
        System.out.println("검색어: " + keyword);
        System.out.println("요청 경로: /player/search");
        System.out.println("========================================");

        // PlayerService를 통해 데이터 조회
        Messenger result = playerService.findByKeyword(keyword);

        System.out.println("=== PlayerController: 검색 결과 ===");
        System.out.println("결과 코드: " + result.getCode());
        System.out.println("결과 메시지: " + result.getMessage());
        if (result.getData() != null) {
            System.out.println("결과 데이터: " + result.getData());
        }
        System.out.println("========================================");

        return ResponseEntity.ok(result);
    }
}
