import java.awt.Cursor;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Home extends JFrame {
	/**
	 * Authors: Daniel Hosseini, Lídia Nyman, Odzaya Batsaikhan.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel MainPanel, TopNavPanel, NavigationPanel,
			SearchPanel;
	private JTextField SearchField;
	private ResultPanel FinalPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager
							.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					// If Nimbus is not available, you can set the GUI to
					// another look and feel.
					
					/**
					* If Nimbus is not available, you can set the GUI to
					* another look and feel.
					*/
				}

				try {

					Home frame = new Home();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {

		/**
		 * This is our interface code and basically its just creating all the Panels, Buttons, labels and so on.
		 */
		
		setMinimumSize(new Dimension(1200, 600));
		setTitle("StockIn");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		TopNavPanel = new JPanel();
		TopNavPanel.setBounds(0, 0, 1194, 54);
		getContentPane().add(TopNavPanel);
		TopNavPanel.setLayout(null);

		JLabel StockInLabel = new JLabel("StockIn");
		StockInLabel.setForeground(Color.white);
		StockInLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		StockInLabel.setFont(new Font("Meiryo UI", Font.BOLD, 16));
		StockInLabel.setBounds(563, 10, 81, 30);
		TopNavPanel.add(StockInLabel);

		/**
		 * Setting our images to ImageIcon that will be used in a JLabel later on.
		 */
		ImageIcon NasdaqImage = new ImageIcon("images/nasdaq.png");
		ImageIcon AmericanImage = new ImageIcon("images/american.png");
		ImageIcon NewYorkImage = new ImageIcon("images/newyork.png");
		ImageIcon MainImage = new ImageIcon("images/main.png");
		
		/**
		 * When you click on the topbar icon it will go back to the first page
		 * and clear all of the other panels.
		 */

		JLabel MainNav = new JLabel(MainImage);
		MainNav.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ClearJpanels(NavigationPanel, TopNavPanel, SearchPanel);

			}
		});
		MainNav.setCursor(new Cursor(Cursor.HAND_CURSOR));
		MainNav.setBounds(509, -23, 175, 99);
		TopNavPanel.add(MainNav);

		JPanel MainStylePanel = new JPanel();
		MainStylePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
				Color.GRAY));
		MainStylePanel.setBackground(Color.WHITE);
		MainStylePanel.setBounds(0, 0, 1204, 43);
		TopNavPanel.add(MainStylePanel);
		MainStylePanel.setLayout(null);

		MainPanel = new JPanel();
		MainPanel.setBounds(0, 0, 1194, 572);
		getContentPane().add(MainPanel);
		MainPanel.setLayout(null);

		NavigationPanel = new JPanel();
		NavigationPanel.setBounds(24, 142, 1160, 354);
		MainPanel.add(NavigationPanel);
		NavigationPanel.setLayout(null);

		JLabel American = new JLabel(AmericanImage);
		American.setBounds(201, 11, 222, 317);
		NavigationPanel.add(American);

		JLabel Nasdaq = new JLabel(NasdaqImage);
		Nasdaq.setBounds(469, 11, 222, 317);
		NavigationPanel.add(Nasdaq);

		JLabel NewYork = new JLabel(NewYorkImage);
		NewYork.setBounds(736, 11, 222, 317);
		NavigationPanel.add(NewYork);

		SearchPanel = new JPanel();
		SearchPanel.setBounds(346, 60, 501, 80);
		MainPanel.add(SearchPanel);
		SearchPanel.setLayout(null);
		
		/**
		 * When you press the button search it will grab the inputed text on the search bar
		 * it will check if it's empty or not. if it's empty it will return a message and the 
		 * user has to try again.
		 */

		JButton SearchButton = new JButton("Search");
		SearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String SearchInput = SearchField.getText();

				if (SearchInput.equals("")) {
					SearchField.setText("Search can't be empty");
				} else {

					/**
					 * Otherwise it will create objects of RssReader and CouchDBTest 
					 * which will try to grab information for the stock and also establish a connection with db.
					 */
					RssReader rss = new RssReader();
					CouchDBTest DB = new CouchDBTest();

					/**
					 * It will then send all the information grabbed from rss to ResultPanel 
					 * that will generate all the charts.
					 */
					try {
						DB.ConnectDB(SearchInput);
						String ReturnRss[] = rss.RssCall(SearchInput);
						FinalPanel = new ResultPanel(ReturnRss);
						ClearJpanels(FinalPanel, TopNavPanel, SearchPanel);
						CouchDBTest.infolist.clear();
					} catch (Exception e) {
						SearchField.setText("No matched stock found!");
						e.printStackTrace();
					}
				}

			}
		});
		SearchButton.setBounds(206, 45, 89, 23);
		SearchPanel.add(SearchButton);

		SearchField = new JTextField();
		SearchField.setFont(new Font("Ubuntu", Font.PLAIN, 11));
		SearchField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SearchField.setText("");
			}
		});
		SearchField.setBounds(90, 11, 321, 28);
		SearchPanel.add(SearchField);
		SearchField.setColumns(10);

	}
	/**
	 * This method is used to repaint and remove the unnecessary Jpanels that you don't want.
	 */

	public void ClearJpanels(JPanel PanelName, JPanel TopNav, JPanel Search) {
		getContentPane().removeAll();
		getContentPane().add(PanelName);
		getContentPane().add(TopNav);
		getContentPane().add(Search);
		revalidate();
		repaint();
	}
}
