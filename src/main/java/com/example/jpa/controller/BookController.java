package com.example.jpa.controller;

import com.example.jpa.entity.Book;
import com.example.jpa.repository.BookRepository;
import com.example.jpa.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller // View -> forward, redirect, @ResponseBody  JSON
@RestController
@RequestMapping("/api") // <--- 외부경로 : CORS(해제)
@RequiredArgsConstructor
public class BookController {

        private final BookService bookService;
        private  final BookRepository bookRepository;
        // GET : http://localhost:8081/api/book : OpenAPI <--Key발급(JWT)-------->
    @GetMapping("/book")
    public ResponseEntity<?> findAll(){
            return new ResponseEntity<>(bookService.getList(), HttpStatus.OK); // 200
    }

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book){
         return new ResponseEntity<>(bookService.register(book), HttpStatus.CREATED) ;// 201
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
         try{
             return new ResponseEntity<>(bookService.getById(id), HttpStatus.OK);
         }catch(Exception e){
             e.printStackTrace();
             return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
         }
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){ // title, price
        try{
            Book b=bookService.update(id, book);
            return ResponseEntity.ok(b); // 200
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try{
            bookService.getByDelete(id);
            return ResponseEntity.ok("OK"); // 200
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build(); // 404
        }
    }
    @GetMapping("/book/price/{price}")
    public ResponseEntity<?> getByPrice(@PathVariable int price){
          return new ResponseEntity<>(bookRepository.getByPrice(price), HttpStatus.OK);
    }
}
