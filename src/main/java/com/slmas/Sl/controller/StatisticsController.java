package com.slmas.Sl.controller;

import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.dto.response.TicketResponseDto;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/statistics")
public class StatisticsController {

    StatisticsService statisticsService;
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/getStatistics")
    public ResponseEntity<?> getStatistics (@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            Statistics response = statisticsService.getStatistics(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RepositoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
