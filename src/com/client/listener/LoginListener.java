package com.client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.net.ClientThread;
import com.client.tools.IOTool;
import com.client.ui.ClientFrame;
import com.client.ui.LoginPanel;
import com.client.ui.StatusPanel;
/**
 * �ͻ��˵�¼��ť������
 * @author Edward Zhang
 *
 */
public class LoginListener implements ActionListener {
	private static LoginListener loginListener = null;

	public static LoginListener getInstance() { // ��¼��ť����������ģʽ
		if (loginListener == null) {
			loginListener = new LoginListener();
		}
		return loginListener;
	}

	public void actionPerformed(ActionEvent e) {
		String serverIP = LoginPanel.getOpIP().getText().trim();
		int port = Integer.parseInt(LoginPanel.getOpPort().getText().trim());
		 // �Ѿ����ӹ���
		if (GameData.isConnected) {
			JOptionPane.showConfirmDialog(null, "���Ѿ���¼��", "���Ӿ���", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
		// ��������
		else {
			try {
				Socket socket = new Socket(serverIP, port);
				GameData.isConnected = true;
				IOTool.getInstance().setWriter(socket.getOutputStream());
				IOTool.getInstance().setReader(socket.getInputStream());
				Thread clientThread = new Thread(new ClientThread());
				clientThread.start();
				StatusPanel.getInstance().setStatusToOn();
				
				// ���Է���
				GameData.myID=String.valueOf(System.currentTimeMillis());
//				ClientFrame.getInstance().setTitle(GameData.myID);
				StatusPanel.getInstance().setID(GameData.myID);
				IOTool.getInstance().getWriter().println("HELLO:" + GameData.myID);
				
				

			} catch (UnknownHostException e1) { // ����ʧ�ܵ�ʱ����
				JOptionPane.showConfirmDialog(null, "������δ����", "���Ӵ���", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				JOptionPane.showConfirmDialog(null, "���������Ӵ���", "���Ӵ���", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

}
