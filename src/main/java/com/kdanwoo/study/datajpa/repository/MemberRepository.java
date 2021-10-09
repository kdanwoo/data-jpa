package com.kdanwoo.study.datajpa.repository;

import com.kdanwoo.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name="Member.findByUsername") //네임드쿼리 사용시
    List<Member> findByUsername(@Param("username") String username);
}
