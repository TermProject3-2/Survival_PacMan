package Progress;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Control.MyKey;
import Init.CharImages;
import Init.MapData;
import Manager.DrawManager;
import Manager.ObjectManager;
import Model.Bullet;
import Model.Ghost;
import Model.PacMan;
import View.GameMenu;

public class BattlePlay extends JPanel implements Runnable {

	MapData mapData;
	CharImages charImages;
	BufferedImage memoryimage;
	Graphics2D mgc;
	Graphics playGameGraphic;
	DrawManager drawManager;
	ObjectManager objectManager;
	Container contentPane, parentPane;

	private String id, ip;
	private int port;
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	private JButton sendBtn;
	private JTextArea textArea;
	private JTextField textField;

	private JPanel waitpanel, playpanel;
	private boolean isHost;
	private Thread battleThread;
	private Thread th;

	private String GameResult;

	public BattlePlay(String id, String ip, int port) {
		this.id = id;
		this.ip = ip;
		this.port = port;

		GameResult = "playing";

		battleThread = new Thread(this);
		parentPane = this;
		// parentPane = getParent();
		waitInit();
		playInit();
		start();
		this.add(waitpanel);
		this.setBounds(0, 0, 400, 500);
		this.setVisible(true);
		network();
	}

	public void waitInit() {
		isHost = false;
		waitpanel = new JPanel();
		waitpanel.setBounds(0, 0, 400, 500);
		// contentPane = new JPanel();
		waitpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		waitpanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 272, 302);
		waitpanel.add(scrollPane);
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		// textArea.setForeground(new Color(255,0,0));
		textArea.setDisabledTextColor(new Color(0, 0, 0));
		textField = new JTextField();
		textField.setBounds(0, 312, 155, 42);
		waitpanel.add(textField);
		textField.setColumns(10);
		sendBtn = new JButton("전   송");
		sendBtn.setBounds(161, 312, 111, 42);
		waitpanel.add(sendBtn);
		textArea.setEnabled(false); // 사용자가 수정못하게 막는다
		setVisible(true);
	}

	public void playInit() {
		playpanel = new JPanel();

		playpanel.setSize(400, 500);
		playpanel.setBackground(Color.BLACK);
		playpanel.setVisible(true);
		contentPane = getParent();
		// getParent 가 이 jpanel 이 달릴 jframe(돌)
		charImages = new CharImages();

		memoryimage = new BufferedImage(400, 500, BufferedImage.TYPE_INT_ARGB);
		mgc = memoryimage.createGraphics(); // 더블 버퍼링하기 위해(돌)

		mapData = new MapData();
		mapData.copyToCurrentMap(4); // current�ʿ� 1�� ���� ����

		objectManager = new ObjectManager(2, 0, mapData);

		objectManager.set();

		drawManager = new DrawManager(objectManager, charImages, mgc, mapData);
	}

	public void versusPlayStart() {

		battleThread.start();
		// this.start();
	}

	@Override // �߰���(��)
	public void run() {

		PacMan pac = objectManager.getPac(0);
		ArrayList<Ghost> gho = objectManager.getGhoList();
		Bullet bullet = pac.getBullet();

		while (true) {
			try {
				Thread.sleep(50);
				objectManager.checkCollision();
				objectManager.ObjectMove();
				drawManager.paint(mgc);

				String pacInfo = "PLAY&" + id + "&PacMan&" + pac.getX() + "," + pac.getY() + "," + pac.getViewdx() + ","
						+ pac.getViewdy() + "&" + pac.getStatus() + "&" + pac.getLife();
				send_Message(pacInfo);
				if (isHost) {
					for (int i = 0; i < gho.size(); i++) {
						String ghostInfo = "PLAY&" + i + "&Ghost&" + gho.get(i).getX() + "," + gho.get(i).getX() + "&"
								+ gho.get(i).getStatus();
						send_Message(ghostInfo);
					}
				}

				String bulletInfo = "PLAY&" + id + "&Bullet&" + bullet.getX() + "," + bullet.getY() + ","
						+ bullet.getDirx() + "," + bullet.getDiry() + "&" + bullet.isBulletExist();
				send_Message(bulletInfo);

				if (objectManager.isGameOverInBattle()) {

					String GameOverMsg = "GAMEOVER&" + id;
					send_Message(GameOverMsg);
					System.out.println(GameOverMsg);
				}

				playGameGraphic = getGraphics();

				if (playGameGraphic != null)
					paint(playGameGraphic);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void paintChildren(Graphics g) {

		// 여기서 추가해야 리스너 먹힘@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		addKeyListener(new MyKey(objectManager.getPac(0), objectManager.getPac(0).getBullet()));
		setFocusable(true);
		requestFocus(true);

		if (objectManager.isGameOverInBattle()) { // 자신이 졌을때
			try {
				g.setColor(Color.YELLOW);
				g.drawString("my Life :      ", 20, 450); // �ܻ� ������
				g.drawString("my Life : " + objectManager.getPac(0).getLife(), 20, 450);

				if (objectManager.getPacList().size() >= 2) {
					g.drawString("enemy Life :      ", 300, 450); // �ܻ� ������
					g.drawString("enemy Life : " + objectManager.getPac(1).getLife(), 300, 450);
				}
				Thread.sleep(2000);
				g.drawImage(charImages.Lose, 0, 0, this);
				Thread.sleep(3000);

				GameMenu panel = new GameMenu();
				contentPane = getParent();
				contentPane.add(panel);
				contentPane.remove(this);
				contentPane.repaint();
				System.out.println("END&end&end@@@@@@@@@@@@");
				send_Message("END&end&end");
				battleThread.stop();
				return;

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else if (GameResult.equals("win")) { // 자신이 이겼을때
			try {
				g.setColor(Color.YELLOW);
				g.drawString("Life :      ", 20, 450);
				g.drawString("Life : " + objectManager.getPac(0).getLife(), 20, 450);
				Thread.sleep(2000);
				g.drawImage(charImages.Win, 0, 0, this);
				Thread.sleep(3000);

				GameMenu panel = new GameMenu();
				contentPane = getParent();
				contentPane.add(panel);
				contentPane.remove(this);
				contentPane.repaint();
				
				System.out.println("END&end&end@@@@@@@@@@@@");
				send_Message("END&end&end");
				battleThread.stop();
				return;

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		} else {
			g.drawImage(memoryimage, 0, 0, this);
		}

	}

	public void network() {
		// 서버에 접속
		try {
			socket = new Socket(ip, port);

			if (socket != null) // socket이 null값이 아닐때 즉! 연결되었을때
			{
				Connection(); // 연결 메소드를 호출
			}
		} catch (UnknownHostException e) {

		} catch (IOException e) {
			System.out.println("소켓 접속 에러!!\n");
		}
	}

	public void Connection() { // 실직 적인 메소드 연결부분
		try { // 스트림 설정
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {

		}
		send_Message("CONNECT" + "&" + id); // 정상적으로 연결되면 나의 id를 전송
		th = new Thread(new Runnable() { // 스레드를 돌려서 서버로부터 메세지를 수신
			@SuppressWarnings("null")
			@Override
			public void run() {
				while (true) {
					try {
						byte[] b = new byte[128];
						dis.read(b, 0, 64);
						String msg = new String(b, "euc-kr");
						msg = msg.trim();
						StringTokenizer st = new StringTokenizer(msg, "&");
						String tmp = st.nextToken();

						// System.out.println(msg);

						switch (tmp) {
						case "CONNECT":
							textArea.append("CONNECT :" + st.nextToken() + "\n");
							textArea.setCaretPosition(textArea.getText().length());
							/*
							 * if (Integer.parseInt(st.nextToken()) == 2) {
							 * System.out.println("I will play game");
							 * 
							 * parentPane.add(playpanel); parentPane.addKeyListener(new
							 * MyKey(objectManager.getPac(0), objectManager.getPac(0).getBullet()));
							 * parentPane.remove(waitpanel); parentPane.repaint(); versusPlayStart();
							 * 
							 * }
							 */
							break;
						case "MESSAGE":
							textArea.append(msg + "\n");
							textArea.setCaretPosition(textArea.getText().length());
							break;
						case "START":

							String idCheck1 = st.nextToken();
							if (!id.equals(idCheck1)) {
								isHost = true;
								objectManager.getPac(0).setX(1 * 24);
								objectManager.getPac(0).setY(7 * 24);
								objectManager.getPac(1).setX(13 * 24);
								objectManager.getPac(1).setY(7 * 24);

							} else {
								objectManager.getPac(0).setX(13 * 24);
								objectManager.getPac(0).setY(7 * 24);
								objectManager.getPac(1).setX(1 * 24);
								objectManager.getPac(1).setY(7 * 24);
							}

							parentPane.add(playpanel);
							addKeyListener(new MyKey(objectManager.getPac(0), objectManager.getPac(0).getBullet()));
							parentPane.remove(waitpanel);
							parentPane.repaint();
							versusPlayStart();
							break;

						case "GAMEOVER": // 자신이 이겼을때
							System.out.println(" 게임 오버 메세지를 받았다@@@@@!!!!!!! ");
							String enemyId = st.nextToken();
							if (!id.equals(enemyId)) {
								GameResult = "win";
								break;
							}
						case "END":
							System.out.println("test@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							th.stop();
							
							break;

						case "PLAY":
							String idCheck = st.nextToken();
							if (!id.equals(idCheck)) {
								String object = st.nextToken();
								switch (object) {
								// !!!!!!!!!!!!!! 처음 각 팩맨의 위치는 서버가 지정해줘야한다!!!!!!!
								case "PacMan": // 받을 프로토콜 객체이름&x,y,viewX,viewy&status status&life는 숫자를
									String pacxy = st.nextToken();
									StringTokenizer pactokenizer = new StringTokenizer(pacxy, ",");
									String pacxytoken = pactokenizer.nextToken();
									objectManager.getPac(1).setX(Integer.parseInt(pacxytoken)); // 상대는 무조건 인덱스 1로 가정
									pacxytoken = pactokenizer.nextToken();
									objectManager.getPac(1).setY(Integer.parseInt(pacxytoken));
									pacxytoken = pactokenizer.nextToken();

									objectManager.getPac(1).setViewdx(Integer.parseInt(pacxytoken));
									pacxytoken = pactokenizer.nextToken();
									objectManager.getPac(1).setViewdy(Integer.parseInt(pacxytoken));

									String pacstatus = st.nextToken();
									objectManager.getPac(1).setStatus(Integer.parseInt(pacstatus));

									String pacLife = st.nextToken();
									objectManager.getPac(1).setLife(Integer.parseInt(pacLife));

									break;
								case "Ghost":

								case "Bullet": // bullet의 경우 상태에 bulletExist boolean타입
									String bulletxy = st.nextToken();
									StringTokenizer bullettokenizer = new StringTokenizer(bulletxy, ",");
									String bulletxytoken = bullettokenizer.nextToken();
									objectManager.getPac(1).getBullet().setX(Integer.parseInt(bulletxytoken));
									bulletxytoken = bullettokenizer.nextToken();
									objectManager.getPac(1).getBullet().setY(Integer.parseInt(bulletxytoken));
									bulletxytoken = bullettokenizer.nextToken();
									objectManager.getPac(1).getBullet().setDirx(Integer.parseInt(bulletxytoken));
									bulletxytoken = bullettokenizer.nextToken();
									objectManager.getPac(1).getBullet().setDiry(Integer.parseInt(bulletxytoken));

									String bulletstatus = st.nextToken();

									if (bulletstatus.equals("true"))
										objectManager.getPac(1).getBullet().setBulletExist(true);
									else
										objectManager.getPac(1).getBullet().setBulletExist(false);
									break;
								}
							}

						}

					} catch (IOException e) {
						System.out.println("메세지 수신 에러!!\n");
						// 서버와 소켓 통신에 문제가 생겼을 경우 소켓을 닫는다
						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							break; // 에러 발생하면 while문 종료
						} catch (IOException e1) {

						}
					}

				} // while문 끝
			}// run메소드 끝
		});
		th.start();
	}

	public void send_Message(String str) { // 서버로 메세지를 보내는 메소드
		try {

			byte[] bb;
			String s = String.format("%-64s", str);
			bb = s.getBytes("euc-kr"); // ksc5601
			dos.write(bb, 0, 64); // .writeUTF(str);
		} catch (IOException e) {
			System.out.println("메세지 송신 에러!!\n");
		}
	}

	public void start() { // 액션이벤트 지정 메소드
		Myaction action = new Myaction();
		sendBtn.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
		textField.addActionListener(action);
	}

	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == sendBtn || e.getSource() == textField) {
				String msg = null;
				/**/ msg = String.format("MESSAGE" + "&" + "[%s]" + "&" + "%s\n", id, textField.getText());
				send_Message(msg);
				textField.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				textField.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
			}
		}
	}

}