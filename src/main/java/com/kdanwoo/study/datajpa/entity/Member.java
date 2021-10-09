package com.kdanwoo.study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","username","age"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name ="member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public void changeUsername(String username){
        this.username = username;
    }
}
