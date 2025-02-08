package org.zerock.libraryapp.dto.book.request;

public class BookLoanRequest { //HTTP 스펙기반

    private String userName;
    private String bookName;

    //getter
    public String getUserName() {
        return userName;
    }

    public String getBookName() {
        return bookName;
    }
}
