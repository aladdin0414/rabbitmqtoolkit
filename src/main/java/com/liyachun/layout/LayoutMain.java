/**
 * 
 */
package com.liyachun.layout;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author liyc
 * @date 2017年9月19日 下午2:30:05
 */
public class LayoutMain {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				LayoutFrame frame = new LayoutFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
