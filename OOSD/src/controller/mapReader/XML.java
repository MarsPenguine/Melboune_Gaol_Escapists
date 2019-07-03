package controller.mapReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
/**
 * 使用dom4j获取xml上的属性信息
 */
import org.dom4j.io.SAXReader;

import utility.Position;
import model.factories.TileFactory;
import model.tile.Floor;
import model.tile.Tile;
import view.utils.Sprite;

public class XML {
	/*
	 * floor = 0 wall = 1 goal = 2 door = 3
	 */
	private static String imagePath;
	private static Sprite sprite;
	private static TileFactory tFactory= new TileFactory();

	public XML() {
		// TODO Auto-generated constructor stub
	}

	public static void initilizeMap() throws Exception {

		SAXReader reader = new SAXReader();
		Document mapFile = reader.read(new File("src/res/untitled.xml"));
		ArrayList<Tile> map = new ArrayList<Tile>();

		// read map info from xml file
		Element conElem = mapFile.getRootElement().element("tileset");
		int tileWidth = Integer.parseInt(conElem.attributeValue("tilewidth"));
		int tileHeight = Integer.parseInt(conElem.attributeValue("tileheight"));
		int tileColumns = Integer.parseInt(conElem.attributeValue("columns"));
		imagePath = conElem.attributeValue("name");
		sprite(tileWidth, tileHeight);

		List<Element> layerList = mapFile.getRootElement().elements("layer");
		// read floor layer from xml file then instantiate floors
		Element floor = layerList.get(0).element("data");
		String buffer = floor.getText();
		String[] bufferStrings = buffer.split(",");
		for (int i = 0; i < bufferStrings.length; i++) {
			BufferedImage img = getImage(31, tileColumns, tileWidth, tileHeight);
			map.add(new Floor(img, Position.reverse(i)));
		}
		
		// read layers from xml file then instantiate tile for different layers
		for (int layer = 1; layer <= 3; layer += 1) {
			Element wallElement = layerList.get(layer).element("data");
			buffer = wallElement.getText();
			System.out.println(buffer);
			bufferStrings = buffer.split(",");
			for (int i = 0; i < bufferStrings.length; i++) {
				bufferStrings[i] = bufferStrings[i].replace("\n", "");
				if (Integer.parseInt(bufferStrings[i]) != 0) {
					Tile tempTile;
					BufferedImage img = getImage(Integer.parseInt(bufferStrings[i]), tileColumns, tileWidth,
							tileHeight);
					tempTile = tFactory.createTile(img, map.get(i), layer);
					map.set(i, tempTile);
				}
			}
		}

		TileManager.setTileMaps(map);
	}

	private static void sprite(int tileWidth, int tileHeight) {
		sprite = new Sprite("res/tile/" + imagePath + ".png", tileWidth, tileHeight);
	}

	private static BufferedImage getImage(int imgNo, int tileColumns, int tileWidth, int tileHeight) {
		int sprite_x;
		int sprite_y;
		
		sprite_x = (imgNo - 1) % tileColumns;
		sprite_y = (imgNo - 1) / tileColumns;

		return sprite.getSprite(sprite_x, sprite_y);
	}

}