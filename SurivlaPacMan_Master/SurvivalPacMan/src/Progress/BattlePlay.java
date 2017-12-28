package Progress;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

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
   private JTextArea textArea, userArea;
   private JTextField textField;
   private JButton readyBtn;
   
   private JPanel waitpanel, playpanel;
   private boolean isHost;
   private Thread battleThread;
   private Thread th;
   private int playerNum = 0;
   
   private String GameResult;

   public BattlePlay(String id, String ip, int port) {
      this.id = id;
      this.ip = ip;
      this.port = port;
      GameResult = "playing";
      this.setBackground(Color.BLACK);
   }
   public void init(){
	   parentPane = getParent();
	   battleThread = new Thread(this);
	   waitInit();
	   playInit();
	   start();
	   parentPane.removeAll();
	   parentPane.add(waitpanel);
	   this.setBounds(0, 0, 400, 500);
	   this.setVisible(true);
	   network();
   }

   public void waitInit() {
      isHost = false; 
      waitpanel = new JPanel();
      waitpanel.setBackground(Color.BLACK);
      waitpanel.setBounds(0, 0, 400, 500);
      waitpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
      waitpanel.setLayout(null);
      
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(0, 0, 272, 392);
      waitpanel.add(scrollPane);
      
      textArea = new JTextArea();
      scrollPane.setViewportView(textArea);
      textArea.setDisabledTextColor(new Color(0, 0, 0));
      
      userArea = new JTextArea();
      userArea.setBounds(278,0,100,340);
      userArea.append(id+"\n");
      userArea.setDisabledTextColor(new Color(0,0,0));
      userArea.setEnabled(false);
      userArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
      waitpanel.add(userArea);
      
      textField = new JTextField();
      textField.setBounds(0, 402, 272, 42);
      waitpanel.add(textField);
      textField.setColumns(10);
      
      sendBtn = new JButton("전   송");
      sendBtn.setBounds(278, 402, 100, 42);
      waitpanel.add(sendBtn);
      
      readyBtn = new JButton("준   비");
      readyBtn.setBounds(278,352,100,42);
      waitpanel.add(readyBtn);
      
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

      objectManager = new ObjectManager(2, 6, mapData);

      objectManager.set();

      drawManager = new DrawManager(objectManager, charImages, mgc, mapData);
   }

   public void versusPlayStart() {
	   
       battleThread.start();
   }

   @Override // �߰���(��)
   public void run() {

      PacMan pac = objectManager.getPac(0);
      ArrayList<Ghost> gho = objectManager.getGhoList();
      Bullet bullet = pac.getBullet();
      playpanel.addKeyListener(new MyKey(objectManager.getPac(0), objectManager.getPac(0).getBullet()));
      playpanel.setFocusable(true);
      playpanel.requestFocus(true);
      while (true) {

         try {
            Thread.sleep(45);
            
            objectManager.checkCollision();
            if(isHost)
            	objectManager.ObjectMove();
            else
            	objectManager.ObjectPacMove();
            drawManager.paint(mgc);

            String pacInfo = "PLAY&" + id + "&PacMan&" + pac.getX() + "," + pac.getY() + "," + pac.getViewdx() + ","
					+ pac.getViewdy() + "&" + pac.getStatus() + "&" + pac.getLife();
            
            send_Message(pacInfo);
            if(isHost){
            	for(int i=0; i<gho.size(); i++){
            		String ghostInfo = "PLAY&"+id+"&Ghost&"+i+"&"+ gho.get(i).getX() + ","+gho.get(i).getY()+ "&"+gho.get(i).getStatus();
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
			
            
			playGameGraphic = parentPane.getGraphics();
           
            playGameGraphic.drawImage(memoryimage, 0, 0, parentPane);
            print(playGameGraphic);
            
          
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }
   public void print(Graphics g){
	   
	   if (objectManager.isGameOverInBattle()) { // 자신이 졌을때
			try {
				g.setColor(Color.YELLOW);
				g.drawString("               ", 20, 450);
				g.drawString("my Life : " + objectManager.getPac(0).getLife(), 20, 450);
				g.drawString("                  ", 300, 450); // �ܻ� ������
				g.drawString("enemy Life : " + objectManager.getPac(1).getLife(), 300, 450);
				g.drawString("Bullet :      ", 140, 450 );
				g.drawString("Bullet : "+ objectManager.getPac(0).getBullet().getBulletNum() , 140, 450 );
				
				Thread.sleep(2000);
				g.drawImage(charImages.Lose, 0, 0, this);
				Thread.sleep(3000);

				GameMenu panel = new GameMenu();
				parentPane.add(panel);
				parentPane.remove(playpanel);
				parentPane.repaint();
				

				send_Message("END&end&end");
				battleThread.stop();
				return;

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else if (GameResult.equals("win")) { // 자신이 이겼을때
			try {
				g.setColor(Color.YELLOW);
				g.drawString("            ", 20, 450);
				g.drawString("my Life : " + objectManager.getPac(0).getLife(), 20, 450);
				g.drawString("                  ", 300, 450); // �ܻ� ������
				g.drawString("enemy Life : " + objectManager.getPac(1).getLife(), 300, 450);
				g.drawString("Bullet :      ", 140, 450 );
				g.drawString("Bullet : "+ objectManager.getPac(0).getBullet().getBulletNum() , 140, 450 );
				
				Thread.sleep(2000);
				g.drawImage(charImages.Win, 0, 0, this);
				Thread.sleep(3000);

				GameMenu panel = new GameMenu();
				parentPane.removeAll();
				parentPane.add(panel);
				parentPane.repaint();
	
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
	   
	   
	   
   

   public void paintChildren(Graphics g) {
	   System.out.println("battle play children");
      parentPane.addKeyListener(new MyKey(objectManager.getPac(0), objectManager.getPac(0).getBullet()));
      setFocusable(true);
      requestFocus(true);


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
         parentPane = getParent();
      } catch (IOException e) {

      }
      send_Message("CONNECT" + "&" + id); // 정상적으로 연결되면 나의 id를 전송
      parentPane = getParent();
      th = new Thread(new Runnable() { // 스레드를 돌려서 서버로부터 메세지를 수신
         @SuppressWarnings("null")
         @Override
         public void run() {
        	
            while (true) {
               try {
                  byte[] b = new byte[128];
                  dis.read(b,0,64);
                  String msg = new String(b, "euc-kr");
                  msg = msg.trim();
                  StringTokenizer st = new StringTokenizer(msg, "&");
                  String tmp = st.nextToken();
         
                     switch (tmp) {
                     case "CONNECT":
                    	String name=st.nextToken();
                        textArea.append("CONNECT :" + name + "\n");
                        textArea.setCaretPosition(textArea.getText().length());
                        send_Message("HELLO&"+id);
                        break;
                     case "MESSAGE":
                        textArea.append(st.nextToken() + "\n");
                        textArea.setCaretPosition(textArea.getText().length());
                        break;
                    case "START":
                    	textArea.append("게임을 시작합니다.\n");
                    	try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String idCheck1 = st.nextToken();
						if (!id.equals(idCheck1)) {
							isHost = true;
							objectManager.getPac(0).setX(1*24);
							objectManager.getPac(0).setY(7*24);
							objectManager.getPac(1).setX(13*24);
							objectManager.getPac(1).setY(7*24);
						}
						else{
							objectManager.getPac(0).setX(13*24);
							objectManager.getPac(0).setY(7*24);
							objectManager.getPac(1).setX(1*24);
							objectManager.getPac(1).setY(7*24);
						}
						parentPane = waitpanel.getParent();
						
						
						parentPane.removeAll();
						parentPane.add(playpanel);
						parentPane.repaint();
						addKeyListener(
								new MyKey(objectManager.getPac(0), objectManager.getPac(0).getBullet()));
						versusPlayStart();
						break;
                    case "READY":
                    	String idCheck2 = st.nextToken();
                    	String temp = st.nextToken();
                    	if(temp.equals("ready"))
                    		textArea.append(idCheck2+" 님이 " + temp+" 했습니다.\n");
                    	else if(temp.equals("unready"))
                    		textArea.append(idCheck2+" 님이 ready를 해제 했습니다.\n");
                    	playerNum= Integer.parseInt(st.nextToken());
                    	break;
                    case "HELLO":
                    	String exname = st.nextToken();
                    	if(!id.equals(exname))
                    		userArea.append(exname+"\n");
                    	break;
                    case "GAMEOVER": // 자신이 이겼을때
						String enemyId = st.nextToken();
						if (!id.equals(enemyId)) {
							GameResult = "win";
						}
						break;
					case "END":
						os.close();
						is.close();
						dos.close();
						dis.close();
						socket.close();
						th.stop();
						
						break;
						
					case "REGEN":
						short[] currentMap = mapData.getCurrentMap();
                        currentMap[7] = (short) (currentMap[7] | 64);
                        currentMap[29] = (short) (currentMap[29] | 69);
                        currentMap[195] = (short) (currentMap[195] | 69);
                        currentMap[217] = (short) (currentMap[217] | 64);
                        currentMap[15] = (short) (currentMap[15] | 32);
                        currentMap[209]= (short) (currentMap[209] | 32);
                        break;
                        
                    case "PLAY":
                        String idCheck = st.nextToken();
                     
                        if (! id.equals(idCheck)) {
                           String object = st.nextToken();
                           
                           switch (object) {
                           case "PacMan": // 받을 프로토콜 객체이름&x,y&status status는 숫자를
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
								else {
									objectManager.getPac(1).getBullet().setBulletExist(false);
									objectManager.getPac(1).getBullet().setDirx(0);
									objectManager.getPac(1).getBullet().setDiry(0);
								}
								break;
                           case "Ghost":
                        	   if(!isHost){
	                        	   int num = Integer.parseInt(st.nextToken());
	                          	   String ghoxy = st.nextToken();
	                          	   StringTokenizer ghotokenizer = new StringTokenizer(ghoxy, ",");
	                          	   String ghox = ghotokenizer.nextToken();
	                          	   String ghoy = ghotokenizer.nextToken();
	                          	   String ghostatus = st.nextToken();
	                          	   objectManager.getGho(num).setX(Integer.parseInt(ghox)); 
	                          	   objectManager.getGho(num).setY(Integer.parseInt(ghoy));
	                          	   objectManager.getGho(num).setStatus(Integer.parseInt(ghostatus));
                        	   }
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
            dos.write(bb,0,64); //.writeUTF(str);
      } catch (IOException e) {
         System.out.println("메세지 송신 에러!!\n");
      }
   }

   public void start() { // 액션이벤트 지정 메소드
      Myaction action = new Myaction();
      sendBtn.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
      textField.addActionListener(action);
      readyBtn.addActionListener(action);
   }

   class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
   {
      @Override
      public void actionPerformed(ActionEvent e) {
         // 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
         if ( (e.getSource() == sendBtn || e.getSource() == textField) && !textField.getText().equals("") ) {
        	 
            String msg = null;
            msg = String.format("MESSAGE" + "&" + "[%s]" + "&" + "%s\n", id, textField.getText());
            send_Message(msg);
            textField.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
            textField.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
         }
         else if(e.getSource() == readyBtn){
        	 String readyMsg = null;
        	 if(readyBtn.getText().equals("준   비")){
        		 readyMsg = String.format("READY&"+id+"&ready&"+ playerNum);
        		 send_Message(readyMsg);
        		 readyBtn.setText("준비 취소");
        	 }
        	 else if(readyBtn.getText().equals("준비 취소")){
        		 readyMsg = String.format("READY&"+id+"&unready&" + playerNum);
        		 send_Message(readyMsg);
        		 readyBtn.setText("준   비");
        	 }
         }
      }
   }
   public void paintComponent(Graphics g){
	   System.out.println("여기가 불리기는 불리니2");
	   super.paintComponent(g);
   }


}