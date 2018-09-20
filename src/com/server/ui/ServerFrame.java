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
 * 服务器类
 * 界面包含PlayerList，和MessagePanel
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
		setTitle("五子棋联网版服务端");
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
			    		 ServerFrame.this, "确定退出服务器? ", "提示 ",JOptionPane.YES_NO_CANCEL_OPTION); 
			      if(option==JOptionPane.YES_OPTION) 
			         if(e.getWindow()   ==   ServerFrame.this) 
			         { 
			        	 HashMap playerMap = HashMapTool.getInstance().getPlayerMap();
			     		Iterator iter = playerMap.entrySet().iterator();
			     		while (iter.hasNext()) {		//遍历玩家map
			     			try {
			     				//给每个用户发送消息提示服务器关闭
			     				Map.Entry entry = (Map.Entry) iter.next();  
			     			    Player player = (Player)entry.getValue(); //获取player对象
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

	// 启动服务器，绑定端口开始监听
	private void startServer(){
		ServerSocket ss=null;
		try{
			isConnected=true;	//表明服务器上线
			ss=new ServerSocket(6666);
			MessageTool.getInstance().addMessage("服务器开启！");
			ListTool.getInstance().addPlayer("等待玩家......");
			//ListTool.getInstance().addPlayer("等待玩家......");
			while(isConnected){
				Socket socket=ss.accept();
				//System.out.println("客户端连接成功");
				Thread serverThread=new Thread(new ServerThread(socket));
				serverThread.start();
			}
		}catch(Exception e){
			JOptionPane.showConfirmDialog(null,"端口已经绑定！请不要启动两个服务器！",
				     "端口占用警告",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}
}
