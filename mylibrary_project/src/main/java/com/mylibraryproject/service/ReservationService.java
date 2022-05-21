package com.mylibraryproject.service;

import com.mylibraryproject.model.Book;
import com.mylibraryproject.model.Reservation;
import com.mylibraryproject.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public void createReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void removeReservation(Book book) {
        book.setReservation(null);
    }
}
