package com.example.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "hookahOrders")
public class HookahOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String base;

    @Column(nullable = false)
    private String strength;

    @Column(nullable = false)
    private String bowl;

    @Column(nullable = false)
    private String coal;

    @ManyToMany
    @JoinTable(
            name = "hookahOrderFlavor",
            joinColumns = @JoinColumn(name = "hookahId"),
            inverseJoinColumns = @JoinColumn(name = "flavorId")
    )
    private Set<Flavor> flavors;

    @ManyToMany
    @JoinTable(
            name = "hookahOrderAdditive",
            joinColumns = @JoinColumn(name = "hookahId"),
            inverseJoinColumns = @JoinColumn(name = "additiveId")
    )
    private Set<Additive> additives;

    @ManyToMany(mappedBy = "hookahs")
    private Set<Booking> bookings;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getBowl() {
        return bowl;
    }

    public void setBowl(String bowl) {
        this.bowl = bowl;
    }

    public String getCoal() {
        return coal;
    }

    public void setCoal(String coal) {
        this.coal = coal;
    }

    public Set<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(Set<Flavor> flavors) {
        this.flavors = flavors;
    }

    public Set<Additive> getAdditives() {
        return additives;
    }

    public void setAdditives(Set<Additive> additives) {
        this.additives = additives;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}