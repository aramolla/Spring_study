package org.zerock.libraryapp.service.book;

import jakarta.persistence.Id;
//import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.libraryapp.domain.book.Book;
import org.zerock.libraryapp.domain.book.BookRepository;
import org.zerock.libraryapp.domain.user.User;
import org.zerock.libraryapp.domain.user.UserRepository;
import org.zerock.libraryapp.domain.user.loanhistory.UserLoanHistory;
import org.zerock.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository;
import org.zerock.libraryapp.dto.book.request.BookCreateRequest;
import org.zerock.libraryapp.dto.book.request.BookLoanRequest;
import org.zerock.libraryapp.dto.book.request.BookReturnRequest;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository; //의존성 추가
    private final UserRepository userRepository;

    public BookService(
            BookRepository bookRepository,
            UserLoanHistoryRepository userLoanHistoryRepository,
            UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
        this.userRepository = userRepository;
        // 매게변수도 추가함으로서 스프링빈으로부터 주입을 받음
    }

    @Transactional
    public void saveBook(BookCreateRequest request){
        bookRepository.save(new Book(request.getName()));//JPA를 이용해 SQL작성없이 책 save
    }

    @Transactional //서비스 객체 역할인 트랜젝션 빼먹지 말기
    public void loanBook(BookLoanRequest request){ //대출 중인지 아닌지 확인
        // 1. 책 정보 가져옴(책 이름 기준)
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new); //책 없는 경우 예외
        // 2. 대출 여부 확인
        // 3. 대출 중 -> 예외 발생시키기
        if(userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(),false)) {//false: 대출 불가
            throw new IllegalArgumentException("이미 대출된 책입니다");
        }

        //4. 유저 정보를 가져온다
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new); //마찬가지로 오타나 없는 이름일 경우 예외 처리

        // 5. 유저 정보와 책 정보를 기반으로 UserLoanHistory객체에 저장
//        userLoanHistoryRepository.save(new UserLoanHistory(user, book.getName()));//isreturn자리는 위에서 검증됐으니 무조건 false
        user.loanBook(book.getName()); //위 코드 리팩토링
    }

    @Transactional
    public void returnBook(BookReturnRequest request){
        // 유저 정보 들고 오기
        User user = userRepository.findByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        //유저 아이디와 책 이름을 통해서 대출 기록을 찾기
//        UserLoanHistory history = userLoanHistoryRepository.findByUserIdAndBookName(user.getId(), request.getBookName())
//                .orElseThrow(IllegalArgumentException::new);
//        history.doReturn(); //history객체의 isRturn값이 True로 바뀌며 반납 처리가 완료됨
        user.returnBook(request.getBookName());

    }
}
