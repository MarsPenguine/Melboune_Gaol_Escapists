package model.player;

import java.awt.image.BufferedImage;

import controller.mainController.Prompt;
import controller.mapReader.TileManager;
import utility.DIR;
import utility.Position;
import model.spell.PlayerVisitor;
import model.tile.Tile;
import view.utils.Animation;
import view.utils.Sprite;

/*
 *
 * FOV is the number of the block this player can see
 * facingDir is which side this player is facing now
 * start is the time when player starts moving in the view
 * */
public abstract class Player {

	protected Position pos;
	protected String name;
	protected int FOV;
	protected Enum<PlayerStat> status;
	protected DIR facingDir;
	protected int stamina;
	protected Sprite sprite;
	protected Sprite arrow;
	private int superChargedTimer;

	protected Prompt es;
	protected Position nextPos;
	private long start;
	private boolean walking;

	protected int wh = 32;
	protected Animation animation;
	protected int imageChoosenNum;

	public Player(Position pos, String name) {
		this.pos = pos;
		this.name = name;
		this.FOV = 3;
		this.status = PlayerStat.normal;
		facingDir = DIR.down;
		sprite = new Sprite("res/entity/references/player_referrence.png", wh, wh);
		arrow = new Sprite("res/entity/references/arr.png", 32, 32);

		stamina = 0;
		es = Prompt.getInstance();

		walking = false;
		animation = new Animation(0, 0);

	}

	public abstract boolean getTrapped();

	public abstract void recover();

	/*
	 * return true if target player is within this player's field of view
	 */
	public boolean withinSight(Player player) {
		int thisx = this.getPos().getX();
		int thisy = this.getPos().getY();
		int px = player.getPos().getX();
		int py = player.getPos().getY();
		int fov = this.getFOV();
		boolean x = Math.abs(px - thisx) <= fov;
		boolean y = Math.abs(py - thisy) <= fov;
		if (x && y) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * return true if target player is within this player's spell range
	 */
	public boolean withinRange(Player player, int range) {
		int thisx = this.getPos().getX();
		int thisy = this.getPos().getY();
		int px = player.getPos().getX();
		int py = player.getPos().getY();
		boolean x = false;
		boolean y = false;
		switch (this.facingDir) {
		case up:
			x = Math.abs(px - thisx) <= range;
			y = thisy - py == 1;
			break;
		case down:
			x = Math.abs(px - thisx) <= range;
			y = thisy - py == -1;
			break;
		case left:
			x = px - thisx == -1;
			y = Math.abs(py - thisy) <= range;
			break;
		case right:
			x = px - thisx == 1;
			y = Math.abs(py - thisy) <= range;
			break;
		}
		if (x && y) {
			return true;
		} else {
			return false;
		}
	}
	
	public void superCharge() {
		if (status == PlayerStat.charged) {
			es.setMessage("you cant supercharge again");
			return;
		} else {
			setStatus(PlayerStat.charged);
			superChargedTimer = 3;
			es.setMessage("YOU ARE SUPERCHARGED!!!");
		}
	}

	// return this player's next position base on its facing
	public Position getNextPosition(DIR dir) {
		int x = this.pos.getX();
		int y = this.pos.getY();
		animation.setOldPos(pos);
		switch (dir) {
		case up:
			y--;
			animation.setDirection(3);
			break;
		case down:
			y++;
			animation.setDirection(0);
			break;
		case left:
			x--;
			animation.setDirection(1);
			break;
		case right:
			x++;
			animation.setDirection(2);
			break;
		}
		Position pos = new Position(x, y);
		return pos;
	}

	// try to move to next tile, throw exception if unable to do so(next tile is not
	// walkable)
	public void move(DIR dir) throws Exception {
		nextPos = getNextPosition(dir);
		System.out.println(nextPos);
		Tile nextTile = TileManager.getTileType(nextPos.getSeq());
		nextTile.interact(this);
		if (status == PlayerStat.charged) {
			superChargedTimer--;
			if (superChargedTimer == 0) {
				superChargedTimer = 3;
				status = PlayerStat.normal;
			}
		}
	}

	// move to next tile
	public void walk() {
		this.setPos(nextPos);
		walking = true;
		start = System.currentTimeMillis();
	}

	public void setFacing(DIR dir) {
		int direction = 0;
		if (dir != null) {
			switch (dir) {
			case down:
				direction = 0;
				break;
			case up:
				direction = 3;
				break;
			case left:
				direction = 1;
				break;
			case right:
				direction = 2;
				break;
			}
			animation.setDirection(direction);
			facingDir = dir;
		}
	}

	public DIR getFacing() {
		return this.facingDir;
	}

	// self explanatory
	public Position getNextXPosition(int X) {
		int x = this.getPos().getX();
		int y = this.getPos().getY();
		DIR dir = this.getFacing();
		Position targetPositions = null;
		switch (dir) {
		case up:
			targetPositions = new Position(x, y - X);
			break;
		case down:
			targetPositions = new Position(x, y + X);
			break;
		case left:
			targetPositions = new Position(x - X, y);
			break;
		case right:
			targetPositions = new Position(x + X, y);
			break;
		}
		return targetPositions;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public int getStamina() {
		return stamina;
	}

	public void recoverStamina() {
		if (this.stamina < 5) {
			this.stamina++;
		}
	}

	public void deductStamina(int requiredStamina) {
		this.stamina -= stamina;
	}

	public boolean checkStamina(int requiredStamina) {
		if (this.getStamina() < requiredStamina) {
			es.setMessage("cant cast spell due to low stamina");
			return false;
		} else {
			return true;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFOV() {
		return this.FOV;
	}

	/**
	 * Set the player's field of view
	 *
	 * @param FOV field of view
	 * @Precondition FOV must greater than 0
	 */
	public void setFOV(int FOV) {
		assert FOV > 0 : ("FOV must greater than 0");
		this.FOV = FOV;
	}

	public Enum<PlayerStat> getStatus() {
		return status;
	}

	public void setStatus(Enum<PlayerStat> status) {
		this.status = status;
	}

	public long getStart() {
		return start;
	}

	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	public boolean isWalking() {
		return walking;
	}

	public BufferedImage getSpriteImage() {
		if (imageChoosenNum < 4)
			return sprite.getSprite(imageChoosenNum * 3 + animation.getNum(), animation.getDirection(), wh, wh);
		else
			return sprite.getSprite((imageChoosenNum - 4) * 3 + animation.getNum(), animation.getDirection() + 4, wh,
					wh);
	}

	public void AnimationAddNum() {
		animation.addNum();
	}

	public BufferedImage getStandingImage() {
		if (imageChoosenNum < 4) {
			return sprite.getSprite(imageChoosenNum * 3, animation.getDirection(), wh, wh);
		} else
			return sprite.getSprite((imageChoosenNum - 4) * 3, animation.getDirection() + 4, wh, wh);
	}

	public BufferedImage getNextStandingImage() {

		return arrow.getSprite(0, 0);
	}

	public abstract void accpet(PlayerVisitor sVisitor);

	public Position getOldPos() {
		return animation.getOldPos();
	}

	public void setImageChoosenNum(int imageChoosenNum) {
		this.imageChoosenNum = imageChoosenNum;
	}
}
