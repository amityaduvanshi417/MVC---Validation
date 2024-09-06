package com.example.demo.repository;

import com.example.demo.model.Attendances;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendancesRepository extends JpaRepository<Attendances, Integer> {

    @Query(value = "SELECT * FROM Attendances WHERE createdAt = :date", nativeQuery = true)
    List<Attendances> findByCreatedAtDate(@Param("date") LocalDate date);

    Optional<Attendances> findByUserAndCreatedat(User user, LocalDate createdat);

    boolean existsByUserEmailAndCreatedat(String email, LocalDate createdat);
}

