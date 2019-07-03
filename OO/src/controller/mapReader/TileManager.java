package controller.mapReader;

import java.util.ArrayList;

import model.tile.Tile;

public class TileManager {
	private static ArrayList<Tile> tileList;

	public TileManager() {
	}


	/**
	 * remove the decorator from a tile
	 * @param tile
	 * @param target
	 */
	public static void undecorate(Tile tile,int target) {
		tileList.set(target, tile);
	}

	public static ArrayList<Tile> getTileMaps() {
		return tileList;
	}

	public static Tile getTileType(int seq) {
		return tileList.get(seq);
	}

	public static void setTileMaps(ArrayList<Tile> map) {
		tileList=map;
		
	}
}
