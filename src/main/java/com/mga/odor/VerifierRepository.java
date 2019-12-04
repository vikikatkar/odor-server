package com.mga.odor;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifierRepository extends JpaRepository<Verifier, Integer> {
    List<Verifier> findByEmailHash(Integer emailHash);
}
