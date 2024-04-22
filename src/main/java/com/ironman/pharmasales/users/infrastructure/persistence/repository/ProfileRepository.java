package com.ironman.pharmasales.users.infrastructure.persistence.repository;

import com.ironman.pharmasales.users.infrastructure.persistence.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    List<Profile> findByState(String state);

    @Query(value = "SELECT" +
            " p.id, p.name, p.description, p.state, p.created_at, p.updated_at" +
            " FROM profiles p" +
            " WHERE (:name IS NULL OR UPPER(p.name) LIKE UPPER(CONCAT('%',:name,'%')) )" +
            " AND (:description IS NULL OR UPPER(p.description) LIKE UPPER(CONCAT('%',:description,'%')) )" +
            " AND (:state IS NULL OR UPPER(p.state) = UPPER(:state) )" +
            " AND (:createdAtFrom IS NULL OR DATE(p.created_at) >= TO_DATE(:createdAtFrom, 'YYYY-MM-DD') )" +
            " AND (:createdAtTo IS NULL OR DATE(p.created_at) <= TO_DATE(:createdAtTo, 'YYYY-MM-DD') )",
            nativeQuery = true
    )
    Page<Profile> findAllPaginated(
            @Param("name") String name,
            @Param("description") String description,
            @Param("state") String state,
            @Param("createdAtFrom") String createdAtFrom,
            @Param("createdAtTo") String createdAtTo,
            Pageable pageable
    );
}
