package com.example.movie.entity;

import com.example.movie.constant.MemberRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "movie_member")
public class Member extends BaseEntity {
    // table 명 movie_member
    // mid(id, 시퀀스)
    // email, password, nickname, MemberRole => string

    @Id
    @SequenceGenerator(name = "movie_member_seq_gen", sequenceName = "movie_member_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_member_seq_gen")
    private Long mid;

    private String email;

    private String password;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

}