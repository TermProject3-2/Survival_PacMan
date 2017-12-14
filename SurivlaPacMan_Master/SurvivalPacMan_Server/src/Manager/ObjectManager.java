package Manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;

import Init.MapData;
import Model.Ghost;
import Model.PacMan;

public class ObjectManager implements Serializable{

		private ArrayList<PacMan> pacList;
		private ArrayList<Ghost> ghoList;
		private int pacNum;
		private int ghoNum;
		private MapData mapData;
		private GhostManager ghostManager;
		
		public ObjectManager(int pacNum, int ghoNum, MapData mapData){
			this.pacNum = pacNum;
			this.ghoNum = ghoNum;
			this.mapData = mapData;
			pacList = new ArrayList<PacMan>();
			ghoList = new ArrayList<Ghost>();
			
		}
		
		

		public void set(){
			for(int i = 0; i < ghoNum; i++)
				ghoList.add(new Ghost(mapData));
			for(int i = 0; i < pacNum; i++)
				pacList.add(new PacMan(mapData));
			
			ghostManager = new GhostManager(ghoList, mapData);
		}
		
		public void ObjectMove() {  // 11 19 ����ö �߰� Ojbetct �̵��� ����ϴ� �޼ҵ� 
			for(int i = 0; i < pacNum; i++) 
				pacList.get(i).move(); // PacMan Ŭ������ move ����
		/*	for(int i = 0; i < ghoNum; i++)
				ghoList.get(i).move();
			*/
			ghostManager.move();
		}
	
		
		public void checkCollision() {
			
			for(int j = 0; j < pacList.size(); j++) {
				
				PacMan pac = pacList.get(j);
				
			   for(int i = 0; i < ghoList.size(); i++) {
				   
				   Ghost ghost = ghoList.get(i);
				   
		    	   if(ghost.getStatus() == 0) // ���� ��Ʈ�� Continue
		    		   continue;
		    	   
			       if(  Math.abs(pac.getX() - ghost.getX()) < 12 && Math.abs(pac.getY() - ghost.getY()) <12 )
			       {  // �Ѹǰ� ��Ʈ�� ��ǥ�� ��ĥ��� �̺�Ʈ�߻�
			    	  if(pac.getStatus() == 2) { // scared ����϶�
			    	   //d.score += 10;
			    	   ghost.setStatus(0);
			          // ��Ʈ �����ʿ�
			    	  }
			    	  else if(pac.getStatus() == 1) { // ����� ������ �Ѹ��� ��Ʈ�� �������
			    		  System.out.println(" Death ");
			    		  pac.setLife(pac.getLife() - 1);
			    		  pac.setStatus(0);
			    		  if(pac.getLife() > 0) { // ����� 1�� �̻� ������� 4���� ��Ȱ�Ѵ�.
			    			  Timer scheduler = new Timer();
			    			  PacResure pacResure = new PacResure(this, j);
			    			  scheduler.schedule(pacResure, 4000);
			    		  }
			    			
			    	  }
			       }   
		       }
			}
		}
		
		public boolean isGameOver() {
			for(int i = 0; i < pacList.size(); i++) {
				if(pacList.get(i).getLife() == 0)
					return true;
			}
			return false;
		}
		
		
		public void resurePac(int i) {
			pacList.get(i).setStatus(1);
		}
		
		
		
		
		
		public PacMan getPac(int i){
			return pacList.get(i);
		}
		public Ghost getGho(int i){
			return ghoList.get(i);
		}
		
		public ArrayList<PacMan> getPacList(){
			return pacList;
     	}
     
		public ArrayList<Ghost> getGhoList(){
			return ghoList;
		}
}


