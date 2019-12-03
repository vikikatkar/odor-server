package com.mga.odor;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifiedReportRepository extends JpaRepository<VerifiedReport, Long> {
    List<Report> findByReporterId(int reporterId);
    List<Report> findByVerifierId(int verifierId);

}
