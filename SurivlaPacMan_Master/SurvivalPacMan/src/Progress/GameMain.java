package Progress;

import java.awt.Container;

import javax.swing.JFrame;

import View.GameMenu;

public class GameMain extends JFrame{
	
	private Container contentPane;
	
    public GameMain(){
		this.setBounds(100, 100, 400, 500);
		this.setResizable(false);
		//어차피 이거 레이아웃 디폹가 border이고 그건 하나만 붙이면 전체로 붙음
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = this.getContentPane(); 
   	 
		GameMenu gameMenu = new GameMenu();
		contentPane.add(gameMenu);
		
		this.setVisible(true);
    }

}



