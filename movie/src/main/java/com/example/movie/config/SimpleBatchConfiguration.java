package com.example.movie.config;

import com.example.movie.batch.MovieItemProcessor;
import com.example.movie.batch.MovieReader;
import com.example.movie.batch.MovieWriter;
import com.example.movie.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class SimpleBatchConfiguration {

    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final JobLauncher jobLauncher;
    private final MovieReader movieReader;
    private final MovieItemProcessor movieProcessor;
    private final MovieWriter movieWriter;

    @Bean
    public Step movieStep() {

        return new StepBuilder("movieStep", jobRepository)
                .<MovieDto, MovieDto>chunk(10, transactionManager)
                .reader(movieReader)
                .processor(movieProcessor)
                .writer(movieWriter)
                .build();
    }

    @Bean
    public Job movieJob() {
        return new JobBuilder("movieJob", jobRepository)
                .start(movieStep())
                .build();
    }
//초 분 시간 일 월 주
    @Scheduled(cron = "0 * * * * *")
    public void runMovieJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(movieJob(), jobParameters);
    }
}