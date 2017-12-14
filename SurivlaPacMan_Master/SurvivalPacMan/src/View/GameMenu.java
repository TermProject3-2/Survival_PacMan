package View;

import java.awt.Container;
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
	JButton soloBtn, coupleBtn, bossBtn;
	Image backgroundImage; // background img
	Container parentPane; // 이게 달릴 부모 펜
	BattlePlay battlePlay;
	JPanel gameMenu;

	public GameMenu() {
		this.setBounds(0, 0, 400, 500);
		this.setLayout(null);
		gameMenu = this;

		backgroundImage = new ImageIcon("pacpix/MainBackground.jpg").getImage();

		soloBtn = new JButton("혼자 하기");
		soloBtn.setBounds(100, 140, 200, 60);

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
		coupleBtn.setBounds(100, 260, 200, 60);
		coupleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConnectPanel connectPanel = new ConnectPanel();

				parentPane = getParent();
				parentPane.add(connectPanel);
				connectPanel.requestFocus(true);
				parentPane.remove(gameMenu);
				parentPane.repaint();

			}
		});
		this.add(coupleBtn);

		bossBtn = new JButton("보스 잡기");
		bossBtn.setBounds(100, 380, 200, 60);
		bossBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		this.add(bossBtn);

		this.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paintComponent(g);
	}

}