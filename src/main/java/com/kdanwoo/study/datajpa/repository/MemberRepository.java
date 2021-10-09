package com.kdanwoo.study.datajpa.repository;

import com.kdanwoo.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);



    //먼저 네임드쿼리를 찾아서 실행하고 (엔티티.해당메서드 명)-> 없으면 메소드쿼리 형태로 사용하기떄문에 바로 아랫줄 주석달아도 돌아간다
    @Query(name="Member.findByUsername") //네임드쿼리 사용시, 네임드쿼리는 실무에서 거의 사용안함
    List<Member> findByUsername(@Param("username") String username);
}
