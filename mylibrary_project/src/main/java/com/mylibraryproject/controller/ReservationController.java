package com.mylibraryproject.controller;

import com.mylibraryproject.model.Book;
import com.mylibraryproject.model.Reservation;
import com.mylibraryproject.model.Users;
import com.mylibraryproject.service.ReservationService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ManagedBean
public class ReservationController implements Serializable {
    private static final long serialVersionUID = 12L;

    @ManagedProperty("#{reservationService}")
    private ReservationService reservationService;
    @ManagedProperty("#{bookController}")
    private BookController bookController;

    private Reservation reservation;

    public void createReservation(Book book, Users user) {
        reservation = new Reservation();
        reservation.setBookID(book.getId());
        reservation.setUserID(user.getId());
        reservation.setReservationDate(Date.valueOf(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))));
        reservation.setReturnDate(Date.valueOf(reservation.getReservationDate().toLocalDate().plusDays(7L)));
        reservationService.createReservation(reservation);
        book.setReservation(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservationService.removeReservation(bookController.getOneBook(reservation.getBookID()));
    }
}
