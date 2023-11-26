package uz.second.project.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.second.project.models.Book;
import uz.second.project.models.Person;;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person person);
    Optional<Book> findById(int id);
    Page<Book>findAll(Pageable var1);
    List<Book> findByNameStartingWith(String name);

}