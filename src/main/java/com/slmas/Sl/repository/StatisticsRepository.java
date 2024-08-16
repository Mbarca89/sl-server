package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;

import java.util.Date;

public interface StatisticsRepository {
    Statistics getStatistics(Date startDate, Date endDate) throws RepositoryException;
}
