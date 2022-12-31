package com.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Setter
@Getter
@Entity
public class Author {

//    Entity로 선언을 할때는, pk가 어떤건지를 정확하게 지정해줘야 한다.
//    Author_basic까지 수행하고 나면 Lombok, Auto-ddl 등으로 변경후 advanced 진행

    //Entity로 선언을 할때는, pk가 어떤건지를 정확하게 지정해줘야 한다.
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Role role;

    @Column
    private LocalDateTime createDate;
}
