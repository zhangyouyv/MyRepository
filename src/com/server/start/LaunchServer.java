package com.server.start;

import java.awt.Font;

import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.server.ui.ServerFrame;

/**
 * �������з����
 * @author Edward
 *
 */
public class LaunchServer {
	public static void main(String[] args){
		//�����µĽ�����
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e) {
		}
		String[] DEFAULT_FONT  = new String[]{
			    "Table.font"
			    ,"TableHeader.font"
			    ,"CheckBox.font"
			    ,"Tree.font"
			    ,"Viewport.font"
			    ,"ProgressBar.font"
			    ,"RadioButtonMenuItem.font"
			    ,"ToolBar.font"
			    ,"ColorChooser.font"
			    ,"ToggleButton.font"
			    ,"Panel.font"
			    ,"TextArea.font"
			    ,"Menu.font"
			    ,"TableHeader.font"
			    // ,"TextField.font"
			    ,"OptionPane.font"
			    ,"MenuBar.font"
			    ,"Button.font"
			    ,"Label.font"
			    ,"PasswordField.font"
			    ,"ScrollPane.font"
			    ,"MenuItem.font"
			    ,"ToolTip.font"
			    ,"List.font"
			    ,"EditorPane.font"
			    ,"Table.font"
			    ,"TabbedPane.font"
			    ,"RadioButton.font"
			    ,"CheckBoxMenuItem.font"
			    ,"TextPane.font"
			    ,"PopupMenu.font"
			    ,"TitledBorder.font"
			    ,"ComboBox.font"
			};
			// ����Ĭ������
			for (int i = 0; i < DEFAULT_FONT.length; i++)
			    UIManager.put(DEFAULT_FONT[i],new Font("΢���ź�", Font.PLAIN,14));
		ServerFrame.getInstance();
	}
}
