package com.test.prototype.repository;

import com.test.prototype.domain.GIDIdentity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GIDIdentity entity.
 */
@Repository
public interface GIDIdentityRepository extends JpaRepository<GIDIdentity, Long> {

    @Query(value = "select distinct gIDIdentity from GIDIdentity gIDIdentity left join fetch gIDIdentity.memberships",
        countQuery = "select count(distinct gIDIdentity) from GIDIdentity gIDIdentity")
    Page<GIDIdentity> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct gIDIdentity from GIDIdentity gIDIdentity left join fetch gIDIdentity.memberships")
    List<GIDIdentity> findAllWithEagerRelationships();

    @Query("select gIDIdentity from GIDIdentity gIDIdentity left join fetch gIDIdentity.memberships where gIDIdentity.id =:id")
    Optional<GIDIdentity> findOneWithEagerRelationships(@Param("id") Long id);
}
