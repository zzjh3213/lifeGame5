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
	ArrayList<Integer> randomArr = new ArrayList<Integer>(); //���ڴ�������������
	private int deathRate = 50;
	private int size = 1000;
	public static int CELLSIZE = 3;
	private gameMap map = new gameMap();
	private GPanel g = new GPanel();
	//���ü�ʱ��
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
				// TODO �Զ����ɵķ������

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO �Զ����ɵķ������
				//��ȡ������������ȼ�ȥ��Ϸ��ͼ��������������ƫ��λ��
		    	//���ٳ��Ե�������Ĵ�С�Ի�ȡ��Ӧ�����������е��±�
		        cellX = (e.getY() - 1) / CELLSIZE;
		        cellY = (e.getX() - 1) / CELLSIZE;
		        map.changeState(cellX, cellY);
		        g.setAliveCells(map.getAliveCell());
		        g.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
		});
		g.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO �Զ����ɵķ������
				int tmpX = (e.getY() - 1) / CELLSIZE;
				int tmpY = (e.getX() - 1) / CELLSIZE;
				//��ֹ�϶��������������δ�Ƴ���Χ����ͬһ��ϸ���ظ��л�״̬
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
				// TODO �Զ����ɵķ������
				
			}
			
		});
		this.add(g);
		JScrollPane sp = new JScrollPane(g, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBounds(2, 2, 1268, 700);
		this.add(sp);
		this.setSize(1500, 800);
		this.setTitle("������Ϸ");
		this.setVisible(true);
		//���ü�ʱ���¼�
		Jtimer.addActionListener(startPerformer);
		//���ÿ�ʼ��ť
		JButton btnStart = new JButton();
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				Jtimer.start();
			}
		});
		btnStart.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnStart.setText("��ʼ");
		btnStart.setBounds(1386, 0, 100, 50);
		btnStart.setBackground(SystemColor.controlHighlight);
		getContentPane().add(btnStart);
		//������ͣ��ť
		JButton btnStop = new JButton();
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				Jtimer.stop();
			}
		});
		btnStop.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnStop.setText("��ͣ");
		btnStop.setBounds(1386, 48, 100, 50);
		btnStop.setBackground(SystemColor.controlHighlight);
		getContentPane().add(btnStop);
		//������հ�ť
		JButton btnClear = new JButton();
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				map.clear(); //��յ�ͼ
				//���ƻ���
				//g.setWorld(map.getWorld());
				g.setAliveCells(map.getAliveCell());
				Jtimer.stop();
				repaint();
			}
		});
		btnClear.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnClear.setText("���");
		btnClear.setBounds(1386, 96, 100, 50);
		btnClear.setBackground(SystemColor.controlHighlight);
		getContentPane().add(btnClear);
		//���������ť
		JButton btnRandom = new JButton();
		btnRandom.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnRandom.setText("�������");
		btnRandom.setBackground(SystemColor.controlHighlight);
		btnRandom.setBounds(1386, 144, 100, 50);
		btnRandom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//����������֣������ݳ�ʼ����������������
				Jtimer.stop();
				randomArr.clear();
				//���������
				for (int i = 0; i < gameMap.getHeigh() * gameMap.getWidth(); ++i) {
					randomArr.add(i, (int)(Math.random() * 100));
				}
				gameMap.STATE[][] tmp = new gameMap.STATE[gameMap.getHeigh()][gameMap.getWidth()];
				for (int i = 0; i < randomArr.size(); ++i) {
					//���ݳ�ʼ��������������Ӧλ�õ�ϸ������״̬
					tmp[i / gameMap.getWidth()][i % gameMap.getWidth()] 
							= randomArr.get(i) >= deathRate 
							? gameMap.STATE.ALIVE 
							: gameMap.STATE.DIE;
				}
				//�������ɺõĵ�ͼ
				map.setWorld(tmp);
				//g.setWorld(map.getWorld());
				g.setAliveCells(map.getAliveCell());
				repaint();
			}
		});
		getContentPane().add(btnRandom);
		
		//���õ����ٶȻ���
		JSlider speedSlider = new JSlider(SwingConstants.VERTICAL, 10, 500, timerDelay);
		speedSlider.setInverted(true);
		speedSlider.setBounds(1311, 232, 56, 272);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setMajorTickSpacing(490);
		speedSlider.setMinorTickSpacing(490);
		Dictionary<Integer, Component> label = new Hashtable<Integer, Component>();
		label.put(10, new JLabel("��"));
		label.put(500, new JLabel("��"));
		speedSlider.setLabelTable(label);
		//������ʾ���ʵı�ǩ
		JLabel speedLabel = new JLabel("<html><body>" + "��֡" + "<br>" + "���:" 
										+ "<br>" + timerDelay + "ms" + "<body></html>");
		speedLabel.setBounds(1278, 324, 36, 50);
		getContentPane().add(speedLabel);
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				//�ı��ʱ�����
				Jtimer.setDelay(speedSlider.getValue());
				speedLabel.setText("<html><body>" + "��֡" + "<br>" + "���:" 
				+ "<br>" + String.valueOf(speedSlider.getValue() + "ms" + "<body></html>"));
			}
		});
		getContentPane().add(speedSlider);
		//���ó�ʼ�����ʱ�ǩ
		JLabel deathLabel = new JLabel("<html><body>" + "��ʼ" + "<br>" + "����" 
										+ "<br>" + "��:" + deathRate + "<body></html>");
		deathLabel.setBounds(1425, 248, 36, 98);
		getContentPane().add(deathLabel);
		//���ó�ʼ�����ʻ���
		JSlider deathRateSlider = new JSlider(SwingConstants.VERTICAL, 0, 100, deathRate);
		deathRateSlider.setInverted(true);
		deathRateSlider.setBounds(1360, 232, 62, 272);
		deathRateSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO �Զ����ɵķ������
				deathRate = deathRateSlider.getValue();
				deathLabel.setText("<html><body>" + "��ʼ" + "<br>" + "����" 
									+ "<br>" + "��:" + deathRate + "<body></html>");
			}
		});
		getContentPane().add(deathRateSlider);
		
		//���ù�ģ��ʾ��ǩ
		JLabel sizeLabel = new JLabel();
		sizeLabel.setText("���ù�ģ��");
		sizeLabel.setBounds(140, 700, 100, 40);
		this.add(sizeLabel);
		//���ù�ģ����
		JSlider sizeSlider = new JSlider(100, 1000, 1000);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setBounds(200, 700, 600, 50);
		//��ʾ��ǰ��ģ��С��ǩ
		JLabel curSizeLabel = new JLabel();
		curSizeLabel.setText("��ǰ��ģ��" + size + "��"+ size);
		curSizeLabel.setBounds(400, 720, 600, 50);
		this.add(curSizeLabel);
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO �Զ����ɵķ������
				size = sizeSlider.getValue();
				curSizeLabel.setText("��ǰ��ģ��" + size + "��"+ size);
			}
		});
		getContentPane().add(sizeSlider);
		//���ù�ģ��ť
		JButton btnSetSize = new JButton();
		btnSetSize.setText("���ô�С");
		btnSetSize.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		btnSetSize.setBackground(SystemColor.controlHighlight);
		btnSetSize.setBounds(800, 700, 100, 50);
		btnSetSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//���õ�ͼ��Сʱ��ֹͣ��Ϸ����յ�ͼ���ٸ��ݵ�ͼ��С�ػ���Ϸ��ͼ
				Jtimer.stop();
				//���ô�С
				gameMap.setMAP_HEIGH(size);
				gameMap.setMAP_WIDTH(size);
				CELLSIZE = 3 + (1000 - size) / 100;
				sp.getViewport().setViewPosition(new Point(0, 0));
				//������ͼ����С
				int curSize = size * CELLSIZE;
				g.setPreferredSize(new Dimension(curSize + 5, curSize + 5));
				//������ʾ����С
				int curWidth = Math.min(curSize, 1268);
				int curHeigh = Math.min(curSize, 700);
				sp.setSize(new Dimension(curWidth, curHeigh));
				//�ػ��ͼ
				map.clear();
				//g.setWorld(map.getWorld());
				g.setAliveCells(map.getAliveCell());
				repaint();
			}
		});
		getContentPane().add(btnSetSize);
		
		//���ó�ʼֵ
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
			//��ʱ���¼�
			//������Ϸ��ͼ
			map.nextWorld();
			//������Ϸ��ͼ
			//g.setWorld(map.getWorld());
			g.setAliveCells(map.getAliveCell());
			repaint();
			//��ͼΪ��ʱ����ͣ��ʱ��
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
		//���Ʊ���
		g.setColor(Color.white);
		g.fillRect(2, 2, gameMap.getWidth() * GameWindow.CELLSIZE, 
				gameMap.getHeigh() * GameWindow.CELLSIZE);
		//���ƻ�ϸ��
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