package com.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.client.listener.SendMessageListener;
import com.client.tools.MessageTool;
/**
 * ��Ϣ������壬�������ڴ����ı���Ϣ
 * ����һ��JTextArea��һ��JTextField���з��͵�JButton
 * ���������շ���Ϣ
 * @author Edward Zhang
 *
 */
public class MessagePanel extends JPanel {
	
	private static MessagePanel mp = null;
	
	private JScrollPane messagePane=new JScrollPane();
	private JTextArea messageArea=MessageTool.getMessageArea();
	private JTextArea sendField=new JTextArea();
	private JButton sendBtn=new JButton("����");
	private JPanel sendPanel=new JPanel(new FlowLayout());
	//����ģʽ
	public static MessagePanel getInstance(){
		if(mp==null){
			mp=new MessagePanel();
		}
		return mp;
	}
	public JTextArea getSendField(){
		return sendField;
	}
	private MessagePanel(){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("��Ϣ���"));
		sendPanel.add(sendField);
		sendPanel.add(sendBtn);
		add(messagePane,BorderLayout.CENTER);
		add(sendPanel,BorderLayout.SOUTH);
		messageArea.setLineWrap(true);
		messagePane.setViewportView(messageArea);
		messagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		messagePane.setSize(200,100);
		sendField.setSize(200,100);
		sendField.setBorder (BorderFactory.createLineBorder(Color.gray,2));
		sendField.setLineWrap(true);
		sendBtn.addActionListener(new SendMessageListener());
	}
	
}
