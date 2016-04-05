package com.example.vaadin542;

import java.sql.SQLException;
import java.util.HashMap;

import com.example.vaadin542.model.Model;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.HorizontalLayout;

public class GenreStatsPage extends GenreStats {
    public GenreStatsPage(String genre) {
        HorizontalLayout layout = new HorizontalLayout();

        Chart chart = new Chart(ChartType.PIE);
                Configuration conf = chart.getConfiguration();
                conf.setTitle("Top Directors");
                PlotOptionsPie plotOptions = new PlotOptionsPie();
                plotOptions.setCursor(Cursor.POINTER);
                DataLabels dataLabels = new DataLabels();
                dataLabels.setEnabled(true);
                dataLabels.setFormatter("'<b>'+ this.point.name +'</b>: '+ this.y");
                plotOptions.setDataLabels(dataLabels);
                conf.setPlotOptions(plotOptions);
                final DataSeries series = new DataSeries();
                try {
                    HashMap<String, Integer> map = Model.getDirectorFromGenre(genre);
                    for(String key : map.keySet()) {
                        series.add(new DataSeriesItem(key, map.get(key)));        
                    }
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                conf.setSeries(series);
                chart.drawChart(conf);

        Chart chart2 = new Chart(ChartType.LINE);
        chart2.setWidth("330px");
        Configuration conf2 = chart2.getConfiguration();
        conf2.setTitle("Movies Under this Genre over Time");
        ListSeries series2 = new ListSeries();
        PlotOptionsLine options2 = new PlotOptionsLine();
        options2.setColor(SolidColor.RED);
        series2.setPlotOptions(options2);
        try {
            series2.setData(Model.getYearFromGenre(genre));
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XAxis xaxis = new XAxis();
        xaxis.setCategories("1925", "1935", "1945", "1955", "1965", "1975", "1985", "1995", "2005");
        xaxis.setTitle("Year");
        YAxis yaxis = new YAxis();
        yaxis.setTitle("Number of Movies");
        conf2.addxAxis(xaxis);
        conf2.addyAxis(yaxis);
        conf2.addSeries(series2);

                chart.drawChart(conf);
                chart2.drawChart(conf2);

                layout.addComponent(chart2);
                layout.addComponent(chart);
                this.addComponent(layout);
        }
}
