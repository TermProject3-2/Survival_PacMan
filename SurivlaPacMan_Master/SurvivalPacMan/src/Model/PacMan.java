package Model;

import java.util.Timer;

import Init.MapData;
import Manager.PacResure;
import TimeTask.PacStatusSet;

public class PacMan extends ObjectType {

	private int dirx, diry;
	private int reqx, reqy;
	private int viewdx, viewdy;
	private int status;
	private int pos;
	private int life;
	private Bullet bullet;
	private short[] currentMap;

	public PacMan(MapData mapData) {
		this.mapData = mapData;
		TypeNum = 1;
		status = 1;
		startX = 7 * mapData.getBlocksize();
		startY = 11 * mapData.getBlocksize();
		x = startX;
		y = startY;
		reqx = 0;
		reqy = 0;
		dirx = 0;
		diry = 0;
		currentMap = mapData.getCurrentMap();
		pos = (x / mapData.getBlocksize()) + (mapData.getNrofblocks() * (y / mapData.getBlocksize()));
		life = 3;
		bullet = new Bullet(mapData);

	}

	public void setPacMan(PacMan ParameterPacMan) {
		this.TypeNum = ParameterPacMan.TypeNum;
		this.x = ParameterPacMan.x;
		this.y = ParameterPacMan.y;
		this.startX = ParameterPacMan.startX;
		this.startY = ParameterPacMan.startY;
		this.status = ParameterPacMan.status;
		this.viewdx = ParameterPacMan.viewdx;
		this.viewdy = ParameterPacMan.viewdy;
		this.dirx = ParameterPacMan.dirx;
		this.diry = ParameterPacMan.diry;
		this.reqx = ParameterPacMan.reqx;
		this.reqy = ParameterPacMan.reqy;
		this.viewdx = ParameterPacMan.viewdx;
		this.viewdy = ParameterPacMan.viewdy;
		this.pos = ParameterPacMan.pos;
		this.status = ParameterPacMan.status;
		this.x = ParameterPacMan.startX;
		this.y = ParameterPacMan.startY;
		this.life = ParameterPacMan.life;

	}

	@Override
	public void move() {
		short coordinate;

		pos = (x / mapData.getBlocksize()) + (mapData.getNrofblocks() * (y / mapData.getBlocksize()));

		coordinate = currentMap[pos];

		// ItemCheck on current position
		if ((coordinate & 16) != 0) {
			currentMap[pos] = (short) (coordinate & 15);

		}
		if ((coordinate & 32) != 0) //
		{
			setStatus(2);
			System.out.println("pacMan status : " + getStatus());
			currentMap[pos] = (short) (coordinate & 15);

			PacStatusSet pacStatusSet = new PacStatusSet(this);
			Timer scheduler = new Timer();
			scheduler.schedule(pacStatusSet, 7000);
		}
		if ((coordinate & 64) != 0) //
		{
			System.out.println("Get Bullet");
			bullet.setBulletNum(bullet.getBulletNum() + 3);
			currentMap[pos] = (short) (coordinate & 15);

			// need to point increase
		}

		if (reqx == -dirx && reqy == -diry) {
			setDirx(reqx);
			setDiry(reqy);
		}

		if ((x % mapData.getBlocksize() == 0) && (y % mapData.getBlocksize() == 0)) {

			if (reqx != 0 || reqy != 0) {
				if (!((reqx == -1 && reqy == 0 && (coordinate & 1) != 0)
						|| (reqx == 1 && reqy == 0 && (coordinate & 4) != 0)
						|| (reqx == 0 && reqy == -1 && (coordinate & 2) != 0)
						|| (reqx == 0 && reqy == 1 && (coordinate & 8) != 0))) {
					//System.out.println("x y = " + x + " / " + y);

					setDirx(reqx);
					setDiry(reqy);
					setViewdx(dirx);
					setViewdy(diry);

				}
			}

			if (((dirx == -1 && diry == 0 && (coordinate & 1) != 0) || (dirx == 1 && diry == 0 && (coordinate & 4) != 0)
					|| (dirx == 0 && diry == -1 && (coordinate & 2) != 0)
					|| (dirx == 0 && diry == 1 && (coordinate & 8) != 0))) {
				setDirx(0);
				setDiry(0);
			}
		}

		setX(x + dirx * 6);
		setY(y + diry * 6);
	}

	@Override
	public void sendInformationToServer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveInformationFromServer() {
		// TODO Auto-generated method stub

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDirx() {
		return dirx;
	}

	public void setDirx(int dirx) {
		this.dirx = dirx;
	}

	public int getDiry() {
		return diry;
	}

	public void setDiry(int diry) {
		this.diry = diry;
	}

	public int getReqx() {
		return reqx;
	}

	public void setReqx(int reqx) {
		this.reqx = reqx;
	}

	public int getReqy() {
		return reqy;
	}

	public void setReqy(int reqy) {
		this.reqy = reqy;
	}

	public int getViewdx() {
		return viewdx;
	}

	public void setViewdx(int viewdx) {
		this.viewdx = viewdx;
	}

	public int getViewdy() {
		return viewdy;
	}

	public void setViewdy(int viewdy) {
		this.viewdy = viewdy;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

}