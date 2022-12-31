package com.example.board.batchs;

import com.example.board.domain.Post;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Configuration // Spring Batch의 모든 Job은 @Configuration으로 등록해서 사용해야 한다.
public class PostJobConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final PostRepository repository;




    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1())
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Start");
                    String scheduled = "checked";
                    for (Post post : repository.findByScheduled(scheduled)) {
//            1초 차이 용인
                        if(post.getScheduledTime().isBefore(LocalDateTime.now().plusSeconds(1))){
                            post.setScheduled(null);
                            repository.save(post);
                        }
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

//    private final PostRepository repository;
//    public void postSchedule(){
//        //schedule걸린건만 조회
//        String scheduled = "checked";
//        for (Post post : repository.findByScheduled(scheduled)) {
////            1초 차이 용인
//            if(post.getScheduledTime().isBefore(LocalDateTime.now().plusSeconds(1))){
//                post.setScheduled(null);
//                repository.save(post);
//            }
//        }
//    }
}
