package com.client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.ui.ChessBoard;
/**
 * ������ť�ļ����¼�
 * @author Edward Zhang
 *
 */
public class ResetListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		GameData.Reset();
		JOptionPane.showConfirmDialog(null, "���ڿ������º����������������", "�Ѿ��ͶԷ��Ͽ�����", JOptionPane.DEFAULT_OPTION,
				JOptionPane.DEFAULT_OPTION);
	}
	
}
