package lifegame;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

import javax.management.openmbean.TabularType;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.tracing.dtrace.ProviderAttributes;

import jdk.internal.jfr.events.FileWriteEvent;

public class Map extends JPanel implements Runnable { 
	public static final int alive = 1;
	public static final int dead = 0;
	private final int rows;
	private final int columns;
	int [][] thisWorld;
	int [][] nextWorld;
	boolean suspend = false;
	static String LOCK = "LOCK";
	static int time;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Map(int rows, int columns) {
		// TODO 自动生成的构造函数存根
		this.rows=rows;
		this.columns=columns;
		thisWorld = new int[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				thisWorld[i][j]=dead;
			}
		}
		nextWorld = new int[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				nextWorld[i][j]=dead;
			}
		}
	}
	public void randominit() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				thisWorld[i][j]=dead;
			}
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(Math.random()<0.1)
				thisWorld[i][j]=alive;
			}
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				nextWorld[i][j]=thisWorld[i][j];
			}
		}
		repaint();
	}
	
	public void designinit() {
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==e.BUTTON1) {
					if(thisWorld[(int)e.getY()/10][(int)e.getX()/10]==alive)
						thisWorld[(int)e.getY()/10][(int)e.getX()/10]=dead;
					else
						thisWorld[(int)e.getY()/10][(int)e.getX()/10]=alive;
				}
					
				for(int i=0;i<rows;i++) {
					for(int j=0;j<columns;j++) {
						nextWorld[i][j]=thisWorld[i][j];
					}
				}
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
	}
	
	public void run() {
		// TODO 自动生成的方法存根
		while(true) {
			
				repaint();
				changeWorld();
				transfer();
				try {
					Thread.sleep(time);
					
			            synchronized (LOCK) {
			                if (suspend) {
			                    try {
			                        LOCK.wait();
			                    } catch (InterruptedException e) {
			                    	e.printStackTrace();
			                    }
			                }
			            }
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(thisWorld[i][j]==alive) 
				{
					g.fillRect(j*10,i*10,10,10);
				}
				else 
				{
					g.drawRect(j*10, i*10, 10, 10);
				}
			}
		}
	}
	public void changeWorld() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				changeUnit(i,j);//
			}
		}
	}
	public void transfer() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				thisWorld[i][j]=nextWorld[i][j];
			}
		}
	}
	public boolean valid(int i,int j) {
		if(i>=0&&i<rows&&j>=0&&j<columns) {
			return true;
		}
		else {
			return false;
		}
	}
	public void changeUnit(int i,int j) {
		int count = 0;
		if(valid(i-1,j-1)&&thisWorld[i-1][j-1]==alive) {
			count++;
		}
		if(valid(i-1,j)&&thisWorld[i-1][j]==alive) {
			count++;
		}
		if(valid(i-1,j+1)&&thisWorld[i-1][j+1]==alive) {
			count++;
		}
		if(valid(i,j-1)&&thisWorld[i][j-1]==alive) {
			count++;
		}
		if(valid(i,j+1)&&thisWorld[i][j+1]==alive) {
			count++;
		}
		if(valid(i+1,j-1)&&thisWorld[i+1][j-1]==alive) {
			count++;
		}
		if(valid(i+1,j)&&thisWorld[i+1][j]==alive) {
			count++;
		}
		if(valid(i+1,j+1)&&thisWorld[i+1][j+1]==alive) {
			count++;
		}
		switch(count) {
		case 2:
			nextWorld[i][j]=thisWorld[i][j];
			break;
		case 3:
			nextWorld[i][j]=alive;
			break;
		default:
			nextWorld[i][j]=dead;
		}
	}
	private void sleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
   public void clear() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				thisWorld[i][j]=dead;
			}
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				nextWorld[i][j]=dead;
			}
		}
		repaint();
   }
   
   public void next() {
	   changeWorld();
	   transfer();
	   repaint();
   }
   
   public void saveas() {
	   
	   try {
		   String fname = JOptionPane.showInputDialog(null,"input the file name",null,JOptionPane.PLAIN_MESSAGE);
			Writer targetfile=new FileWriter(fname);
			
			for(int i=0;i<rows;i++) {
				for(int j=0;j<columns;j++) {
					targetfile.append(thisWorld[i][j]+"");
				}
				targetfile.append("\n");
			}
			
			targetfile.close();
			JOptionPane.showMessageDialog(null, "save as " + fname);
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
		
		
	   
	   
   }
}
