package com.kuaiyou.lucky.thread.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.kuaiyou.lucky.entity.Template;

public class FixedThreadPool {

	AtomicInteger counter = new AtomicInteger(0);

	// 并发线程数量
	private int threads = 200;
	// 线程队列中元素最大数量，不需要设置太大防止任务重启丢失数据
	private int maxQueueThreads = Integer.MAX_VALUE;
	// 定时器延时启动
	private long timerDelay = TimeUnit.SECONDS.toMillis(5);
	private String name;
	private ThreadPoolExecutor executor = null;
	private Timer providerTimer = null;
	private AtomicBoolean init = new AtomicBoolean(false);

	private List<TaskProvider> taskProviders = new ArrayList<>();

	private int queueSize = 1000;

	public FixedThreadPool(String name) {
		Objects.requireNonNull(name);
		this.name = name;
		executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(queueSize), new NamedThreadFactory(name, false), new AbortPolicy());
		providerTimer = new Timer(this.name + "-providertimer", false);
	}
	
	public void submitTaskProvider(TaskProvider taskProvider) {
		taskProviders.add(taskProvider);
		start();
	}
	
	public void submitTaskProvider(Template template) {
		start();
	}

	private synchronized void start() {
		if (init.get() == false) {
			providerTimer();
			init.set(true);
		}
	}

	private void providerTimer() {
		long period = TimeUnit.SECONDS.toMillis(2);
		providerTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TaskProvider taskProvider = null;
				for (TaskProvider item : taskProviders) {
					taskProvider = item;
					break;
				}
				int size = executor.getQueue().size();
				if (taskProvider != null) {
					while (size < maxQueueThreads) {
						Runnable runnable = taskProvider.getTask();
						if (runnable == null) {
							// 如果runnable等于空，说明任务结束
							// 调整移除任务构建器的时机
							taskProviders.remove(taskProvider);
							break;
						} else {
							executor.submit(runnable);
							counter.incrementAndGet();
						}
					}
				}
			}
		}, timerDelay, period);
	}

	public void destroy() {
		if (providerTimer != null) {
			providerTimer.cancel();
			providerTimer = null;
		}
		if (executor != null) {
			executor.shutdown();
		}
	}

	public ThreadPoolExecutor getExcutor() {
		return executor;
	}


	public boolean getActiveCount() {
		return executor.getActiveCount() > 0;
	}
	
	public int getQueueSize() {
		return executor.getQueue().size();
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

}
