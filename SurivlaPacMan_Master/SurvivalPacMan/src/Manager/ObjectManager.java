package Manager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

import Init.MapData;
import Model.Bullet;
import Model.Ghost;
import Model.PacMan;

public class ObjectManager {

	private ArrayList<PacMan> pacList;
	private ArrayList<Ghost> ghoList;
	private int pacNum;
	private int ghoNum;
	private MapData mapData;
	private GhostManager ghostManager;

	public ObjectManager(int pacNum, int ghoNum, MapData mapData) {
		this.pacNum = pacNum;
		this.ghoNum = ghoNum;
		this.mapData = mapData;
		pacList = new ArrayList<PacMan>();
		ghoList = new ArrayList<Ghost>();

	}

	public void set() {
		for (int i = 0; i < ghoNum; i++)
			ghoList.add(new Ghost(mapData));
		for (int i = 0; i < pacNum; i++)
			pacList.add(new PacMan(mapData));

		ghostManager = new GhostManager(ghoList, mapData);
	}

	public void ObjectMove() {
		for (int i = 0; i < pacNum; i++) {
			pacList.get(i).move();
			pacList.get(i).getBullet().move();
		}
		ghostManager.move();

	}

	public void ObjectPacMove() {
		for (int i = 0; i < pacNum; i++) {
			pacList.get(i).move();
			pacList.get(i).getBullet().move();
		}
	}

	public void checkCollision() {

		PacMan myPac = pacList.get(0);
		
		for (int j = 0; j < pacList.size(); j++) {

			PacMan pac = pacList.get(j);

			if (j == 1 && pac.getBullet().isBulletExist()) {

				

				// 여기 수정 일반상태일때만 총알에 맞을경우 처리시 == 1로 !
				if (myPac.getStatus() == 1 && Math.abs(pac.getBullet().getX() - myPac.getX()) < 12
						&& Math.abs(pac.getBullet().getY() - myPac.getY()) < 12) {

					pac.getBullet().setBulletExist(false);
					pac.getBullet().setDirx(0);
					pac.getBullet().setDiry(0);
					myPac.setLife(myPac.getLife() - 1);
					myPac.setStatus(0);

					if (myPac.getLife() > 0) {
						Timer scheduler = new Timer();
						PacResure pacResure = new PacResure(this, 0); // 살릴 Pac은 자신이기 때문에 인덱스 = 0
						scheduler.schedule(pacResure, 3000);
					}

				}
				
			}
			
			if(j == 1 && (myPac.getStatus() == 1 && pac.getStatus() == 2)) {
				if (Math.abs(pac.getX() - myPac.getX()) < 12
						&& Math.abs(pac.getY() - myPac.getY()) < 12) {
					
					myPac.setLife(myPac.getLife() - 1);
					myPac.setStatus(0);
					if (myPac.getLife() > 0) {
						Timer scheduler = new Timer();
						PacResure pacResure = new PacResure(this, 0); // 살릴 Pac은 자신이기 때문에 인덱스 = 0
						scheduler.schedule(pacResure, 3000);
					}
				}
			}

			for (int i = 0; i < ghoList.size(); i++) {

				Ghost ghost = ghoList.get(i);

				if (ghost.getStatus() == 0) // 占쏙옙占쏙옙 占쏙옙트占쏙옙 Continue
					continue;

				// Check bullet with wall -> Bullet.move()
				// Check bullet collision with ghost

				if (Math.abs(pac.getX() - ghost.getX()) < 12 && Math.abs(pac.getY() - ghost.getY()) < 12) {

					if (pac.getStatus() == 1) { //
						System.out.println(" Death ");
						pac.setLife(pac.getLife() - 1);
						pac.setStatus(0);
						if (pac.getLife() > 0) { //
							Timer scheduler = new Timer();
							PacResure pacResure = new PacResure(this, j);
							scheduler.schedule(pacResure, 3000);
						}

					}
				}
			}
		}
	}

	public boolean isGameOver() {
		for (int i = 0; i < pacList.size(); i++) {
			if (pacList.get(i).getLife() == 0)
				return true;
		}
		return false;
	}

	public boolean isGameOverInBattle() {
		if (pacList.get(0).getLife() == 0)
			return true;
		else
			return false;
	}

	public void resurePac(int i) {
		pacList.get(i).setStatus(1);
	}

	public MapData getMapData() {
		return mapData;
	}

	public void setMapData(MapData mapData) {
		this.mapData = mapData;
	}

	public PacMan getPac(int i) {
		return pacList.get(i);
	}

	public Ghost getGho(int i) {
		return ghoList.get(i);
	}

	public ArrayList<PacMan> getPacList() {
		return pacList;
	}

	public ArrayList<Ghost> getGhoList() {
		return ghoList;
	}
}