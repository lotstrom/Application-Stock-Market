import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.google.gson.JsonObject;

/**
 * Authors: Daniel Hosseini, Lídia Nyman, Odzaya Batsaikhan.
 */

public class PriceChart extends JPanel {

	private static final long serialVersionUID = 8382665736644843686L;
	public static JsonObject object;
	public static String Close;
	private static ArrayList<JsonObject> arrlist;
	public static String Time;

	public PriceChart(ArrayList<JsonObject> list) {
		this.arrlist = list;

		/**
		 * Sets the size of the chart and sets the options for print, zoom etc..
		 */

		JFreeChart chart = createChart();
		ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
		panel.setPreferredSize(new java.awt.Dimension(570, 320));
		this.add(panel);
	}

	/**
	 * Basically setting up the charts that JFreechart offers.
	 * This is some of the settings for a chart.
	 */
	private static JFreeChart createChart() {

		XYDataset priceData = createPriceDataset();
		String title = "Pricechart";
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Date",
				"Price", priceData, true, true, false);
		// chart.getXYPlot().getRangeAxis().setInverted(true);

		XYPlot plot = chart.getXYPlot();
		NumberAxis rangeAxis1 = (NumberAxis) plot.getRangeAxis();
		rangeAxis1.setLowerMargin(0.40); // Leaves room for volume bars
		DecimalFormat format = new DecimalFormat("00.00");
		rangeAxis1.setNumberFormatOverride(format);

		XYItemRenderer renderer1 = plot.getRenderer();
		renderer1.setToolTipGenerator(new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
				new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")));
		return chart;

	}


	public static XYDataset createPriceDataset() {
		String[] timesplit;
		int year = 0, mm = 0, dd = 0;



		/**
		 * Creates the dataset based on how many arrays of info we have. It
		 * grabs the info from arrlist(InfoList) and paints it out on the graph
		 */
		TimeSeries series1 = new TimeSeries("Price", Day.class);
		for (int i = 0; i < arrlist.size(); i++) {
			object = arrlist.get(i);
			Time = object.get("Time").getAsString();
			Close = object.get("Close").getAsString();
			double C = Double.parseDouble(Close);

			/**
			 * Here we convert the time from unix to a simpledateformat that we
			 * can use in the Pricechart graph
			 */

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
			} catch (Exception e) {

			}

			series1.add(new Day(dd, mm, year), C);

		}

		return new TimeSeriesCollection(series1);

	}



}
