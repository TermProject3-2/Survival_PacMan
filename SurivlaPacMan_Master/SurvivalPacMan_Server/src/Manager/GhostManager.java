package Manager;

import java.util.ArrayList;
import java.util.List;

import Init.MapData;
import Model.Ghost;

public class GhostManager {
	
	private ArrayList<Ghost> ghoList;
	private short[] currentMap;
	private MapData mapData;
	private int pos;
	private int caseRouteX[], caseRouteY[];
	private short coordinate;

	
	
	public GhostManager(ArrayList ghoList, MapData mapData) {
		this.ghoList = ghoList;
	    this.mapData = mapData;
	    this.currentMap = mapData.getCurrentMap();
	  
	   
	    for(int i = 0; i < ghoList.size(); i++) {
	        Ghost ghost = (Ghost) ghoList.get(i);
	    	pos = (ghost.getX() / mapData.getBlocksize()) + (mapData.getNrofblocks() * ghost.getY() / mapData.getBlocksize());
	    
	    }
	    caseRouteX = new int[ghoList.size()];
	    caseRouteY = new int[ghoList.size()];
	}
	
	
	public void move() {
		int count;
		
		currentMap[7*mapData.getNrofblocks()+6] = 10;
		currentMap[7*mapData.getNrofblocks()+8] = 10;
		
		for(int i = 0; i < ghoList.size(); i++) {
			
			Ghost ghost = ghoList.get(i);
			
			if(ghost.getStatus() == 0) // 죽은 고스트는 continue
				continue;
			
			if(ghost.getX() % mapData.getBlocksize() == 0 && ghost.getY() % mapData.getBlocksize() == 0) {
				pos = (ghost.getX() / mapData.getBlocksize()) + (mapData.getNrofblocks() * ghost.getY() / mapData.getBlocksize());
			    coordinate = currentMap[pos];
			    count = 0;
			    
			    
			    if( (coordinate & 1) == 0 && ghost.getDirx() != 1) {
			  		caseRouteX[count] = -1;
			  		caseRouteY[count] = 0;
			  		count++;
			  	}
			  	if( (coordinate & 2) == 0 && ghost.getDiry() != 1) {
			  		caseRouteX[count] = 0;
			  		caseRouteY[count] = -1;
			  		count++;
			  	}
			  	if( (coordinate & 4) == 0 && ghost.getDirx() != -1) {
			  		caseRouteX[count] = 1;
			  		caseRouteY[count] = 0;
			  		count++;
			  	}
			  	if( (coordinate & 8) == 0 && ghost.getDiry() != -1) {
			  		caseRouteX[count] = 0;
			  		caseRouteY[count] = 1;
			  		count++;
			  	}
			  	
			  	if(count == 0) {
			  		if( (coordinate & 15) == 15 ){
			  			ghost.setDirx(0);
			  			ghost.setDiry(0);
			  		}
			  		else {
			  			ghost.setDirx(-ghoList.get(i).getDirx());
			  			ghost.setDiry(-ghoList.get(i).getDiry());
			  		}
			  	}
			  	else {
			  		count = (int)(Math.random()*count);
			  	    if(count > 3)  count = 3;
			  	    ghost.setDirx(caseRouteX[count]);
			  	    ghost.setDiry(caseRouteY[count]);
			  	}    
			  	
			 }
			     ghost.setX(ghoList.get(i).getX() +  ghost.getDirx());
			     ghost.setY(ghoList.get(i).getY() +  ghost.getDiry());
		}
			    
	}
				


}
