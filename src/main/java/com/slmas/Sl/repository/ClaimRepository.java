package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Claim;
import java.time.LocalDate;
import java.util.List;

public interface ClaimRepository {
    List<Claim> findByUserIdAndDate(Long userId, LocalDate date);
    Claim create(Claim claim);
    Claim update(Claim claim);
}
