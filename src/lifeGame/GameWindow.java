package lifeGame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameWindow extends JFrame {
	ArrayList<Integer> randomArr = new ArrayList<Integer>(); //用于存放随机数的数组
	private int deathRate = 50;
	private int size = 1000;
	public static int CELLSIZE = 3;
	private gameMap map = new gameMap();
	private GPanel g = new GPanel();
	//设置计时器
	private int timerDelay = 300;
	private Timer Jtimer = new Timer(timerDelay, null);
	private int cellX = -2;
	private int cellY = -2;
	public GameWindow() throws InterruptedException
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		g.setBounds(2, 2, 4200, 4200);
		g.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		//g.setOpaque(true);
		g.setPreferredSize(new Dimension(3005, 3005));
		g.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				//获取鼠标的坐标后，首先减去游戏地图界面相对于坐标的偏移位置
		    	//，再除以单个方框的大小以获取对应方框在数组中的下标
		        cellX = (e.getY() - 1) / CELLSIZE;
		        cellY = (e.getX() - 1) / CELLSIZE;
		        map.changeState(cellX, cellY);
		        g.setAliveCells(map.getAliveCell());
		        g.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
		});
		g.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO 自动生成的方法存根
				int tmpX = (e.getY() - 1) / CELLSIZE;
				int tmpY = (e.getX() - 1) / CELLSIZE;
				//防止拖动过程中由于鼠标未移出范围而对同一个细胞重复切换状态
				int needChange = 0;
				if (cellX != tmpX) {
					cellX = tmpX;
					needChange++;
				};
			    if (cellY != tmpY) {
			    	cellY = tmpY;
			    	needChange++;
			    }
			    System.out.println(cellX + " " + cellY);
			    if (needChange > 0) {
			    	map.changeState(cellX, cellY);
			    }
			    g.setAliveCells(map.getAliveCell());
			    g.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
		});
		this.add(g);
		JScrollPane sp = new JScrollPane(g, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBounds(2, 2, 1268, 700);
		this.add(sp);
		this.setSize(1500, 800);
		this.setTitle("生命游戏");
		this.setVisible(true);
		//设置计时器事件
		Jtimer.addActionListener(startPerformer);
		//设置开始按钮
		JButton btnStart = new JButton();
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				Jtimer.start();
			}
		});
		btnStart.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnStart.setText("开始");
		btnStart.setBounds(1386, 0, 100, 50);
		btnStart.setBackground(SystemColor.controlHighlight);
		getContentPane().add(btnStart);
		//设置暂停按钮
		JButton btnStop = new JButton();
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				Jtimer.stop();
			}
		});
		btnStop.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnStop.setText("暂停");
		btnStop.setBounds(1386, 48, 100, 50);
		btnStop.setBackground(SystemColor.controlHighlight);
		getContentPane().add(btnStop);
		//设置清空按钮
		JButton btnClear = new JButton();
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				map.clear(); //清空地图
				//绘制画面
				//g.setWorld(map.getWorld());
				g.setAliveCells(map.getAliveCell());
				Jtimer.stop();
				repaint();
			}
		});
		btnClear.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnClear.setText("清空");
		btnClear.setBounds(1386, 96, 100, 50);
		btnClear.setBackground(SystemColor.controlHighlight);
		getContentPane().add(btnClear);
		//设置随机按钮
		JButton btnRandom = new JButton();
		btnRandom.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnRandom.setText("随机生成");
		btnRandom.setBackground(SystemColor.controlHighlight);
		btnRandom.setBounds(1386, 144, 100, 50);
		btnRandom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//随机生成数字，并根据初始死亡率来决定生死
				Jtimer.stop();
				randomArr.clear();
				//生成随机数
				for (int i = 0; i < gameMap.getHeigh() * gameMap.getWidth(); ++i) {
					randomArr.add(i, (int)(Math.random() * 100));
				}
				gameMap.STATE[][] tmp = new gameMap.STATE[gameMap.getHeigh()][gameMap.getWidth()];
				for (int i = 0; i < randomArr.size(); ++i) {
					//根据初始死亡率来决定对应位置的细胞生死状态
					tmp[i / gameMap.getWidth()][i % gameMap.getWidth()] 
							= randomArr.get(i) >= deathRate 
							? gameMap.STATE.ALIVE 
							: gameMap.STATE.DIE;
				}
				//绘制生成好的地图
				map.setWorld(tmp);
				//g.setWorld(map.getWorld());
				g.setAliveCells(map.getAliveCell());
				repaint();
			}
		});
		getContentPane().add(btnRandom);
		
		//设置调整速度滑条
		JSlider speedSlider = new JSlider(SwingConstants.VERTICAL, 10, 500, timerDelay);
		speedSlider.setInverted(true);
		speedSlider.setBounds(1311, 232, 56, 272);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setMajorTickSpacing(490);
		speedSlider.setMinorTickSpacing(490);
		Dictionary<Integer, Component> label = new Hashtable<Integer, Component>();
		label.put(10, new JLabel("快"));
		label.put(500, new JLabel("慢"));
		speedSlider.setLabelTable(label);
		//设置显示速率的标签
		JLabel speedLabel = new JLabel("<html><body>" + "两帧" + "<br>" + "间隔:" 
										+ "<br>" + timerDelay + "ms" + "<body></html>");
		speedLabel.setBounds(1278, 324, 36, 50);
		getContentPane().add(speedLabel);
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				//改变计时器间隔
				Jtimer.setDelay(speedSlider.getValue());
				speedLabel.setText("<html><body>" + "两帧" + "<br>" + "间隔:" 
				+ "<br>" + String.valueOf(speedSlider.getValue() + "ms" + "<body></html>"));
			}
		});
		getContentPane().add(speedSlider);
		//设置初始死亡率标签
		JLabel deathLabel = new JLabel("<html><body>" + "初始" + "<br>" + "死亡" 
										+ "<br>" + "率:" + deathRate + "<body></html>");
		deathLabel.setBounds(1425, 248, 36, 98);
		getContentPane().add(deathLabel);
		//设置初始死亡率滑条
		JSlider deathRateSlider = new JSlider(SwingConstants.VERTICAL, 0, 100, deathRate);
		deathRateSlider.setInverted(true);
		deathRateSlider.setBounds(1360, 232, 62, 272);
		deathRateSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO 自动生成的方法存根
				deathRate = deathRateSlider.getValue();
				deathLabel.setText("<html><body>" + "初始" + "<br>" + "死亡" 
									+ "<br>" + "率:" + deathRate + "<body></html>");
			}
		});
		getContentPane().add(deathRateSlider);
		
		//设置规模提示标签
		JLabel sizeLabel = new JLabel();
		sizeLabel.setText("设置规模：");
		sizeLabel.setBounds(140, 700, 100, 40);
		this.add(sizeLabel);
		//设置规模滑条
		JSlider sizeSlider = new JSlider(100, 1000, 1000);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setBounds(200, 700, 600, 50);
		//显示当前规模大小标签
		JLabel curSizeLabel = new JLabel();
		curSizeLabel.setText("当前规模：" + size + "×"+ size);
		curSizeLabel.setBounds(400, 720, 600, 50);
		this.add(curSizeLabel);
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO 自动生成的方法存根
				size = sizeSlider.getValue();
				curSizeLabel.setText("当前规模：" + size + "×"+ size);
			}
		});
		getContentPane().add(sizeSlider);
		//设置规模按钮
		JButton btnSetSize = new JButton();
		btnSetSize.setText("设置大小");
		btnSetSize.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnSetSize.setBackground(SystemColor.controlHighlight);
		btnSetSize.setBounds(800, 700, 100, 50);
		btnSetSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//设置地图大小时，停止游戏并清空地图，再根据地图大小重绘游戏地图
				Jtimer.stop();
				//设置大小
				gameMap.setMAP_HEIGH(size);
				gameMap.setMAP_WIDTH(size);
				CELLSIZE = 3 + (1000 - size) / 100;
				sp.getViewport().setViewPosition(new Point(0, 0));
				//调整绘图区大小
				int curSize = size * CELLSIZE;
				g.setPreferredSize(new Dimension(curSize + 5, curSize + 5));
				//调整显示区大小
				int curWidth = Math.min(curSize, 1268);
				int curHeigh = Math.min(curSize, 700);
				sp.setSize(new Dimension(curWidth, curHeigh));
				//重绘地图
				map.clear();
				//g.setWorld(map.getWorld());
				g.setAliveCells(map.getAliveCell());
				repaint();
			}
		});
		getContentPane().add(btnSetSize);
		
		//设置初始值
		gameMap.STATE[][] world = new gameMap.STATE[gameMap.getHeigh()][gameMap.getWidth()];
		gameMap.initWorld(world);
		world[7][32] = gameMap.STATE.ALIVE;
		world[7][31] = gameMap.STATE.ALIVE;
		world[7][30] = gameMap.STATE.ALIVE;
		world[6][32] = gameMap.STATE.ALIVE;
		world[5][31] = gameMap.STATE.ALIVE;
		map.setWorld(world);
		//g.setWorld(world);
		g.setAliveCells(map.getAliveCell());
		
		repaint();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		try {
			for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
				if("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		new GameWindow();
	}

	ActionListener startPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//计时器事件
			//更新游戏地图
			map.nextWorld();
			//绘制游戏地图
			//g.setWorld(map.getWorld());
			g.setAliveCells(map.getAliveCell());
			repaint();
			//地图为空时，暂停计时器
			if (map.isClear()) {
				Jtimer.stop();
			}
		}
	};
}

class GPanel extends JPanel
{
	//private int[][] world = new int[gameMap.getHeigh()][gameMap.getWidth()];
	private boolean paintFrame = true;
	private ArrayList<Integer> aliveCells = new ArrayList<Integer>();
	
	public GPanel(int[][] world) {
		super();
		//this.world = world;
	}

	public GPanel() {
		super();
		this.setBackground(Color.WHITE);
	}
	
	/*public void setWorld(int[][] world) {
		this.world = world;
	}*/
	
	/*public LinkedList<Integer> getAliveCells() {
		return aliveCells;
	}*/

	public void setAliveCells(LinkedList<Integer> aliveCells) {
		this.aliveCells = new ArrayList<Integer>(aliveCells);
	}

	public void shouldPaintFrame() {
		this.paintFrame = true;
	}

	public void paintComponent(Graphics g)
	{
		//绘制背景
		g.setColor(Color.white);
		g.fillRect(2, 2, gameMap.getWidth() * GameWindow.CELLSIZE, 
				gameMap.getHeigh() * GameWindow.CELLSIZE);
		//绘制活细胞
		for (int i = 0; i != this.aliveCells.size(); ++i) {
			g.setColor(Color.black);
			g.fillRect(this.aliveCells.get(i) % gameMap.getWidth() * GameWindow.CELLSIZE + 2, 
					this.aliveCells.get(i) / gameMap.getWidth() * GameWindow.CELLSIZE + 2, 
					GameWindow.CELLSIZE, GameWindow.CELLSIZE);
			/*g.setColor(Color.WHITE);
			g.drawRect(this.aliveCells.get(i) % gameMap.getWidth() * GameWindow.CELLSIZE + 2, 
					this.aliveCells.get(i) / gameMap.getWidth() * GameWindow.CELLSIZE + 2, 
					GameWindow.CELLSIZE, GameWindow.CELLSIZE);*/
		}
	}
}