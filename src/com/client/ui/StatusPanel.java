package com.client.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.client.listener.ResetListener;

public class StatusPanel extends JPanel {
	private static StatusPanel statusPanel = null;
	private JButton resetBtn = new JButton("����");
	private JLabel statusLbl = new JLabel("��ǰ״̬��δ��¼");
	/*private JLabel whoseturn = new JLabel("���ӱ�ʶ");
	public JLabel getWhoseturn() {
		return whoseturn;
	}
	public void setWhoseturnforOwn() {
		whoseturn.setText("����������");
	}
	public void setWhoseturnforOther() {
		whoseturn.setText("�ö���������");
	}*/
	
	private JLabel myID= new JLabel("                       ����ID����¼��ȡID");

	public static StatusPanel getInstance() {
		if (statusPanel == null) {
			statusPanel = new StatusPanel();
		}
		return statusPanel;
	}
	
	public JLabel getStatus(){
		return statusLbl;
	}

	public void setStatusToOn(){
		statusLbl.setText("��ǰ״̬���ѵ�¼");
	}
	public void setStatusToOff(){
		statusLbl.setText("��ǰ״̬��δ��¼");
	}
	public void setResetStatusValid(){
		resetBtn.setEnabled(true);
	}
	public void setResetStatusInValid(){
		resetBtn.setEnabled(false);
	}
	public void setID(String ID){
		myID.setText("                       ����ID��"+ID);
	}
	public StatusPanel(){
		this.setLayout(new BorderLayout());
		this.add(resetBtn,BorderLayout.EAST);
		this.add(statusLbl,BorderLayout.WEST);
		/*this.add(whoseturn,BorderLayout.NORTH);*/
		this.add(myID, BorderLayout.CENTER);
		resetBtn.setEnabled(false);
		resetBtn.addActionListener(new ResetListener());
	}
	
	
}
