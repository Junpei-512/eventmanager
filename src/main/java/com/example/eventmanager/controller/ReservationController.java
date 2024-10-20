package com.example.eventmanager.controller;

import com.example.eventmanager.entity.Reservation;
import com.example.eventmanager.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    // 予約の全リストを取得
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // 予約をIDで取得
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return ResponseEntity.ok(reservation);
    }
    @GetMapping("/reservations")
    public String getReservations(Model model) {
        List<Reservation> reservations = reservationRepository.findAll();
        model.addAttribute("reservations", reservations);
        return "reservations"; // Thymeleafテンプレートファイル名（拡張子不要）
    }
        @PostMapping("/reservations")
    public String createReservation(@ModelAttribute Reservation reservation) {
        reservationRepository.save(reservation);
        return "redirect:/reservations";
    }

    @GetMapping("/reservations/new")
    public String showCreateForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "create_reservation";
    }
    // 予約情報を更新
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }

        reservation.setUser(reservationDetails.getUser());
        reservation.setEvent(reservationDetails.getEvent());
        reservation.setReservationDate(reservationDetails.getReservationDate());

        Reservation updatedReservation = reservationRepository.save(reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    // 予約を削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
