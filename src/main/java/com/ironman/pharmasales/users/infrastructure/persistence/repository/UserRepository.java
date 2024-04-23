package com.ironman.pharmasales.users.infrastructure.persistence.repository;

import com.ironman.pharmasales.users.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findByState(String state);

    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT" +
            " u.id, u.name, u.last_name, u.email, u.password, u.profile_id, u.state, u.created_at, u.updated_at" +
            " , p.name as profile_name" +
            " FROM users u" +
            " INNER JOIN profiles p ON p.id = u.profile_id" +
            " WHERE (:name IS NULL OR UPPER(u.name) LIKE UPPER(CONCAT('%',:name,'%')) )" +
            " AND (:lastName IS NULL OR UPPER(u.last_name) LIKE UPPER(CONCAT('%',:lastName,'%')) )" +
            " AND (:email IS NULL OR UPPER(u.email) LIKE UPPER(CONCAT('%',:email,'%')) )" +
            " AND (:profileId IS NULL OR u.profile_id = :profileId )" +
            " AND (:state IS NULL OR UPPER(u.state) = UPPER(:state) )" +
            " AND (:createdAtFrom IS NULL OR DATE(u.created_at) >= TO_DATE(:createdAtFrom, 'YYYY-MM-DD') )" +
            " AND (:createdAtTo IS NULL OR DATE(u.created_at) <= TO_DATE(:createdAtTo, 'YYYY-MM-DD') )",
            nativeQuery = true
    )
    Page<UserEntity> findAllPaginated(
            String name,
            String lastName,
            String email,
            Long profileId,
            String state,
            String createdAtFrom,
            String createdAtTo,
            Pageable pageable
    );
}
