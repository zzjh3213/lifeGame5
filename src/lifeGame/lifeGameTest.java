package lifeGame;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class lifeGameTest {
	private gameMap map = new gameMap();
	private gameMap.STATE[][] world;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testNextWorld() {
		world = new gameMap.STATE[map.getHeigh()][map.getWidth()];
		//初始化游戏地图
		world[0][0] = gameMap.STATE.ALIVE;
		world[0][1] = gameMap.STATE.ALIVE;
		world[1][0] = gameMap.STATE.ALIVE;
		world[0][gameMap.getWidth() - 1] = gameMap.STATE.ALIVE;
		world[gameMap.getHeigh() - 1][0] = gameMap.STATE.ALIVE;      
		map.setWorld(world);
		map.nextWorld(); //调用nextWorld方法
		//按照预期，更改游戏地图的数据
		world = new gameMap.STATE[map.getHeigh()][map.getWidth()];
		world[0][1] = gameMap.STATE.ALIVE;
		world[1][1] = gameMap.STATE.ALIVE;
		world[1][0] = gameMap.STATE.ALIVE;
		world[map.getHeigh() - 1][1] = gameMap.STATE.ALIVE;
		world[map.getHeigh() - 1][0] = gameMap.STATE.ALIVE;
		world[0][map.getWidth() - 1] = gameMap.STATE.ALIVE;
		world[1][map.getWidth() - 1] = gameMap.STATE.ALIVE;
		world[map.getHeigh() - 1][map.getWidth() - 1] = gameMap.STATE.ALIVE;
		//检验nextWorld方法是否按预期运行
		for (int i = 0; i < map.getHeigh(); ++i) {
			for (int j = 0; j < map.getWidth(); ++j) {
				TestCase.assertEquals(world[i][j], map.getWorld()[i][j]);
			}
		}
	}

	@Test
	public void testChangeState() {
		world = new gameMap.STATE[map.getHeigh()][map.getWidth()];
		world[1][1] = gameMap.STATE.ALIVE;
		map.setWorld(world);
		map.changeState(1, 1);
		world[1][1] = gameMap.STATE.DIE;
		TestCase.assertEquals(world, map.getWorld());
		map.changeState(1, 1);
		world[1][1] = gameMap.STATE.ALIVE;
		TestCase.assertEquals(world, map.getWorld());
	}
	
	@Test
	public void testGetNeighbors() {
		world = new gameMap.STATE[map.getHeigh()][map.getWidth()];
		//测试用例
		world[0][0] = gameMap.STATE.ALIVE;
		world[0][1] = gameMap.STATE.ALIVE;
		world[1][0] = gameMap.STATE.ALIVE;
		world[0][gameMap.getWidth() - 1] = gameMap.STATE.ALIVE;
		world[gameMap.getHeigh() - 1][0] = gameMap.STATE.ALIVE;
		map.setWorld(world);
		TestCase.assertEquals(4, map.getNeighbors(0, 0));
	}

	@Test
	public void testClear() {
		map.clear();
		for (int i = 0; i < map.getHeigh(); ++i) {
			for (int j = 0; j < map.getWidth(); ++j) {
				TestCase.assertEquals(0, map.getWorld()[i][j]);
			}
		}
	}

}
