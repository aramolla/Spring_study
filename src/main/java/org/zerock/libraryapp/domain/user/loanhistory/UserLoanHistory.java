package org.zerock.libraryapp.domain.user.loanhistory;

import jakarta.persistence.*;
import org.zerock.libraryapp.domain.user.User;

@Entity //테이블에 매핑되는 객체
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//고유번호
    private Long id = null;

//    private long userId;
    @ManyToOne
    private User user;

    private String bookName;

    private Boolean isReturn; //int도 가능하지만 우리는 0,1만 사용할 것이기 떄문에

    protected UserLoanHistory() {

    }

    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }

    public void doReturn(){
        this.isReturn = true;
    }

    public String getBookName() {
        return bookName;
    }
}
