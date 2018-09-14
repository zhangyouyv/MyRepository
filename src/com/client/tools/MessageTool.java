package com.client.tools;

import javax.swing.JTextArea;

/**
 * JTextArea�İ�װ�࣬���ڶ������е���
 * @author Edward
 *
 */
public class MessageTool {
	private static MessageTool messageTool = null;
	private static JTextArea messageArea=null;
	
	public static MessageTool getInstance() {  //����ģʽ
		if (messageTool == null) {
			messageTool = new MessageTool();
		}
		return messageTool;
	}
	
	private MessageTool(){
		
	}
	
	public static JTextArea getMessageArea(){ //���ڲ��ò���
		if(messageArea==null){
			messageArea=new JTextArea();
			messageArea.setEditable(false);
		}
		return messageArea;
	}
	
	public void addMessage(String message){  //ֱ��ʹ�ø�����ã���������
		messageArea=getMessageArea();
		messageArea.append(message+"\n\n");
	}
}
