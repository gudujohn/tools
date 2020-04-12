package org.john.support.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadExecutorPoolConfig {

	@Bean("mainThreadRestExecutor")
	public Executor MainThreadRestExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(5);
		executor.setThreadNamePrefix("mainThreadRestExecutor-");
		executor.initialize();
		return executor;
	}

	@Bean("threadRestExecutor")
	public Executor ThreadRestExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setThreadNamePrefix("threadRestExecutor-");
		executor.initialize();
		return executor;
	}

}