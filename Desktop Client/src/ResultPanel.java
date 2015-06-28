import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ResultPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Authors: Daniel Hosseini, Lídia Nyman, Odzaya Batsaikhan.
	 */

	private JPanel VolumePanel, PricePanel, CandlePanel;

	public JPanel getChartPanel() {

		return VolumePanel;
	}

	/**
	 * Takes a string that contains all the news.
	 */
	public ResultPanel(String[] RssResult) {

		/**
		 * Interface code.
		 */

		setLayout(null);
		setBounds(17, 131, 1160, 426);

		CandlePanel = new JPanel();
		CandlePanel.setBackground(Color.WHITE);
		CandlePanel.setLayout(null);
		CandlePanel.setBounds(10, 55, 599, 329);
		add(CandlePanel);
		/**
		 * Volumechart, Candlestick and Pricechart objects are created and its
		 * sending the infolist we generated in CouchDBTest.
		 */
		Candlestick Candlegraph = new Candlestick(CouchDBTest.infolist);
		Candlegraph.setBackground(Color.WHITE);
		Candlegraph.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Candlegraph.setBounds(0, 11, 590, 307);
		CandlePanel.add(Candlegraph);

		PricePanel = new JPanel();
		PricePanel.setBackground(Color.WHITE);
		PricePanel.setLayout(null);
		PricePanel.setBounds(31, 55, 578, 329);
		add(PricePanel);
		PricePanel.setVisible(false);

		PriceChart pricegraph = new PriceChart(CouchDBTest.infolist);
		pricegraph.setBackground(Color.WHITE);
		pricegraph.setBounds(0, 25, 572, 279);
		PricePanel.add(pricegraph);
		PricePanel.setVisible(false);

		JLabel ChartLabel = new JLabel("Charts");
		ChartLabel.setBounds(257, 27, 78, 25);
		add(ChartLabel);
		ChartLabel.setFont(new Font("Exo", Font.PLAIN, 24));

		VolumePanel = new JPanel();
		VolumePanel.setBackground(Color.WHITE);
		VolumePanel.setBounds(10, 55, 594, 329);
		add(VolumePanel);
		VolumePanel.setLayout(null);

		Volumchart volumegraph = new Volumchart(CouchDBTest.infolist);
		volumegraph.setBackground(Color.WHITE);
		volumegraph.setBounds(10, 11, 586, 295);
		VolumePanel.add(volumegraph);
		VolumePanel.setVisible(false);

		JPanel NewsPanel = new JPanel();
		NewsPanel.setBounds(599, 27, 551, 368);
		add(NewsPanel);
		NewsPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 39, 492, 309);
		NewsPanel.add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Ubuntu", Font.PLAIN, 13));
		scrollPane.setViewportView(textArea);
		JLabel NewsLabel = new JLabel("News");
		NewsLabel.setBounds(242, -19, 85, 63);

		NewsPanel.add(NewsLabel);
		NewsLabel.setFont(new Font("Exo", Font.PLAIN, 24));

		JPanel FakeborderPanel = new JPanel();
		FakeborderPanel.setBackground(Color.WHITE);
		FakeborderPanel.setBounds(31, 27, 515, 330);
		NewsPanel.add(FakeborderPanel);

		/**
		 * Creating buttons that switches between the charts.
		 */

		JButton Candlebutton = new JButton("Candlestick");
		Candlebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VolumePanel.setVisible(false);
				PricePanel.setVisible(false);
				CandlePanel.setVisible(true);
			}
		});
		Candlebutton.setBounds(113, 405, 102, 23);
		add(Candlebutton);

		JButton Volumebutton = new JButton("Volumechart");
		Volumebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VolumePanel.setVisible(true);
				PricePanel.setVisible(false);
				CandlePanel.setVisible(false);
			}
		});
		Volumebutton.setBounds(246, 405, 102, 23);
		add(Volumebutton);

		JButton Pricebutton = new JButton("Pricechart");
		Pricebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VolumePanel.setVisible(false);
				PricePanel.setVisible(true);
				CandlePanel.setVisible(false);
			}
		});
		Pricebutton.setBounds(383, 405, 89, 23);
		add(Pricebutton);

		/**
		 * Writes everything that is stored on the RssResult on the textarea,
		 * this is our rss feed
		 */

		for (String Elem : RssResult) {
			textArea.append(Elem + "\n");

		}

	}
}
