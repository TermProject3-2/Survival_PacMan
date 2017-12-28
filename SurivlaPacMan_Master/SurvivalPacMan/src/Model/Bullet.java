package Model;

import Init.MapData;

public class Bullet {
	private int x, y;
	private int dirx, diry;
	private int pos, coordinate;
	private int bulletNum;
	private boolean bulletExist;
	private MapData mapData;
	private short[] currentMap;

	public Bullet(MapData mapData) {
		x = 0;
		y = 0;
		dirx = 0;
		diry = 0;
		bulletExist = false;
		bulletNum = 0;
		this.mapData = mapData;
		pos = 0;
		coordinate = 0;
		currentMap = mapData.getCurrentMap();
	}

	public void Shoot(PacMan pac) {

		if (bulletNum != 0) { // �Ѿ��� �������� ���� �Ѿ��� �������
			
			
			if (bulletExist == true && pac.getStatus() == 2 ){
				if(x % mapData.getBlocksize() != 0)
					x -= x % mapData.getBlocksize();
				if(y % mapData.getBlocksize() != 0)
					y -= y % mapData.getBlocksize();
				
				pac.setX(x);
			    pac.setY(y);
			    pac.setReqx(0);
			    pac.setReqy(0);
			    pac.setViewdx(dirx);
			    pac.setViewdy(diry);
			    bulletExist = false;
			    return;
		    }	
			
			if (bulletExist == false && pac.getStatus() != 0) {
				bulletNum--;
				bulletExist = true;
				x = pac.getX() / 24 * 24;
				y = pac.getY() / 24 * 24;
				dirx = pac.getDirx();
				diry = pac.getDiry();
			}
		}


	}

	public void move() {
		if (bulletExist) {
			if (dirx == 0 && diry == 0)
				bulletExist = false;

			if (x % mapData.getBlocksize() == 0 && y % mapData.getBlocksize() == 0) {
				int pos = x / mapData.getBlocksize() + mapData.getNrofblocks() * y / mapData.getBlocksize();
				coordinate = currentMap[pos];
			}
			if (((dirx == -1 && diry == 0 && (coordinate & 1) != 0) || (dirx == 1 && diry == 0 && (coordinate & 4) != 0)
					|| (dirx == 0 && diry == -1 && (coordinate & 2) != 0)
					|| (dirx == 0 && diry == 1 && (coordinate & 8) != 0))) {
				dirx = 0;
				diry = 0;
				bulletExist = false;
			}

			x += (dirx * 12);
			y += (diry * 12);

		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public boolean isBulletExist() {
		return bulletExist;
	}

	public void setBulletExist(boolean bulletExist) {
		this.bulletExist = bulletExist;
	}

	public int getBulletNum() {
		return bulletNum;
	}

	public void setBulletNum(int bulletNum) {
		this.bulletNum = bulletNum;
	}

}