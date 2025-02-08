package org.zerock.libraryapp.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {//JPA는 BOOK에 관한 것이고 id타입은 Long타입이다

    Optional<Book> findByName(String name);//이름을 기준으로 가져올 수 있게끔 함
}
