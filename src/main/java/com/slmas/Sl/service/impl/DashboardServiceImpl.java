package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.dto.response.DashboardTodayResponseDto;
import com.slmas.Sl.service.DashboardService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final JdbcTemplate jdbcTemplate;

    public DashboardServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DashboardTodayResponseDto getTodaySummary() {
        LocalDate today = LocalDate.now();

        List<DashboardTodayResponseDto.RecurringTaskDashboardItemDto> recurringTasks = jdbcTemplate.query(
                "SELECT rt.id, rt.user_id, u.user_name, rt.title, rt.description FROM RecurringTasks rt JOIN Users u ON u.id = rt.user_id ORDER BY u.user_name, rt.title",
                (rs, rowNum) -> {
                    DashboardTodayResponseDto.RecurringTaskDashboardItemDto item = new DashboardTodayResponseDto.RecurringTaskDashboardItemDto();
                    item.setId(rs.getString("id"));
                    item.setUserId(rs.getLong("user_id"));
                    item.setUserName(rs.getString("user_name"));
                    item.setTitle(rs.getString("title"));
                    item.setDescription(rs.getString("description"));
                    return item;
                });

        List<Claim> claims = jdbcTemplate.query(
                "SELECT * FROM Claims WHERE claim_date = ? ORDER BY user_name, title",
                new Object[]{Date.valueOf(today)},
                (rs, rowNum) -> {
                    Claim claim = new Claim();
                    claim.setId(rs.getString("id"));
                    claim.setUserId(rs.getLong("user_id"));
                    claim.setUserName(rs.getString("user_name"));
                    claim.setDate(rs.getDate("claim_date").toLocalDate());
                    claim.setType("reclamo");
                    claim.setTitle(rs.getString("title"));
                    claim.setArea(rs.getString("area"));
                    claim.setClaimant(rs.getString("claimant"));
                    claim.setProblemType(rs.getString("problem_type"));
                    claim.setDescription(rs.getString("description"));
                    claim.setSolution(rs.getString("solution"));
                    return claim;
                });

        List<CompletedWork> completedWorks = jdbcTemplate.query(
                "SELECT * FROM CompletedWorks WHERE work_date = ? ORDER BY user_name, title",
                new Object[]{Date.valueOf(today)},
                (rs, rowNum) -> {
                    CompletedWork completedWork = new CompletedWork();
                    completedWork.setId(rs.getString("id"));
                    completedWork.setUserId(rs.getLong("user_id"));
                    completedWork.setUserName(rs.getString("user_name"));
                    completedWork.setDate(rs.getDate("work_date").toLocalDate());
                    completedWork.setTitle(rs.getString("title"));
                    completedWork.setArea(rs.getString("area"));
                    completedWork.setDescription(rs.getString("description"));
                    return completedWork;
                });

        DashboardTodayResponseDto response = new DashboardTodayResponseDto();
        response.setDate(today);
        response.setRecurringTasks(recurringTasks);
        response.setClaims(claims);
        response.setCompletedWorks(completedWorks);
        return response;
    }
}
