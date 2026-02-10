package com.ubisam.example.helloes;
// 패키지명은 소문자로만  작성하는 것이 관례입니다.

import org.springframework.data.jpa.repository.JpaRepository;


public interface HelloRepository extends JpaRepository<Hello, Long> {
    
}
