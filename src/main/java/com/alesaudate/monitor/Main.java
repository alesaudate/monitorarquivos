package com.alesaudate.monitor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.alesaudate.monitor.listeners.MoveByExtensionFileListener;
import com.alesaudate.monitor.utils.Utils;
import com.alesaudate.monitor.visualizacao.GraphicUtils;

public class Main {
	
	
	public static void main(String[] args) throws Exception {
		
		
		Pipe pipe = Pipe.open();
		SinkChannel sinkChannel = pipe.sink();
		
		OutputStream outStream = Channels.newOutputStream(sinkChannel);
		SourceChannel sourceChannel = pipe.source();
		
		InputStream inputStream = Channels.newInputStream(sourceChannel);
		
		PrintStream printStream = new PrintStream(outStream);
		
		System.setOut(printStream);
		System.setErr(printStream);
		
		GraphicUtils.bootGraphics(new InputStreamReader(inputStream));
		
		Properties properties = Utils.loadProperties(args[0]);
		
		Monitor monitor = new Monitor("C:\\Users\\Alexandre\\Downloads");
		
		for (Object fileType : properties.keySet()) {
			MoveByExtensionFileListener moveByExtensionFileListener = new MoveByExtensionFileListener(fileType.toString(), properties.get(fileType).toString());
			monitor.acceptListener(moveByExtensionFileListener);
		}
		
		Thread thread = new Thread(monitor);
		thread.start();
		
		
		
		
	}

}
