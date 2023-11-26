package uz.second.project.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message="Name should not be empty")
    @Size(min=2,max=30,message = "Name should be between 2 and 30")
    @Column(name = "name")
    private String name;
    @Column(name = "author_name")
    private String author_name;
    @Column(name = "yearOfBook")
    private int yearOfBook;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Transient
    private int days;
    @Transient
    private boolean expired;
    @ManyToOne
    @JoinColumn(name="person_id",referencedColumnName = "id")
    private Person owner;
    public Book() {
    }

    public Book(int id, String name, String author_name, int yearOfBook) {
        this.id = id;
        this.name = name;
        this.author_name = author_name;
        this.yearOfBook = yearOfBook;
    }

    public int getId() {
        return id;
    }






    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public int getYearOfBook() {
        return yearOfBook;
    }

    public void setYearOfBook(int yearOfBook) {
        this.yearOfBook = yearOfBook;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getDays(){
        if(createdAt!=null) {
            long diffInMillies = Math.abs(new Date().getTime() - createdAt.getTime());
            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return days;
        }
        else
            return 0;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
