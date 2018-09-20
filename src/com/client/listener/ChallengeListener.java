 package com.client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.server.tools.HashMapTool;
import com.server.tools.Player;

public class ChallengeListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		//���û�е�¼��ʾ
		if (GameData.isConnected == false) {
			JOptionPane.showConfirmDialog(null, "���ȵ�¼����ս��", "��¼��ʾ", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION);
		} 
		else {
			
		     		
				
			
			//ѡ�������ʾ
			if (GameData.chosenOpponentID.equals("")) {
				JOptionPane.showConfirmDialog(null, "����ѡ��һ����������ս��", "ѡ�������ʾ", JOptionPane.DEFAULT_OPTION,
						JOptionPane.DEFAULT_OPTION);
			} 
			//����ƥ�䵽���ڶ�ս�����
			
			    if(HashMapTool.getInstance().getPlayer(GameData.chosenOpponentID).getStatus() == true){
			   JOptionPane.showConfirmDialog(null, "����������Ϸ������ƥ���µ���ң�", "��Ϸ������ƥ��", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);}
			else {
				//���ܺ��Լ���ս��ʾ
				if(GameData.chosenOpponentID.contains("(")){
				if (GameData.chosenOpponentID.equals(GameData.myID) | GameData.chosenOpponentID.substring(0, GameData.chosenOpponentID.indexOf("(")).equals(GameData.myID)) {
					System.out.println(GameData.chosenOpponentID);
					JOptionPane.showConfirmDialog(null, "���ܺ��Լ���ս��", "��ս����", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				} 
				}
				//��Ϸ����ʾ����ƥ������
				else if(!GameData.opponentID.equals("")&&GameData.gameOver==false){
					JOptionPane.showConfirmDialog(null, "��ǰ������Ϸ������ƥ���µ���ң�", "��Ϸ������ƥ��", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					String message = "CHALL:" + GameData.myID + "#" + GameData.chosenOpponentID;
					// System.out.println(message);
					IOTool.getInstance().getWriter().println(message); // ���Ͷ�ս����
					JOptionPane.showConfirmDialog(null, "�Ѿ����Ͷ�ս����", "�����ͳɹ�", JOptionPane.DEFAULT_OPTION,
							JOptionPane.DEFAULT_OPTION);
				}
			}
		}
	}
	}


