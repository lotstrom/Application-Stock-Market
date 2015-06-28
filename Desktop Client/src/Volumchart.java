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
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import com.google.gson.JsonObject;

public class Volumchart extends JPanel {

	/**
	 * Authors: Daniel Hosseini, Lídia Nyman, Odzaya Batsaikhan.
	 */

	private static final long serialVersionUID = 1L;
	public static JsonObject object;
	public static String Volume;
	private static ArrayList<JsonObject> arrlist;
	public static String Time;

	public Volumchart(ArrayList<JsonObject> list) {
		this.arrlist = list;

		/**
		 * Sets the size of the chart and sets the options for print, zoom etc..
		 */

		JFreeChart chart = createChart();
		ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
		panel.setPreferredSize(new java.awt.Dimension(575, 340));
		this.add(panel);

	}

	/**
	 * Creates the chart with titles and gives us the opportunity to add
	 * Axislabels
	 */
	private static JFreeChart createChart() {

		XYDataset priceData = createVolumeDataset();
		String title = "Volumechart";
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Date",
				"Price", priceData, true, true, false);
		XYPlot plot = chart.getXYPlot();

		/**
		 * Setting the axis options like range,space etc
		 */
		NumberAxis rangeAxis2 = new NumberAxis("Volume");
		rangeAxis2.setUpperMargin(1.00); // to leave room for price line
		plot.setRangeAxis(rangeAxis2);
		plot.setDataset(createVolumeDataset());
		plot.setRangeAxis(rangeAxis2);
		plot.mapDatasetToRangeAxis(0, 0);
		XYBarRenderer renderer2 = new XYBarRenderer(0.20);
		renderer2.setToolTipGenerator(new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
				new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat(
						"0,000.00")));
		plot.setRenderer(renderer2);
		return chart;

	}

	private static IntervalXYDataset createVolumeDataset() {
		String[] timesplit;
		int year = 0, mm = 0, dd = 0;

		/**
		 * Creates the dataset based on how many arrays of info we have. It
		 * grabs the info from arrlist(InfoList) and paints it out on the graph
		 */
		
		TimeSeries series1 = new TimeSeries("Price", Day.class);

		for (int i = 0; i < arrlist.size(); i++) {
			object = arrlist.get(i);
			Time = object.get("Time").getAsString(); //Grabbing the Time and volume from arrlist(Infolist)
			Volume = object.get("Volume").getAsString();
			double V = Double.parseDouble(Volume);

			/**
			 * Here we convert the time grabbed from the database from unix to a
			 * simpledateformat that we can use in the Volumechart graph
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
			/**
			 * We add the time to the series so that the axis can print it out
			 */
			series1.add(new Day(dd, mm, year), V);

		}
		return new TimeSeriesCollection(series1);

	}

}
