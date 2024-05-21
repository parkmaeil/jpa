package com.example.jpa.controller;

import com.example.jpa.entity.Book;
import com.example.jpa.entity.Member;
import com.example.jpa.service.BookService;
import com.example.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RouteController {

    private final BookService bookService;
    private final MemberService memberService;
    @GetMapping("/ui/list") // http://localhost:8081/ui/list
    public String bookList(Model model){
        List<Book> list=bookService.getList();
        model.addAttribute("list", list);
        return "book/list"; // list.html
    }

    @GetMapping("/member/register")
    public String register(){
        return "member/register"; //회원가입페이지
    }

    @PostMapping("/member/register")
    public String register(Member member){
          memberService.register(member);
          return "redirect:/ui/list"; // 메인리스트피이지
    }
}
