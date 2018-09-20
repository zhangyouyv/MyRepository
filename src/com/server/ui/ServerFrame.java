package com.server.ui;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.client.gamedata.GameData;
import com.client.net.ClientThread;
import com.client.tools.ListTool;
import com.server.net.ServerThread;
import com.server.tools.HashMapTool;
import com.server.tools.MessageTool;
import com.server.tools.Player;

/*
 * ��������
 * �������PlayerList����MessagePanel
 * 
 */
public class ServerFrame extends JFrame {
	private static ServerFrame server = null;
	private boolean isConnected = false;
	private MessagePanel messagePanel = MessagePanel.getInstance();
	private PlayerPanel playerPanel = PlayerPanel.getInstance();
	private PrintStream writer = null;

	public static ServerFrame getInstance() {
		if (server == null) {
			server = new ServerFrame();
		}
		return server;
	}

	private ServerFrame() {
		setTitle("����������������");
		setSize(600, 700);
		setLocation(100, 200);
		setLayout(new GridLayout());
		add(messagePanel);
		add(playerPanel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		  addWindowListener(new WindowAdapter(){
			   @Override
			   public void windowClosing(WindowEvent e) {
			     int   option=   JOptionPane.showConfirmDialog( 
			    		 ServerFrame.this, "ȷ���˳�������? ", "��ʾ ",JOptionPane.YES_NO_CANCEL_OPTION); 
			      if(option==JOptionPane.YES_OPTION) 
			         if(e.getWindow()   ==   ServerFrame.this) 
			         { 
			        	 HashMap playerMap = HashMapTool.getInstance().getPlayerMap();
			     		Iterator iter = playerMap.entrySet().iterator();
			     		while (iter.hasNext()) {		//�������map
			     			try {
			     				//��ÿ���û�������Ϣ��ʾ�������ر�
			     				Map.Entry entry = (Map.Entry) iter.next();  
			     			    Player player = (Player)entry.getValue(); //��ȡplayer����
			     				writer = player.getWriter();
			     				writer.println("ERROR:1");
			     			} catch (Exception ex) {
			     				ex.printStackTrace();
			     			}
			     		}
			         System.exit(0); 
			         } 
			         else 
			         { 
			         return; 
			         
			         } 
			   }
			  });
		setResizable(false);
		setVisible(true);
		startServer();
	}

	// �������������󶨶˿ڿ�ʼ����
	private void startServer(){
		ServerSocket ss=null;
		try{
			isConnected=true;	//��������������
			ss=new ServerSocket(6666);
			MessageTool.getInstance().addMessage("������������");
			ListTool.getInstance().addPlayer("�ȴ����......");
			//ListTool.getInstance().addPlayer("�ȴ����......");
			while(isConnected){
				Socket socket=ss.accept();
				//System.out.println("�ͻ������ӳɹ�");
				Thread serverThread=new Thread(new ServerThread(socket));
				serverThread.start();
			}
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null,"�˿��Ѿ��󶨣��벻Ҫ����������������",
				     "�˿�ռ�þ���",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}
}
