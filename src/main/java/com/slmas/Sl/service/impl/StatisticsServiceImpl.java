package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.StatisticsRepository;
import com.slmas.Sl.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class StatisticsServiceImpl implements StatisticsService {

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    private StatisticsRepository statisticsRepository;
    public Statistics getStatistics(Date startDate, Date endDate) throws RepositoryException {
        return statisticsRepository.getStatistics(startDate, endDate);
    }
}
