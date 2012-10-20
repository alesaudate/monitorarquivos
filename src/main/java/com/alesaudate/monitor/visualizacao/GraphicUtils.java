package com.alesaudate.monitor.visualizacao;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedReader;
import java.io.PrintWriter;
import java.io.Reader;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.alesaudate.monitor.Main;

public class GraphicUtils {

	
	
	public static void bootGraphics(Reader reader) throws Exception {
		
		JFrame logFrame = createLogWindow(reader);
		ShowLogWindowActionListener showLogWindowActionListener = new ShowLogWindowActionListener(logFrame);
		createTrayIcon(
				createMenuItem("Visualizar log", showLogWindowActionListener), 
				createMenuItem("Desligar", new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				})
		);
		
	}
	
	static void createTrayIcon(MenuItem... menuItems) throws IOException, AWTException {
		InputStream stream = GraphicUtils.class.getResourceAsStream("/gmail.bmp");
		Image image = ImageIO.read(stream);
		
		TrayIcon trayIcon = new TrayIcon(image, "Monitor de arquivos");
		
		
		PopupMenu menu = new PopupMenu("teste");
		
		if (menuItems != null) {
			for (MenuItem menuItem : menuItems) {
				menu.add(menuItem);
			}
		}
		trayIcon.setPopupMenu(menu);
		
		SystemTray.getSystemTray().add(trayIcon);
	}
	
	
	static MenuItem createMenuItem(String label, ActionListener actionListener) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.addActionListener(actionListener);
		return menuItem;
	}
	
	
	static JFrame createLogWindow(Reader reader) {
		JFrame frame = new JFrame("Monitor de arquivos");
		frame.setSize(600, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		
		
		
		final LogEventsTextArea logEventsTextArea = new LogEventsTextArea(reader);
		//logEventsTextArea.setLineWrap(true);
		logEventsTextArea.setAutoscrolls(true);
		logEventsTextArea.setEditable(false);
		
		JScrollPane scrollBar = new JScrollPane(logEventsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logEventsTextArea.setText("");
				
			}
		});
		panel.add(botaoLimpar);
		frame.add(scrollBar, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
		new Thread(logEventsTextArea).start();
		return frame;
	}
	
}
