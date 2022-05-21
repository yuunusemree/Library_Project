package com.mylibraryproject.service;

import com.mylibraryproject.model.Book;
import com.mylibraryproject.model.BookStatus;
import com.mylibraryproject.model.Users;
import com.mylibraryproject.repository.BookRepository;
import com.mylibraryproject.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public static Users user;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public void saveUser(Users user) {
        userRepository.save(user);
    }

    public Users getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    public void orderBook(Book book, Users user) throws NotFoundException {
        Optional<Users> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User does not exist");
        }
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (!optionalBook.isPresent()) {
            throw new NotFoundException("Book does not exist");
        }
        if (optionalBook.get().getUser() != null) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "You can not reserve this book!", null));
        } else {
            book.setUser(user);
            book.setBookStatus(BookStatus.RESERVED);
            bookRepository.save(book);
            user.setBooks(book);
            saveUser(user);
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Book is reserved successfully!", null));
        }
    }

    public void giveBackBook(Book book, Users user) throws NotFoundException {
        Optional<Users> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User does not exist");
        }
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (!optionalBook.isPresent()) {
            throw new NotFoundException("Book does not exist");
        }
        if (optionalBook.get().getUser() == null) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "You can not give back this book!", null));
        } else {
            user.removeBook(book);
            saveUser(user);
            book.setUser(null);
            book.setBookStatus(BookStatus.FREE);
            bookRepository.save(book);
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Book is given back successfully!", null));
        }
    }

    public List<Book> getUserBooks(Users user) throws NotFoundException {
        Optional<Users> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User does not exist!");
        }
        return user.getBooks();
    }

}
