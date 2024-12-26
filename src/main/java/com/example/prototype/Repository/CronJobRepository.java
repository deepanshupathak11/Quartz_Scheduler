package com.example.prototype.Repository;

import com.example.prototype.Entity.CronJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CronJobRepository extends JpaRepository<CronJob, Long> {
    List<CronJob> findAllByEnabledTrue();
}

