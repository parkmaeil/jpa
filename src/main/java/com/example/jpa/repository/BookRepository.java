package com.example.jpa.repository;

import com.example.jpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // 1. public List<Book> bookList(); --> findAll(), save() - insert, update() - update X, findById(Long id)
    // 여기에 JPA를 사용하는 방법 5가지
   //  3. @Query("SQL") - JPQL(1. Entity, 2. Table) : (Java Persistence Query Language) - 사용자정의쿼리
   // 책 가격이 30000이상인 책을 검색
   //@Query("select b from Book b where b.price > :price") - Entity 기준
    @Query(value = "select * from book b where b.price > ?1", nativeQuery = true)  // Table 기준
   List<Book> getByPrice(int price);

    // ex) 책제목과 저자가 일치하는 책을 검색하는 SQL - 1권
    // 1.   쿼리메서드
    public Book findByTitleAndAuthor(String title, String author);
    // 2.   JPQL
    //@Query("select b Book b where b.title=:title and b.author=:author")
    @Query(value = "select * Book b where b.title=?1 and b.author=?2", nativeQuery = true)
    public Book getByTitleAndAuthor(String title,  String author);

    // 4. QueryDsl - 고급 ?
    //public Book getByName(String name); // Hibernate ---->  SQL X
    // 2. 쿼리메서드(규칙을 가지고 있는 메서드)
    //public Book findByTitleAndAuthor(String title, String author);
    // Hibernate ---->  select b.* from Book b where b.title=:title and b.author=:author
}
/*
  public class SqlSessionFactoryBean implements  BookMapper{

  }
 public class EntityManagerFactory implements  BookRepository{

  }

 */