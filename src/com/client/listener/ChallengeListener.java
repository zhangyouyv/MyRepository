 package com.client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.server.tools.HashMapTool;
import com.server.tools.Player;

public class ChallengeListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		//如果没有登录提示
		if (GameData.isConnected == false) {
			JOptionPane.showConfirmDialog(null, "请先登录再挑战！", "登录提示", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION);
		} 
		else {
			
		     		
				
			
			//选择对手提示
			if (GameData.chosenOpponentID.equals("")) {
				JOptionPane.showConfirmDialog(null, "请先选择一个对手再挑战！", "选择对手提示", JOptionPane.DEFAULT_OPTION,
						JOptionPane.DEFAULT_OPTION);
			} 
			//不能匹配到正在对战的玩家
			
			    if(HashMapTool.getInstance().getPlayer(GameData.chosenOpponentID).getStatus() == true){
			   JOptionPane.showConfirmDialog(null, "对手正在游戏，不能匹配新的玩家！", "游戏中请勿匹配", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);}
			else {
				//不能和自己对战提示
				if(GameData.chosenOpponentID.contains("(")){
				if (GameData.chosenOpponentID.equals(GameData.myID) | GameData.chosenOpponentID.substring(0, GameData.chosenOpponentID.indexOf("(")).equals(GameData.myID)) {
					System.out.println(GameData.chosenOpponentID);
					JOptionPane.showConfirmDialog(null, "不能和自己对战！", "对战错误", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				} 
				}
				//游戏中提示不能匹配提醒
				else if(!GameData.opponentID.equals("")&&GameData.gameOver==false){
					JOptionPane.showConfirmDialog(null, "当前正在游戏，不能匹配新的玩家！", "游戏中请勿匹配", JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					String message = "CHALL:" + GameData.myID + "#" + GameData.chosenOpponentID;
					// System.out.println(message);
					IOTool.getInstance().getWriter().println(message); // 发送对战请求
					JOptionPane.showConfirmDialog(null, "已经发送对战请求！", "请求发送成功", JOptionPane.DEFAULT_OPTION,
							JOptionPane.DEFAULT_OPTION);
				}
			}
		}
	}
	}


