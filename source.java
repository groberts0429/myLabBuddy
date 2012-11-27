import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class myLabBuddy extends JFrame{
	BufferedImage background;
	public myLabBuddy() {
		initMenu();
	}
	
	private static final long serialVersionUID = 1L;

	public final void initMenu() {
			JMenuBar menubar = new JMenuBar(); // www.clker.com/clipart-flask-icon.html
			ImageIcon flask = new ImageIcon(getClass().getResource("/resources/flask_32x32.jpg"));
			ImageIcon protocol = new ImageIcon(getClass().getResource("/resources/protocolAqua_32x32.jpg"));
			//BufferedImage menuFlask = new BufferedImage(32, 32, BufferedImage.TYPE_INT_BGR);
			
			JMenu tasks = new JMenu("Tasks");
			tasks.setMnemonic(KeyEvent.VK_T);
			
			// tasks.menuSolution
			JMenuItem menuTaskSolution = new JMenuItem("Make Solution - by molarity or percent", (Icon) flask);
			menuTaskSolution.setMnemonic(KeyEvent.VK_S);
			menuTaskSolution.setToolTipText("Calculate dry weight to add for x molar solution");
			menuTaskSolution.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					makeSolution mySolution = new makeSolution();
					mySolution.getParameters();
				}
			});
			tasks.add(menuTaskSolution);
			
			// tasks.Protocol
			JMenuItem menuTaskProtocol = new JMenuItem("Protocol - read a protocol", (Icon) protocol);
			menuTaskProtocol.setMnemonic(KeyEvent.VK_P);
			menuTaskProtocol.setToolTipText("Read a protocol");
			menuTaskProtocol.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Protocol myProtocol = new Protocol();
					myProtocol.selectProtocol();
					//myProtocol.???
				}
			});
			tasks.add(menuTaskProtocol);
			
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
				ImageIcon flask = new ImageIcon(getClass().getResource("/resources/flask_32x32.jpg"));
				JOptionPane.showMessageDialog(null, "Add " + grams + " grams of " + compound + " then bring the solution to a final volume of " + volume + " liter(s).", null,JOptionPane.INFORMATION_MESSAGE, flask);
			}
		}
		
		public class Protocol extends JFrame implements ActionListener{
			public void selectProtocol() {
					final String protocols[][] = {
							{"Make solution", "If making a solution from a solid:\n1) Place the beaker on a stirrer and add an appropriately sized stir-bar.\n2) slowly dissolve the indicated amount in a beaker containing about one-half of the intended volume.\n3) Observe any additional solution tips that may be provided."}, 
							{"pH a solution", "1) Wash probe with distilled water, dry probe.\n2) Calibrate the probe with a standard above and below the target pH."}, 
							{"Sonicate tissue", "1) Clean the probe with distilled water and dab dry with lint-free wipe.\n2) Wear >20 dB reduction earmuffs.\n3) place probe in tissue/buffer mix and pulse - be sure not to allow the solution to froth!"} 
					};
					
					String options[] =  {"Make solution", "pH a solution", "Sonicate tissue"};
						
					final JFrame frameProtocol = new JFrame();
					JComboBox comboProtocol = new JComboBox(options);


					comboProtocol.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	JComboBox cp = (JComboBox)e.getSource();
				    	int option = (int)cp.getSelectedIndex();
				    	JOptionPane.showMessageDialog(null, protocols[option][1]);
				    	System.out.println("Protocol worked!");
				      }
				    });
					
					frameProtocol.add(comboProtocol);
					frameProtocol.setSize(300, 100);
				    frameProtocol.setVisible(true);
			}

			@Override
			public void actionPerformed(ActionEvent arg0) { // TODO Auto-generated method stub
				
			}			
		}
}