package com.server.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * ����ÿ���ͻ��˵Ĵ����߳�
 * 
 * @author Edward Zhang
 *
 */
public class ServerThread implements Runnable {
	private boolean isConnected = false; // �ͻ�������
	private Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	// �����������˵ı�����Ϣ���͸�Resolver����������
	public void run() {
		isConnected = true;
		String playerID = null;
		try {
			while (isConnected) {
				InputStream playerReader=socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(playerReader));
				String message = reader.readLine();
				String[] messageSplit = message.split(":");
				playerID = messageSplit[1].split("#")[0].trim();
//				System.out.println(playerID);
				new Resolver(playerID, message,socket).resolve();
			}
		} catch (Exception e) { // ��ȡ�����������ʱ�������쳣��ֱ���ж��ͻ�������
								// �ߵ�ʱ��Ҫ����GOODBYE����̫��233
			isConnected = false;
//			System.out.println("����Ѿ�����");
			new Resolver().playerOffLine(playerID);  //������ߵĴ������
			//e.printStackTrace();
		}
	}

}
