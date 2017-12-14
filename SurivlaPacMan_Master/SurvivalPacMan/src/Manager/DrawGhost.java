package Manager;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;

import Init.CharImages;
import Model.Ghost;
import Model.PacMan;


public class DrawGhost extends JFrame{
	
	private int AnimationCount;
	private boolean scared; //���� ��ɸ�� ���� 
	private List<Ghost> ghostList;
	private CharImages charImages;
	PacMan pac;
	
	public DrawGhost(ObjectManager objectManager, CharImages charImages) {
		this.pac = objectManager.getPac(0); // ������� ���� �ڱ��ڽ��� �ε���0
		this.ghostList = objectManager.getGhoList();
		this.charImages = charImages;
		 AnimationCount = 0;
	}
	
	public void paint(Graphics g){
		for(int i = 0; i < ghostList.size(); i++) {
			
		   AnimationCount++;
		   if( AnimationCount > 10)
				  AnimationCount = 0;
		   
		   if(ghostList.get(i).getStatus() == 0)
			   continue;
			
		   if(AnimationCount % 5 == 0  &&  pac.getStatus()!= 2)
		       g.drawImage(charImages.ghost1, ghostList.get(i).getX()+12, ghostList.get(i).getY()+31, this); //4��° ���� �̹��������� this�� �ֱ� ���ؼ� JFrame�� ���
		   else if(AnimationCount % 5 != 0 &&   pac.getStatus()!= 2)
			   g.drawImage(charImages.ghost2, ghostList.get(i).getX()+12, ghostList.get(i).getY()+31, this); 
		   else if(AnimationCount % 5 == 0 &&   pac.getStatus()== 2)  // status = 2�϶� scared ���
			   g.drawImage(charImages.ghostscared1, ghostList.get(i).getX()+12, ghostList.get(i).getY()+31, this); 
		   else if(AnimationCount % 5 != 0 &&   pac.getStatus()== 2)
			   g.drawImage(charImages.ghostscared2, ghostList.get(i).getX()+12, ghostList.get(i).getY()+31, this); 
		   }	
		 
		
	}

}
