package com.mylibraryproject.controller;

import com.mylibraryproject.model.Author;
import com.mylibraryproject.model.Book;
import com.mylibraryproject.model.Users;
import com.mylibraryproject.service.LoginService;
import com.mylibraryproject.service.UserService;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.util.List;

@Getter
@Setter
@ManagedBean
public class UserController {
    private static final long serialVersionUID = 4L;

    @ManagedProperty("#{userService}")
    private UserService userService;
    @ManagedProperty("#{bookController}")
    private BookController bookController;
    @ManagedProperty("#{loginService}")
    private LoginService loginService;
    @ManagedProperty("#{reservationController}")
    private ReservationController reservationController;

    public Users user;
    private List<Author> authorList;
    private List<Book> bookList;
    private List<Users> userList;
    private List<Book> userBookList;
    private Book book;

    @PostConstruct
    public void initData(){
        if (UserService.user == null) {
            loginService.loginCheck();
        } else {
            this.user = UserService.user;
            authorList = bookController.getAuthorList();
            bookList = bookController.getBookList();
            findAllUsers();
            try {
                userBookList = userService.getUserBooks(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void findAllUsers() {
        userList = userService.findAllUsers();
    }

    public void saveUser(Users user) {
        userService.saveUser(user);
    }

    public void getUserBooks() throws NotFoundException {
        if (UserService.user == null) {
            loginService.loginCheck();
        } else {
            userBookList = userService.getUserBooks(user);
        }
    }

    public void orderBook(Book book) throws NotFoundException {
        userService.orderBook(book, user);
        reservationController.createReservation(book, user);
        authorList = bookController.getAuthorList();
        bookList = bookController.getBookList();
        bookController.findReservedBooksNumber();
        getUserBooks();
    }

    public void giveBackBook(Book book) throws NotFoundException {
        user = this.getUser();
        userService.giveBackBook(book, user);
        book.setReservation(null);
        authorList = bookController.getAuthorList();
        bookList = bookController.getBookList();
        bookController.findReservedBooksNumber();
        bookController.findReservationsOfBook(book);
        getUserBooks();
    }

}
