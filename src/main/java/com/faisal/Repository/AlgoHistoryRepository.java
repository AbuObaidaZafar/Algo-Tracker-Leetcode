package com.faisal.Repository;

import com.faisal.model.AlgoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgoHistoryRepository extends JpaRepository<AlgoHistory, Long> {
}
