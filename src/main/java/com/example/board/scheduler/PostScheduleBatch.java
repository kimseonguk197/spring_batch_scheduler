package com.example.board.scheduler;

import com.example.board.batchs.PostJobConfiguration;
import com.example.board.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PostScheduleBatch {


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private PostJobConfiguration postJobConfiguration;


    private final PostRepository repository;
    public PostScheduleBatch(PostRepository repository) {
        this.repository = repository;
    }
    @Scheduled(cron =  "0 0/1 * * * *")
    public void postSchedule(){
//        배치 job을 호출하는 방식
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {

            jobLauncher.run(postJobConfiguration.simpleJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        }
    }
}
