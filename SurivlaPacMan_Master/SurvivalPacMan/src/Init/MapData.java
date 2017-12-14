package Init;

import java.awt.Color;

public class MapData {
	int blocksize = 24;
	int nrofblocks = 15;
	int scrsize = nrofblocks * blocksize;
	Color dotcolor = new Color(192, 192, 0);
	int bigdotcolor = 192;
	int dbigdotcolor = -2;
	Color mazecolor = new Color(32, 192, 255);
	int level;
	short[] currentMap;

	short level1data[] = { // screendata�� �̹� leveldata������ �ʱ�ȭ��
			19, 26, 26, 22, 9, 12, 19, 26, 22, 9, 12, 19, 26, 26, 22, 37, 11, 14, 17, 26, 26, 20, 15, 17, 26, 26, 20,
			11, 14, 69, 17, 26, 26, 20, 11, 6, 17, 26, 20, 3, 14, 17, 26, 26, 20, 21, 3, 6, 25, 22, 5, 21, 7, 21, 5, 19,
			28, 3, 6, 21, 21, 9, 8, 14, 21, 13, 21, 5, 21, 13, 21, 11, 8, 12, 21, 25, 18, 26, 18, 24, 18, 28, 5, 25, 18,
			24, 18, 26, 18, 28, 6, 21, 7, 21, 7, 21, 11, 8, 14, 21, 7, 21, 7, 21, 03, 4, 21, 5, 21, 5, 21, 11, 10, 14,
			21, 5, 21, 5, 21, 1, 12, 21, 13, 21, 13, 21, 11, 10, 14, 21, 13, 21, 13, 21, 9, 19, 24, 26, 24, 26, 16, 26,
			18, 26, 16, 26, 24, 26, 24, 22, 21, 3, 2, 2, 6, 21, 15, 21, 15, 21, 3, 2, 2, 06, 21, 21, 9, 8, 8, 4, 17, 26,
			8, 26, 20, 1, 8, 8, 12, 21, 17, 26, 26, 22, 13, 21, 11, 2, 14, 21, 13, 19, 26, 26, 20, 69, 11, 14, 17, 26,
			24, 22, 13, 19, 24, 26, 20, 11, 14, 37, 25, 26, 26, 28, 3, 6, 25, 26, 28, 3, 6, 25, 26, 26, 28 };

	short level2data[] = { 0, 12, 19, 26, 26, 22, 9, 0, 12, 19, 26, 26, 22, 9, 0, 12, 19, 20, 11, 14, 17, 22, 13, 19,
			20, 11, 14, 17, 22, 9, 43, 16, 24, 26, 26, 16, 24, 26, 24, 16, 26, 26, 24, 16, 78, 6, 21, 3, 2, 6, 21, 11,
			2, 14, 21, 3, 2, 6, 21, 3, 12, 21, 9, 8, 12, 17, 30, 5, 27, 20, 9, 8, 12, 21, 9, 27, 16, 18, 26, 18, 20, 11,
			8, 14, 17, 18, 26, 18, 16, 30, 6, 17, 28, 7, 25, 16, 26, 42, 26, 16, 28, 7, 25, 20, 3, 4, 21, 11, 0, 14, 21,
			11, 10, 14, 21, 11, 0, 14, 21, 1, 12, 17, 22, 13, 19, 16, 30, 5, 27, 16, 22, 13, 19, 20, 9, 27, 16, 16, 26,
			16, 20, 11, 8, 14, 17, 16, 26, 16, 16, 30, 6, 17, 28, 7, 25, 16, 26, 18, 26, 16, 28, 7, 25, 20, 3, 12, 21,
			3, 0, 6, 21, 15, 21, 15, 21, 3, 0, 6, 21, 9, 27, 20, 9, 8, 12, 17, 18, 24, 18, 20, 9, 8, 12, 17, 30, 6, 25,
			18, 26, 18, 16, 28, 7, 25, 16, 18, 26, 18, 28, 3, 0, 6, 77, 7, 25, 28, 3, 0, 6, 25, 28, 7, 45, 3, 0 };

	short level3data[] = { 12, 19, 26, 18, 22, 9, 0, 4, 27, 18, 26, 26, 26, 78, 9, 19, 28, 7, 25, 16, 30, 9, 8, 14, 21,
			11, 10, 10, 6, 23, 37, 11, 0, 14, 21, 15, 19, 26, 26, 24, 26, 18, 22, 13, 21, 25, 22, 13, 19, 24, 26, 28, 3,
			10, 10, 6, 25, 16, 26, 20, 14, 17, 18, 28, 11, 10, 10, 4, 19, 22, 9, 6, 21, 7, 21, 19, 24, 20, 15, 19, 26,
			22, 5, 17, 24, 22, 13, 21, 13, 21, 29, 7, 17, 18, 28, 15, 29, 13, 45, 15, 25, 18, 24, 26, 20, 10, 4, 25, 20,
			15, 23, 11, 10, 14, 23, 15, 21, 3, 6, 29, 23, 9, 14, 17, 18, 16, 30, 5, 27, 16, 18, 20, 9, 8, 10, 25, 18,
			18, 24, 16, 20, 11, 8, 14, 17, 16, 24, 18, 18, 30, 14, 17, 28, 15, 17, 16, 18, 18, 18, 24, 20, 15, 25, 20,
			11, 19, 28, 7, 19, 16, 24, 16, 16, 28, 15, 17, 22, 7, 25, 22, 21, 11, 4, 25, 28, 7, 17, 20, 7, 27, 24, 28,
			1, 14, 21, 25, 22, 1, 2, 10, 12, 17, 20, 9, 10, 2, 2, 12, 19, 28, 6, 77, 1, 4, 27, 26, 24, 24, 26, 30, 1, 4,
			27, 28, 3 };

	public MapData() {
		currentMap = new short[level1data.length];
		level = 1;
	}

	public short[] returnMap() {
		switch (level) {
		case 0:
			return currentMap;
		case 1:
			return level1data;
		case 2:
			return level2data;
		case 3:
			return level3data;
		default:
			return level1data;
		}
	}

	public short[] copyToCurrentMap(int num) {

		for (int i = 0; i < level1data.length; i++) {

			switch (num) {

			case 1:
				currentMap[i] = level1data[i];
				break;
			case 2:
				currentMap[i] = level2data[i];
				break;
			case 3:
				currentMap[i] = level3data[i];
				break;
			case 4:
				if ( (level1data[i] & 16) != 0) 
					currentMap[i] = (short)(level1data[i]&15);
				else
					currentMap[i] = level1data[i];
				break;
			default:
				currentMap[i] = level1data[i];
				break;
			}
		}
		return currentMap;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int MapSize(int i) {
		if (i == 1)
			return level1data.length;
		else
			return 0;
	}

	public int getBlocksize() {
		return blocksize;
	}

	public void setBlocksize(int blocksize) {
		this.blocksize = blocksize;
	}

	public int getNrofblocks() {
		return nrofblocks;
	}

	public void setNrofblocks(int nrofblocks) {
		this.nrofblocks = nrofblocks;
	}

	public int getScrsize() {
		return scrsize;
	}

	public void setScrsize(int scrsize) {
		this.scrsize = scrsize;
	}

	public Color getDotcolor() {
		return dotcolor;
	}

	public void setDotcolor(Color dotcolor) {
		this.dotcolor = dotcolor;
	}

	public int getBigdotcolor() {
		return bigdotcolor;
	}

	public void setBigdotcolor(int bigdotcolor) {
		this.bigdotcolor = bigdotcolor;
	}

	public int getDbigdotcolor() {
		return dbigdotcolor;
	}

	public void setDbigdotcolor(int dbigdotcolor) {
		this.dbigdotcolor = dbigdotcolor;
	}

	public Color getMazecolor() {
		return mazecolor;
	}

	public void setMazecolor(Color mazecolor) {
		this.mazecolor = mazecolor;
	}

	public short[] getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(short[] currentMap) {
		this.currentMap = currentMap;
	}

	public short[] getLevel1data() {
		return level1data;
	}

	public void setLevel1data(short[] level1data) {
		this.level1data = level1data;
	}

	public short[] getLevel2data() {
		return level2data;
	}

	public void setLevel2data(short[] level2data) {
		this.level2data = level2data;
	}

	public short[] getLevel3data() {
		return level3data;
	}

	public void setLevel3data(short[] level3data) {
		this.level3data = level3data;
	}

}
