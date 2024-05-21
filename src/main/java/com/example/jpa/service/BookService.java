package com.example.jpa.service;

import com.example.jpa.entity.Book;
import com.example.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService{

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> getList(){
        // 비즈니스 로직 ~~~, 트렌젝션처리
        return bookRepository.findAll(); //  ---Hibernate---> select b from Book  b
        //findByName(String name) --> select b.title, b.price from Book b where b.name=name
    }

     public Book register(Book book){
         return bookRepository.save(book); // insert SQL
     }

     public Book getById(Long id){
               Optional<Book> optional=bookRepository.findById(id); // O, X
               if(optional.isPresent()){
                     return optional.get(); // Book
               }else{
                   throw new IllegalArgumentException("Book이 존재하지 않음:" + id);
               }
     }
     // 수정하기
    @Transactional // 중요
    public Book update(Long id , Book reqBook){
             Optional<Book> optional=bookRepository.findById(id); // 읽고
             if(optional.isPresent()){
                   Book book=optional.get();  // Db에서 가져온 Book   : 쓰기
                   book.setTitle(reqBook.getTitle()); // 수정동작
                   book.setPrice(reqBook.getPrice()); // 수정동작
                   //return book; // 수정이 이루어진다. -> 더티체킹 , save(book) -> id
                  return bookRepository.save(book);
             } else{
                 throw new IllegalArgumentException("Book이 존재하지 않음:" + id);
             }
    }

    public void getByDelete(Long id){
          bookRepository.deleteById(id);
    }
}
