package com.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.client.gamedata.GameData;
import com.client.tools.ListTool;
import com.server.tools.HashMapTool;
import com.server.tools.MessageTool;
import com.server.tools.Player;

/**
 * ��������ϷЭ������� ֻ���յ���String���д���
 * 
 * @author Edward Zhang
 *
 */
public class Resolver {
	private String message = null;
	private String playerID = null;
	private Socket socket = null;
	private PrintStream writer = null;
	private BufferedReader reader = null;

	public Resolver(String playerID, String message, Socket socket) {
		this.message = message;
		this.playerID = playerID;
		this.socket = socket;
	}

	// Э�������������
	public void resolve() {
		try {
			String[] messageSplit = message.split(":");
			String header = messageSplit[0];
			String content = messageSplit[1].trim();
			
			if (header.equals("HELLO")) {
				playerLogin(content);
			} 
			else if(header.equals("LIST")){
				sendPlayerList();
			}
			else if (header.equals("CHALL")) {
				String[] IDSet=content.split("#");
				String fromID=IDSet[0];
				String toID=IDSet[1];
				askPlayerToBattle(fromID, toID);
				flushPlayerList();
			}
			//����ս��ͬ����ս����ʼ��Ϸ
			else if(header.equals("YESCHALL")){
				String[] IDSet=content.split("#");
				String fromID=IDSet[0];
				String toID=IDSet[1];
				agreePlayerToBattle(fromID, toID);
				youAreBlack(toID);
				youAreWhite(fromID);
				HashMapTool.getInstance().getPlayer(fromID).setBusy();
				HashMapTool.getInstance().getPlayer(toID).setBusy();
				flushPlayerList();
				//setBusy(fromID,toID);
				
			}
			else if(header.equals("NOCHALL")){
				String[] IDSet=content.split("#");
				String fromID=IDSet[0];
				String toID=IDSet[1];
				refusePlayerToBattle(fromID, toID);
				flushPlayerList();
			}
			else if(header.equals("WIN")){
				String[] IDSet=content.split("#");
				String fromID=IDSet[0];
				String toID=IDSet[1];
				sendWin(content, toID);
				//releaseBusy(fromID,toID);
				HashMapTool.getInstance().getPlayer(fromID).releaseBusy();
				HashMapTool.getInstance().getPlayer(toID).releaseBusy();
				flushPlayerList();
			}
			else if(header.equals("LOSE")){
				String[] IDSet=content.split("#");
				String fromID=IDSet[0];
				String toID=IDSet[1];
				sendLose(content, toID);
				//releaseBusy(fromID,toID);
				HashMapTool.getInstance().getPlayer(fromID).releaseBusy();
				HashMapTool.getInstance().getPlayer(toID).releaseBusy();
				flushPlayerList();
			}
			else if(header.equals("YOURTURN")){
				String[] IDSet=content.split("#");
				String toID=IDSet[1];
				sendTurn(content, toID);
				flushPlayerList();
			}
			else if(header.equals("SAY")){
				String[] IDSet=content.split("#");
				String toID=IDSet[1];
				sendMessage(content.split("#")[2], toID);
				flushPlayerList();
			}
			else if(header.equals("LEAVE")){
				String[] IDSet=content.split("#");
				String toID=IDSet[1];
				sendLeave(content.split("#")[2], toID);
				flushPlayerList();
			}
			else if(header.equals("REJECT")){
				String[] IDSet=content.split("#");
				String toID=IDSet[1];
				sendReject(content.split("#")[2], toID);
				flushPlayerList();
			}
			
		} catch (Exception e) {
		}
	}

	public Resolver() {

	}

	// ��ҵ�¼
	public void playerLogin(String playerID) throws IOException {
		Player player = new Player(playerID, socket);
		player.setReader(socket.getInputStream());
		player.setWriter(socket.getOutputStream());
		HashMapTool.getInstance().addPlayer(playerID, player);
		MessageTool.getInstance().addMessage("���" + playerID + "���ߣ�");
		sendPlayerList();
		flushPlayerList();
	}

	// �������
	public void playerOffLine(String playerID) {  //�����������ڶ�ս��Ҫ��һ������
		HashMapTool.getInstance().dropPlayer(playerID);
		sendPlayerList();
		flushPlayerList();
		MessageTool.getInstance().addMessage("���" + playerID + "���ߣ�");
	}

	// ��������б�
	public void sendPlayerList() {
		HashMap playerMap = HashMapTool.getInstance().getPlayerMap();
		Iterator iter = playerMap.entrySet().iterator();
		String playerList = HashMapTool.getInstance().getPlayerList();
		while (iter.hasNext()) {		//�������map����ÿ����ҷ�������б�
			try {
				Map.Entry entry = (Map.Entry) iter.next();  
			    Player player = (Player)entry.getValue(); //��ȡplayer����
				writer = player.getWriter();
				writer.println("LIST:" + playerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//���·���������б�
	public void flushPlayerList(){
		ListTool.getInstance().removePlayer();
		HashMap playerMap = HashMapTool.getInstance().getPlayerMap();
		Iterator iter = playerMap.entrySet().iterator();
		while (iter.hasNext()) {		
			try {
				Map.Entry entry = (Map.Entry) iter.next();  
			    Player player = (Player)entry.getValue();
				ListTool.getInstance().addPlayer(player.getPlayerID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//�������
	public void askPlayerToBattle(String fromID,String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("NEWCHALL:"+fromID+"#"+toID);
	}
	//���ضԷ�ͬ���ս����Ϣ
	public void agreePlayerToBattle(String fromID,String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("YESCHALL:"+fromID+"#"+toID);
	}
	//���ز�ͬ���ս����Ϣ
	public void refusePlayerToBattle(String fromID,String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("NOCHALL:"+fromID+"#"+toID);
	}
	//֪ͨ��������
	public void youAreBlack(String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("YOUAREBLACK:"+toID);
	}
	//֪ͨ�������
	public void youAreWhite(String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("YOUAREWHITE:"+toID);
	}
	//֪ͨtoID����
	public void sendTurn(String message,String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("YOURTURN:"+message);
	}
	//֪ͨtoID�Է�Ӯ
	public void sendWin(String message,String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("WIN:"+message);
	}
	//֪ͨtoID�Է���
	public void sendLose(String message,String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("LOSE:"+message);
	}
	//֪ͨtoID�Է��쳣�˳�
		public void sendLeave(String message,String toID){
			HashMapTool.getInstance().getPlayer(toID).getWriter().println("LEAVE:"+message);
			ListTool.getInstance().dropPlayer(GameData.myID);
		}
		//֪ͨtoID�Է��쳣�˳�
				public void sendReject(String message,String toID){
					HashMapTool.getInstance().getPlayer(toID).getWriter().println("REJECT:"+message);
				}
	//��Ϣ����
	public void sendMessage(String message,String toID){
		HashMapTool.getInstance().getPlayer(toID).getWriter().println("SAY:"+message);
	}

}