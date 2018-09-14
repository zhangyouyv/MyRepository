package com.client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;

public class ChallengeListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if (GameData.isConnected == false) {
			JOptionPane.showConfirmDialog(null, "���ȵ�¼����ս��", "��¼��ʾ", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION);
		} 
		else {
			if (GameData.chosenOpponentID.equals("")) {
				JOptionPane.showConfirmDialog(null, "����ѡ��һ����������ս��", "ѡ�������ʾ", JOptionPane.DEFAULT_OPTION,
						JOptionPane.DEFAULT_OPTION);
			} 
			else {
				if (GameData.chosenOpponentID.equals(GameData.myID) | GameData.chosenOpponentID.substring(0, GameData.chosenOpponentID.indexOf("(")).equals(GameData.myID)) {
					System.out.println(GameData.chosenOpponentID);
					JOptionPane.showConfirmDialog(null, "���ܺ��Լ���ս��", "��ս����", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				} 
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
