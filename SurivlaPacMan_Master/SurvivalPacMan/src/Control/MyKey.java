package Control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Model.Bullet;
import Model.PacMan;

public class MyKey extends KeyAdapter { ///////////////// �۵� �ȉ�
	private PacMan pac;
	private Bullet bullet;

	public MyKey(PacMan pac, Bullet bullet) {
		this.pac = pac;
		this.bullet = bullet;
	}



	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 32) {
			if (bullet.getBulletNum() > 0 && pac.getStatus() != 0)
				bullet.Shoot(pac);
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			pac.setReqx(-1);
			pac.setReqy(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			pac.setReqx(1);
			pac.setReqy(0);
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			pac.setReqx(0);
			pac.setReqy(-1);
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			pac.setReqx(0);
			pac.setReqy(1);
		}

	}
}
