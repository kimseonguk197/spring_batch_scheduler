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
    public Job excuteJob() {
        return jobBuilderFactory.get("excuteJob")
                .start(firstStep())
                .build();
    }
    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get("firstStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Start");
                    String scheduled = "checked";
                    for (Post post : repository.findByScheduled(scheduled)) {
                        if(post.getScheduledTime().isBefore(LocalDateTime.now().plusSeconds(1))){
                            post.setScheduled(null);
                            repository.save(post);
                        }
                    }
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
