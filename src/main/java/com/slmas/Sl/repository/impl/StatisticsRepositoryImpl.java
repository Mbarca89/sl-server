package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.CountEntry;
import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.StatisticsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

    public StatisticsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    @Override
    public Statistics getStatistics(Date startDate, Date endDate) throws RepositoryException {
        Statistics statistics = new Statistics();

        String GET_BY_AREA = "SELECT area, COUNT(*) as count FROM Tickets WHERE ticket_date BETWEEN ? AND ? GROUP BY area ORDER BY count DESC";
        String GET_AVERAGE_RESPONSE_TIME = "SELECT AVG(TIMESTAMPDIFF(MINUTE, ticket_date, solved_date)) as avgResponseTime FROM Tickets WHERE solved_date IS NOT NULL AND ticket_date BETWEEN ? AND ?";
        String GET_TODAY_TICKETS = "SELECT COUNT(*) FROM Tickets WHERE CAST(ticket_date AS DATE) = CURRENT_DATE";
        String GET_TICKET_COUNT = "SELECT COUNT(*) FROM Tickets WHERE ticket_date BETWEEN ? AND ?";
        String GET_TICKETS_COUNT_BY_USER = "SELECT user_name, COUNT(*) as count FROM Tickets WHERE ticket_date BETWEEN ? AND ? GROUP BY user_name ORDER BY count DESC LIMIT 10";
        String GET_TICKETS_COUNT_BY_TYPE = "SELECT type, COUNT(*) as count FROM Tickets WHERE ticket_date BETWEEN ? AND ? GROUP BY type ORDER BY count DESC LIMIT 10";

        try {
            // Obtener y ordenar tickets por área
            List<CountEntry> ticketsByArea = jdbcTemplate.query(GET_BY_AREA, new Object[]{startDate, endDate}, (rs, rowNum) ->
                    new CountEntry(rs.getString("area"), rs.getInt("count"))
            );

            // Obtener el tiempo promedio de respuesta
            Double averageResponseTime = jdbcTemplate.queryForObject(GET_AVERAGE_RESPONSE_TIME, new Object[]{startDate, endDate}, Double.class);

            // Obtener la cantidad de tickets de hoy
            Integer todayTickets = jdbcTemplate.queryForObject(GET_TODAY_TICKETS, Integer.class);

            // Obtener la cantidad total de tickets
            Integer totalCount = jdbcTemplate.queryForObject(GET_TICKET_COUNT, new Object[]{startDate, endDate}, Integer.class);

            // Obtener y ordenar tickets por usuario
            List<CountEntry> ticketsByUser = jdbcTemplate.query(GET_TICKETS_COUNT_BY_USER, new Object[]{startDate, endDate}, (rs, rowNum) ->
                    new CountEntry(rs.getString("user_name"), rs.getInt("count"))
            );

            // Obtener y ordenar tickets por tipo
            List<CountEntry> ticketsByType = jdbcTemplate.query(GET_TICKETS_COUNT_BY_TYPE, new Object[]{startDate, endDate}, (rs, rowNum) ->
                    new CountEntry(rs.getString("type"), rs.getInt("count"))
            );

            // Asignar los resultados a las propiedades de 'statistics'
            statistics.setTicketsPerArea(ticketsByArea);
            statistics.setAverageResponseTime(averageResponseTime != null ? averageResponseTime : 0.0);
            statistics.setTodayTickets(todayTickets != null ? todayTickets : 0);
            statistics.setTotalTickets(totalCount != null ? totalCount : 0);
            statistics.setTicketsByUser(ticketsByUser);
            statistics.setTicketsByType(ticketsByType);

        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }

        return statistics;
    }


}
