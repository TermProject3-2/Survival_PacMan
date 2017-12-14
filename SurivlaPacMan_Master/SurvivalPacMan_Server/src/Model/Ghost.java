package Model;

import Init.MapData;

public class Ghost extends ObjectType{
	
	int reqx, reqy;
    int dirx, diry;
	int viewdx, viewdy;
	//int pos;
	
	short[] currentMap;
	
	public Ghost (MapData mapData) {
		TypeNum = 1;
		startX = 7; // ������ġ �ʱ�ȭ�� �ʿ��� ����
		startY = 7;
		status = 1;
		dirx = 0;
		diry = 0;
		reqx = 0;
		reqy = 0;
		this.mapData = mapData;
		this.x = startX * mapData.getBlocksize();
		this.y = startY * mapData.getBlocksize(); 
		currentMap = mapData.getCurrentMap();

	}
	
	
	
	
	@Override
	public void sendInformationToServer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveInformationFromServer() {
		// TODO Auto-generated method stub
		
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(reqx + " " + reqy + " " + dirx + 
				" " + diry + " " + viewdx + " " + viewdy + "\n");
		return sb.toString();
	}




	@Override
	public void move() {
		
	}

	


     
	
}
