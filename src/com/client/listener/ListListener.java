package com.client.listener;

/**
 * �ͻ����б������ ˫��ѡ�����
 * @author Edward
 *
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.client.tools.ListTool;

public class ListListener extends MouseAdapter {
	public void mouseClicked(MouseEvent e) {
		if (GameData.isConnected){
			if (GameData.opponentID.equals("")) {
				if (!ListTool.getInstance().getPlayerList().isSelectionEmpty()) { // ���ǿհ�
					if (e.getClickCount() == 2) {
						JList list = ListTool.getInstance().getPlayerList();
						int index = list.locationToIndex(e.getPoint());
						String opponentID = (String) list.getModel().getElementAt(index); // ��ȡ����
						// ���ܺ��Լ���ս
						if(GameData.chosenOpponentID.contains("(")){
							if (GameData.chosenOpponentID.substring(0, GameData.chosenOpponentID.indexOf("(")).equals(GameData.myID)) {
								JOptionPane.showConfirmDialog(null, "���ܺ��Լ���ս��", "��ս����", JOptionPane.DEFAULT_OPTION,
										JOptionPane.ERROR_MESSAGE);
							}
							}
						else if (opponentID.equals(GameData.myID)) {
							JOptionPane.showConfirmDialog(null, "���ܺ��Լ���ս��", "��ս����", JOptionPane.DEFAULT_OPTION,
									JOptionPane.ERROR_MESSAGE);
						}
						
						// ѡ��
						else {
							String message = "CHALL:" + GameData.myID + "#" + opponentID;
							// System.out.println(message);
							JOptionPane.showConfirmDialog(null, "�Ѿ����Ͷ�ս����", "�����ͳɹ�", JOptionPane.DEFAULT_OPTION,
									JOptionPane.DEFAULT_OPTION);
							IOTool.getInstance().getWriter().println(message); // ���Ͷ�ս����
						}
					}
					else if(!ListTool.getInstance().getPlayerList().isSelectionEmpty()&&e.getClickCount() == 1){
						JList list = ListTool.getInstance().getPlayerList();
						int index = list.locationToIndex(e.getPoint());
						GameData.chosenOpponentID=(String) list.getModel().getElementAt(index); // ��ȡ����
						System.out.println(GameData.chosenOpponentID);
					}
				}
			}
			//��Ϸû�н���������ƥ�����
			else if(!GameData.opponentID.equals("")&&GameData.gameOver==false){
				JOptionPane.showConfirmDialog(null, "��ǰ������Ϸ������ƥ���µ���ң�", "��Ϸ������ƥ��", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
			//������Ϸ����
			else if(!GameData.opponentID.equals("")&&GameData.gameOver==true){
				JOptionPane.showConfirmDialog(null, "���������������֣�", "���ֽ���", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
