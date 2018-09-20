package com.server.ui;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.client.tools.ListTool;
import com.server.tools.MessageTool;

import javafx.scene.layout.Border;

/**
 * 
 * �������˵���Ϣ��ʾ���棬��ʾ�������ĸ�����Ϣ
 * @author Edward Zhang
 */
public class MessagePanel extends JPanel{
	private static MessagePanel messagePanel = null;
	private JScrollPane scrollPane=new JScrollPane();
	private JTextArea messageArea=MessageTool.getMessageArea();
	public static MessagePanel getInstance() {
		if (messagePanel == null) {
			messagePanel = new MessagePanel();
		}
		return messagePanel;
	}
	private MessagePanel(){
		setLayout(new BorderLayout());
		
		setBorder(BorderFactory.createTitledBorder("��������Ϣ"));
		add(scrollPane);
		scrollPane.setViewportView(messageArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
}
