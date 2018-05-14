package com.example.demo.service;

import com.example.demo.entity.Program;
import com.example.demo.entity.ProgramResponseEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest2ApplicationTests {

    int size = 1000;

    @Autowired
    private ApiTestService2 apiTestService;

    @Test
    public void TEST1() {
        String testSize = System.getProperty("testSize");
//        if(testSize.isInteger()) size = testSize.toInteger();
//        log.info("test size : {}", testSize);

        List<Program> programList = apiTestService.findList(size);

        List<ProgramResponseEntity> programResponseEntityList = apiTestService.getProgramResponseEntityList(programList);

        double totalElapsedSecond = 0.0;
        List<ProgramResponseEntity> failList = new ArrayList<>();
        for (ProgramResponseEntity programResponseEntity : programResponseEntityList) {
            totalElapsedSecond += programResponseEntity.getElapsedSecond();
            if (programResponseEntity.getHttpStatus() == null ||
                    programResponseEntity.getHttpStatus() != HttpStatus.OK ||
                    programResponseEntity.getResponseEntity() == null ||
                    programResponseEntity.getResponseEntity().getBody().length == 0) {
                failList.add(programResponseEntity);
            }
        }

        then:
        System.out.println("failList : " + failList);
        System.out.println("Processing per second : " + totalElapsedSecond / size);

        Assert.assertEquals(failList.size(), 0);
    }

}
