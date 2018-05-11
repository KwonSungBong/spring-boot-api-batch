package com.example.demo.repository;

import com.example.demo.entity.ApiResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiResultRepository extends CrudRepository<ApiResult, Long> {

}
