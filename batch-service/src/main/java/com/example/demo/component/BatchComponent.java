package com.example.demo.component;

import com.example.demo.entity.ApiResult;
import com.example.demo.entity.Program;
import com.example.demo.repository.ApiResultRepository;
import com.example.demo.repository.ProgramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class BatchComponent {

    Map<String, ApiResult> programMap = new ConcurrentHashMap<>();

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ApiResultRepository apiResultRepository;

    public void task() {
        Iterable<Program> result = programRepository.findAll();
        StreamSupport.stream(result.spliterator(), true)
            .forEach(program -> {
                ApiResult apiResult = new ApiResult();
                apiResult.setProgramId(program.getId());
                apiResult.setResultStatus("ok");
                programMap.put(program.getId(), apiResult);
            });

        apiResultRepository.saveAll(programMap.values());
        log.info("program size : {}", programMap.size());
    }

}
