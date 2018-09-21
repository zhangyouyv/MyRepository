package com.client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.client.tools.MessageTool;
import com.client.ui.MessagePanel;

public class SendMessageListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(GameData.isConnected==false){
			JOptionPane.showConfirmDialog(null,"���ȵ�¼��",
				     "δ��¼",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
		}
		else if(GameData.opponentID.equals("")){
			JOptionPane.showConfirmDialog(null,"���ȿ�ʼ��Ϸ��",
				     "û�����",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
		}
		else if(MessagePanel.getInstance().getSendField().getText().trim().equals("")){
			JOptionPane.showConfirmDialog(null,"�벻Ҫ���Ϳ���Ϣ��",
				     "����Ϣ��ʾ",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
		}
		else{
			String sendMessage=MessagePanel.getInstance().getSendField().getText();
			IOTool.getInstance().getWriter().println("SAY:"+GameData.myID+"#"+GameData.opponentID+"#"+sendMessage); //������Ϣ
			MessagePanel.getInstance().getSendField().setText("");
			MessageTool.getInstance().addMessage("��˵��"+sendMessage);
		}
	}

}
