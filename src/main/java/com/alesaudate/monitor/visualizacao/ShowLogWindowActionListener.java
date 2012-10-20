package com.alesaudate.monitor.visualizacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

class ShowLogWindowActionListener implements ActionListener {
	
	
	private JFrame jFrame;
	
	public ShowLogWindowActionListener(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jFrame.setVisible(true);
		
	}

}
