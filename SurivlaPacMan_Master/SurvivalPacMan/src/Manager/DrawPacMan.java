package Manager;

import java.awt.Graphics;

import javax.swing.JFrame;

import Init.CharImages;
import Model.PacMan;

public class DrawPacMan extends JFrame {

	// PacMan CharImages mgc
	private PacMan pac;
	private CharImages charImages;
	int AnimationCount;
	ObjectManager objectManager;

	public DrawPacMan(ObjectManager objectManager, CharImages charImages) {
		this.objectManager = objectManager;
		// this.pac = objectManager.getPac(0); // �ӽ÷� �ϵ��ڵ� @@@@@@@@@@@@@@@@@@@@@@@
		this.charImages = charImages;
		AnimationCount = 0;

	}

	public void paint(Graphics g) {

		for (int i = 0; i < objectManager.getPacList().size(); i++) {
			this.pac = objectManager.getPacList().get(i);

			AnimationCount++;
			if (pac.getStatus() == 0)
				g.drawImage(charImages.pacDeath, pac.getX() + 13, pac.getY() + 31, this);

			else {
				if (pac.getViewdx() == -1)
					DrawPacLeft(g, i);
				else if (pac.getViewdx() == 1)
					DrawPacRight(g, i);
				else if (pac.getViewdy() == -1)
					DrawPacUp(g, i);
				else
					DrawPacDown(g, i);
			}
		}
	}

	public void DrawPacLeft(Graphics g, int index) {

		if (index == 0) {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.pacman1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.pacman2left, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.pacman3left, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.pacman4left, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		} else {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.enemy1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.enemy2left, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.enemy3left, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.enemy4left, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		}
	}

	public void DrawPacRight(Graphics g, int index) {

		if (index == 0) {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.pacman1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.pacman2right, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.pacman3right, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.pacman4right, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		} else {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.enemy1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.enemy2right, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.enemy3right, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.enemy4right, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		}
	}

	public void DrawPacUp(Graphics g, int index) {

		if (index == 0) {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.pacman1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.pacman2up, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.pacman3up, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.pacman4up, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		} else {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.enemy1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.enemy2up, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.enemy3up, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.enemy4up, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		}
	}

	public void DrawPacDown(Graphics g, int index) {

		if (index == 0) {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.pacman1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.pacman2down, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.pacman3down, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.pacman4down, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		} else {
			switch (AnimationCount % 4) {
			case 0:
				g.drawImage(charImages.enemy1, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 1:
				g.drawImage(charImages.enemy2down, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 2:
				g.drawImage(charImages.enemy3down, pac.getX() + 13, pac.getY() + 31, this);
				break;
			case 3:
				g.drawImage(charImages.enemy4down, pac.getX() + 13, pac.getY() + 31, this);
				break;
			}
		}
	}

}
