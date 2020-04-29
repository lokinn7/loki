package com.kuaiyou.lucky.thread.thread;

import java.util.HashMap;

public class ThreadTest implements Runnable {

	private String name;
	private Object prev;
	private Object self;

	private ThreadTest(String name, Object prev, Object self) {
		this.name = name;
		this.prev = prev;
		this.self = self;
	}

	@Override
	public void run() {
		int count = 10;
		while (count > 0) {
			synchronized (prev) {
				synchronized (self) {
					System.out.print(name);
					count--;

					self.notify();
				}
				try {
					prev.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private static FixedThreadPool threadPool = new FixedThreadPool("name");

}
