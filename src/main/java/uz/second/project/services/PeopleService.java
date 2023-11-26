package uz.second.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.second.project.models.Book;
import uz.second.project.models.Person;
import uz.second.project.repositories.PeopleRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }
    public Person findByName(String name) {
        Optional<Person> foundPerson = peopleRepository.findByName(name);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        //person.setCreatedAt(new Date());

        peopleRepository.save(person);

    }
    @Transactional
    public boolean existsByName(String name){
        return peopleRepository.existsByName(name);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public Person findByItems(ArrayList<Book> books){
       return peopleRepository.findByBooksIn(books);
    }
}
