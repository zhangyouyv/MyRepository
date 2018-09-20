package com.server.tools;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * ����б����ʾ������ ����һ��JList��ListModel���Լ�һϵ�в��ݵķ���
 * 
 * @author Edward Zhang
 *
 */
public class ListTool {
	private static ListTool listTool = null;
	private static JList playerList=null;
	private static DefaultListModel listModel=null;
	
	public static ListTool getInstance(){  //����ģʽ
		if(listTool==null){
			listTool=new ListTool();
		}
		return listTool;
	}
	private static DefaultListModel getListModel(){ //ListModel����
		if(listModel==null){
			listModel=new DefaultListModel();
		}
		return listModel;
	}
	public static JList getPlayerList(){   //������Ϊ�������
		if(playerList==null){
			playerList=new JList(getListModel());
		}
		return playerList;
	}
	private ListTool(){  //���캯��
		
	}
	public void addPlayer(String playerName){  //�����ң���ʾ�������
		getListModel().addElement(playerName);
		//getPlayerList().repaint();
	}
	public void dropPlayer(String playerName){
		getListModel().removeElement(playerName);
	}
	public void removePlayer(){
		getListModel().removeAllElements();
	}
}
