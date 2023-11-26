package uz.second.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uz.second.project.models.Book;
import uz.second.project.models.Person;
import uz.second.project.services.BookService;
import uz.second.project.services.PeopleService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;
    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }
    @GetMapping()
    public String index(Model model,@RequestParam("page") Optional<Integer> page,
    @RequestParam("books_per_page") Optional<Integer> books_per_page,
                        @RequestParam("sort_by_year") Optional<Boolean> sort_by_year)
    {
        int currentPage = page.orElse(1);
        int size = books_per_page.orElse(6);
        Boolean sort = sort_by_year.orElse(false);
            model.addAttribute("books", bookService.findAll(currentPage-1,size,sort));
        //}
        //else
        //model.addAttribute("books", bookService.findAll(page,books_per_page));
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,@ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("personN",peopleService.findByItems(new ArrayList<>(Collections.singletonList(bookService.findOne(id)))));
        model.addAttribute("people",peopleService.findAll());
        return "books/show";
    }
    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book",bookService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}/delete")
    public String deleteNote(@PathVariable("id") int id){
        bookService.updateOwner(null,bookService.findOne(id));
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String makeAdmin(@ModelAttribute("person") Person person,@PathVariable("id") int id){
        bookService.updateOwner(person,bookService.findOne(id));
        return "redirect:/books";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/search")
    public String search(Model model,@RequestParam(value ="search",required=false) String name) {
       // bookService.save(book);
        //model.addAttribute("books", bookService.findByNameStartingWith(name));
        if(name!=null)
        model.addAttribute("books", bookService.findByNameStartingWith(name));
        model.addAttribute("name", name);
        return "books/search";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }
}
