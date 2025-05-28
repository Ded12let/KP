package com.example.controller;

import com.example.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.repository.UserRepository;
import com.example.repository.BookingRepository;
import java.time.LocalDateTime;
import java.util.List;
import com.example.entity.Booking;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public HomeController(UserRepository userRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/user")
    public String userHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        // История и предстоящие бронирования
        var allBookings = bookingRepository.findAllByUserIdOrderByStartTimeDesc(user.getId())
            .stream().filter(b -> !b.isDeleted()).toList();
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookingsList = allBookings;
        var upcoming = bookingsList.stream().filter(b -> b.getStartTime().isAfter(now) || b.getStartTime().isEqual(now)).toList();
        var past = bookingsList.stream().filter(b -> b.getEndTime().isBefore(now)).toList();
        model.addAttribute("upcomingBookings", upcoming);
        model.addAttribute("pastBookings", past);
        return "user";
    }

    @GetMapping("/user/booking")
    public String startBooking(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        model.addAttribute("selectedDate", today); // по умолчанию сегодня

        return "booking_date";
    }

    @GetMapping("/user/history")
    public String userHistory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        var allBookings = bookingRepository.findAllByUserIdOrderByStartTimeDesc(user.getId())
            .stream().filter(b -> !b.isDeleted()).toList();
        LocalDateTime now = LocalDateTime.now();
        List<Booking> bookingsList = allBookings;
        var past = bookingsList.stream().filter(b -> b.getEndTime().isBefore(now)).toList();
        model.addAttribute("pastBookings", past);
        return "user_history";
    }

    @PostMapping("/user/bookings/delete/{id}")
    public String deleteUserBooking(@PathVariable Integer id) {
        var bookingOpt = bookingRepository.findById(id);
        bookingOpt.ifPresent(b -> {
            b.setDeleted(true);
            if (b.getHookahs() != null) {
                for (var hookah : b.getHookahs()) {
                    hookah.setDeleted(true);
                }
            }
            bookingRepository.save(b);
        });
        return "redirect:/user";
    }
}