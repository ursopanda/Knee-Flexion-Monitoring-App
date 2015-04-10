package lv.edi.SmartWear;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import java.util.List;
import lv.edi.Database.Databasehandler;
import lv.edi.Database.FlexionStats;


public class GraphActivity extends Activity {

    // Getting a refference to a DB to get Flexion's values to the table
    final Databasehandler db = new Databasehandler(this);

    private View mChart;
    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Thread is used to update a line chart
//        Thread t = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                                // Draw a line chart
                                openChart();
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            };
//        };
//        t.start();
    }

    // Method used to create and draw charts
    private void openChart(){
        // Creating an XYSeries for Flexion Values from DB
        XYSeries flexionValueSeries = new XYSeries("Flexion");
        // Getting all the flexion values that are stored in DB
        List<FlexionStats> flexionStatsList = db.getAllFlexionStats();
        for (int i=0; i < flexionStatsList.size(); i++) {
            flexionValueSeries.add(i,flexionStatsList.get(i).getFlexion_value());
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(flexionValueSeries);

        // Creating XYSeriesRenderer to customize flexionValueSeries
        XYSeriesRenderer flexionRenderer = new XYSeriesRenderer();
        flexionRenderer.setColor(Color.CYAN); // setting color of the graph
        flexionRenderer.setFillPoints(true);
        flexionRenderer.setLineWidth(2f);
        flexionRenderer.setDisplayChartValues(true);
        flexionRenderer.setDisplayChartValuesDistance(5); // setting chart value distance
        flexionRenderer.setPointStyle(PointStyle.CIRCLE); // setting line graph point style to circle
        flexionRenderer.setStroke(BasicStroke.SOLID); // setting stroke of the line to solid

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Flexion Value Chart");
        multiRenderer.setXTitle("");
        multiRenderer.setYTitle("Flexion Angle");

        /***
         * Customizing graphs
         */
        // Setting text size of the title
        multiRenderer.setChartTitleTextSize(28);
        // Setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
        // Setting text size of the graph lable
        multiRenderer.setLabelsTextSize(24);
        // Setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(true);
        // Setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(true, true);
        // Setting click false on graph
        multiRenderer.setClickEnabled(true);
        // Setting zoom to false on both axis
        multiRenderer.setZoomEnabled(true, true);
        // Setting lines to display on y axis
        multiRenderer.setShowGridY(true);
        // Setting lines to display on x axis
        multiRenderer.setShowGridX(true);
        // Setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
        // Setting displaying line on grid
        multiRenderer.setShowGrid(true);
        // Setting zoom to false
        multiRenderer.setZoomEnabled(true);
        // Setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(true);
        // Setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
        // Setting to in scroll to false
        multiRenderer.setInScroll(false);
        // Setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
        // Setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
        // Setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
        // Setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        // Setting no of values to display in y axis
        multiRenderer.setYLabels(15);
        // Setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(150);
        // Setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(0);
        // Setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMax(11);
        // Setting bar size or space between two bars
        // multiRenderer.setBarSpacing(0.5);
        // Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        // Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setScale(1f);
        // setting x axis point size
        multiRenderer.setPointSize(4f);
        // setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        // Adding flexionRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(flexionRenderer);

        // This part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        // Remove any views before u paint the chart
        chartContainer.removeAllViews();
        // Drawing bar chart
        mChart = ChartFactory.getLineChartView(GraphActivity.this, dataset, multiRenderer);
        // Adding the view to the linearlayout
        chartContainer.addView(mChart);
    }

}
