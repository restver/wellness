package com.studyai.wellness.repository;

import com.studyai.wellness.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Metric entity.
 *
 * <p>This repository provides data access methods for Metric entities.</p>
 */
@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {

    /**
     * Find all metrics for a specific user.
     *
     * @param userId the user ID
     * @return list of metrics for the user
     */
    List<Metric> findByUserId(Long userId);

    /**
     * Find the latest metrics for a specific user.
     *
     * @param userId the user ID
     * @return list of latest metrics
     */
    @Query("SELECT m FROM Metric m WHERE m.user.id = :userId ORDER BY m.recordDate DESC")
    List<Metric> findLatestMetricsByUserId(@Param("userId") Long userId);

    /**
     * Find metrics by record date.
     *
     * @param userId the user ID
     * @param recordDate the record date
     * @return list of metrics for the date
     */
    List<Metric> findByUserIdAndRecordDate(Long userId, String recordDate);
}
