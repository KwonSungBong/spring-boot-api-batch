package com.example.demo.repository;

import com.example.demo.entity.Program;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends CrudRepository<Program, String> {

}
