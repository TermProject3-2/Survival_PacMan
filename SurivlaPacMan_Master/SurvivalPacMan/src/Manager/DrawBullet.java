package Manager;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import Model.Bullet;
import Model.PacMan;

public class DrawBullet extends JFrame {
	private ObjectManager objectManager;

	public DrawBullet(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < objectManager.getPacList().size(); i++) {
			Bullet bullet = objectManager.getPac(i).getBullet();
			if (bullet.isBulletExist()) {
				g.setColor(Color.green);
				g.fillRect(bullet.getX() + 22, bullet.getY() + 40, 4, 4);
			}
		}
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void setObjectManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}

}
