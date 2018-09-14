package com.client.net;

import java.awt.Color;

import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.client.tools.ListTool;
import com.client.tools.MessageTool;
import com.client.ui.ChessBoard;
import com.client.ui.ChessPoint;
import com.client.ui.MessagePanel;
import com.client.ui.PlayerPanel;
import com.client.ui.StatusPanel;

/**
 * �ͻ��˵�Э������� ���ڱ��ز�������������Ƿ�����������������
 * 
 * @author Edward
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
		
		//�����б�
		if (header.equals("LIST")) { 		
			ListTool.getInstance().removePlayer();
			String[] playerList = content.split("#");
			for (int i = 0; i < playerList.length; i++) {
				if(playerList[i].equals(GameData.myID)){
					playerList[i] = playerList[i]+"(��������ƥ��)";
				}
				ListTool.getInstance().addPlayer(playerList[i]);
			}
		}
		else if(header.equals("NEWCHALL")){
			String fromID=content.split("#")[0];
			int choice=JOptionPane.showConfirmDialog(null,fromID+"������ս���Ƿ�ͬ�⣿",
				     "�յ���ս����",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			//ͬ���ս
			if(choice==JOptionPane.YES_OPTION){
				GameData.hasOpponent=true;
				GameData.opponentID=fromID;
				IOTool.getInstance().getWriter().println("YESCHALL:"+GameData.myID+"#"+fromID);
				PlayerPanel.getInstance().setEscapeEnabledValid();
			}
			//��ͬ���ս
			else if(choice==JOptionPane.NO_OPTION){
				IOTool.getInstance().getWriter().println("NOCHALL:"+GameData.myID+"#"+fromID);
			}
		}
		//�Է�ͬ���ս
		else if(header.equals("YESCHALL")){
			GameData.hasOpponent=true;
			GameData.opponentID=content.split("#")[0];
			JOptionPane.showConfirmDialog(null,"�Է�������������ս��",
				     "�Է�������ս",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
			PlayerPanel.getInstance().setEscapeEnabledValid();
		}
		//�Է���ͬ���ս
		else if(header.equals("NOCHALL")){
			JOptionPane.showConfirmDialog(null,"�Է��ܾ���������ս��",
				     "�Է��ܾ���ս",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
		}
		//�ҷ��Ǻ�ɫ����
		else if(header.equals("YOUAREBLACK")){
			GameData.isBlack=true;
			GameData.myTurn=true;
			GameData.myColor= (GameData.isBlack)?Color.black:Color.WHITE;
			MessageTool.getInstance().addMessage("��Ϸ��ʼ���������֣�");
		}
		//�ҷ��ǰ�ɫ����
		else if(header.equals("YOUAREWHITE")){
			GameData.isBlack=false;
			GameData.myTurn=false;
			GameData.myColor= (GameData.isBlack)?Color.black:Color.WHITE;
			MessageTool.getInstance().addMessage("��Ϸ��ʼ���ȴ��Է����ӡ���");
		}
		//�ֵ��ҷ��ж�
		else if(header.equals("YOURTURN")){
			GameData.myTurn=true;
			MessageTool.getInstance().addMessage("��������...");
			//��ȡ����λ�ò��Ҽ���
			String[] position=content.split("#");
			int currentX=Integer.parseInt(position[2]);
			int currentY=Integer.parseInt(position[3]);
			ChessBoard.cp[ChessBoard.chessCount++] = new ChessPoint(currentX, currentY, (GameData.isBlack)?Color.WHITE:Color.black);
			ChessBoard.getInstance().repaint();
		}
		//�Է�����ʤ����ʶ���ҷ�ʧ��
		else if(header.equals("WIN")){
			GameData.gameOver=true;
			MessageTool.getInstance().addMessage("���������������֣�");
			JOptionPane.showConfirmDialog(null,"������",
				     "�������",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
			StatusPanel.getInstance().setResetStatusValid();
			PlayerPanel.getInstance().setEscapeEnabledInvalid();
		}
		//�Է�����ʧ�ܱ�ʶ���ҷ�ʤ��
		else if(header.equals("LOSE")){
			GameData.gameOver=true;
			MessageTool.getInstance().addMessage("���������������֣�");
			JOptionPane.showConfirmDialog(null,"�Է����ܣ���Ӯ�ˣ�",
				     "�������",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION);
			StatusPanel.getInstance().setResetStatusValid();
			PlayerPanel.getInstance().setEscapeEnabledInvalid();
		}
		else if(header.equals("SAY")){
			MessageTool.getInstance().addMessage("�Է�˵��"+content);
		}

	}
}
