package com.client.gamedata;

import java.awt.Color;

import com.client.ui.ChessBoard;

/**
 * �洢��Ϸ����
 * 
 * @author Edward
 *
 */
public class GameData {

	public static final int FrameHeight = 750;
	public static final int FrameWidth = 1270;
	
	public static final int port = 6666;
	public static String serverIP = "127.0.0.1";  //��������ʱû���õ�
	
	public static boolean isConnected = false; // �Ƿ����ӵı�־
	public static boolean hasOpponent = false; //�Ƿ��ж��֣��ж���Ϸ�Ƿ����ڽ���
	
	
	public static boolean gameOver=false;	//������Ϸ���̵ı���
	public static boolean isWinner=false;
	public static boolean isLoser=false;	//�������û���õ�
	
	public static boolean isBlack=false;	//�ҷ�������ɫ
	public static boolean myTurn=false;
	public static Color myColor= Color.blue;
	
	
	public static String myID="";
	public static String opponentID="";
	
	public static String chosenOpponentID=""; //�б����Ѿ�ѡ���ID
	
	public static void Reset(){
		GameData.gameOver=false;
		GameData.isWinner=false;
		GameData.isLoser=false;
		GameData.chosenOpponentID="";
		GameData.opponentID="";
		GameData.myTurn=false;
		ChessBoard.chessCount=0;
		ChessBoard.getInstance().clearCheessBoard();
		ChessBoard.getInstance().repaint();
	}
}
