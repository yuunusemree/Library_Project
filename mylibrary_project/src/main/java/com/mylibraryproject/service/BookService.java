package com.mylibraryproject.service;

import com.mylibraryproject.model.Book;
import com.mylibraryproject.model.BookStatus;
import com.mylibraryproject.model.Reservation;
import com.mylibraryproject.repository.BookRepository;
import com.mylibraryproject.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Long findReservedBooksNumber(List<Book> reservedBookList) {
        long number = 0;
        for (Book book : reservedBookList) {
            if (book.getBookStatus() == BookStatus.RESERVED) {
                number ++;
            }
        }
        return number;
    }

    public Book findBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
        FacesContext.getCurrentInstance().addMessage
                (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Book saved successfully!", null));
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
        FacesContext.getCurrentInstance().addMessage
                (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Book deleted successfully!", null));
    }

    public List<Reservation> findReservationsOfBook(Long id) {
        return reservationRepository.findAllByBookID(id);
    }

}