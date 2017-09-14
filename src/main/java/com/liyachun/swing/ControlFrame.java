package com.liyachun.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.liyachun.j2se.util.LogUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @author liyc
 * @date 2017年9月14日 上午9:35:47
 */
public class ControlFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 5393927706521481439L;
	
	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT = 600;
	
	JTextField tfHost,tfUser,tfQueueName,tfMessage;
	JPasswordField tfPsw;
	JTextArea jtMessage;
	JButton connectButton,closeButton,newChannelButton,clearLogButton,publishButton;
	Persistence persistence = new Persistence();
	
	public ControlFrame() {
		super("rabbitmqtoolkit");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Dimension scrSize= Toolkit.getDefaultToolkit().getScreenSize(); 
		setLocation((scrSize.width-DEFAULT_WIDTH)/2,(scrSize.height-DEFAULT_HEIGHT)/2); 
		
		DataModel storeData = persistence.getDataModel();
		
		int lbX = 20;
		int lbWidth = 110;
		int lbHeight = 25;
		int tfWidth = DEFAULT_WIDTH - lbX*2 - lbWidth;
		int tfHeight = 25;
		int tfX = lbX + lbWidth;
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		
		JLabel lbHost = new JLabel("host:",JLabel.CENTER);
		lbHost.setSize(lbWidth, lbHeight);
		lbHost.setLocation(lbX, 20);
		centerPanel.add(lbHost);
		tfHost = new JTextField(storeData.getHost());
		tfHost.setSize(tfWidth, tfHeight);
		tfHost.setLocation(tfX, 20);
		
		centerPanel.add(tfHost);
		
		
		JLabel lbUser = new JLabel("user:",JLabel.CENTER);
		lbUser.setSize(lbWidth, lbHeight);
		lbUser.setLocation(lbX, calY(lbHost));
		centerPanel.add(lbUser);
		
		tfUser = new JTextField(storeData.getUser());
		tfUser.setSize(tfWidth, tfHeight);
		tfUser.setLocation(tfX, calY(tfHost));
		centerPanel.add(tfUser);
		
		JLabel lbPsw = new JLabel("password:",JLabel.CENTER);
		lbPsw.setSize(lbWidth, lbHeight);
		lbPsw.setLocation(lbX, calY(lbUser));
		centerPanel.add(lbPsw);
		
		tfPsw = new JPasswordField();
		tfPsw.setSize(tfWidth, tfHeight);
		tfPsw.setLocation(tfX, calY(tfUser));
		centerPanel.add(tfPsw);
		
		JLabel lbQueueName = new JLabel("queue name:",JLabel.CENTER);
		lbQueueName.setSize(lbWidth, lbHeight);
		lbQueueName.setLocation(lbX, calY(lbPsw));
		centerPanel.add(lbQueueName);
		
		tfQueueName = new JTextField();
		tfQueueName.setSize(tfWidth, tfHeight);
		tfQueueName.setLocation(tfX, calY(tfPsw));
		centerPanel.add(tfQueueName);
		
		//message
		JLabel lbMessage = new JLabel("message:",JLabel.CENTER);
		lbMessage.setSize(lbWidth, lbHeight);
		lbMessage.setLocation(lbX, calY(lbQueueName));
		centerPanel.add(lbMessage);
		
		tfMessage = new JTextField();
		tfMessage.setSize(tfWidth, tfHeight);
		tfMessage.setLocation(tfX, calY(tfQueueName));
		centerPanel.add(tfMessage);
		
		
		jtMessage = new JTextArea();
		jtMessage.setEditable(false);
		jtMessage.setSize(DEFAULT_WIDTH - 2*lbX, 250);
		jtMessage.setLocation(lbX, calY(tfMessage));

		JScrollPane scroll = new JScrollPane(jtMessage); 
		scroll.setSize(jtMessage.getWidth(),jtMessage.getHeight());
		scroll.setLocation(jtMessage.getX(),jtMessage.getY());
		
		centerPanel.add(scroll);
		
		add(centerPanel,BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		connectButton = new JButton("connect");
		connectButton.addActionListener(this);
		southPanel.add(connectButton);
		closeButton = new JButton("close");
		closeButton.addActionListener(this);
		southPanel.add(closeButton);
		
		//create a new channel
//		newChannelButton = new JButton("new channel");
//		newChannelButton.addActionListener(this);
//		southPanel.add(newChannelButton);
		
		publishButton = new JButton("publish");
		publishButton.addActionListener(this);
		southPanel.add(publishButton);
		
		clearLogButton = new JButton("clear logs");
		clearLogButton.addActionListener(this);
		southPanel.add(clearLogButton);
		
		add(southPanel,BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DataModel dm = new DataModel();
				dm.setHost(tfHost.getText());
				dm.setUser(tfUser.getText());
				persistence.save(dm);
				System.exit(0);  
			}
		});
	}
	
	int calY(JComponent lab) {
		return lab.getY() + lab.getHeight() + 10;
	}
	
	ConnectionFactory factory;
	Connection connection;
	List<Channel> channelList = new ArrayList<Channel>();

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connectButton) {
			String host = tfHost.getText();
			String user = tfUser.getText();
			String psw = new String(tfPsw.getPassword());
			if(StringUtils.isEmpty(host)||StringUtils.isEmpty(user)||StringUtils.isEmpty(psw)) {
				log("host or user or password is empty");
				return;
			}
			factory = new ConnectionFactory();
			factory.setHost(tfHost.getText());
			factory.setUsername(tfUser.getText());
			factory.setPassword(new String(tfPsw.getPassword()));
			factory.setPort(AMQP.PROTOCOL.PORT);
			try {
				if(connection==null||!connection.isOpen()) {
					connection = factory.newConnection();
					log("connect success");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				log("connect failure");
			} 
		}
		else if(e.getSource() == closeButton) {
			try {
				if(connection!=null&&connection.isOpen()) {
					channelList.clear();
					connection.close();
					log("close success");
				}
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				log("close failure");
			}
		}
		else if(e.getSource() == newChannelButton) {
			try {
				if(connection !=null && connection.isOpen()) {
					channelList.add(connection.createChannel());
					log("add a new channel, now "+channelList.size()+ " channels");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				log("add channel failure");
			}
		}
		else if(e.getSource() == clearLogButton) {
			messages = new StringBuilder("");
			jtMessage.setText("");
		}
		else if(e.getSource() == publishButton) {
			try {
				Channel channel = connection.createChannel();
				channel.basicPublish("", tfQueueName.getText(), MessageProperties.PERSISTENT_TEXT_PLAIN, tfMessage.getText().getBytes());
				channel.close();
				log("publish success");
			} catch (Exception ex) {
				ex.printStackTrace();
				log("publish failure");
			}
		}
	}
	
	StringBuilder messages = new StringBuilder();
	
	void log(String message){
		messages.append(LogUtil.log(message));
		messages.append("\n");
		jtMessage.setText(messages.toString());
	}
}
