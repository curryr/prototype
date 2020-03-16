package com.test.prototype.repository;

import com.test.prototype.domain.GIDIdentity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GIDIdentity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GIDIdentityRepository extends JpaRepository<GIDIdentity, Long> {
}
