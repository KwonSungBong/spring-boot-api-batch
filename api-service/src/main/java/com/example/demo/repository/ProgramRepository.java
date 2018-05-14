package com.example.demo.repository;

import com.example.demo.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Repository
public interface ProgramRepository extends PagingAndSortingRepository<Program, String> {

    Page<Program> findByGradeGreaterThan(int grade, Pageable pageable);

    @Query(value = "SELECT * FROM program WHERE grade>:grade " +
            "ORDER BY RAND() limit :size", nativeQuery = true)
    Set<Program> findByGrade(@Param("size") int size, @Param("grade") int grade);

    @Query(value = "SELECT * FROM program " +
            "ORDER BY RAND() limit :size", nativeQuery = true)
    Set<Program> findSet(@Param("size") int size);

    @Query(value = "SELECT * FROM program " +
            "ORDER BY RAND() limit :size", nativeQuery = true)
    List<Program> findList(@Param("size") int size);

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query(value = "SELECT * FROM program " +
            "ORDER BY RAND() limit :size", nativeQuery = true)
    Stream<Program> findStream(@Param("size") int size);

}
