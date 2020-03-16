package com.test.prototype.repository;

import com.test.prototype.domain.GIDUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GIDUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GIDUserRepository extends JpaRepository<GIDUser, Long> {
}
