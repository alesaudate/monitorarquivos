package com.alesaudate.monitor.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MoveByExtensionFileListener implements FileListener{
	
	private String fileExtension;
	private Path whereToMove;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
	
	private Queue<Path> queue = new LinkedBlockingQueue<>(); 
	
	public MoveByExtensionFileListener(String fileExtension, String whereToMove) {
		this.fileExtension = "." + fileExtension;
		this.whereToMove = new File(whereToMove).toPath();
	}

	@Override
	public void run() {
		Path path = queue.remove();
		if (path.toString().endsWith(fileExtension)) {
			try {
				Path source = path.toFile().toPath();
				Path target = whereToMove.resolve(path.toFile().getName());
				
				
				StringBuilder message = new StringBuilder();
				message
					.append("[")
					.append(sdf.format(new Date()))
					.append("]")
					.append(" Movendo ")
					.append(source)
					.append(" para ")
					.append(target)
					/*.append(" <a href=\"file://")
					.append(target)
					.append("\">Abrir</a>")*/
					;
				System.out.println(message.toString());
				
				Files.move(source, whereToMove.resolve(path.toFile().getName()));
			} catch (IOException e) {
			
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void accept(Path path) {
		queue.offer(path);
	}

}
