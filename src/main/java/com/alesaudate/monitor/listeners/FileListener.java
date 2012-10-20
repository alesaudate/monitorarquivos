package com.alesaudate.monitor.listeners;

import java.nio.file.Path;

public interface FileListener extends Runnable {

	public void accept(Path path);
	
}
