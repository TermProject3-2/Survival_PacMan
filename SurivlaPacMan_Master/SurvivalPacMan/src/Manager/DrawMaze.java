package Manager;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import Init.CharImages;
import Init.MapData;


public class DrawMaze extends JFrame{
	int blocksize;
	int scrsize;
	int bigdotcolor;
	int dbigdotcolor;
	Color dotcolor;
	Color mazecolor;
	short []Maze;
	CharImages charImages;
	
	public DrawMaze(MapData mapData, CharImages charImages){
		this.blocksize = mapData.getBlocksize();
		this.scrsize = mapData.getScrsize();
		this.bigdotcolor = mapData.getBigdotcolor();
		this.dbigdotcolor = mapData.getDbigdotcolor();
		this.dotcolor = mapData.getDotcolor();
		this.mazecolor = mapData.getMazecolor();
		this.charImages = charImages;
		this.Maze = mapData.getCurrentMap();
	}
	
	
	public void paint(Graphics g) {
		
		int x, y;
		int i = 0;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 410, 490);
		
		
		if(g == null)
			System.out.println(" ����׷��� NULL ");
		
		 bigdotcolor=bigdotcolor+dbigdotcolor;
		 if (bigdotcolor<=64 || bigdotcolor>=192)
		      dbigdotcolor=-dbigdotcolor;
		 
		 for (y=30; y<scrsize+30; y+=blocksize)
		    {
		      for (x=11; x<scrsize+11; x+=blocksize)
		      {
			       g.setColor(mazecolor);

		        if ((Maze[i]&1)!=0) // �׵θ� ����
			{
		           g.drawLine(x,y,x,y+blocksize-1);
			}
			if ((Maze[i]&2)!=0) // �׵θ� ����
			{
		           g.drawLine(x,y,x+blocksize-1,y);
			}
			if ((Maze[i]&4)!=0) // �׵θ� ������
			{
		           g.drawLine(x+blocksize,y,x+blocksize-1,y+blocksize-1);
			}
			if ((Maze[i]&8)!=0) // �׵θ� �Ʒ���
			{
		           g.drawLine(x,y+blocksize,x+blocksize-1,y+blocksize-1);
			}
			if ((Maze[i]&16)!=0) // ��Ű ���
			{
		           g.setColor(dotcolor);
		           g.fillRect(x+11,y+11,2,2);
			}
			if ((Maze[i]&32)!=0) // item 
			{
		           g.setColor(new Color(224,224-bigdotcolor,bigdotcolor));
		           g.fillRect(x+8,y+8,8,8);
			}
			if ((Maze[i]&64)!=0) // item 
			{
		           g.drawImage(charImages.bullet, x+2, y+2, this);
			}
			
			i++; 
		      }
		    }
	}

}
