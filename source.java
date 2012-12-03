import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.Math;

public class myLabBuddy extends JFrame{
	BufferedImage background;
	public myLabBuddy() {
		initMenu();
	}
	
	private static final long serialVersionUID = 1L;

	public final void initMenu() {
			JMenuBar menubar = new JMenuBar(); // www.clker.com/clipart-flask-icon.html
			//ImageIcon flask = new ImageIcon(getClass().getResource("/resources/flask_32x32.jpg"));
			ImageIcon protocol = new ImageIcon(getClass().getResource("/resources/protocolAqua_32x32.jpg"));
			ImageIcon flask_M = new ImageIcon(getClass().getResource("/resources/flask_M_32x32.jpg"));
			ImageIcon flask_pct = new ImageIcon(getClass().getResource("/resources/flask_pct_32x32.jpg"));
			ImageIcon buff = new ImageIcon(getClass().getResource("/resources/buff_32x32.jpg"));
			ImageIcon exit = new ImageIcon(getClass().getResource("/resources/exit_32x32.jpg"));
			
			JMenu tasks = new JMenu("Tasks");
			tasks.setMnemonic(KeyEvent.VK_T);
			
			JMenuItem menuTaskMolarSolution = new JMenuItem("Make Solution - by molarity", (Icon) flask_M);
			menuTaskMolarSolution.setMnemonic(KeyEvent.VK_S);
			menuTaskMolarSolution.setToolTipText("Calculate dry weight to add for x molar solution");
			menuTaskMolarSolution.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					MakeMolarSolution mySolution = new MakeMolarSolution("water", 1, 18, 55.55f); // inside joke - water is 55.55 molar: (1000 / 18).
					mySolution.getMolarParameters();
				}
			});
			tasks.add(menuTaskMolarSolution);
			
			JMenuItem menuTaskPercentSolution = new JMenuItem("Make Solution - by percent", (Icon) flask_pct);
			menuTaskPercentSolution.setMnemonic(KeyEvent.VK_C);
			menuTaskPercentSolution.setToolTipText("Make a percent solution by weight or volume.");
			menuTaskPercentSolution.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					MakePercentSolution mySolution = new MakePercentSolution("water", 1, 1, 1, 1, true);
					mySolution.getPercentParameters();
				}
			});
			tasks.add(menuTaskPercentSolution);

			JMenuItem menuTaskProtocol = new JMenuItem("Protocol - read a protocol", (Icon) protocol);
			menuTaskProtocol.setMnemonic(KeyEvent.VK_P);
			menuTaskProtocol.setToolTipText("Read a protocol");
			menuTaskProtocol.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Protocol myProtocol = new Protocol();
					myProtocol.selectProtocol();
				}
			});
			tasks.add(menuTaskProtocol);
			
			JMenuItem menuTaskFormulateBuffer = new JMenuItem("Formulate a Buffer", (Icon) buff);
			menuTaskFormulateBuffer.setMnemonic(KeyEvent.VK_B);
			menuTaskFormulateBuffer.setToolTipText("Formulate a buffer by entering pKa and [buffer].");
			menuTaskFormulateBuffer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					FormulateBuffer myBuffer = new FormulateBuffer();
					myBuffer.getParameters();
					myBuffer.calculateConcentration();
				}
			});
			tasks.add(menuTaskFormulateBuffer);
			
			JMenuItem menuTaskExit = new JMenuItem("Exit", (Icon) exit);
			menuTaskExit.setMnemonic(KeyEvent.VK_X);
			menuTaskExit.setToolTipText("Exit this application - Happy Trails!.");
			menuTaskExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					System.exit(0); // Happy Trails!
				}
			});
			tasks.add(menuTaskExit);
			
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
			
		public abstract class MakeAbstractSolution{
			float volume;
			String compound;
			
			public MakeAbstractSolution(String entered_compound, float entered_volume_float) {
				compound = entered_compound;
				volume = entered_volume_float;
			}
			
			public void getAbstractSolutionParameters(){
				compound = JOptionPane.showInputDialog("Enter the compound name");
				String volume_string = JOptionPane.showInputDialog("Enter the final volume in liters (e.g. 0.05 for 50 mL)");
				volume = Float.parseFloat(volume_string);
			}
			
			public abstract void calculateAmount();
		}
		
		public class MakeMolarSolution extends MakeAbstractSolution{
			float FW;
			float molarity;			
			public MakeMolarSolution(	
					String compound,
					float vol,
					float FW,
					float mol
					) {
						super(compound, vol);
					}
			public void getMolarParameters(){
				String FW_string;
				String molarity_string;		
				FW_string = JOptionPane.showInputDialog("Enter the formula weight");
				FW = Float.parseFloat(FW_string);
				molarity_string = JOptionPane.showInputDialog("Enter the molarity");
				molarity = Float.parseFloat(molarity_string);
				this.getAbstractSolutionParameters();
				calculateAmount();
			}
			
			public void calculateAmount(){
				float grams = FW * molarity * volume;
				ImageIcon flask_M = new ImageIcon(getClass().getResource("/resources/flask_M_32x32.jpg"));
				JOptionPane.showMessageDialog(null, "Add " + grams + " grams of " + compound + " then bring the solution to a final volume of " + volume + " liter(s).", null, JOptionPane.INFORMATION_MESSAGE, flask_M);
			}
		}
		
		public class MakePercentSolution extends MakeAbstractSolution {
			float percent;
			boolean byWeight;
			public MakePercentSolution(
					String compound,
					float vol,
					float pct,
					float gm, // won't be used for a by percent by volume solution
					float solute_vol, // won't be used for a percent by weight solution - NTBCW volume which is the total volume
					boolean byWght
					) {
				super(compound, vol);
				percent = pct;
				volume = vol;
				byWeight = byWght;
			}
			
			public void getPercentParameters() {
				String percent_string = JOptionPane.showInputDialog("Enter the Percent of the solute");
				percent = Float.parseFloat(percent_string);
				String byWeight_string = JOptionPane.showInputDialog("Enter 'y' if by weight, 'n' if by volume.");
				this.getAbstractSolutionParameters();
				char response = byWeight_string.charAt(0);
				if ((response == 'Y') || (response == 'y'))
					byWeight = true;
				else if ((response == 'N') || (response == 'n'))
					byWeight = false;
				else
					byWeight_string = JOptionPane.showInputDialog("Enter only 'y' or 'n'.\nEnter 'y' if by weight, 'n' if by volume. ");
				calculateAmount();
			}
		
			public void calculateAmount() {
				ImageIcon flask_pct = new ImageIcon(getClass().getResource("/resources/flask_pct_32x32.jpg"));
				if (byWeight){
					float grams = percent * (volume * 10f); // 1g per 100 ml = 1% w/v. Volume is in liters.
					JOptionPane.showMessageDialog(null, "Add " + grams + " grams of " + compound + " then bring the solution to a final volume of " + volume + " liter(s).", null,JOptionPane.INFORMATION_MESSAGE, flask_pct);
				}
					
				else {
					float solute_volume = percent * (volume / 100f) ; // 0.01L per 1L = 1% v/v. Volume is in liters
					JOptionPane.showMessageDialog(null, "Add " + solute_volume + " liters of " + compound + " then add " + (volume - solute_volume) + " liter(s) of water.", null,JOptionPane.INFORMATION_MESSAGE, flask_pct);
				}
			}
		}
		
		public class FormulateBuffer {
			double pKa;
			double pH;
			double ratio;
			float concentration; // concentration of conjugate acid + conjugate base
			float conjAcid_concentration;
			float conjBase_concentration;
			public void getParameters() {
				String pKa_string = JOptionPane.showInputDialog("Enter the pKa of the conjugate acid.");
					pKa = Double.parseDouble(pKa_string);
				String pH_string = JOptionPane.showInputDialog("Enter the desired buffer pH");
					pH = Double.parseDouble(pH_string);
				String concentration_string = JOptionPane.showInputDialog("Enter the mM concentration of the buffer solution where [buffer concentration] = [conjugate acid] + [conjugate base].)");					concentration = Float.parseFloat(concentration_string);
			}
			
			public void calculateConcentration() {
				ratio = Math.pow(10, pH - pKa); // pH is a log10 scale
				conjBase_concentration = (float)( (ratio / (ratio + 1) ) * concentration );
				conjAcid_concentration = concentration - conjBase_concentration;
				ImageIcon buff_icon = new ImageIcon(getClass().getResource("/resources/buff_32x32.jpg"));
				JOptionPane.showMessageDialog(null, "The final concentration of the conjugate acid will be " + conjAcid_concentration + "mM.\n The final concentration of the conjugate base will be " + conjBase_concentration + "mM.\n The total buffer concentration will be " + concentration + "mM.", null, JOptionPane.INFORMATION_MESSAGE, buff_icon);
			}
		}
	
		public class Protocol extends JFrame implements ActionListener{ // ADD A TITLEBAR TITLE - ADD ICON FOR EACH PROTOCOL - REPLACE "MAKE SOLUTION" WITH "SELECT A PROTOCOL" AS THE DEFAULT TEXT.
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
				      }
				    });
					
					frameProtocol.add(comboProtocol);
					frameProtocol.setSize(300, 100);
				    frameProtocol.setVisible(true);
			}

			@Override public void actionPerformed(ActionEvent arg0) { ; }		
		}
}