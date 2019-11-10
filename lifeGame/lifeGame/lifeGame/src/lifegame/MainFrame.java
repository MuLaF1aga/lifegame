package lifegame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Event;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Lock;
import javax.swing.SwingConstants;


public class MainFrame extends JFrame{
	private final static Map map = new Map(80,80);
	private static JPanel contentPane;
	static JFrame frame=new MainFrame();
	static JTextField textField;
	Thread thread=new Thread(map);
	private int init=1;
	private boolean mark = true;
	
	public static void main(String[] args) {
		frame.setVisible(true);
		contentPane.add(map);
		
	}
	
	public MainFrame() {
		setTitle("LifeGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1021, 855);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		
		map.setBounds(0, 10, 807, 809);
		contentPane.add(map);
		
		JButton btnNewButton = new JButton("start/pause");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				map.time=Integer.parseInt(textField.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Wrong Input£¡", "error£¡", JOptionPane.ERROR_MESSAGE);
					
					MainFrame.frame.setVisible(false);
					MainFrame.frame.dispose();
					return;
				}
				if(init==1) {
				thread.start();
				init=0;
				}
				else {
					if (mark==true)
					{
						if(!(map.suspend=true)) {
							synchronized (map.LOCK) {
								map.LOCK.notifyAll();
							}
						}
						map.suspend=true;
						mark=false;
					}
					else
					{
						if(!(map.suspend=false)) {
							synchronized (map.LOCK) {
								map.LOCK.notifyAll();
							}
						}
						map.suspend=false;
						mark=true;
					}
				}
				
			}
		});
		btnNewButton.setBounds(821, 98, 182, 27);
		contentPane.add(btnNewButton);
		
		JLabel lblSetDefaultRefresh = new JLabel("default refresh time(ms)");
		lblSetDefaultRefresh.setBounds(821, 13, 194, 18);
		contentPane.add(lblSetDefaultRefresh);
		
		textField = new JTextField();
		textField.setBounds(821, 50, 132, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnRandomSet = new JButton("random set");
		btnRandomSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.randominit();
			}
		});
		btnRandomSet.setBounds(821, 512, 113, 27);
		contentPane.add(btnRandomSet);
		
		JButton btnNewButton_1 = new JButton("design set");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.designinit();
			}
		});
		btnNewButton_1.setBounds(821, 577, 113, 27);
		contentPane.add(btnNewButton_1);
		
		JButton btnInsertSet = new JButton("save as");
		btnInsertSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.saveas();
			}
		});
		btnInsertSet.setBounds(821, 641, 113, 27);
		contentPane.add(btnInsertSet);
		
		JButton btnNewButton_2 = new JButton("clear");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.clear();
			}
		});
		btnNewButton_2.setBounds(821, 201, 182, 27);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("\u221A");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.time=Integer.parseInt(textField.getText());
				JOptionPane.showMessageDialog(null, "refresh time set£¡");
			}
		});
		btnNewButton_3.setBounds(959, 50, 54, 24);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("next");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.next();
			}
		});
		btnNewButton_4.setBounds(821, 152, 180, 27);
		contentPane.add(btnNewButton_4);
		
		
		
	}
}
