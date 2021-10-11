package com.kdanwoo.study.datajpa.repository;

import com.kdanwoo.study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
