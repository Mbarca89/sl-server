package com.slmas.Sl.domain;

import java.util.List;
import java.util.Map;

public class Statistics {
    private List<CountEntry> ticketsPerArea;
    private List<CountEntry> ticketsByUser;
    private List<CountEntry> ticketsByType;
    private Double averageResponseTime;
    private Integer todayTickets;
    private Integer totalTickets;

    public Statistics() {
    }

    public Statistics(List<CountEntry> ticketsPerArea, List<CountEntry> ticketsByUser, List<CountEntry> ticketsByType, Double averageResponseTime, Integer todayTickets, Integer totalTickets) {
        this.ticketsPerArea = ticketsPerArea;
        this.ticketsByUser = ticketsByUser;
        this.ticketsByType = ticketsByType;
        this.averageResponseTime = averageResponseTime;
        this.todayTickets = todayTickets;
        this.totalTickets = totalTickets;
    }

    public List<CountEntry> getTicketsPerArea() {
        return ticketsPerArea;
    }

    public void setTicketsPerArea(List<CountEntry> ticketsPerArea) {
        this.ticketsPerArea = ticketsPerArea;
    }

    public List<CountEntry> getTicketsByUser() {
        return ticketsByUser;
    }

    public void setTicketsByUser(List<CountEntry> ticketsByUser) {
        this.ticketsByUser = ticketsByUser;
    }

    public List<CountEntry> getTicketsByType() {
        return ticketsByType;
    }

    public void setTicketsByType(List<CountEntry> ticketsByType) {
        this.ticketsByType = ticketsByType;
    }

    public Double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(Double averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public Integer getTodayTickets() {
        return todayTickets;
    }

    public void setTodayTickets(Integer todayTickets) {
        this.todayTickets = todayTickets;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }
}
