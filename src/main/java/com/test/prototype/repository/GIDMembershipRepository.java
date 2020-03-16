package com.test.prototype.repository;

import com.test.prototype.domain.GIDMembership;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GIDMembership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GIDMembershipRepository extends JpaRepository<GIDMembership, Long> {
}
