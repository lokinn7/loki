package com.kuaiyou.lucky.observer;

import java.util.Observable;

import org.springframework.stereotype.Component;

@Component
public class OpendrawObserver extends Observable {

	public void setFlag(Integer doingcount) {
		setChanged();
		notifyObservers(doingcount);
	}
}
