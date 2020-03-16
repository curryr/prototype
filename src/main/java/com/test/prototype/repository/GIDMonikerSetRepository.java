package com.test.prototype.repository;

import com.test.prototype.domain.GIDMonikerSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GIDMonikerSet entity.
 */
@Repository
public interface GIDMonikerSetRepository extends JpaRepository<GIDMonikerSet, Long> {

    @Query(value = "select distinct gIDMonikerSet from GIDMonikerSet gIDMonikerSet left join fetch gIDMonikerSet.monikers",
        countQuery = "select count(distinct gIDMonikerSet) from GIDMonikerSet gIDMonikerSet")
    Page<GIDMonikerSet> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct gIDMonikerSet from GIDMonikerSet gIDMonikerSet left join fetch gIDMonikerSet.monikers")
    List<GIDMonikerSet> findAllWithEagerRelationships();

    @Query("select gIDMonikerSet from GIDMonikerSet gIDMonikerSet left join fetch gIDMonikerSet.monikers where gIDMonikerSet.id =:id")
    Optional<GIDMonikerSet> findOneWithEagerRelationships(@Param("id") Long id);
}
