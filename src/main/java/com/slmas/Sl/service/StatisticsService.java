package com.slmas.Sl.service;

import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.Date;

public interface StatisticsService {
    Statistics getStatistics(Date startDate, Date endDate) throws RepositoryException;
}
