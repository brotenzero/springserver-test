package com.brobrown.soccerservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 데이터베이스 초기화 및 테이블 생성 확인
 * 애플리케이션 시작 시 Entity 스키마 기반으로 테이블이 생성되었는지 확인
 */
@Slf4j
@Component
@Order(1) // 다른 초기화 로직보다 먼저 실행
public class DatabaseInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void run(String... args) {
        log.info("=== 데이터베이스 초기화 확인 시작 ===");
        
        try {
            // PostgreSQL에 생성된 테이블 목록 조회
            String sql = "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema = 'public' " +
                        "ORDER BY table_name";
            
            Query query = entityManager.createNativeQuery(sql);
            @SuppressWarnings("unchecked")
            List<String> tables = query.getResultList();
            
            log.info("생성된 테이블 개수: {}", tables.size());
            log.info("생성된 테이블 목록:");
            tables.forEach(table -> log.info("  - {}", table));
            
            // 예상되는 테이블 확인
            String[] expectedTables = {"player", "team", "stadium", "schedule"};
            for (String expectedTable : expectedTables) {
                if (tables.contains(expectedTable)) {
                    log.info("✓ 테이블 '{}' 생성 확인됨", expectedTable);
                } else {
                    log.warn("✗ 테이블 '{}' 생성되지 않음", expectedTable);
                }
            }
            
            log.info("=== 데이터베이스 초기화 확인 완료 ===");
        } catch (Exception e) {
            log.error("데이터베이스 초기화 확인 중 오류 발생: {}", e.getMessage(), e);
        }
    }
}

