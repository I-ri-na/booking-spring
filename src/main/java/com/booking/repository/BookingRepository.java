package com.booking.repository;

import com.booking.entity.Booking;
import com.booking.entity.BookingStatus;
import com.booking.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
       
    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.client ORDER BY b.id DESC")
    List<Booking> findBookingsWithClient();
}
