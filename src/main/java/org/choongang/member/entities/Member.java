package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.commons.entities.Base;
import org.choongang.member.Authority;

@Data
@Entity
public class Member extends Base {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid;

    @Column(length=80, nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private Authority authority = Authority.USER;

    private boolean enable = true;

}
