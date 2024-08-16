package com.slmas.Sl.domain;

import java.util.Map;

public class Statistics {
    Map<String, Integer> ticketsPerArea;
    Double averageResponseTime;
    Integer totalTickets;
    Integer todayTickets;
    Map<String, Integer> ticketsByUser;
    Map<String, Integer> ticketsByType;

    public Statistics() {
    }

    public Statistics(Map<String, Integer> ticketsPerArea, Double averageResponseTime, Integer todayTickets, Map<String, Integer> ticketsByUser, Map<String, Integer> ticketsByType) {
        this.ticketsPerArea = ticketsPerArea;
        this.averageResponseTime = averageResponseTime;
        this.todayTickets = todayTickets;
        this.ticketsByUser = ticketsByUser;
        this.ticketsByType = ticketsByType;
    }

    public Map<String, Integer> getTicketsPerArea() {
        return ticketsPerArea;
    }

    public void setTicketsPerArea(Map<String, Integer> ticketsPerArea) {
        this.ticketsPerArea = ticketsPerArea;
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

    public Map<String, Integer> getTicketsByUser() {
        return ticketsByUser;
    }

    public void setTicketsByUser(Map<String, Integer> ticketsByUser) {
        this.ticketsByUser = ticketsByUser;
    }

    public Map<String, Integer> getTicketsByType() {
        return ticketsByType;
    }

    public void setTicketsByType(Map<String, Integer> ticketsByType) {
        this.ticketsByType = ticketsByType;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }
}
