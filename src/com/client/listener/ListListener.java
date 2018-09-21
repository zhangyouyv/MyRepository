package com.client.listener;

/**
 * 客户端列表监听器 双击选择玩家
 * @author Edward Zhang
 *
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JList;
import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.client.tools.ListTool;
import com.server.tools.HashMapTool;
import com.server.tools.Player;

public class ListListener extends MouseAdapter {
	public void mouseClicked(MouseEvent e) {
		if (GameData.isConnected){
			HashMap playerMap = HashMapTool.getInstance().getBattleMap();
			if (GameData.opponentID.equals("")) {
				if (!ListTool.getInstance().getPlayerList().isSelectionEmpty()) { // 不是空白
					if (e.getClickCount() == 2) {
						JList list = ListTool.getInstance().getPlayerList();
						int index = list.locationToIndex(e.getPoint());
						String opponentID = (String) list.getModel().getElementAt(index); // 获取内容
						// 不能和自己对战
						if(GameData.chosenOpponentID.contains("(")){
							if (GameData.chosenOpponentID.substring(0, GameData.chosenOpponentID.indexOf("(")).equals(GameData.myID)) {
								JOptionPane.showConfirmDialog(null, "不能和自己对战！", "对战错误", JOptionPane.DEFAULT_OPTION,
										JOptionPane.ERROR_MESSAGE);
							}
							}
						else if (opponentID.equals(GameData.myID)) {
							JOptionPane.showConfirmDialog(null, "不能和自己对战！", "对战错误", JOptionPane.DEFAULT_OPTION,
									JOptionPane.ERROR_MESSAGE);
						}
						
						//不能匹配到正在对战的玩家
						/*else 
						    if(HashMapTool.getInstance().getPlayer(GameData.chosenOpponentID).getStatus() == true){
								   JOptionPane.showConfirmDialog(null, "对手正在游戏，不能匹配新的玩家！", "游戏中请勿匹配", JOptionPane.DEFAULT_OPTION,
										JOptionPane.ERROR_MESSAGE);}
						*/
						// 选择
						else {
							String message = "CHALL:" + GameData.myID + "#" + opponentID;
							// System.out.println(message);
							JOptionPane.showConfirmDialog(null, "已经发送对战请求！", "请求发送成功", JOptionPane.DEFAULT_OPTION,
									JOptionPane.DEFAULT_OPTION);
							IOTool.getInstance().getWriter().println(message); // 发送对战请求
						}
					}
					else if(!ListTool.getInstance().getPlayerList().isSelectionEmpty()&&e.getClickCount() == 1){
						JList list = ListTool.getInstance().getPlayerList();
						int index = list.locationToIndex(e.getPoint());
						GameData.chosenOpponentID=(String) list.getModel().getElementAt(index); // 获取内容
						System.out.println(GameData.chosenOpponentID);
					}
				}
			}
			//游戏没有结束，不能匹配玩家
			else if(!GameData.opponentID.equals("")&&GameData.gameOver==false&&e.getClickCount() == 2&&!ListTool.getInstance().getPlayerList().isSelectionEmpty()){
				JOptionPane.showConfirmDialog(null, "当前正在游戏，不能匹配新的玩家！", "游戏中请勿匹配", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
			//当局游戏结束
			else if(!GameData.opponentID.equals("")&&GameData.gameOver==true){
				JOptionPane.showConfirmDialog(null, "请点击重来结束当局！", "当局结束", JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
