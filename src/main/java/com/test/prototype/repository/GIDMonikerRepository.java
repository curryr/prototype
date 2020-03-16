package com.test.prototype.repository;

import com.test.prototype.domain.GIDMoniker;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GIDMoniker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GIDMonikerRepository extends JpaRepository<GIDMoniker, Long> {
}
