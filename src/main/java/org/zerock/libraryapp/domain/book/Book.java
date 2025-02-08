package org.zerock.libraryapp.domain.book;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 255, name = "name")//column정의
    private String name;

    protected Book() {
        //JPA경우 엔티티를 데이터베이스와 매핑하기 위해 내부적으로 객체를 생성할 때 기본 생성자를 필요
        //기본 생성자는 객체를 생성할 때 아무 값도 전달하지 않아도 호출이 가능한 생성자입니다.
        //JPA의 기본 생성자는 필수적이지만, 개발자가 직접 호출하지 않도록 제한하고 싶을 때 protected로 선언
    }

    public Book(String name) {//id는 자동 생성이라 name생성자를 만듬
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException(String.format("잘못된 네임(%s)이 들어왔습니다",name));
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
