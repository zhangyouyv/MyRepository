package com.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.client.listener.SendMessageListener;
import com.client.tools.MessageTool;
/**
 * ��Ϣ������壬�������ڴ����ı���Ϣ
 * ����һ��JTextArea��һ��JTextField���з��͵�JButton
 * ���������շ���Ϣ
 * @author Edward
 *
 */
public class MessagePanel extends JPanel {
	
	private static MessagePanel mp = null;
	
	private JScrollPane messagePane=new JScrollPane();
	private JTextArea messageArea=MessageTool.getMessageArea();
	private JTextField sendField=new JTextField(33);
	private JButton sendBtn=new JButton("����");
	private JPanel sendPanel=new JPanel(new FlowLayout());
	//����ģʽ
	public static MessagePanel getInstance(){
		if(mp==null){
			mp=new MessagePanel();
		}
		return mp;
	}
	public JTextField getSendField(){
		return sendField;
	}
	private MessagePanel(){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("��Ϣ���"));
		sendPanel.add(sendField);
		sendPanel.add(sendBtn);
		add(messagePane,BorderLayout.CENTER);
		add(sendPanel,BorderLayout.SOUTH);
		messagePane.setViewportView(messageArea);
		sendField.setSize(200,100);
		sendBtn.addActionListener(new SendMessageListener());
	}
	
}
