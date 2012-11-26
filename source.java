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
					makeSolution mySolution = new makeSolution();
					mySolution.getParameters();
				}
			});
			tasks.add(labMenuItem);
			menubar.add(tasks);
			setJMenuBar(menubar);
			setTitle("myLabBuddy - Life science solutions at your fingertips");
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
			
		private class makeSolution{ // at this point only concentration in terms of molarity is accepted
			private String FW;
			private String compound;
			private String molarity;
			private String volume;
			
			public void getParameters() {
				FW = JOptionPane.showInputDialog("Enter the formula weight");
				compound = JOptionPane.showInputDialog("Enter the compound name");
				molarity = JOptionPane.showInputDialog("Enter the molarity"); 
				volume = JOptionPane.showInputDialog("Enter the final volume in liters (e.g. 0.05 for 50 mL)");
				calculateAmount();
			}
			
			public void calculateAmount(){
				float FW_float = Float.parseFloat(FW);
				float molarity_float = Float.parseFloat(molarity);
				float volume_float = Float.parseFloat(volume);
				float grams = FW_float * molarity_float * volume_float;
				JOptionPane.showMessageDialog(null, "Add " + grams + " grams of " + compound + " then bring the solution to a final volume of " + volume + " liter(s).");
			}
		}
}
		
	

