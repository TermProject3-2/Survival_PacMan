package Server;
// Java Chatting Server

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Init.MapData;
import Manager.ObjectManager;
import Model.Ghost;

public class Server extends JFrame {
	private JPanel contentPane;
	private JTextField textField; // 사용할 PORT번호 입력
	private JButton Start; // 서버를 실행시킨 버튼
	JTextArea textArea; // 클라이언트 및 서버 메시지 출력

	private ObjectManager objectManager;
	private MapData mapData;

	private ServerSocket socket; // 서버소켓
	private Socket soc; // 연결소켓
	private int Port; // 포트번호
	private Vector<UserInfo> vc = new Vector(); // 연결된 사용자를 저장할 벡터

	public static void main(String[] args) {
		Server frame = new Server();
		frame.setVisible(true);
	}

	public Server() {
		init();
	}

	private void init() { // GUI를 구성하는 메소드
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane js = new JScrollPane();
		mapData = new MapData();
		objectManager = new ObjectManager(2, 6, mapData);
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setRows(5);
		js.setBounds(0, 0, 264, 254);
		contentPane.add(js);
		js.setViewportView(textArea);

		textField = new JTextField();
		textField.setBounds(98, 264, 154, 37);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(12, 264, 98, 37);
		contentPane.add(lblNewLabel);
		Start = new JButton("서버 실행");

		Myaction action = new Myaction();
		Start.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
		textField.addActionListener(action);
		Start.setBounds(0, 325, 264, 37);
		contentPane.add(Start);
		textArea.setEditable(false); // textArea를 사용자가 수정 못하게끔 막는다.
	}

	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == Start || e.getSource() == textField) {
				if (textField.getText().equals("") || textField.getText().length() == 0)// textField에
																						// 값이
																						// 들어있지
																						// 않을때
				{
					textField.setText("포트번호를 입력해주세요");
					textField.requestFocus(); // 포커스를 다시 textField에 넣어준다
				} else {
					try {
						Port = Integer.parseInt(textField.getText());
						server_start(); // 사용자가 제대로된 포트번호를 넣었을때 서버 실행을위헤 메소드 호출
					} catch (Exception er) {
						// 사용자가 숫자로 입력하지 않았을시에는 재입력을 요구한다
						textField.setText("숫자로 입력해주세요");
						textField.requestFocus(); // 포커스를 다시 textField에 넣어준다
					}
				} // else 문 끝
			}

		}

	}

	private void server_start() {
		try {
			socket = new ServerSocket(Port); // 서버가 포트 여는부분
			Start.setText("서버실행중");
			Start.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
			textField.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다

			if (socket != null) // socket 이 정상적으로 열렸을때
			{
				Connection();
			}

		} catch (IOException e) {
			textArea.append("소켓이 이미 사용중입니다...\n");

		}

	}

	private void Connection() {
		Thread th = new Thread(new Runnable() { // 사용자 접속을 받을 스레드
			@Override
			public void run() {
				while (true) { // 사용자 접속을 계속해서 받기 위해 while문
					try {
						textArea.append("사용자 접속 대기중...\n");
						soc = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
						textArea.append("사용자 접속!!\n");
						UserInfo user = new UserInfo(soc, vc, objectManager); // 연결된
																				// 소켓
																				// 객체
																				// 생성
						// 매개변수로 현재 연결된 소켓과, 벡터를 담아둔다

						vc.add(user); // 해당 벡터에 사용자 객체를 추가

						vc.lastElement().User_network();

						user.start(); // 만든 객체의 스레드 실행
					} catch (IOException e) {
						textArea.append("!!!! accept 에러 발생... !!!!\n");
					}
				}
			}
		});
		th.start();
	}

	class UserInfo extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;
		private Socket user_socket;
		private Vector user_vc;
		private ObjectManager objectManager;
		private String Nickname = "";
		private StringBuilder stringBuilder;

		public UserInfo(Socket soc, Vector vc, ObjectManager objectManager) // 생성자메소드
		{
			// 매개변수로 넘어온 자료 저장
			this.user_socket = soc;

			this.user_vc = vc;
			this.objectManager = objectManager;
			// User_network();
		}

		public void User_network() {
			try {

				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);
				// Nickname = dis.readUTF(); // 사용자의 닉네임 받는부분

				byte[] b = new byte[128];
				dis.read(b);
				String inputData = new String(b);
				inputData = inputData.trim();

				StringTokenizer st = new StringTokenizer(inputData, "&");

				switch (st.nextToken()) {
				case "CONNECT":
					Nickname = st.nextToken();
					textArea.append("ID " + Nickname + " 접속\n");
					textArea.setCaretPosition(textArea.getText().length());
					int count = user_vc.size(); ////
					InMessage("CONNECT" + "&" + Nickname + "&" + count);
					if (user_vc.size() == 2) {
						System.out.println("here is first");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("here is second");

						InMessage("START" + "&" + Nickname);
					}

					/*
					 * StringTokenizer st = new StringTokenizer(inputData, "&");
					 * 
					 * switch (st.nextToken()) { case "CONNECT": Nickname =
					 * st.nextToken(); textArea.append("ID " + Nickname +
					 * " 접속\n");
					 * textArea.setCaretPosition(textArea.getText().length());
					 * int count = user_vc.size(); //// //
					 * send_Message("CONNECT" + "&" + Nickname + "&" +
					 * count);//이건 생성자에서 일어나고 생성자 지난 // 후에 벡터에 넣어줘서
					 * InMessage("CONNECT" + "&" + Nickname + "&" + count);
					 * 
					 * break; }
					 */

					
					 /* textArea.append("ID " + Nickname + " 접속\n");
					 * textArea.setCaretPosition(textArea.getText().length());
					 * send_Message(Nickname + "님 환영합니다."); // 연결된 사용자에게 정상접속을
					 * 알림
					 */
				}
			} catch (Exception e) {
				textArea.append("스트림 셋팅 에러\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
		}

		public void InMessage(String str) {
			// textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");

			/*
			 * textArea.append(str + "\n");
			 * textArea.setCaretPosition(textArea.getText().length());
			 */ // 디버깅용

			process(objectManager);
			// 사용자 메세지 처리
			broad_cast(str);
		}

		public void process(ObjectManager objectManager) {
			this.objectManager = this.objectManager;

		}

		public void broad_cast(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserInfo imsi = (UserInfo) user_vc.elementAt(i);

				textArea.append(str + "\nvector size = " + user_vc.size() + "\n");
				imsi.send_Message(str);

			}
		}

		public void send_Message(String str) {
			try {
				// dos.writeUTF(str);
				byte[] bb;
				String s = String.format("%-64s",str);
				bb = s.getBytes("euc-kr");
				dos.write(bb,0,64); // .writeUTF(str);
			} catch (IOException e) {
				textArea.append("메시지 송신 에러 발생\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
		}

		public void run() // 스레드 정의
		{

			while (true) {
				try {

					// 사용자에게 받는 메세지
					byte[] b = new byte[128];
					dis.read(b, 0, 64);
					String msg = new String(b, "euc-kr"); // + "\n";
					msg = msg.trim();
					String data;

					// System.out.println("Server"+msg);
					StringTokenizer st = new StringTokenizer(msg, "&");
					String tmp = st.nextToken();
					switch (tmp) {
					case "CONNECT":
						Nickname = st.nextToken();
						textArea.append("ID " + Nickname + " 접속\n");
						textArea.setCaretPosition(textArea.getText().length());
						InMessage("CONNECT" + "&" + Nickname + "&" + user_vc.size());
						if(user_vc.size()==2){
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							InMessage("START" + "&" + Nickname + "&" +user_vc.size());
						}

						break;
					case "MESSAGE":
						Nickname = st.nextToken();
						data = st.nextToken();
						InMessage("MESSAGE" + "&" + Nickname + " : " + data);
						break;
						
					case "END":
						dos.close();
						dis.close();
						user_socket.close();
						vc.removeElement(this); // 
						break;
						
					default:
						textArea.append(msg + "\n");
						InMessage(msg);
						break;

					
					  // System.out.println("Server"+msg); StringTokenizer st =
//					  new StringTokenizer(msg, "&");
//					  String tmps = st.nextToken();
//					  switch (tmps) { case "CONNECT": Nickname = st.nextToken();
//					  textArea.append("ID " + Nickname +  " 접속\n");
//					  textArea.setCaretPosition(textArea.getText().length());
//					  InMessage("CONNECT" + "&" + Nickname + "&" +  user_vc.size()); 
//					  if (user_vc.size() == 2) { 
//						  //// sleep(5); 
//					  }
//					  
//					  break; 
//					  case "MESSAGE": Nickname = st.nextToken(); data =
//					  st.nextToken(); InMessage("MESSAGE" + "&" + Nickname +
//					  " : " + data); break;
//					  
//					  default: textArea.append(msg + "\n"); InMessage(msg);
//					  break;
					 
					}

					/*
					 * msg = msg.trim(); // String msg = dis.readUTF();
					 * InMessage(msg);
					 */

				} catch (IOException e) {

					try {
						dos.close();
						dis.close();
						user_socket.close();
						vc.removeElement(this); // 에러가난 현재 객체를 벡터에서 지운다
						textArea.append(vc.size() + " : 현재 벡터에 담겨진 사용자 수\n");
						textArea.append("사용자 접속 끊어짐 자원 반납\n");
						textArea.setCaretPosition(textArea.getText().length());

						break;

					} catch (Exception ee) {

					} // catch문 끝
				} // 바깥 catch문끝

			}

		}// run메소드 끝

	} // 내부 userinfo클래스끝

}
