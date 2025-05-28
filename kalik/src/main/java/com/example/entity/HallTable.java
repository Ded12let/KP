package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "halltables")
public class HallTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tableNumber")
    private int tableNumber;

    @Column(name = "zone")
    private String zone;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "hasConsole")
    private Integer hasConsole; // используем Integer вместо int для поддержки null

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Integer getHasConsole() {
        return hasConsole;
    }

    public void setHasConsole(Integer hasConsole) {
        this.hasConsole = hasConsole;
    }
}