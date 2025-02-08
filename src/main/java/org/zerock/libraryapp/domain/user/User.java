package org.zerock.libraryapp.domain.user;


import jakarta.persistence.*;
import org.zerock.libraryapp.domain.user.loanhistory.UserLoanHistory;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL에서 auto_increment와 동일하다.
    private Long id = null;

    @Column(nullable = false, length = 20, name = "name") // name varchar(20) Column 어노테이션에는 null이 들어갈 수 있는지 여부, 길이 제한, DB에서의 column 이름 등등... 이 들어갈 수 있다. 그리고 User객체의 필드 이름과 DB에서의 name column이름이 같으면 name 매개변수 생략가능하다. 특정 경우(글자 길이의 제한이 없거나, null이 들어가도 상관이 없는 경우)에는 이 어노테이션 자체를 생략할 수도 있다.
    private String name;
    private Integer age;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {}

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() { return id; }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(String bookName){
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName){
        //userLoanHistories중에서 해당 책 이름을 가진 기록 찾기
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))//getBookName함수 getter이용하여 만듬
                .findFirst()//첫번째로 걸리는 것을 찾음
                .orElseThrow(IllegalArgumentException::new);//findFirst은 옵셔날 형태를 반환, 예외처리 해주기
        targetHistory.doReturn();// UserLoanHistory의 반납 함수로 이 함수 안에서 반납처리 해줌
    }
}
