package com.example.demo.service;

import com.example.demo.entity.Program;
import com.example.demo.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    public Page<Program> findAll(int page, int limit) {

        return programRepository.findAll(PageRequest.of(page, limit));
    }

    @Transactional(readOnly = true)
    public void test1() {
        int page = 0;
        int limit = 100;
        int size = 1000;
        Page<Program> programPage = programRepository.findAll(PageRequest.of(page, limit));

//        while (!programPage.isLast()) {
        while (programPage.getPageable().getPageNumber() == size/limit) {
            programPage = programRepository.findAll(PageRequest.of(page, limit));
        }
    }

}
