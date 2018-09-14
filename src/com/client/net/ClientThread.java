package com.client.net;

import java.io.IOException;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;

/**
 * ѭ�����շ���������Ϣ
 * 
 * @author Edward
 *
 */
public class ClientThread implements Runnable {

	public ClientThread() {

	}

	public void run() {
		while (GameData.isConnected) {
			try {
				String message = IOTool.getInstance().getReader().readLine();
				new Resolver(message).resolve();
			} catch (IOException e) {
				e.printStackTrace();
			} // �õ�Э������
		}
	}

}
