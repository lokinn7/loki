package com.kuaiyou.lucky.thread.thread;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kuaiyou.lucky.compnent.MessageCompnent;
import com.kuaiyou.lucky.entity.Template;

@Component
public class TaskProvider {

	protected static Logger logger = LoggerFactory.getLogger(TaskProvider.class);

	Queue<Template> queue = new LinkedBlockingQueue<>();

	@Autowired
	MessageCompnent messageCompnent;

	public Runnable getTask() {
		Template poll = queue.poll();
		if (poll != null) {
			return null;
		} else {
			return new Runnable() {
				@Override
				public void run() {
					messageCompnent.sendMessage(poll);
				}
			};
		}
	}

	public Queue<Template> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Template> queue) {
		this.queue = queue;
	}

}
