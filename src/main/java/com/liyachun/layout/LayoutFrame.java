/**
 * 
 */
package com.liyachun.layout;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * @author liyc
 * @date 2017年9月19日 下午2:30:25
 */
public class LayoutFrame extends JFrame{
	
	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT = 600;
	
	public LayoutFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Dimension scrSize= Toolkit.getDefaultToolkit().getScreenSize(); 
		setLocation((scrSize.width-DEFAULT_WIDTH)/2,(scrSize.height-DEFAULT_HEIGHT)/2); 
		
		LinearLayout layout = new LinearLayout(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(20, 20, 20, 20);
		layout.setGap(10);
		for(int i=0;i<21;i++) {
			JTextField tf = new JTextField();
			tf.setSize(25, 25);
			layout.addChild(tf);
		}
//		layout.setBackground(new Color(122, 122, 22));
		setContentPane(layout);
	}
}
