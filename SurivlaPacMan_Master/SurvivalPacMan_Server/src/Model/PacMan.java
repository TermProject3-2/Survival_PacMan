package Model;

import Init.MapData;

public class PacMan extends ObjectType{
    /*
     * ObjectType�� ����
       int TypeNum;
        int x, y;
        int startX, startY;
        int status;
    	int viewdx, viewdy;
    	MapData mapData; // 11 19 ����ö �߰� 
     */
    int dirx, diry; // ������ �̵��� ����
    int reqx, reqy; // Ű�� ���� �Էµ� ����
	int viewdx, viewdy; // �Ѹ��� �ٷκ��� ����
	int status;
	int pos; // 1���� ��ǥ���� ��ġ
	int life;
	short[] currentMap;
	
	public PacMan (MapData mapData) {
		TypeNum = 1;
		status = 1; // 1 ���� 0 ����
		startX = 7 * mapData.getBlocksize();
		startY = 11 * mapData.getBlocksize();
		x = startX;
		y = startY;
		reqx = 0;
		reqy = 0;
		dirx = 0;
		diry = 0;
		this.mapData = mapData;
		currentMap = mapData.getCurrentMap();
		pos = (x / mapData.getBlocksize()) + (mapData.getNrofblocks()*(y / mapData.getBlocksize()));
		life = 3;
		
	}
	
	public void setPacMan(PacMan ParameterPacMan){
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
	public void move() { // move�� ObjectManager���� �����Ѵ�.
		
		short coordinate;
		
		if(status == 1){ // �Ѹ��� ����������
		
		if(reqx == -dirx && reqy == -diry) { // �Է°� ������ ������ �ݴ��� ��� ������ ���� ����
			setDirx(reqx);
			setDiry(reqy);
		}
			
		if( (x % mapData.getBlocksize() == 0) &&  ( y % mapData.getBlocksize() == 0) ) // �� ���������� ��ġ�� ������������ �̵��� ������ ����
		{
			pos = (x / mapData.getBlocksize()) + (mapData.getNrofblocks()*(y / mapData.getBlocksize())); // 1�������� ��ǥ��
			
			coordinate = currentMap[pos]; // �ش���ǥ�� �� ����, �������̳� Feed ������ ���� ����
			
			if(reqx != 0 || reqy != 0){      
				if(!( (reqx == -1 && reqy == 0 && (coordinate&1) != 0) 
				    || (reqx == 1 && reqy == 0 && (coordinate&4) != 0) 
					|| (reqx == 0 && reqy ==-1 && (coordinate&2) != 0)
					|| (reqx == 0 && reqy == 1 && (coordinate&8) != 0)  ) )
				{
					System.out.println("x y = " + x + " / " + y);
					// �̵��� ������ �Է��� �������� ����, �ٶ󺸴¹��⵵ ����
				    setDirx(reqx);
				    setDiry(reqy);
					setViewdx(dirx);
					setViewdy(diry);			
				
			    }		
			}
			// �̵��� ������ ����������� �̵����� �ʴ°����� ����
		    if(( (dirx == -1 && diry == 0 && (coordinate&1) != 0) 
			   || (dirx == 1 && diry == 0 && (coordinate&4) != 0) 
		       || (dirx == 0 && diry ==-1 && (coordinate&2) != 0)
			   || (dirx == 0 && diry == 1 && (coordinate&8) != 0)  ) )
		   	{
			setDirx(0);
			setDiry(0);
	    	}
		}
		
		setX(x + dirx * 6); // ������ �̵�
		setY(y + diry * 6); // ������ �̵�
		}
		
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




     
	
}
