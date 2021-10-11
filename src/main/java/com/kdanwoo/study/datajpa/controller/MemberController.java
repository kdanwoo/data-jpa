package com.kdanwoo.study.datajpa.controller;

import com.kdanwoo.study.datajpa.dto.MemberDto;
import com.kdanwoo.study.datajpa.entity.Member;
import com.kdanwoo.study.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5) Pageable pageable) {
        //엔티티를 API로 노출하면 다양한 문제가 발생
        //Page는 map() 을 지원해서 내부 데이터를 다른 것으로 변경할 수 있다.
        Page<Member> page = memberRepository.findAll(pageable);
        return page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
    }

    @PostConstruct
    public void init(){
        for (int i =0; i<100; i++){
            memberRepository.save(new Member("userA"+i,i));
        }

    }
}
