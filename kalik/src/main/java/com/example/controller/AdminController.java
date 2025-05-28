package com.example.controller;

import com.example.entity.Booking;
import com.example.entity.HookahOrder;
import com.example.entity.User;
import com.example.repository.BookingRepository;
import com.example.repository.HallTableRepository;
import com.example.repository.UserRepository;
import com.example.repository.HookahOrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HookahOrderRepository hookahOrderRepository;
    private final UserRepository userRepository;
    private final HallTableRepository hallTableRepository;
    private final BookingRepository bookingRepository;

    public AdminController(UserRepository userRepository, HallTableRepository hallTableRepository,
                           BookingRepository bookingRepository, HookahOrderRepository hookahOrderRepository) {
        this.userRepository = userRepository;
        this.hallTableRepository = hallTableRepository;
        this.bookingRepository = bookingRepository;
        this.hookahOrderRepository = hookahOrderRepository;
    }

    @GetMapping
    public String adminHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "admin_home";
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin_users";
    }

    @GetMapping("/tables")
    public String allTables(Model model) {
        model.addAttribute("tables", hallTableRepository.findAll());
        return "admin_tables";
    }

    @GetMapping("/bookings")
    public String allBookings(Model model) {
        List<Booking> bookings = bookingRepository.findAll().stream()
            .filter(b -> !b.isDeleted())
            .toList();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        var upcoming = bookings.stream().filter(b -> b.getStartTime().isAfter(now) || b.getStartTime().isEqual(now)).toList();
        var past = bookings.stream().filter(b -> b.getEndTime().isBefore(now)).toList();
        model.addAttribute("upcomingBookings", upcoming);
        model.addAttribute("pastBookings", past);
        return "admin_bookings";
    }

    @GetMapping("/hookahs")
    public String showHookahOrders(Model model) {
        List<HookahOrder> hookahOrders = hookahOrderRepository.findAll().stream()
            .filter(h -> !h.isDeleted())
            .toList();
        model.addAttribute("hookahOrders", hookahOrders);
        return "admin_hookahs";
    }

    @PostMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Integer id) {
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
        return "redirect:/admin/bookings";
    }
}