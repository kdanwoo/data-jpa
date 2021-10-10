package com.kdanwoo.study.datajpa.repository;

import com.kdanwoo.study.datajpa.dto.MemberDto;
import com.kdanwoo.study.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    //먼저 네임드쿼리를 찾아서 실행하고 (엔티티.해당메서드 명)-> 없으면 메소드쿼리 형태로 사용하기떄문에 바로 아랫줄 주석달아도 돌아간다
    @Query(name="Member.findByUsername") //네임드쿼리 사용시, 네임드쿼리는 실무에서 거의 사용안함
    List<Member> findByUsername(@Param("username") String username);

    // @Query, 리포지토리 메소드에 쿼리 정의하기
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // new Operation 사용 @Query, 값, DTO 조회하기
    // 주의! DTO로 직접 조회 하려면 JPA의 new 명령어를 사용해야 한다. 그리고 다음과 같이 생성자가 맞는 DTO가 필요하다. (JPA와 사용방식이 동일하다.)
    @Query("select new com.kdanwoo.study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // 컬렉션 바인딩
    // Collection 타입으로 in절 지원
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username);
    Member findMemberByUsername(String username);
    Optional<Member> findOptionalByUsername(String username);

    //쿼리가 복잡해지면 countQuery를 분리해야한다
    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    //spring data jpa의 bulkupdate
    // 벌크 연산은 영속성 컨텍스트에 반영하지 않고 벌크연산을 디비에 바로 때려버리는 상황이 발생함
    // 벌크성 쿼리를 실행하고 나서 영속성 컨텍스트 초기화
    //참고: 벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 때문에, 영속성 컨텍스트에 있는 엔티티의 상태와 DB에 엔티티 상태가 달라질 수 있다.
    //> 권장하는 방안
    //> 1. 영속성 컨텍스트에 엔티티가 없는 상태에서 벌크 연산을 먼저 실행한다.
    //> 2. 부득이하게 영속성 컨텍스트에 엔티티가 있으면 벌크 연산 직후 영속성 컨텍스트를 초기화 한다.
    @Modifying(clearAutomatically = true) // excuteUpdate를 실행하는것.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);



}
