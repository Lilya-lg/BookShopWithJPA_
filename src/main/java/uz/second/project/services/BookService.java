package uz.second.project.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import uz.second.project.models.Book;
import uz.second.project.models.Person;
import uz.second.project.repositories.BookRepository;
import uz.second.project.repositories.PeopleRepository;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(int page,int books_per_page,boolean sort){
        if(sort)
        return bookRepository.findAll(PageRequest.of(page,books_per_page, Sort.by("yearOfBook"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page,books_per_page)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }


    @Transactional
    public void save(Book book) {
        //person.setCreatedAt(new Date());

        bookRepository.save(book);
    }

    @Transactional
    public void update(int id) {
        Optional<Book> updatedBook = bookRepository.findById(id);
        if(updatedBook.isPresent()) {
            updatedBook.get().setOwner(null);
        }

    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }
    @Transactional
    public void updateOwner(Person person,Book updatedBook){
        updatedBook.setCreatedAt(new Date());
        updatedBook.setOwner(person);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public List<Book> findByOwner(Person person){
        List<Book> books = bookRepository.findByOwner(person);
        if(books.isEmpty()){
            return Collections.emptyList();
        }
        else {
            for (Book f : bookRepository.findByOwner(person)) {
                Hibernate.initialize(f.getDays());
                if (f.getDays() > 10)
                    f.setExpired(true);
                else
                    f.setExpired(false);
            }
            return bookRepository.findByOwner(person);
        }
    }
    @Transactional
    public List<Book> findByNameStartingWith(String name){
        return bookRepository.findByNameStartingWith(name);
    }
    @Transactional
    Boolean checkByDate(long days){
        if(days<10)
        return false;
        else
            return true;
    }
}
