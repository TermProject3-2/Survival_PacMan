package Manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Init.CharImages;
import Init.MapData;
import Model.Ghost;
import Model.PacMan;

public class DrawManager {
	// 11 19 ����ö ���� �����带 ���� ������ ���߹��۸��� �ϸ� �������� �ſ� ����

	CharImages charImages;
	Graphics mgc;
	DrawPacMan drawPacMan;
	DrawMaze drawMaze;
	DrawGhost drawGhost;
	DrawBullet drawBullet;
	ObjectManager objectManager;
	MapData mapData;

	public DrawManager(ObjectManager objectManager, CharImages charImages, Graphics mgc, MapData mapData) {

		this.objectManager = objectManager;
		this.charImages = charImages; // ĳ���� �̹����� ������ Ŭ����
		this.mgc = mgc; // �����̹����� �׷���
		this.mapData = mapData;

		drawMaze = new DrawMaze(mapData, charImages);
		drawPacMan = new DrawPacMan(objectManager, charImages);
		drawGhost = new DrawGhost(objectManager, charImages); // �Ѹ� ��ü�� �ʿ��ϱ� ������ ObjectManager ä�� ����
		drawBullet = new DrawBullet(objectManager);
	}

	public void paint(Graphics g) { // run���� ȣ�����

		drawMaze.paint(g);
		drawPacMan.paint(g);
		drawGhost.paint(g);
		drawBullet.paint(g);

		// �� Font�� �׸���� �ַθ�� ȭ�� �ε��� �������� ��..
		// g.setFont(new Font("Helvetica", Font.BOLD, 14));
		g.setColor(Color.YELLOW);
		g.drawString("my Life :      ", 20, 450); // �ܻ� ������
		g.drawString("my Life : " + objectManager.getPac(0).getLife(), 20, 450);

		if (objectManager.getPacList().size() >= 2) {
			g.drawString("enemy Life :      ", 300, 450); // �ܻ� ������
		    g.drawString("enemy Life : " + objectManager.getPac(1).getLife(), 300, 450);
		}

	}

	public CharImages getCharImages() {
		return charImages;
	}

	public void setCharImages(CharImages charImages) {
		this.charImages = charImages;
	}

	public Graphics getMgc() {
		return mgc;
	}

	public void setMgc(Graphics mgc) {
		this.mgc = mgc;
	}

	public DrawPacMan getDrawPacMan() {
		return drawPacMan;
	}

	public void setDrawPacMan(DrawPacMan drawPacMan) {
		this.drawPacMan = drawPacMan;
	}

	public DrawMaze getDrawMaze() {
		return drawMaze;
	}

	public void setDrawMaze(DrawMaze drawMaze) {
		this.drawMaze = drawMaze;
	}

	public DrawGhost getDrawGhost() {
		return drawGhost;
	}

	public void setDrawGhost(DrawGhost drawGhost) {
		this.drawGhost = drawGhost;
	}

	public DrawBullet getDrawBullet() {
		return drawBullet;
	}

	public void setDrawBullet(DrawBullet drawBullet) {
		this.drawBullet = drawBullet;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void setObjectManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}

	public MapData getMapData() {
		return mapData;
	}

	public void setMapData(MapData mapData) {
		this.mapData = mapData;
	}

}
