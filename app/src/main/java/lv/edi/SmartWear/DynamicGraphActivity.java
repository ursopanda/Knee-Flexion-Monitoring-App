package lv.edi.SmartWear;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import lv.edi.Database.Databasehandler;
import lv.edi.Database.FlexionStats;

public class DynamicGraphActivity extends Activity {
    // Getting a refference to a DB to get Flexion's values to the table
    final Databasehandler db = new Databasehandler(this);

    private static Random random = new Random();

    private static TimeSeries timeSeries;
    private static XYMultipleSeriesDataset dataset;
    private static XYMultipleSeriesRenderer renderer;
    private static XYSeriesRenderer rendererSeries;
    private static GraphicalView view;
    private static Thread mThread;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataset = new XYMultipleSeriesDataset();

        renderer = new XYMultipleSeriesRenderer();
        renderer.setAxesColor(Color.BLUE);
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitle("Flexion Value Chart");
        renderer.setChartTitleTextSize(15);
        renderer.setFitLegend(true);
        renderer.setGridColor(Color.LTGRAY);
        renderer.setPanEnabled(true, true);
        renderer.setPointSize(10);
        renderer.setXTitle("");
        renderer.setYTitle("Flexion Angle");
        renderer.setMargins( new int []{20, 30, 15, 0});
        renderer.setZoomButtonsVisible(true);
        renderer.setBarSpacing(10);
        renderer.setShowGrid(true);


        rendererSeries = new XYSeriesRenderer();
        rendererSeries.setColor(Color.RED);
        renderer.addSeriesRenderer(rendererSeries);
        rendererSeries.setFillPoints(true);
        rendererSeries.setPointStyle(PointStyle.CIRCLE);

        timeSeries = new TimeSeries("Flexion");
        db.deleteFlexionStats();
//        db.addFlexionStats(new FlexionStats(9));
//        db.addFlexionStats(new FlexionStats(13));
//        db.addFlexionStats(new FlexionStats(19));
//        db.addFlexionStats(new FlexionStats(23));
//        db.addFlexionStats(new FlexionStats(35));
//        db.addFlexionStats(new FlexionStats(42));
//        db.addFlexionStats(new FlexionStats(48));
//        db.addFlexionStats(new FlexionStats(57));
//        db.addFlexionStats(new FlexionStats(68));
//        db.addFlexionStats(new FlexionStats(75));
//        db.addFlexionStats(new FlexionStats(84));
//        db.addFlexionStats(new FlexionStats(91));
//        db.addFlexionStats(new FlexionStats(75));
//        db.addFlexionStats(new FlexionStats(63));
       // FlexionStats value = db.getFlexionValue(202);
        //int val = (Integer) value.getFlexion_value();
        final List<FlexionStats> flexionStats = db.getAllFlexionStats();
        mThread = new Thread(){
            public void run(){
                while(true){
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //implement getFlexionValue() method in here
                    List<FlexionStats> flexionStatsList = db.getAllFlexionStats();
                    for (int i=0; i < flexionStatsList.size(); i++) {
                        timeSeries.add(i,flexionStatsList.get(i).getFlexion_value());
                    }
                    //timeSeries.add(new Date(), random.nextInt(10));
                    view.repaint();
                }
            }
        };
        mThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataset.addSeries(timeSeries);
        view = ChartFactory.getTimeChartView(this, dataset, renderer, "Test");
        view.refreshDrawableState();
        view.repaint();
        setContentView(view);
    }
}