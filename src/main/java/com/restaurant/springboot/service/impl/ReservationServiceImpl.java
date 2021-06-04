package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.ReservationDto;
import com.restaurant.springboot.domain.entity.Reservation;
import com.restaurant.springboot.domain.entity.RestaurantTable;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.repository.ReservationRepository;
import com.restaurant.springboot.domain.repository.RestaurantTableRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationServiceImpl implements ReservationService {

    public static final long HOUR = 3600 * 1000;
    public static final int CLOSING_TIME = 1;   // godz. 01.00
    public static final int OPENING_TIME = 8;   // godz 08.00
    public static final int MAX_BOOKING_HOUR = 8;
    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, RestaurantTableRepository restaurantTableRepository,
                                  UserRepository userRepository, JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.reservationRepository = reservationRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean createReservation(ReservationDto reservationDto) {

        if (reservationDto.getUserId() == null || reservationDto.getStartBooking() == null ||
                reservationDto.getBookingTime() == null || reservationDto.getNumberOfTableSeats() == null) return false;

        User user = userRepository.findById(reservationDto.getUserId()).orElse(null);
        if (user == null) return false;
        List<RestaurantTable> certainTables = getTables(reservationDto.getNumberOfTableSeats());

        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date startBooking = null;
        try {
            startBooking = simpleDateFormat.parse(reservationDto.getStartBooking());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!checkAvailabilityOfDate(startBooking, reservationDto.getBookingTime())) return false;

        Date endBooking = new Date(Objects.requireNonNull(startBooking).getTime() + (reservationDto.getBookingTime() * HOUR));

        for (RestaurantTable restaurantTable : certainTables) {
            int currentNumberTableReservation = restaurantTable.getReservations().size();
            Date lastTableReservation =  restaurantTable.getReservations().isEmpty()
                    ? null
                    : restaurantTable.getReservations().get(currentNumberTableReservation - 1).getEndBooking();
            if (lastTableReservation == null || startBooking.after(lastTableReservation)) {
                Reservation newReservation = new Reservation(startBooking, endBooking, user, restaurantTable);
                reservationRepository.save(newReservation);

                sendBookingEmail(reservationDto, user);

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkAvailabilityOfDate(Date startBooking, Integer time) {

        Calendar cal = Calendar.getInstance();
        Date endBooking = new Date(Objects.requireNonNull(startBooking).getTime() + (time * HOUR));
        cal.setTime(endBooking);
        int endHours = cal.get(Calendar.HOUR_OF_DAY);

        return time <= MAX_BOOKING_HOUR && (endHours < CLOSING_TIME || endHours >= OPENING_TIME);
    }

    @Override
    public List<RestaurantTable> getTables(Integer seats) {
        return restaurantTableRepository.findAllBySeatsNumber(seats);
    }

    @Override
    public boolean deleteLastUserReservation(Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) return false;

        List<Reservation> allSortedUserReservation = reservationRepository.findAllByClientOrderByEndBookingDesc(user);

        if (allSortedUserReservation.isEmpty()) return false;

        Reservation lastReservation = allSortedUserReservation.get(0);
        reservationRepository.delete(lastReservation);
        return true;
    }

    @Override
    public void sendBookingEmail(ReservationDto reservationDto, User user) {
        Context context = new Context();
        context.setVariable("client", reservationDto.getClientName() + " " + reservationDto.getClientSurname());
        context.setVariable("bookingDate", reservationDto.getStartBooking());
        context.setVariable("time", reservationDto.getBookingTime());
        context.setVariable("seats", reservationDto.getNumberOfTableSeats());

        String html = templateEngine.process("Reservation", context);

        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(user.getEmail());
            helper.setFrom("restaurant.toik2021@gmail.com");
            helper.setSubject("Restaurant App - Potwierdzenie rezerwacji");
            helper.setSentDate(new Date());
            helper.setText(html, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }
}
