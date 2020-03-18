package com.test.prototype.repository;

import com.test.prototype.domain.GIDUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GIDUser entity.
 */
@Repository
public interface GIDUserRepository extends JpaRepository<GIDUser, Long> {

    @Query(value = "select distinct gIDUser from GIDUser gIDUser left join fetch gIDUser.identities",
        countQuery = "select count(distinct gIDUser) from GIDUser gIDUser")
    Page<GIDUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct gIDUser from GIDUser gIDUser left join fetch gIDUser.identities")
    List<GIDUser> findAllWithEagerRelationships();

    @Query("select gIDUser from GIDUser gIDUser left join fetch gIDUser.identities where gIDUser.id =:id")
    Optional<GIDUser> findOneWithEagerRelationships(@Param("id") Long id);
}
