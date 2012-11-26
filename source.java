import javax.swing.*;
import java.awt.event.ActionEvent;
// 40 minutes
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public static void main(final String[] args) {
	menu();	
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;



public class myLabBuddy extends JFrame{
	
	public myLabBuddy() {
		initMenu();
	}
	private static final long serialVersionUID = 1L;

	public final void initMenu() {
			JMenuBar menubar = new JMenuBar(); // www.clker.com/clipart-flask-icon.html
			ImageIcon flask = new ImageIcon(getClass().getResource("/resources/flask_32x32.jpg"));
			//BufferedImage menuFlask = new BufferedImage(32, 32, BufferedImage.TYPE_INT_BGR);
			
			JMenu tasks = new JMenu("Tasks");
			tasks.setMnemonic(KeyEvent.VK_T);
			
			JMenuItem labMenuItem = new JMenuItem("Make Solution - by molarity or percent", (Icon) flask);
			labMenuItem.setMnemonic(KeyEvent.VK_S);
			labMenuItem.setToolTipText("Calculate dry weight to add for x molar solution");
			labMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					makeAbstractSolution();
				}
			});
			tasks.add(labMenuItem);
			menubar.add(tasks);
			setJMenuBar(menubar);
			setTitle("myLabBuddy - Solutions at your fingertips");
			setSize(700,400);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
			
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                myLabBuddy MenuBuddy = new myLabBuddy();
	                MenuBuddy.setVisible(true);
	            }
	        });
		}
			
		private static void makeAbstractSolution() {
			//
		}
	
}
		
	

