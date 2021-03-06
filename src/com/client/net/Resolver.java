package com.client.net;

import java.awt.Color;

import javax.swing.JList;
import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.listener.FlushListener;
import com.client.tools.IOTool;
import com.client.tools.ListTool;
import com.client.tools.MessageTool;
import com.client.ui.ChessBoard;
import com.client.ui.ChessPoint;
import com.client.ui.ClientFrame;
import com.client.ui.MessagePanel;
import com.client.ui.PlayerPanel;
import com.client.ui.StatusPanel;
import com.server.tools.HashMapTool;

/**
 * 客户端的协议解析器 对于本地产生的请求或者是服务器请求作出解析
 * 
 * @author Edward Zhang
 *
 */
public class Resolver {

	private String message = null;
	public Resolver(String message) {
		this.message = message;
	}

	public void resolve() {

		String[] messageSplit = message.split(":");
		// System.out.println(message);
		String header = messageSplit[0];
		String content = messageSplit[1];
		System.out.println("消息内容："+header+"------------"+content);
		//服务器关闭客户端提示
		if(header.equals("ERROR")){
			JOptionPane.showConfirmDialog(null,"连接服务器错误，请检查网络",
				     "连接错误",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
			GameData.isConnected = false;
			ClientFrame.getInstance().dispose();
		}
		//接收列表
		if (header.equals("LIST")) { 		
			ListTool.getInstance().removePlayer();
			String[] playerList = content.split("#");
			for (int i = 0; i < playerList.length; i++) {
				if(playerList[i].equals(GameData.myID)){
					playerList[i] = playerList[i]+"(本机请勿匹配)";
				}
				ListTool.getInstance().addPlayer(playerList[i]);
			}
		}
		else if(header.equals("NEWCHALL")){
			String fromID=content.split("#")[0];
			if(GameData.hasOpponent == false){
			int choice=JOptionPane.showConfirmDialog(null,fromID+"向您挑战，是否同意？",
				     "收到对战请求",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			//同意对战
			if(choice==JOptionPane.YES_OPTION){
				
					GameData.hasOpponent=true;
					GameData.opponentID=fromID;
					IOTool.getInstance().getWriter().println("YESCHALL:"+GameData.myID+"#"+fromID);
					PlayerPanel.getInstance().setEscapeEnabledValid();	
				
				
			}
			//不同意对战
			else if(choice==JOptionPane.NO_OPTION){
				IOTool.getInstance().getWriter().println("NOCHALL:"+GameData.myID+"#"+fromID);
			}
			}else{
				IOTool.getInstance().getWriter().println("NOCHALL:"+GameData.myID+"#"+fromID);
			}
		}
		//对方同意对战
		else if(header.equals("YESCHALL")){
				GameData.hasOpponent=true;
				GameData.opponentID=content.split("#")[0];
				JOptionPane.showConfirmDialog(null,"对方接收了您的挑战！",
					     "对方接受挑战",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
				PlayerPanel.getInstance().setEscapeEnabledValid();
		}
		//对方不同意对战
		else if(header.equals("NOCHALL")){
			JOptionPane.showConfirmDialog(null,"对方拒绝了您的挑战！",
				     "对方拒绝挑战",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
		}
		
		//我方是黑色棋子
		else if(header.equals("YOUAREBLACK")){
			GameData.isBlack=true;
			GameData.myTurn=true;
			GameData.myColor= (GameData.isBlack)?Color.black:Color.WHITE;
			MessageTool.getInstance().addMessage("游戏开始，请您先手！");
		}
		//我方是白色棋子
		else if(header.equals("YOUAREWHITE")){
			GameData.isBlack=false;
			GameData.myTurn=false;
			GameData.myColor= (GameData.isBlack)?Color.black:Color.WHITE;
			MessageTool.getInstance().addMessage("游戏开始，等待对方落子……");
		}
		
		//对方发来胜利标识，我方失败
		else if(header.equals("WIN")){
			GameData.gameOver=true;
			MessageTool.getInstance().addMessage("请点击重来结束本局！");
			JOptionPane.showConfirmDialog(null,"你输了",
				     "结果产生",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
			StatusPanel.getInstance().setResetStatusValid();
			PlayerPanel.getInstance().setEscapeEnabledInvalid();
			GameData.Reset();
			GameData.hasOpponent = false;
		}
		//对方发来失败标识，我方胜利
		else if(header.equals("LOSE")){
			GameData.gameOver=true;
			MessageTool.getInstance().addMessage("请点击重来结束本局！");
			JOptionPane.showConfirmDialog(null,"对方逃跑！你赢了！",
				     "结果产生",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
			StatusPanel.getInstance().setResetStatusValid();
			PlayerPanel.getInstance().setEscapeEnabledInvalid();
			GameData.hasOpponent = false;
			GameData.Reset();
		}
		//轮到我方行动
				
		else if(header.equals("SAY")){
			MessageTool.getInstance().addMessage("对方说："+content);
		}
		//对方异常退出，请重开
		else if (header.equals("LEAVE")) {
			GameData.gameOver = true;
			MessageTool.getInstance().addMessage("请点击重来结束本局！");
			JOptionPane.showConfirmDialog(null, "对方异常退出，请重开！", "结果产生", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION);
			StatusPanel.getInstance().setResetStatusValid();
			PlayerPanel.getInstance().setEscapeEnabledInvalid();
			GameData.Reset();
			GameData.hasOpponent = false;
		} else if (header.equals("REJECT")) {
			JOptionPane.showConfirmDialog(null, "对方正在游戏，拒绝了您的挑战！", "对方拒绝挑战", JOptionPane.DEFAULT_OPTION,
					JOptionPane.DEFAULT_OPTION);
		} else {
			if (header.equals("YOURTURN")) {
				
				GameData.myTurn = true;
				MessageTool.getInstance().addMessage("请您落子...");
				// 获取棋子位置并且加入
				String[] position = content.split("#");
				int currentX = Integer.parseInt(position[2]);
				int currentY = Integer.parseInt(position[3]);
				ChessBoard.cp[ChessBoard.chessCount++] = new ChessPoint(currentX, currentY,
						(GameData.isBlack) ? Color.WHITE : Color.black);
				ChessBoard.getInstance().repaint();
			}
		}

	}
	}
