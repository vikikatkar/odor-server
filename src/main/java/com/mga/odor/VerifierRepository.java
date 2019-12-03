package com.mga.odor;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifierRepository extends JpaRepository<Verifier, Long> {
    List<Report> findByEmailHash(int emailHash);

}
