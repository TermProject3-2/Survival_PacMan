package Init;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CharImages extends JFrame {
	public Image		ghost1,ghost2,ghostscared1,ghostscared2;
	public Image		pacDeath;
	public Image pacman1;
	public Image pacman2up;
	public Image pacman2left;
	public Image pacman2right;
	public Image pacman2down;
	public Image		pacman3up, pacman3down, pacman3left, pacman3right;
	public Image		pacman4up, pacman4down, pacman4left, pacman4right;
	public Image enemy1;
	public Image enemy2up;
	public Image enemy2left;
	public Image enemy2right;
	public Image enemy2down;
	public Image		enemy3up, enemy3down, enemy3left, enemy3right;
	public Image		enemy4up, enemy4down, enemy4left, enemy4right;
	public Image     Start,Clear,Over,Next, Win, Lose;
	public Image bullet;
	
	public  CharImages()
	  {
		Win = new ImageIcon("pacpix/win.gif").getImage();
		Lose = new ImageIcon("pacpix/lose.gif").getImage();
		Start = new ImageIcon("pacpix/start.gif").getImage();
		Clear= new ImageIcon("pacpix/clear.gif").getImage();
	    Over= new ImageIcon("pacpix/gameover.gif").getImage();
	    Next= new ImageIcon("pacpix/nextstage.gif").getImage();
		bullet = new ImageIcon("pacpix/bullet.gif").getImage();
		pacDeath = new ImageIcon("pacpix/pacDeath.gif").getImage();
	    ghost1= new ImageIcon("pacpix/Ghost1.gif").getImage();
	    ghost2= new ImageIcon("pacpix/Ghost2.gif").getImage();
	    ghostscared1=new ImageIcon("pacpix/GhostScared1.gif").getImage();
	    ghostscared2=new ImageIcon("pacpix/GhostScared2.gif").getImage();

	    pacman1= new ImageIcon("pacpix/PacMan1.gif").getImage();
	    pacman2up=new ImageIcon("pacpix/PacMan2up.gif").getImage();
	    pacman3up=new ImageIcon("pacpix/PacMan3up.gif").getImage();
	    pacman4up=new ImageIcon("pacpix/PacMan4up.gif").getImage();

	    pacman2down=new ImageIcon("pacpix/PacMan2down.gif").getImage();

	    pacman3down=new ImageIcon("pacpix/PacMan3down.gif").getImage();

	    pacman4down=new ImageIcon("pacpix/PacMan4down.gif").getImage();
	

	    pacman2left=new ImageIcon("pacpix/PacMan2left.gif").getImage();

	    pacman3left=new ImageIcon("pacpix/PacMan3left.gif").getImage();

	    pacman4left=new ImageIcon("pacpix/PacMan4left.gif").getImage();
	

	    pacman2right=new ImageIcon("pacpix/PacMan2right.gif").getImage();
	 
	    pacman3right=new ImageIcon("pacpix/PacMan3right.gif").getImage();
	
	    pacman4right=new ImageIcon("pacpix/PacMan4right.gif").getImage();
	    
	    enemy1= new ImageIcon("pacpix/Enemy1.gif").getImage();
	    enemy2up=new ImageIcon("pacpix/Enemy2up.gif").getImage();
	    enemy3up=new ImageIcon("pacpix/Enemy3up.gif").getImage();
	    enemy4up=new ImageIcon("pacpix/Enemy4up.gif").getImage();

	    enemy2down=new ImageIcon("pacpix/Enemy2down.gif").getImage();

	    enemy3down=new ImageIcon("pacpix/Enemy3down.gif").getImage();

	    enemy4down=new ImageIcon("pacpix/Enemy4down.gif").getImage();
	

	    enemy2left=new ImageIcon("pacpix/Enemy2left.gif").getImage();

	    enemy3left=new ImageIcon("pacpix/Enemy3left.gif").getImage();

	    enemy4left=new ImageIcon("pacpix/Enemy4left.gif").getImage();
	

	    enemy2right=new ImageIcon("pacpix/Enemy2right.gif").getImage();
	 
	    enemy3right=new ImageIcon("pacpix/Enemy3right.gif").getImage();
	
	    enemy4right=new ImageIcon("pacpix/Enemy4right.gif").getImage();
	   
	  }

}
