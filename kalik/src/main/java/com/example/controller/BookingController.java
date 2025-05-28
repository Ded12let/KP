package com.example.controller;

import com.example.entity.Booking;
import com.example.entity.HallTable;
import com.example.entity.User;
import com.example.entity.HookahOrder;
import com.example.repository.BookingRepository;
import com.example.repository.HallTableRepository;
import com.example.repository.FlavorRepository;
import com.example.repository.AdditiveRepository;
import com.example.repository.HookahOrderRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final HallTableRepository hallTableRepository;
    private final BookingRepository bookingRepository;
    private final FlavorRepository flavorRepository;
    private final AdditiveRepository additiveRepository;
    private final HookahOrderRepository hookahOrderRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingController(HallTableRepository hallTableRepository,
                             BookingRepository bookingRepository,
                             FlavorRepository flavorRepository,
                             AdditiveRepository additiveRepository,
                             HookahOrderRepository hookahOrderRepository,
                             UserRepository userRepository) {
        this.hallTableRepository = hallTableRepository;
        this.bookingRepository = bookingRepository;
        this.flavorRepository = flavorRepository;
        this.additiveRepository = additiveRepository;
        this.hookahOrderRepository = hookahOrderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Шаг 1: Выбор даты (начиная с сегодняшнего дня)
     */
    @GetMapping("/date")
    public String selectDate(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "redirect:/login";

        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        return "booking_date";
    }

    /**
     * Шаг 2: Выбор времени (1-часовые слоты, 10:00–22:00)
     */
    @PostMapping("/time")
    public String selectTime(@RequestParam String selectedDate, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "redirect:/login";

        LocalDate date = LocalDate.parse(selectedDate);
        LocalDateTime dayStart = date.atTime(12, 0);
        LocalDateTime dayEnd = date.atTime(0, 0).plusDays(1);

        List<Booking> bookings = bookingRepository.findAllByStartTimeBetween(
                dayStart.minusHours(1), dayEnd.plusHours(1)
        );

        List<Map<String, Object>> timeSlots = new ArrayList<>();
        for (int hour = 12; hour <= 23; hour++) {
            LocalDateTime slotStart = date.atTime(hour, 0);
            LocalDateTime slotEnd = slotStart.plusHours(1);

            boolean occupied = bookings.stream().anyMatch(b ->
                    !(b.getEndTime().isBefore(slotStart) || b.getStartTime().isAfter(slotEnd))
            );

            Map<String, Object> slot = new HashMap<>();
            slot.put("time", slotStart);
            slot.put("occupied", occupied);
            timeSlots.add(slot);
        }

        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("timeSlots", timeSlots);
        return "booking_time";
    }

    /**
     * Шаг 3: Выбор доступного стола
     */
    @PostMapping("/tables")
    public String showTables(@RequestParam String selectedDate,
                             @RequestParam int startHour,
                             @RequestParam int endHour,
                             Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "redirect:/login";

        LocalDate date = LocalDate.parse(selectedDate);
        LocalDateTime start = date.atTime(startHour, 0);
        LocalDateTime end = date.atTime(endHour, 0);

        List<HallTable> tables = hallTableRepository.findAll();
        List<Map<String, Object>> availableTables = new ArrayList<>();

        for (HallTable table : tables) {
            boolean isFree = !bookingRepository.existsByHallTableIdAndStartTimeLessThanAndEndTimeGreaterThan(
                    table.getId(), end, start
            );
            Map<String, Object> tableData = new HashMap<>();
            tableData.put("table", table);
            tableData.put("free", isFree);
            availableTables.add(tableData);
        }

        model.addAttribute("availableTables", availableTables);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("startHour", startHour);
        model.addAttribute("endHour", endHour);
        return "booking_tables";
    }

    // DTO для одного кальяна
    public static class HookahOrderForm {
        private String base;
        private String strength;
        private String bowl;
        private String coal;
        private List<Long> flavors;
        private List<Long> additives;
        // геттеры и сеттеры
        public String getBase() { return base; }
        public void setBase(String base) { this.base = base; }
        public String getStrength() { return strength; }
        public void setStrength(String strength) { this.strength = strength; }
        public String getBowl() { return bowl; }
        public void setBowl(String bowl) { this.bowl = bowl; }
        public String getCoal() { return coal; }
        public void setCoal(String coal) { this.coal = coal; }
        public List<Long> getFlavors() { return flavors; }
        public void setFlavors(List<Long> flavors) { this.flavors = flavors; }
        public List<Long> getAdditives() { return additives; }
        public void setAdditives(List<Long> additives) { this.additives = additives; }
    }

    /**
     * Шаг 4: Создание бронирования
     */
    @PostMapping("/create")
    public String createBooking(@RequestParam String selectedDate,
                                @RequestParam int startHour,
                                @RequestParam int endHour,
                                @RequestParam Integer tableId,
                                @ModelAttribute HookahOrderFormList hookahOrderFormList,
                                Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "redirect:/login";

        LocalDate date = LocalDate.parse(selectedDate);
        LocalDateTime start = date.atTime(startHour, 0);
        LocalDateTime end = date.atTime(endHour, 0);

        HallTable table = hallTableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Стол не найден"));

        boolean conflict = bookingRepository.existsByHallTableIdAndStartTimeLessThanAndEndTimeGreaterThan(
                table.getId(), end, start
        );

        if (conflict) {
            return "redirect:/booking/date?error=conflict";
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setHallTable(table);
        booking.setStartTime(start);
        booking.setEndTime(end);

        Set<HookahOrder> hookahOrders = new HashSet<>();
        for (HookahOrderForm form : hookahOrderFormList.getHookahs()) {
            HookahOrder hookahOrder = new HookahOrder();
            hookahOrder.setBase(form.getBase());
            hookahOrder.setStrength(form.getStrength());
            hookahOrder.setBowl(form.getBowl());
            hookahOrder.setCoal(form.getCoal());
            if (form.getFlavors() != null && !form.getFlavors().isEmpty()) {
                hookahOrder.setFlavors(new HashSet<>(flavorRepository.findAllById(form.getFlavors())));
            }
            if (form.getAdditives() != null && !form.getAdditives().isEmpty()) {
                hookahOrder.setAdditives(new HashSet<>(additiveRepository.findAllById(form.getAdditives())));
            }
            hookahOrderRepository.save(hookahOrder);
            hookahOrders.add(hookahOrder);
        }
        booking.setHookahs(hookahOrders);
        bookingRepository.save(booking);
        return "redirect:/user?success=booking_created";
    }

    public static class HookahOrderFormList {
        private List<HookahOrderForm> hookahs;
        public List<HookahOrderForm> getHookahs() { return hookahs; }
        public void setHookahs(List<HookahOrderForm> hookahs) { this.hookahs = hookahs; }
    }

    @PostMapping("/hookah")
    public String showHookahConstructor(@RequestParam String selectedDate,
                                        @RequestParam int startHour,
                                        @RequestParam int endHour,
                                        @RequestParam Integer tableId,
                                        Model model) {
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("startHour", startHour);
        model.addAttribute("endHour", endHour);
        model.addAttribute("tableId", tableId);
        // Здесь можно добавить загрузку вкусов, добавок и т.д. для конструктора
        return "booking_hookah";
    }

}
