package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Progress.BattlePlay;
import Progress.SoloPlay;

public class GameMenu extends JPanel {
	JButton soloBtn, coupleBtn;
	Image backgroundImage, logoImage; // background img
	Container parentPane; // 이게 달릴 부모 펜
	BattlePlay battlePlay;
	JPanel gameMenu;

	public GameMenu() {
		this.setBounds(0, 0, 400, 500);
		this.setLayout(null);
		gameMenu = this;

		backgroundImage = new ImageIcon("pacpix/packmanback.jpg").getImage();
		logoImage = new ImageIcon("pacpix/topLogo.jpg").getImage();
		soloBtn = new JButton("혼자 하기");
		soloBtn.setBounds(100, 150, 200, 60);
		soloBtn.setForeground(Color.WHITE);
		soloBtn.setBackground(Color.BLACK);
		soloBtn.setOpaque(false);
		soloBtn.setFont(new Font("Serif",Font.BOLD,13));
		soloBtn.setBorder(null);

		soloBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Thread soloModeThread;
				SoloPlay soloMode = new SoloPlay();

				parentPane = getParent();
				parentPane.add(soloMode);
				soloMode.requestFocus(true);
				parentPane.remove(gameMenu);
				parentPane.repaint();

				soloModeThread = new Thread(soloMode);
				soloModeThread.start();

			}
		});
		this.add(soloBtn);

		coupleBtn = new JButton("둘이 하기");
		coupleBtn.setBounds(100, 270, 200, 60);
		coupleBtn.setForeground(Color.WHITE);
		coupleBtn.setBackground(Color.BLACK);
		coupleBtn.setOpaque(false);
		coupleBtn.setFont(new Font("Serif",Font.BOLD,13));
		coupleBtn.setBorder(null);
		coupleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConnectPanel connectPanel = new ConnectPanel();

				parentPane = getParent();
							
				parentPane.remove(gameMenu);				
				parentPane.add(connectPanel);
				parentPane.repaint();
				connectPanel.requestFocus(true);
				
			}
		});
		this.add(coupleBtn);

		this.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(logoImage, 50, 20, 300, 170, this);
		setOpaque(false);
		super.paintComponent(g);
	}

}