package com.booking.service;

import com.booking.entity.Booking;
import com.booking.entity.BookingStatus;
import com.booking.repository.BookingRepository;
import com.booking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookingService {

    private static final int ROOM_NUMBER_MAX_LENGTH = 10;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepository.findBookingsWithClient();
    }

    public Booking addBooking(Integer clientId, Booking data) {
        if (clientId == null || !clientRepository.existsById(clientId)) {
            throw new BusinessException("Выберите существующего клиента");
        }
        validate(data);

        Booking booking = new Booking();
        booking.setClient(clientRepository.getReferenceById(clientId));
        booking.setCheckIn(data.getCheckIn());
        booking.setCheckOut(data.getCheckOut());
        booking.setRoomNumber(data.getRoomNumber());
        booking.setStatus(BookingStatus.created);
        booking.setBookedAt(LocalDateTime.now());
        return bookingRepository.saveAndFlush(booking);
    }

    public Booking updateBooking(Integer id, Booking data) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Бронирование с id " + id + " не найдено"));

        validate(data);
        if (data.getStatus() == null) {
            throw new BusinessException("Укажите статус бронирования");
        }

        existing.setCheckIn(data.getCheckIn());
        existing.setCheckOut(data.getCheckOut());
        existing.setRoomNumber(data.getRoomNumber());
        existing.setStatus(data.getStatus());
        return bookingRepository.saveAndFlush(existing);
    }

    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }

    private void validate(Booking b) {
        if (b.getCheckIn() == null || b.getCheckOut() == null) {
            throw new BusinessException("Укажите даты заезда и выезда");
        }
        if (!b.getCheckOut().isAfter(b.getCheckIn())) {
            throw new BusinessException("Дата выезда должна быть позже даты заезда");
        }

        String room = b.getRoomNumber() == null ? "" : b.getRoomNumber().trim();
        if (room.isEmpty()) {
            throw new BusinessException("Укажите номер комнаты");
        }
        if (room.length() > ROOM_NUMBER_MAX_LENGTH) {
            throw new BusinessException("Номер комнаты не длиннее " + ROOM_NUMBER_MAX_LENGTH + " символов");
        }
        b.setRoomNumber(room);
    }
}
