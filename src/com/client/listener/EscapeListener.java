package com.client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.client.tools.MessageTool;
import com.client.ui.PlayerPanel;
import com.client.ui.StatusPanel;
/**
 * �������ˡ���ť�ļ����¼�
 * @author Edward
 *
 */
public class EscapeListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if(GameData.isConnected==false){
			JOptionPane.showConfirmDialog
			(null,"���ȵ�¼��", "��¼��ʾ",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
		}
		else if(GameData.hasOpponent==false){
			JOptionPane.showConfirmDialog
			(null,"���ȿ�ʼ��Ϸ��", "��Ϸδ��ʼ",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
		}
		else if(GameData.hasOpponent==true &&!GameData.opponentID.equals("")){
			int choice=JOptionPane.showConfirmDialog
			(null,"ս�������ܾ������䣬��ȷ����������", "�˳���ǰ���",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(choice==JOptionPane.YES_OPTION){
				GameData.gameOver=true;
				MessageTool.getInstance().addMessage("���������������֣�");
				JOptionPane.showConfirmDialog(null,"������",
					     "�������",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
				IOTool.getInstance().getWriter().println("LOSE:"+GameData.myID+"#"+GameData.opponentID);
				PlayerPanel.getInstance().setEscapeEnabledInvalid();
				StatusPanel.getInstance().setResetStatusValid();
			}
			else if(choice==JOptionPane.NO_OPTION){
				//��������
			}
		}
	}	
	

}
