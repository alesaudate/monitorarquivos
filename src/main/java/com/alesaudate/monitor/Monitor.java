package com.alesaudate.monitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alesaudate.monitor.listeners.FileListener;

public class Monitor implements FileWatchdog {
	
	
	private String rootFile;
	private Collection<FileListener> listeners = new ArrayList<>();
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	public Monitor(String rootFile) {
		this.rootFile = rootFile;
	}

	public void scan() throws IOException, InterruptedException {
		FileSystem fileSystem = FileSystems.getDefault();
		Path path = fileSystem.getPath(rootFile);
		WatchService watchService = fileSystem.newWatchService();
		Kind<Path> createFile = StandardWatchEventKinds.ENTRY_CREATE;
		path.register(watchService, createFile);
		
		while (true) {
			WatchKey watchKey = watchService.take();
			
			List<WatchEvent<?>> events = watchKey.pollEvents();
			
			for (WatchEvent<?> event : events) {
				@SuppressWarnings("unchecked")
				WatchEvent<Path> pathEvent = (WatchEvent<Path>)event;
				Path contextPath = pathEvent.context();
				for (FileListener listener : listeners) {
					if (!contextPath.isAbsolute()) {
						contextPath = new File(rootFile, contextPath.toFile().getName()).toPath();
					}
					listener.accept(contextPath);
					executorService.execute(listener);
				}
				
			}
			
			watchKey.reset();
		}
		
	}

	@Override
	public void run() {
		while (true) {
			try {
				scan();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void acceptListener(FileListener listener) {
		listeners.add(listener);
	}

}
