package com.alesaudate.monitor.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;

public class WriterOutputStream extends ByteArrayOutputStream {
	
	private Writer writeTo;
	
	public WriterOutputStream(Writer writeTo) {
		this.writeTo = writeTo;
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		writeTo.write(new String(b));
	}
	
	@Override
	public synchronized void write(byte[] b, int off, int len) {
		try {
			writeTo.write(new String(b, off, len));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void write(int b) {
		try {
			writeTo.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
