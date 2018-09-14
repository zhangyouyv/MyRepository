package com.client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.client.listener.ChallengeListener;
import com.client.listener.EscapeListener;
import com.client.listener.FlushListener;
import com.client.listener.ListListener;
import com.client.tools.ListTool;
/**
 * �ͻ�������б����
 * ����һ��Jlist��ʾ��ң�������ť�ֱ�Ϊ����ս�������͡������ˡ�
 * ���Զ�̬ˢ���������
 * �����ҿ��Խ��ж�ս
 * @author Edward
 *
 */
public class PlayerPanel extends JPanel {

	private static PlayerPanel playerpanel = null;

	private JScrollPane playerPane=new JScrollPane();
	private JList<String> playerList=ListTool.getPlayerList();
	
	private JButton challengeBtn=new JButton("������Ҷ�ս");
	private JButton escapeBtn=new JButton("������");
	private JButton flushBtn=new JButton("ˢ��");
	private JPanel buttonBar=new JPanel();
	
	public static PlayerPanel getInstance() {
		if (playerpanel == null) {
			playerpanel = new PlayerPanel();
		}
		return playerpanel;
	}
	
	private PlayerPanel(){  //��������
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("��������б�"));  //��ӱ߿�
		add(playerPane,BorderLayout.CENTER);
		add(buttonBar,BorderLayout.SOUTH);
		playerList.addMouseListener(new ListListener());
		playerList.setFixedCellWidth(240);  //���ù̶���Ԫ���С��������������Զ�����
		//playerPane.setSize(20, 500);
		playerPane.setViewportView(playerList);
		playerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //ֻ�к��������
		//playerPane.setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		buttonBar.setLayout(new BorderLayout());
		buttonBar.add(challengeBtn,BorderLayout.CENTER);
		buttonBar.add(escapeBtn,BorderLayout.EAST);
		buttonBar.add(flushBtn,BorderLayout.WEST);
		challengeBtn.addActionListener(new ChallengeListener());
		escapeBtn.addActionListener(new EscapeListener());
		escapeBtn.setEnabled(false);
		flushBtn.addActionListener(new FlushListener());
	}
	
	public void setEscapeEnabledInvalid(){
		escapeBtn.setEnabled(false);
	}
	public void setEscapeEnabledValid(){
		escapeBtn.setEnabled(true);
	}


}
