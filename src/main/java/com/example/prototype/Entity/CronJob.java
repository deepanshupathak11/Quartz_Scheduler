package com.example.prototype.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cron_jobs")
public class CronJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cronExpression;

    @Column(nullable = false)
    private String jobClass;

    @Column(nullable = false)
    private String jobMethod;

    private boolean enabled;
}
