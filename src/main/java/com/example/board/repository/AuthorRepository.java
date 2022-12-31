package com.example.board.repository;

import com.example.board.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    JpaRepository에 기본적인 CRUD를 포함한 여러가지 기능이 사전 구현돼 있음
//    커스터마이징 하고 싶은 쿼리는 따로 추가하면됨.
//    findByA : 스프링에서 A로 조건을 걸어 조회하는 기능을 제공
//    findByAandB : 이러한 규칙을 가지고 다양한 조회 가능
    Optional<Author> findByEmail(String email);
}
