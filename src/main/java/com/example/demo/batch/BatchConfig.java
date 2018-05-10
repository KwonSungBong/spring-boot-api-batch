package com.example.demo.batch;

import com.example.demo.entity.ApiResult;
import com.example.demo.entity.Program;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.persistence.EntityManagerFactory;

//@Configuration
//@EnableBatchProcessing
public class BatchConfig {

    private static final int CHUNK_AND_PAGE_SIZE = 1000;

    @Value("${max-threads:10}")
    private int maxThreads;

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(maxThreads);
        return taskExecutor;
    }

    @Bean
    public Job importJob() {
        return jobBuilderFactory.get("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Program, ApiResult> chunk(CHUNK_AND_PAGE_SIZE)
                .reader(reader())
                .processor(processor1())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Program> reader() {
        JpaPagingItemReader<Program> reader = new JpaPagingItemReader<>();
        reader.setQueryString("select p from Program p order by p.id");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(CHUNK_AND_PAGE_SIZE);
        return reader;
    }

    @Bean
    public ItemProcessor processor1() {
//        return new ItemProcessor<Program, ApiResult>() {
//            @Override
//            public ApiResult process(Program program) throws Exception {
//                ApiResult apiResult = new ApiResult();
//                apiResult.setProgramId(program.getId());
//                apiResult.setResultStatus("ok");
//                return apiResult;
//            }
//        };

        return item -> {
            Program program = (Program)item;
            ApiResult apiResult = new ApiResult();
            apiResult.setProgramId(program.getId());
            apiResult.setResultStatus("ok");
            return apiResult;
        };
    }

    @Bean
    public JpaItemWriter<ApiResult> writer() {
        JpaItemWriter<ApiResult> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}
