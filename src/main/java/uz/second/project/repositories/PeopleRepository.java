package uz.second.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.second.project.models.Book;
import uz.second.project.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
    Boolean existsByName(String name);

    Person findByBooksIn(ArrayList<Book> books);


}