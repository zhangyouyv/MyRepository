package com.client.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * 用于管理客户端的输入输出系统
 * 
 * @author Edward
 *
 */
public class IOTool {
	private static IOTool ioTool = null;
	private PrintStream writer = null;
	private BufferedReader reader = null;

	public static IOTool getInstance() {
		if (ioTool == null) {
			ioTool = new IOTool();
		}
		return ioTool;
	}

	public PrintStream getWriter() {
		return writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setWriter(OutputStream out) {  //使用原始输入输出流进行构造
		try {
			this.writer = new PrintStream(out,true,"GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	public void setReader(InputStream in) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(in,"GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
