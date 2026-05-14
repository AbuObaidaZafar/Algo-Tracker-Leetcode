package com.faisal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "algo_history")
@Getter
@Setter
@NoArgsConstructor
public class AlgoHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false, length = 5000)
    private String submittedCode;

    @Column(nullable = false)
    private String timeComplexity;

    @Column(nullable = false)
    private String spaceComplexity;

    @Column(nullable = false)
    private String efficiencyRating;

    @Column(length = 2000)
    private String optimizationSuggestion;

    private LocalDateTime createdAt = LocalDateTime.now();
}
