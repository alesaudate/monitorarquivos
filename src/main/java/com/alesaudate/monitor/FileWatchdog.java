package com.alesaudate.monitor;

import com.alesaudate.monitor.listeners.FileListener;

public interface FileWatchdog extends Runnable {
	
	public void acceptListener(FileListener listener);
}
