package com.client.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.server.tools.HashMapTool;
import com.server.tools.Player;
import com.server.ui.ServerFrame;

/**
 * 五子棋游戏客户端
 * 
 * @author Edward Zhang
 *
 */
public class ClientFrame extends JFrame {

	private static ClientFrame client = null;
	private GamePanel gamePanel = GamePanel.getInstance();
	private FunctionPanel functionPanel = FunctionPanel.getInstance();
	private PrintStream writer = null;

	public static ClientFrame getInstance() {
		if (client == null)
			client = new ClientFrame();
		return client;
	}

	// 构造函数，总体绘制
	public ClientFrame() {
		this.setTitle("五子棋对战客户端");
		this.setSize(GameData.FrameWidth, GameData.FrameHeight);
		this.setLocation(50, 10);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		  addWindowListener(new WindowAdapter(){
			   @Override
			   public void windowClosing(WindowEvent e) {
			     int   option=   JOptionPane.showConfirmDialog( 
			    		 ClientFrame.this, "确定退出游戏！ ", "提示 ",JOptionPane.YES_NO_OPTION); 
			      if(option==JOptionPane.YES_OPTION) 
			         if(e.getWindow()   ==   ClientFrame.this) 
			         { //获取对战map
			        	 IOTool.getInstance().getWriter().println("LEAVE:"+GameData.myID+"#"+GameData.opponentID+"#"+"1");
			         System.exit(0); 
			         } 
			         else 
			         { 
			         return; 
			         
			         } 
			   }
			  });

		// 添加面板组件
		this.setLayout(new BorderLayout());

		this.add(gamePanel, BorderLayout.CENTER);
		this.add(PlayerPanel.getInstance(), BorderLayout.WEST);
		this.add(MessagePanel.getInstance(), BorderLayout.EAST);
		this.setVisible(true);

	}
}
