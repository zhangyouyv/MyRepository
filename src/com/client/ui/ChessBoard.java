package com.client.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.client.gamedata.GameData;
import com.client.tools.IOTool;
import com.client.tools.MessageTool;

/**
 * ������ ���Ƴ����̺�����
 */
public class ChessBoard extends JPanel implements MouseListener {
	private static ChessBoard chessBoard = null;

	private static final int MARGIN = 30; // �������������ı߾�
	private static final int GRID_MARGIN = 35; // ����դ��֮��ļ��
	private static final int ROWS = 15; // ���̵�����
	private static final int COLS = 15; // ���̵�����
	private static final int SUCCESS_COUNT = 5; // ��ʤ��Ҫ������������

	private int currentX = 0; // ���µ����ӵ�����
	private int currentY = 0;

	// private Color currentChessColor = Color.black; // ��ǰ���ӵ���ɫ
	public static int chessCount = 0; // ���ӵ�����
	public static ChessPoint[] cp = new ChessPoint[(ROWS + 1) * (COLS + 1)]; // ���̶���

	// ��ȡ����
	public static ChessBoard getInstance() {
		if (chessBoard == null)
			chessBoard = new ChessBoard();
		return chessBoard;
	}

	public void clearCheessBoard() {
		ChessPoint[] cp = new ChessPoint[(ROWS + 1) * (COLS + 1)];
	}

	// ���캯���󶨼�����
	public ChessBoard() {
		// this.setBackground(Color.gray);

		addMouseListener(this);
		addMouseMotionListener(new MouseMotionListener() {
			// ��ȡ�ƶ��������꣬����淶��
			public void mouseMoved(MouseEvent e) {
				if (GameData.isConnected == false || GameData.hasOpponent == false || GameData.gameOver
						|| GameData.myTurn == false) {
					return;
				}
				int x = (e.getX() - MARGIN + GRID_MARGIN / 2) / GRID_MARGIN; // ����ת���Ĵ����ڽӵ㴦��
				int y = (e.getY() - MARGIN + GRID_MARGIN / 2) / GRID_MARGIN;
				if (x < 0 || x > COLS || y < 0 || y > ROWS || GameData.gameOver || getChessPoint(x, y) != null)
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				else
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				// System.out.println("�������ǣ�"+x+",��������:"+y);
			}

			public void mouseDragged(MouseEvent arg0) {

			}
		});
	}

	// ���ƺ�����ÿ�ε��õ�ʱ���ػ��������̣����������б��ػ�����
	// �൱��������µ�����
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // ��ʱ��֪��ΪʲôҪ����д

		Toolkit tool = this.getToolkit();
		//��ȡ����ͼƬ��·������
		Image image = tool.getImage(".\\Background\\image4.jpg");
		g.drawImage(image, 0, 0, chessBoard.getWidth(), chessBoard.getHeight(), 0, 0, image.getWidth(this),
				image.getHeight(this), this);

		g.setColor(Color.BLACK);
		// ������
		for (int i = 0; i <= COLS; i++) {
			g.drawLine(MARGIN, MARGIN + i * GRID_MARGIN, MARGIN + ROWS * GRID_MARGIN, MARGIN + i * GRID_MARGIN);
		}
		// ������
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN + i * GRID_MARGIN, MARGIN, MARGIN + i * GRID_MARGIN, MARGIN + COLS * GRID_MARGIN);
		}
		// ���������б��������
		for (int i = 0; i < chessCount; i++) {
			int x0 = cp[i].getCPX();
			int y0 = cp[i].getCPY();
			int x = MARGIN + x0 * GRID_MARGIN; // ������ʵ����
			int y = MARGIN + y0 * GRID_MARGIN;

			g.setColor(cp[i].getCPColor());
			Graphics2D g2d = (Graphics2D) g;
			// RadialGradientPaint paint = new RadialGradientPaint(65, 65, 50,
			// new float[]{0f, 1f}, new Color[]{Color.WHITE, Color.BLACK});
			// g2d.setPaint(paint);
			// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			// RenderingHints.VALUE_ANTIALIAS_ON);
			// g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
			// RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
			Ellipse2D e = new Ellipse2D.Float(x - GRID_MARGIN / 3, y - GRID_MARGIN / 3, GRID_MARGIN * 2 / 3,
					GRID_MARGIN * 2 / 3);
			g2d.fill(e);

			// g.setColor(cp[i].getCPColor());
			// g.fillArc(x - GRID_MARGIN / 2, y - GRID_MARGIN / 2, 25, 25, 0,
			// 360); // ���Ƽ򵥵�����
		}
	}

	// ���庯��
	public void mousePressed(MouseEvent e) {
		if (GameData.isConnected == false || GameData.hasOpponent == false || GameData.gameOver
				|| GameData.myTurn == false ) {
			return;
		}
		currentX = (e.getX() - MARGIN + GRID_MARGIN / 2) / GRID_MARGIN; // ����淶����������������һ����Ϊ��
		currentY = (e.getY() - MARGIN + GRID_MARGIN / 2) / GRID_MARGIN; // �ٽ�������
		// �����������߲���ͻ��ִ�������
		if (currentX < 0 || currentX > COLS || currentY < 0 || currentY > ROWS
				|| getChessPoint(currentX, currentY) != null)
			return;
		else {
			cp[chessCount++] = new ChessPoint(currentX, currentY, GameData.myColor);
			GameData.myTurn = false;
			if(isWin() != true){
			IOTool.getInstance().getWriter().println(
					"YOURTURN:" + GameData.myID + "#" + GameData.opponentID + "#" + currentX + "#" + currentY + "#");
			System.out.println(
					"YOURTURN:" + GameData.myID + "#" + GameData.opponentID + "#" + currentX + "#" + currentY + "#");
			// �����ҷ�����������λ�ã������ҷ�����
			}else{
				if (isWin()) {
					GameData.gameOver = true;
					// ����ʤ����Ϣ
					IOTool.getInstance().getWriter().println(
							"YOURTURN:" + GameData.myID + "#" + GameData.opponentID + "#" + currentX + "#" + currentY + "#");
					IOTool.getInstance().getWriter().println("WIN:" + GameData.myID + "#" + GameData.opponentID);
					MessageTool.getInstance().addMessage("���������������֣�");
					JOptionPane.showConfirmDialog(null, "��Ӯ�ˣ�", "ʤ��", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION);
					// ������ť����
					StatusPanel.getInstance().setResetStatusValid();
					PlayerPanel.getInstance().setEscapeEnabledInvalid();
					GameData.Reset();
					GameData.hasOpponent = false;
				}
			}
		}
		repaint();
		
		// currentChessColor = currentChessColor == Color.BLACK ? Color.white :
		// Color.black; // ��ɫ

	}

	// ���������б������Ƿ�������Ϊx��y������
	public ChessPoint getChessPoint(int x, int y) {
		for (int i = 0; i < chessCount; i++) {
			if (cp[i].getCPX() == x && cp[i].getCPY() == y) {
				// System.out.println("not null");
				return cp[i];
			}
		}
		return null;
	}

	// �ж�ʤ���ĺ��ĺ���
	// ��Ҫ˼�����Ե�ǰ���µ�����Ϊ���ģ��������������Լ�б�������
	public boolean isWin() {
		// System.out.println(currentX+" "+currentY+"
		// "+currentChessColor.toString());
		int continueChess = 1; // ������������������ǰ�����Ӳ���
		// ��������
		for (int pointerX = currentX - 1; pointerX >= 0; pointerX--) {
			ChessPoint p = getChessPoint(pointerX, currentY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������
				continueChess++;
			} else {
				// System.out.println("no");
				break;
			}
		}
		// ��������
		for (int pointerX = currentX + 1; pointerX <= COLS; pointerX++) {
			ChessPoint p = getChessPoint(pointerX, currentY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������������
				continueChess++;
			} else {
				// System.out.println("no");
				break;
			}

		}
		// �ж��Ƿ����������
		if (continueChess >= SUCCESS_COUNT) {
			return true;
		} else {
			continueChess = 1;
		}

		// ���������ж�������
		for (int pointerY = currentY - 1; pointerY >= 0; pointerY--) {
			ChessPoint p = getChessPoint(currentX, pointerY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������
				continueChess++;
			} else {
				// System.out.println("no");
				break;
			}
		}
		for (int pointerY = currentY + 1; pointerY <= ROWS; pointerY++) {
			ChessPoint p = getChessPoint(currentX, pointerY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������
				continueChess++;
			} else {
				// System.out.println("no");
				break;
			}
		}
		if (continueChess >= SUCCESS_COUNT) {
			return true;
		} else {
			continueChess = 1;
		}
		// �������������ж�������
		for (int pointerX = currentX - 1, pointerY = currentY - 1; pointerX >= 0
				&& pointerY >= 0; pointerX--, pointerY--) {
			ChessPoint p = getChessPoint(pointerX, pointerY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������
				continueChess++;
			} else {
				// System.out.println("no");
				break;
			}
		}
		for (int pointerX = currentX + 1, pointerY = currentY + 1; pointerX <= COLS
				&& pointerY <= ROWS; pointerX++, pointerY++) {
			ChessPoint p = getChessPoint(pointerX, pointerY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������
				continueChess++;
			} else {
				// System.out.println("no");
				break;
			}
		}
		if (continueChess >= SUCCESS_COUNT) {
			return true;
		} else {
			continueChess = 1;
		}
		// �������������ж�������
		for (int pointerX = currentX + 1, pointerY = currentY - 1; pointerX <= COLS
				&& pointerY >= 0; pointerX++, pointerY--) {
			ChessPoint p = getChessPoint(pointerX, pointerY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������
				continueChess++;
			} else {
				break;
			}
		}
		for (int pointerX = currentX - 1, pointerY = currentY + 1; pointerX >= 0
				&& pointerY <= ROWS; pointerX--, pointerY++) {
			ChessPoint p = getChessPoint(pointerX, pointerY);
			if (p != null && p.getCPColor() == GameData.myColor) { // ������������
				continueChess++;
			} else {
				break;
			}
		}
		if (continueChess >= SUCCESS_COUNT) {
			return true;
		} else {
			continueChess = 1;
			return false;
		}

	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

}
