package com.alesaudate.monitor.visualizacao;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

import javax.swing.JTextArea;

public class LogEventsTextArea extends JTextArea implements Runnable{

	
	private Reader pipedReader;
	
	public LogEventsTextArea(Reader pipedReader) {
		this.pipedReader = pipedReader;
	}
	
	@Override
	public void run() {
		
		while (true) {
			CharBuffer charBuffer = CharBuffer.allocate(1000);
			try {
				pipedReader.read(charBuffer);
				char[] array = charBuffer.array();
				int position = charBuffer.position();
				String text = new String (array, 0, position);
				append(text);
				charBuffer.clear();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
