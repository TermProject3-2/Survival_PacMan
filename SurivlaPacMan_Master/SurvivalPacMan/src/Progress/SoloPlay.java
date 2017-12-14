package Progress;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Control.MyKey;
import Init.CharImages;
import Init.MapData;
import Manager.DrawManager;
import Manager.ObjectManager;
import View.GameMenu;

public class SoloPlay extends JPanel implements Runnable {

	MapData mapData;
	CharImages charImages;
	BufferedImage memoryimage;
	Graphics2D mgc;
	Graphics playGameGraphic;
	DrawManager drawManager;
	ObjectManager objectManager;
	Container contentPane;
	int stage;
	MyKey myKey;
	boolean GameClear;
	

	public SoloPlay() { // �г��� �ȸ���� ���� ������ ��ü�� ���
		stage = 1;
		GameClear = false;
		this.setSize(400, 500);
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		contentPane = getParent();
		// �̹��� ���� �ε�
		charImages = new CharImages();

		// ���߹��۸��� ���� ���� �̹��� ����
		memoryimage = new BufferedImage(400, 500, BufferedImage.TYPE_INT_ARGB);
		// ���� �̹����� ���� �׷��� get
		mgc = memoryimage.createGraphics();

		mapData = new MapData();
		mapData.copyToCurrentMap(stage); // current�ʿ� 1�� ���� ����

		objectManager = new ObjectManager(1, 6, mapData); // �ӽ÷� �ϵ��ڵ� , 11 19
															// ����ö mapData��
															// �Ķ���ͷ� �ް� ����
		objectManager.set();

		myKey = new MyKey(objectManager.getPac(0),objectManager.getPac(0).getBullet());
		addKeyListener(myKey); // �ӽ÷� 0���� �ϵ��ڵ�

		drawManager = new DrawManager(objectManager, charImages, mgc, mapData);

	}
	
	
	public void setNextStage(int stage) {
		this.stage = stage;
		
		try {
			Thread.sleep(300);
			mapData.copyToCurrentMap(stage);
			objectManager = new ObjectManager(1, 8, mapData);
			objectManager.set();
			removeKeyListener(myKey);
			myKey = new MyKey(objectManager.getPac(0),objectManager.getPac(0).getBullet());
			addKeyListener(myKey); 
			drawManager = new DrawManager(objectManager, charImages, mgc, mapData);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
	}
	
	public boolean finish() { // All feed, item not exist return true
		int i = 0;
		short[] curMap = mapData.getCurrentMap();
		 while (i < mapData.getNrofblocks() * mapData.getNrofblocks())
		    {
		      if ((curMap[i]&112)!=0) // ��Ű�� ������ (&64 +  &32 + &16)�� �ϳ��� ���������� finished = false
                  return false;
		      i++;
		    }	
		 return true;
	}
	
	
	public void checkStageClear() {
		 if (finish()) {
		    	stage++;
		    	
		    	if(stage == 4) {// GameClear
		    		GameClear = true;
		    		playGameGraphic = getGraphics();
					paint(playGameGraphic);
		    	}
		    	else // if current staget clear, get next stage
		    	setNextStage(stage);
		    }
	}

	@Override // �߰���(��)
	public void run() {
		
		while (true) {
			try {
				Thread.sleep(50);
				objectManager.checkCollision();
				objectManager.ObjectMove();
				
				playGameGraphic = getGraphics();
				paint(playGameGraphic);
				checkStageClear();
				drawManager.paint(mgc); // 11 19 ����ö ���� paint�� �ϱ� ������
				// drawManager�� ������� �����ʿ䰡 ����
// ������� ���� ��� �������� �ſ� ������
				if (GameClear || objectManager.isGameOver())
					break;
				
			   
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	// 11/19 ����ö �����̹����� �׸��� ��ǥ 0,0���� ����
	public void paintComponent(Graphics g) {
		
		if(GameClear) {
			try {
				g.drawImage(charImages.Clear, 0, 0, this);
				Thread.sleep(3000);
				GameMenu panel = new GameMenu();
				contentPane = getParent();
				contentPane.add(panel);
				contentPane.remove(this);
				contentPane.repaint();
				return;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

		if (objectManager.isGameOver()) { // GameOver�� ��� GameOver ȭ�� ���
			try {
				g.setColor(Color.YELLOW);
				g.drawString("Life :      ", 20, 450); // �ܻ� ������
				g.drawString("Life : " + objectManager.getPac(0).getLife(), 20, 450);
				Thread.sleep(3000);
				g.drawImage(charImages.Over, 0, 0, this);

				Thread.sleep(1000);

				GameMenu panel = new GameMenu();
				contentPane = getParent();
				contentPane.add(panel);
				contentPane.remove(this);
				contentPane.repaint();
				return;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			g.drawImage(memoryimage, 0, 0, this); // ����ȭ�鿡 �����̹����� �׸���.
		}

	}

}
