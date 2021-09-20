import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainPage{

	private JFrame frame;
	private JLabel lblTotalPerson, lblRandomNumber;

	private ArrayList<JPanel> panelPersons = new ArrayList<>();
	private ArrayList<JCheckBox> chckbxPersons = new ArrayList<>();
	private ArrayList<JLabel> lblPersonName = new ArrayList<>();
	private ArrayList<JLabel> lblPersonCounts = new ArrayList<>();
	
	private int count_greenColorPanels;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frame.setUndecorated(true);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.decode("#333F50"));
		frame.setBounds(100, 100, 1024, 605);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addMouseMotionListener(new MouseMotionListener() {
			private int tx, ty;

			@Override
			public void mouseMoved(MouseEvent e) {tx= e.getX();ty=e.getY();}
			
			@Override
			public void mouseDragged(MouseEvent e) {frame.setLocation(e.getXOnScreen() -tx, e.getYOnScreen() -ty);}
		});

		initWinBtns();
		initGenerator();
		initAllPersons();
		initSetCount();

	}
	
	
	/*
	 * initialized window buttons
	 * 1.close button for exit window
	 * 2.Minimize button for minimizing window
	 */
	private void initWinBtns() {

		JButton btnClose = new JButton("");
		try {
			Image img = ImageIO.read(getClass().getResource("ic_close.png"));
			btnClose.setIcon(new ImageIcon(img));
		} catch (Exception ex) {System.out.println(ex);		}
		btnClose.addActionListener( e -> System.exit(0));
		btnClose.setForeground(Color.blue);
		btnClose.setBackground(Color.decode("#333F50"));
		btnClose.setBounds(1004, 0, 20, 20);
		frame.getContentPane().add(btnClose);

		JButton btnMinimize = new JButton("");
		try {
			Image img = ImageIO.read(getClass().getResource("ic_minimize.png"));
			btnMinimize.setIcon(new ImageIcon(img));
		} catch (Exception ex) {System.out.println(ex);		}
		btnMinimize.addActionListener( e -> frame.setState(Frame.ICONIFIED));
		btnMinimize.setForeground(Color.BLUE);
		btnMinimize.setBackground(Color.decode("#333F50"));
		btnMinimize.setBounds(984, 0, 20, 20);
		frame.getContentPane().add(btnMinimize);

	}

	/*
	 * initialized random number generator button
	 * initialized random number label
	 * 
	 * 1. call highlightPerson function for highlighting the person 
	 *    when random number label text changed 
	 * 2. Check random number label and if it is not zero then 
	 *    generating new random number 
	 */
	private  void  initGenerator() {
		lblRandomNumber = new JLabel("Random Number");
		lblRandomNumber.setOpaque(true);
		lblRandomNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblRandomNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRandomNumber.setForeground(Color.WHITE);
		lblRandomNumber.setBackground(Color.decode("#8497B0"));
		lblRandomNumber.setBounds(390, 40, 134, 33);
		PropertyChangeListener l = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				highlightPerson(lblRandomNumber.getText());
			}
		};
		lblRandomNumber.addPropertyChangeListener("text", l);
		frame.getContentPane().add(lblRandomNumber);

		JButton btnRGenerator = new JButton("Generate");
		btnRGenerator.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRGenerator.setBounds(534, 42, 100, 30);

		btnRGenerator.addActionListener(e -> {
		
			if (Integer.parseInt(lblTotalPerson.getText()) != 0 ){
				if (count_greenColorPanels != 2) {
					int low = 1;
					int high = Integer.parseInt(lblTotalPerson.getText()) ;
					int randomBefore = Integer.valueOf(lblRandomNumber.getText()); 
					while(true) {
						int randomAfter = (new Random().nextInt(high-low+1) + low);
						if(randomBefore != (randomAfter)) {
							btnRGenerator.setEnabled(true);
							lblRandomNumber.setText(String.valueOf(randomAfter));
							count_greenColorPanels++;
							return;
						}
						System.out.println(randomAfter);
						btnRGenerator.setEnabled(false);
					}
				}else {
					JOptionPane.showMessageDialog(frame,
							"Pair is selected, now click set counts button",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(frame,
						"First set persons' counts by click set counts button",
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		frame.getContentPane().add(btnRGenerator);
	}

		private void highlightPerson(String randNum) {

		for(JLabel c : lblPersonCounts) {
			if( c.getText().equals(randNum)) {
				panelPersons.get(lblPersonCounts.indexOf(c)).setBackground(Color.GREEN);
				chckbxPersons.get(lblPersonCounts.indexOf(c)).setBackground(Color.GREEN);
				chckbxPersons.get(lblPersonCounts.indexOf(c)).setSelected(true);
				lblPersonName.get(lblPersonCounts.indexOf(c)).setBackground(Color.GREEN);
				lblPersonCounts.get(lblPersonCounts.indexOf(c)).setBackground(Color.GREEN);
			}
		}

	}

	private void initSetCount() {

		lblTotalPerson = new JLabel("00");
		lblTotalPerson.setOpaque(true);
		lblTotalPerson.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalPerson.setForeground(Color.WHITE);
		lblTotalPerson.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTotalPerson.setBackground(new Color(132, 151, 176));
		lblTotalPerson.setBounds(271, 100, 30, 30);
		frame.getContentPane().add(lblTotalPerson);

		JButton btnSetCounts = new JButton("Set Counts");
		btnSetCounts.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSetCounts.setBounds(127, 100, 134, 30);
		btnSetCounts.addActionListener(e ->{
			lblTotalPerson.setText(String.valueOf(handlePersons()));
			lblRandomNumber.setText("00");
			count_greenColorPanels=0;
		});
		frame.getContentPane().add(btnSetCounts);
		
		JLabel lblNewLabel = new JLabel("Developed by Rizni Mohamed");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(830, 585, 187, 15);
		frame.getContentPane().add(lblNewLabel);

	}

		/*
		 * 1. hide checked box
		 * 2. set counts for all persons
		 */
		private int handlePersons() {
		int i = 0;
		for(JCheckBox cb : chckbxPersons) {
			if(cb.isSelected()) {
				panelPersons.get(chckbxPersons.indexOf(cb)).setVisible(false);
				chckbxPersons.get(chckbxPersons.indexOf(cb)).setVisible(false);
				lblPersonName.get(chckbxPersons.indexOf(cb)).setVisible(false);
				lblPersonCounts.get(chckbxPersons.indexOf(cb)).setVisible(false);
			}else {
				lblPersonCounts.get(chckbxPersons.indexOf(cb)).setText(String.valueOf(++i));
			}
		}
		return i;
	}
	
	private void initAllPersons() {
		int x = 127;
		int y = 150;
		int w = 150;
		int h = 33;
		int p = 0;
		for(int j=0 ; j < 5 ; j++) {
			for(int i=0; i<10 ; i++) {
				p++;
				initPerson("Person"+p,x,y,w,h);
				y = (y+h+10);
			}
			x = (x+w+10);
			y = 150;
		}
	}

		private void initPerson(String name, int x, int y, int w, int h) {
		JPanel Person1 = new JPanel();
		Person1.setBackground(Color.decode("#8497B0"));
		Person1.setBounds(x,y,w,h);
		frame.getContentPane().add(Person1);
		Person1.setLayout(null);
		panelPersons.add(Person1);

		JCheckBox chckbxP1 = new JCheckBox("");
		chckbxP1.setBounds(7, 5, 21, 21);
		Person1.add(chckbxP1);
		chckbxP1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxP1.setForeground(Color.WHITE);
		chckbxP1.setBackground(Color.decode("#8497B0"));
		chckbxPersons.add(chckbxP1);

		JLabel lblPN1 = new JLabel(name);
		lblPN1.setBounds(33, 5, 70, 20);
		Person1.add(lblPN1);
		lblPN1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPN1.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				lblPN1.setText( JOptionPane.showInputDialog("Enter your name: "));
			}
		});
		lblPN1.setForeground(new Color(255, 255, 255));
		lblPN1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPN1.setBackground(Color.decode("#8497B0"));
		lblPN1.setOpaque(true);
		lblPersonName.add(lblPN1);

		JLabel lblPC1 = new JLabel("00");
		lblPC1.setBounds(120, 5, 18, 20);
		Person1.add(lblPC1);
		lblPC1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPC1.setForeground(Color.WHITE);
		lblPC1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPC1.setBackground(Color.decode("#8497B0"));
		lblPC1.setOpaque(true);
		lblPersonCounts.add(lblPC1);
	}

}
