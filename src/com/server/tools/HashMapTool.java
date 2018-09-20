package com.server.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * �������洢������� �����ṩͳһ�Ľӿ�
 * 
 * @author Edward Zhang
 *
 */
public class HashMapTool {
	private static HashMapTool hashMapTool = null;
	private HashMap<String, Player> playerMap = null; // �洢���������
	private HashMap<Player, Player> battleMap = null; // �洢��Ե����˫��

	public static HashMapTool getInstance() {
		if (hashMapTool == null) {
			hashMapTool = new HashMapTool();
		}
		return hashMapTool;
	}

	// ��ȡ���Map
	public HashMap<String, Player> getPlayerMap() { // ���Է����û��б��Ƿ��ƻ���װ��
		if (playerMap == null) {
			playerMap = new HashMap<String, Player>();
		}
		return playerMap;
	}

	// ��ȡæµ���Map
	public HashMap<Player, Player> getBattleMap() { //
		if (battleMap == null) {
			battleMap = new HashMap<Player, Player>();
		}
		return battleMap;
	}

	public void addPlayer(String playerID, Player player) {
		getPlayerMap().put(playerID, player);
	}

	public void dropPlayer(String playerID) {
		getPlayerMap().remove(playerID);
	}

	public void removeAllPlayer() {
		getPlayerMap().clear();
	}

	public Player getPlayer(String playerID) {
		return getPlayerMap().get(playerID);
	}

	// // ��������ҽ���ƥ�䣬����æµ���Map
	// public void putToBusy(String fromID, String toID) {
	// Player fromPlayer=getPlayerMap().get(fromID);
	// Player toPlayer=getPlayerMap().get(toID);
	// getBattleMap().put(fromPlayer, toPlayer);
	// getPlayerMap().remove(fromID);
	// getPlayerMap().remove(toID);
	// System.out.println(getPlayerMap().size());
	// }
	//
	// // ������Ҷ�ս�������������ͷŻ����Map
	// public void releaseFromBusy(String fromID, String toID) {
	// Iterator iter = getBattleMap().entrySet().iterator();
	// while (iter.hasNext()) {
	// Map.Entry entry = (Map.Entry) iter.next();
	//
	// Player p1=(Player)entry.getKey();
	// System.out.println(p1.getPlayerID());
	// if(p1.getPlayerID().equals(fromID)){
	// Player p2=getBattleMap().get(p1);
	// getBattleMap().remove(p1,p2);
	// getPlayerMap().put(p1.getPlayerID(), p1);
	// getPlayerMap().put(p2.getPlayerID(), p2);
	//
	// System.out.println(getPlayerMap().size());
	// break;
	//
	// }
	// else if(p1.getPlayerID().equals(toID)){
	// Player p2=getBattleMap().get(p1);
	// getBattleMap().remove(p1,p2);
	// getPlayerMap().put(p1.getPlayerID(), p1);
	// getPlayerMap().put(p2.getPlayerID(), p2);
	// System.out.println(getPlayerMap().size());
	// break;
	// }
	// }
	//
	// }

	// ��ȡ����б�����Э�����ݷ��ͣ�
	public String getPlayerList() {
		StringBuffer strbuf = new StringBuffer();
		Iterator iter = getPlayerMap().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String playerID = (String) entry.getKey();
			if (!getPlayerMap().get(playerID).getStatus()) {  //æµ������
				strbuf = strbuf.append(playerID);
				strbuf = strbuf.append("#");
			}
		}
		return strbuf.toString();
	}
}
