package view;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import socket.ClientListener;
import socket.GameListener;

public class Mainframe extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1548737955508193090L;

	private JLabel lblIP, lblPort, lblConsole;
	
	private JScrollPane scpConsole;
	private JTextArea txaConsole;
	
	
	public Mainframe(int port){
		super();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("GUIApp");
        this.setLocation(300, 200);
        this.setSize(800, 400);
        
        
        lblIP = new JLabel("IP-address: N/A");
        lblPort = new JLabel("Port: " + port);
        lblConsole = new JLabel("Console:");
        
        lblIP.setBounds(20, 20, 300, 30);
        lblPort.setBounds(20, 60, 300, 30);
        lblConsole.setBounds(20, 100, 150, 30);
        
        txaConsole = new JTextArea();
        scpConsole = new JScrollPane(txaConsole);
        scpConsole.setBounds(20, 140, 700, 150);
        
        this.add(lblIP);
        this.add(lblPort);
        this.add(lblConsole);
        this.add(scpConsole);
        
        this.setVisible(true);
	}

	public JLabel getLblIP() {
		return lblIP;
	}

	public void setLblIP(JLabel lblIP) {
		this.lblIP = lblIP;
	}

	public JLabel getLblPort() {
		return lblPort;
	}

	public void setLblPort(JLabel lblPort) {
		this.lblPort = lblPort;
	}

	public JTextArea getTxaConsole() {
		return txaConsole;
	}

	public void setTxaConsole(JTextArea txaConsole) {
		this.txaConsole = txaConsole;
	}
	
	public void showLocalIpInLabel(){
String localAddress = "";
        
        try {
        	 localAddress = InetAddress.getLocalHost().getHostAddress();
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        lblIP.setText("IP-address: " + localAddress);
	}
	
	
}
