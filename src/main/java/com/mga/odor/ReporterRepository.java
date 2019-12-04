package com.mga.odor;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporterRepository extends JpaRepository<Reporter, Integer> {
    List<Reporter> findByEmailHash(Integer emailHash);
}
