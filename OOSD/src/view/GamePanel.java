package view;/*
				* *OOSD
				* *@Date:  2019-03-22
				* *@author:  Biao Li
				* *StudentID :  s3675917
				* *@ver 1.0
				* */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.mainController.GameControl;
import controller.mainController.Prompt;
import controller.mapReader.TileManager;
import controller.mapReader.XML;
import utility.Position;
import model.player.Player;
import model.player.PlayerStat;
import model.tile.Tile;
import view.utils.Sprite;

public class GamePanel extends JPanel {

	private int width;
	private int height;
	private BufferStrategy bs;
	private Image img;
	private Graphics g;
	private Prompt es;
	private int scale;
	private GameControl gc;
	private Sprite num;
	private Sprite stat;//0angel 1charged 2handcuff 3trap

	public GamePanel(BufferStrategy bs, int width, int height,int scale) {
		loadImg();
		gc= GameControl.getInstance();
		this.bs = bs;
		this.scale=scale;
		this.width = width*scale;
		this.height = height*scale;
		setPreferredSize(new Dimension(this.width, this.height));
		setFocusable(true);
		requestFocus();
		init();
		es = Prompt.getInstance();
	}
	
	private void loadImg() {
		num=new Sprite("res/entity/references/num.png", 32, 32);
		stat = new Sprite("res/entity/references/stat.png", 32, 32);
	}

	private void init() {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = img.getGraphics();
		new GameControl();
		try {
			XML.initilizeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void renderMap() {
		// drawing map
		gc= GameControl.getInstance();
		Player prePlayer =gc.getPreviousPlayer();
		Player currentplayer = gc.getCurrentPlayer();
		Player player;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		ArrayList<Tile> tiles = TileManager.getTileMaps();
		if (currentplayer.isWalking()) {
			player=currentplayer;
		}else {
			player=prePlayer;
		}if (!prePlayer.isWalking()) {
			player=currentplayer;
		}else {
			player=prePlayer;
		}
		for (Tile t : tiles) {
			if (t.withinrange(player)) {
				g.drawImage(t.getImg(), t.getPos().getX() * 32, t.getPos().getY() * 32, t.getWidth(), t.getHeight(),
						null);
			}

		}
	}

	public void render() {
		gc= GameControl.getInstance();
		Player prePlayer =gc.getPreviousPlayer();
		Player player = gc.getCurrentPlayer();


		long now;
		Position pos = prePlayer.getPos();
		Position oldPos = prePlayer.getOldPos();
		if (prePlayer.isWalking()) {
			now = System.currentTimeMillis();
			int oldPosXxH = oldPos.getX() * 32;
			int oldPosYxH = oldPos.getY() * 32;
			if (now - prePlayer.getStart() < 1000) {
				Double i = new Double((now - prePlayer.getStart()) / 30);
				int j = i.intValue();
				prePlayer.AnimationAddNum();
				g.drawImage(prePlayer.getSpriteImage(), oldPosXxH + j * (pos.getX() - oldPos.getX()),
						oldPosYxH + j * (pos.getY() - oldPos.getY()), this);
			} else {
				prePlayer.setWalking(false);
			}
		} else {
			int newPosXxH = player.getPos().getX() * 32;
			int newPosYxH = player.getPos().getY() * 32;
			
			// draw the pointer on the next movable player
			BufferedImage indicator = loadIndicator(player);
			g.drawImage(indicator, newPosXxH, newPosYxH - 32, this);

			// draw non-moving player
			for (int i = 0; i < gc.getPlayers().size(); i++) {
				Player temPlayer = gc.getPlayers().get(i);
				int X = temPlayer.getPos().getX() * 32;
				int Y = temPlayer.getPos().getY() * 32;
				if (player.withinSight(temPlayer)) {
					g.drawImage(temPlayer.getStandingImage(), X, Y, this);
				}
			}

		}

	}
	
	private BufferedImage loadIndicator(Player player) {
		BufferedImage indicator = null;
		PlayerStat stat = (PlayerStat) player.getStatus();
		switch (stat) {
		case normal:
			int stamina= player.getStamina();
			indicator = num.getSprite(stamina, 0);
			break;
		case arrested:
			indicator = this.stat.getSprite(2, 0);
			break;
		case trapped:
			indicator = this.stat.getSprite(3, 0);
			break;
		case invincible:
			indicator = this.stat.getSprite(0, 0);
			break;
		case charged:
			indicator = this.stat.getSprite(1, 0);
			break;
				

		default:
			break;
		}
		return indicator;
	}

	public void draw() {
		do {
			Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
			g2.scale(scale, scale);
			g2.drawImage(img, 8, 31, width, height, this);
			// drawing error message
			g2.setColor(Color.WHITE);
			g2.drawString(es.getMessage(), 550, 700);
			g2.dispose();
			bs.show();
		} while (bs.contentsLost());

	}

}
