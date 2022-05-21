package com.mylibraryproject.controller;

import com.mylibraryproject.model.Author;
import com.mylibraryproject.model.Book;
import com.mylibraryproject.model.BookStatus;

import com.mylibraryproject.model.Reservation;
import com.mylibraryproject.service.BookService;
import com.mylibraryproject.service.LoginService;
import com.mylibraryproject.service.UserService;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.io.Serializable;

import java.util.List;

@Getter
@Setter
@ManagedBean
public class BookController implements Serializable {
    private static final long serialVersionUID = 2L;

    @ManagedProperty("#{authorController}")
    private AuthorController authorController;
    @ManagedProperty("#{bookService}")
    private BookService bookService;
    @ManagedProperty("#{loginService}")
    private LoginService loginService;

    private List<Author> authorList;
    private List<Book> bookList;
    private Book book;
    private List<Book> reservedBookList;
    private Long reservedBookNumber;
    private List<Reservation> reservationList;

    @PostConstruct
    public void loadBooks() {
        if (UserService.user == null) {
            loginService.loginCheck();
        } else {
            bookList = bookService.findAllBooks();
            authorList = authorController.findAllAuthors();
            findReservedBooksNumber();
            book = new Book();
        }
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getOneBook(Long id) {
        book = bookService.findBook(id);
        return book;
    }

    public void saveBook(Book book) {
        if (bookService.findBook(book.getId()) == null)
            book.setBookStatus(BookStatus.FREE);
        bookService.saveBook(book);
        bookList = bookService.findAllBooks();
    }

    public void deleteBook(Book book) {
        if (book.getBookStatus() == BookStatus.RESERVED) {
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "You can not delete a reserved book!", null));
        } else {
            bookService.deleteBook(book);
            bookList = bookService.findAllBooks();
        }
    }

    public void setSelectedBook(Book book) {
        this.book = book;
        System.out.println("book selected : " + book);
    }

    public void clear() {
        book = new Book();
    }

    public void findReservedBooksNumber() {
        reservedBookNumber = bookService.findReservedBooksNumber(bookList);
    }

    public void findReservationsOfBook(Book book) {
        reservationList = bookService.findReservationsOfBook(book.getId());
    }
}
