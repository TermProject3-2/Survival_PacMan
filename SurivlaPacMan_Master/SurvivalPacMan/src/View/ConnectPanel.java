package View;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.java.swing.plaf.motif.MotifGraphicsUtils;

import Progress.BattlePlay;

public class ConnectPanel extends JPanel {

	private JPanel contentPane;
	private Container parentPane;
	private JTextField tf_ID; // ID를 입력받을곳
	private JTextField tf_IP; // IP를 입력받을곳
	private JTextField tf_PORT; //PORT를 입력받을곳
	private Image img;
	public ConnectPanel() // 생성자
	{
		
		contentPane = this;
		init();
		start();
	}

	public void init() // 화면 구성
	{
		
		
		
		setBounds(0, 0, 400, 500);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(68, 57, 90, 34);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setFont(new Font("Serif",Font.BOLD,13));
		
		contentPane.add(lblNewLabel);

		tf_ID = new JTextField();
		tf_ID.setBounds(107, 62, 170, 24);
		contentPane.add(tf_ID);
		tf_ID.setColumns(10);

		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setBounds(27, 111, 90, 52);
		lblServerIp.setOpaque(true);
		lblServerIp.setForeground(Color.WHITE);
		lblServerIp.setBackground(Color.BLACK);
		lblServerIp.setFont(new Font("Serif",Font.BOLD,13));
		
		contentPane.add(lblServerIp);

		tf_IP = new JTextField();
		tf_IP.setColumns(10);
		tf_IP.setBounds(107, 121, 170, 24);
		contentPane.add(tf_IP);

		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(51, 178, 90, 52);
		lblPort.setOpaque(true);
		lblPort.setForeground(Color.WHITE);
		lblPort.setBackground(Color.BLACK);
		lblPort.setFont(new Font("Serif",Font.BOLD,13));
		
		contentPane.add(lblPort);

		tf_PORT = new JTextField();
		tf_PORT.setColumns(10);
		tf_PORT.setBounds(107, 193, 170, 24);
		contentPane.add(tf_PORT);
		
		
		JButton btnNewButton = new JButton("접    속");
		btnNewButton.setBounds(86, 296, 206, 52);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("Serif",Font.BOLD,13));
		btnNewButton.setBorder(null);
		contentPane.add(btnNewButton);
		
		ConnectAction action = new ConnectAction();
		btnNewButton.addActionListener(action);
		tf_PORT.addActionListener(action);	
		
		
		contentPane.setVisible(true);
		
	}
	class ConnectAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {		
			

			String _id = tf_ID.getText().trim(); // 공백이 있지 모르니 공백 제거 trim() 사용
			String _ip= tf_IP.getText().trim(); // 공백이 있을지 모르므로 공백제거
			int _port=Integer.parseInt(tf_PORT.getText().trim()); // 공백을 제거한 후 int형으로 변환 		
			
			
			parentPane = getParent(); //새로운 펜을 다는 작업(여기서는 ip, port, id 만받아서  waiting pane 으로 넘김.
			BattlePlay view = new BattlePlay(_id,_ip,_port);
			
			parentPane.remove(contentPane);
			parentPane.add(view);
			parentPane.repaint();
			view.init();
			parentPane.repaint();
					
		}
	}
	
	public void start() // 이벤트 처리
	{
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		img = new ImageIcon("pacpix/packmanback.jpg").getImage();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		
	}
}
