package lifeGame;

import java.util.HashMap;
import java.util.LinkedList;

public class gameMap {
	enum STATE {
		DIE, ALIVE;
	};
	private static int MAP_HEIGH = 1000;
	private static int MAP_WIDTH = 1000; 
	private STATE[][] world = new STATE[MAP_HEIGH][MAP_WIDTH]; 
	private LinkedList<Integer> aliveCell = new LinkedList<>(); //�洢��ϸ��λ��
	private HashMap<Integer,Boolean> checkedCell
			= new HashMap<Integer, Boolean>();
	public gameMap(STATE[][] world) {
		super();
		this.world = world;
	}
	
	public gameMap() {
		super();
		for (int i = 0; i < world.length; ++i) {
			for (int j = 0; j < world[i].length; ++j) {
				world[i][j] = STATE.DIE;
			}
		}
	}
	
	public static void initWorld(STATE[][] w) {
		for (int i = 0; i < w.length; ++i) {
			for (int j = 0; j < w[i].length; ++j) {
				w[i][j] = STATE.DIE;
			}
		}
	}
	
	public STATE[][] getWorld() {
		return world;
	}
	
	

	public LinkedList<Integer> getAliveCell() {
		return aliveCell;
	}

	public void setAliveCell(LinkedList<Integer> aliveCell) {
		this.aliveCell = aliveCell;
		
	}

	public void setWorld(STATE[][] world) {
		this.world = world;
		this.aliveCell.clear();
		for (int i = 0; i < MAP_HEIGH; ++i) {
			for (int j = 0; j < MAP_WIDTH; ++j) {
				//����ϸ����λ�ü�¼��aliveCell
				if (world[i][j] == gameMap.STATE.ALIVE) {
					this.aliveCell.add(i * MAP_WIDTH + j);
				}
			}
		}
	}

	public static int getHeigh() {
		return MAP_HEIGH;
	}

	public static int getWidth() {
		return MAP_WIDTH;
	}
	
	public static void setMAP_HEIGH(int mAP_HEIGH) {
		MAP_HEIGH = mAP_HEIGH;
	}

	public static void setMAP_WIDTH(int mAP_WIDTH) {
		MAP_WIDTH = mAP_WIDTH;
	}

	public void print() {
		for (int i = 0; i < MAP_HEIGH; ++i) {
			for (int j = 0; j < MAP_WIDTH; ++j) {
				System.out.print(world[i][j]);
			}
			System.out.println();
		}
	}
	
	private STATE cellState(int x, int y) {
		return world[x][y];
	}

	public int getNeighbors(int x, int y) {
		int n = 0;
		//����߽����
		int left = y - 1 < 0 ? MAP_WIDTH - 1 : y - 1;
		int right = y + 1 == MAP_WIDTH ? 0 : y + 1;
		int up = x - 1 < 0 ? MAP_HEIGH - 1 : x - 1;
		int down = x + 1 == MAP_HEIGH ? 0 : x + 1;
		//n�洢λ��(x, y)����ϸ�����ھ���
		n += cellState(up, left).ordinal();
	    n += cellState(up, y).ordinal();
	    n += cellState(up, right).ordinal();
	    n += cellState(x, left).ordinal();
	    n += cellState(x, right).ordinal();
	    n += cellState(down, left).ordinal();
	    n += cellState(down, y).ordinal();
	    n += cellState(down, right).ordinal();
        return n;
	}
	
	private boolean isOnEdge(int x, int y) {
		if (x == 0 || x == MAP_HEIGH - 1) {
			return true;
		} else if (y == 0 || y == MAP_WIDTH - 1) {
			return true;
		}
		return false;
	}
	
	public void nextWorld() {
		//��Ӹ��·�ʽ�ж��Ż���
		//�Ż���Ĵ��룺
		//����һ����ʱ��ά����洢���¹���ʱ�ĵ�ͼ״̬���Է�ֹ���¹���Ӱ����
		STATE[][] temp = new STATE[MAP_HEIGH][MAP_WIDTH];
		initWorld(temp);
		int aliveCellCount = this.aliveCell.size();
		if (9 * aliveCellCount < MAP_HEIGH * MAP_WIDTH) {
			for (int i = 0; i != aliveCellCount; ++i) {
				//��ȡ������״̬��ϸ����λ��,�����Ը�λ��Ϊ���ĵľŸ�ϸ��
				int tmpI = this.aliveCell.get(0) / MAP_WIDTH;
				int tmpJ = this.aliveCell.get(0) % MAP_WIDTH;
				//��ȡ��x,y�����ڵ�����ϽǴ�������
				int up = tmpI - 1 < 0 ? MAP_HEIGH - 1 : tmpI - 1;
				int down = tmpI + 1 == MAP_HEIGH ? 0 : tmpI + 1;
				int left = tmpJ - 1 < 0 ? MAP_WIDTH - 1 : tmpJ - 1;
				int right = tmpJ + 1 == MAP_WIDTH ? 0 : tmpJ + 1;
				//�����Ͻǿ�ʼ����
				for (int j = 0; j < 3; ++j) {
					for (int k = 0; k < 3; ++k) {
						int x;
						int y;
						if (left + j >= MAP_WIDTH) {
							x = left + j - MAP_WIDTH;
						} else {
							x = left + j;
						}
						if (up + k >= MAP_HEIGH) {
							y = up + k - MAP_HEIGH;
						} else {
							y = up + k;
						}
						//���ýڵ��Ѿ��жϹ�������
						if (isChecked(y, x)) {
							continue;
						} else {
							this.checkedCell.put(y * MAP_WIDTH + x, true);
						}
						int neighbors = getNeighbors(y, x);
						if (neighbors == 3) {
							//�����ھ�
							temp[y][x] = gameMap.STATE.ALIVE;
							//����ϸ�������ϸ������
							this.aliveCell.add(y * MAP_WIDTH + x);
						} else if (neighbors == 2) {
							//�����ھ�ʱ���账��
							temp[y][x] = this.world[y][x];
							//����ϸ�������ϸ������
							if (temp[y][x] == gameMap.STATE.ALIVE) {
								this.aliveCell.add(y * MAP_WIDTH + x);
							}
						} else {
							//�������
							temp[y][x] = gameMap.STATE.DIE;
						}
					}
				}
				//ÿ���һ�ֶ�һ����ϸ���ı������ʹӻ�ϸ��������ɾ�������ϸ��
				this.aliveCell.remove(0);
			}
			world = temp;
			this.checkedCell.clear();
		}
		else {
			//�Ż�ǰ�Ĵ��룺
			this.aliveCell.clear(); //�Ƚ���ϸ���������
			for (int i = 0; i < MAP_HEIGH; ++i) {
				for (int j = 0; j < MAP_WIDTH; ++j) {
					temp[i][j] = world[i][j];
					if (getNeighbors(i, j) == 3) {
						//�����ھ�
						temp[i][j] = gameMap.STATE.ALIVE;
						this.aliveCell.add(i * MAP_WIDTH + j);
					} else if (getNeighbors(i, j) == 2) {
						//�����ھ�ʱ���账��
						if (temp[i][j] == gameMap.STATE.ALIVE) {
							this.aliveCell.add(i * MAP_WIDTH + j);
						}
					} else {
						//�������
						temp[i][j] = gameMap.STATE.DIE;
					}
				}
			}
			world = temp;
		}
	}
	
	private boolean isChecked(int y, int x) {
		// TODO �Զ����ɵķ������
		return this.checkedCell.get(y * MAP_WIDTH + x) != null;
	}

	public void changeState(int x, int y) {
		if (this.world[x][y] == gameMap.STATE.ALIVE) {
			this.world[x][y] = gameMap.STATE.DIE;
			for (int i = 0; i != this.aliveCell.size(); ++i) {
				if (this.aliveCell.get(i) == x * MAP_WIDTH + y) {
					this.aliveCell.remove(i);
					break;
				}
			}
		} else {
			this.world[x][y] = gameMap.STATE.ALIVE;
			this.aliveCell.add(x * MAP_WIDTH + y);
		}
	}
	
	public void clear() {
		this.world = null;
		//�������е�ֵ������Ϊ0
		this.world = new STATE[MAP_HEIGH][MAP_WIDTH];
		this.aliveCell.clear();
	}
	
	public boolean isClear() {
		for (int i = 0; i < MAP_HEIGH; ++i) {
			for (int j = 0; j < MAP_WIDTH; ++j) {
				if (world[i][j] ==STATE.ALIVE) {
					return false;
				}
			}
		}
		return true;
	}
}
