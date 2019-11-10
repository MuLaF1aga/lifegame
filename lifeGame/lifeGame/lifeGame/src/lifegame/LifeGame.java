package lifegame;

import javax.swing.JFrame;

public class LifeGame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map map;
	LifeGame(int rows,int colomns){
		map = new Map(rows,colomns);
		new Thread(map).start();
		this.add(map);
	}
	public static void main(String[]args) {
		LifeGame frame = new LifeGame(40,40);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setTitle("Life Game");
		frame.setVisible(true);
	}
}
