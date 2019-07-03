package utility;

import controller.mainController.GameControl;

public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Position pos) {
		if (this.getX() == pos.getX() && this.getY() == pos.getY()) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String toString() {
		return "x: " + x + " y: " + y;
	}

	/**
	 * reverse the sequence of the position in game board to XY position
	 * @param seq
	 * @return
	 */
	public static Position reverse(int seq) {
		GameControl gc = GameControl.getInstance();
		Position pos;
		if (seq < gc.getGameSize()) {
			pos = new Position(seq, 0);
		} else {
			pos = new Position(seq % gc.getGameSize(), seq / gc.getGameSize());
		}
		return pos;
	}

	/**
	 * @return sequence of the position in game board
	 */
	public int getSeq() {
		GameControl gc = GameControl.getInstance();
		int gz = gc.getGameSize();

		int seq = gz * y + x;

		return seq;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
