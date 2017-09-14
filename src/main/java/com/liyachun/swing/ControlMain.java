/**
 * 
 */
package com.liyachun.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author liyc
 * @date 2017年9月14日 上午9:35:31
 */
public class ControlMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				ControlFrame frame = new ControlFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

}
