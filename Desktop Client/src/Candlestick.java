import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultHighLowDataset;

import com.google.gson.JsonObject;

public class Candlestick extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Authors: Daniel Hosseini, Lídia Nyman, Odzaya Batsaikhan.
	 */
	DateAxis domainAxis;
	NumberAxis rangeAxis;
	CandlestickRenderer renderer;
	XYPlot mainPlot;
	double T, O, H, L, C, V;
	String[] timesplit;
	int year, mm, dd;
	public String Open;
	public String High;
	public String Low;
	public String Close;
	public String Volume;
	public String Time;
	public JsonObject object;
	public int serice;
	Date[] date;
	double[] high;
	double[] low;
	double[] open;
	double[] close;
	double[] volume;

	public Candlestick(ArrayList<JsonObject> list) {

		serice = list.size();
		date = new Date[serice];
		high = new double[serice];
		low = new double[serice];
		open = new double[serice];
		close = new double[serice];
		volume = new double[serice];

		/**
		 * Goes trough the list and grabs all the data that we want like Time,
		 * open, high etc then we parse the time from unix to a standard time
		 * that the chart can handle. We conver the Strings to a double and them
		 * to the chart this is a loop that goes on based on the lsit size from
		 * (InfoList)
		 */

		for (int i = 0; i < list.size(); i++) {

			object = list.get(i);
			Time = object.get("Time").getAsString();
			Open = object.get("Open").getAsString();
			High = object.get("High").getAsString();
			Low = object.get("Low").getAsString();
			Close = object.get("Close").getAsString();
			Volume = object.get("Volume").getAsString();

			try {
				Long time = Long.parseLong(Time);
				Date date1 = new Date(time);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String formattedate = sdf.format(date1);
				timesplit = formattedate.split("-");
				// System.out.println(formattedate);

				year = Integer.parseInt(timesplit[0]);
				mm = Integer.parseInt(timesplit[1]);
				dd = Integer.parseInt(timesplit[2]);

				O = Double.parseDouble(Open);
				H = Double.parseDouble(High);
				L = Double.parseDouble(Low);
				C = Double.parseDouble(Close);
				V = Double.parseDouble(Volume);

				Calendar calendar = Calendar.getInstance();
				calendar.set(year, mm, dd);

				date[i] = createData(year, mm, dd);
				high[i] = H;
				low[i] = L;
				open[i] = O;
				close[i] = C;
				volume[i] = V;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		/**
		 * Most of the settings for the candlestick like the axis names, size
		 * and how the charts should be displayed
		 * 
		 */

		domainAxis = new DateAxis("Date");
		rangeAxis = new NumberAxis("Price");
		renderer = new CandlestickRenderer();

		final DefaultHighLowDataset dataset = createDataset();
		// mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

		renderer.setSeriesPaint(0, Color.BLACK);
		renderer.setDrawVolume(false);
		rangeAxis.setAutoRangeIncludesZero(false);
		rangeAxis.setAutoRangeMinimumSize(L);
		domainAxis.setTimeline(SegmentedTimeline
				.newMondayThroughFridayTimeline());

		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setPreferredSize(new java.awt.Dimension(580, 330));
		this.add(chartPanel);
	}

	/**
	 * Generating the charts based on the information above
	 * 
	 */
	private DefaultHighLowDataset createDataset() {

		DefaultHighLowDataset data = new DefaultHighLowDataset("", date, high,
				low, open, close, volume);
		return data;
	}

	private Date createData(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return calendar.getTime();
	}

	private JFreeChart createChart(final DefaultHighLowDataset dataset) {
		final JFreeChart chart = ChartFactory.createCandlestickChart(
				"Candlestick", "Time", "Price", dataset, false);
		/**
		 * Setting the correct range for the axis by using the Lowest and highest value.
		 * 
		 */	    chart.getXYPlot().getRangeAxis().setRange(L*0.95, H*1.05);

		return chart;
	}

}