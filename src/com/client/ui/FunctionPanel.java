package com.client.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import javafx.scene.layout.Border;

/**
 * ������������һ������б�JPanel��һ����Ϣ���͵�JPanel
 * @author Edward Zhang
 *
 */
public class FunctionPanel extends JPanel{
	private static FunctionPanel functionPanel=null;
	
	private MessagePanel messagePanel=MessagePanel.getInstance();
	private PlayerPanel playerPanel=PlayerPanel.getInstance();
	
	public static FunctionPanel getInstance(){
		if(functionPanel==null){
			functionPanel=new FunctionPanel();
		}
		return functionPanel;
	}
	private FunctionPanel(){
		setLayout(new BorderLayout());
		add(playerPanel,BorderLayout.WEST);
		add(messagePanel,BorderLayout.CENTER);
	}
}
