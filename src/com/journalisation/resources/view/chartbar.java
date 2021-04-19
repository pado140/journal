package com.journalisation.resources.view;

import javafx.collections.ObservableMap;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.util.Map;

public class chartbar  extends AnchorPane {

    private BarChart<String,Number> chart;
    private String titre;
    private NumberAxis nax;
    private CategoryAxis cax;
    private ObservableMap<String, Map<String,Number>> data;

    public chartbar() {
        nax=new NumberAxis();
        cax=new CategoryAxis();
        chart=new BarChart<>(cax,nax);
    }

    public String getTitre() {
        return titre;
    }

    public BarChart<String, Number> getChart() {
        return chart;
    }

    public void setChart(BarChart<String, Number> chart) {
        this.chart = chart;
    }

    public ObservableMap<String, Map<String, Number>> getData() {
        return data;
    }

    public void setData(ObservableMap<String, Map<String, Number>> data) {
        this.data = data;
    }


    public void setTitre(String titre) {
        this.titre = titre;
        this.chart.setTitle(titre);
    }

    public NumberAxis getNax() {
        return nax;
    }

    public void setNax(NumberAxis nax) {
        this.nax = nax;
    }

    public CategoryAxis getCax() {
        return cax;
    }

    public void setCax(CategoryAxis cax) {
        this.cax = cax;
    }

    public void setMaxX(double max){
        nax.setUpperBound(max);
    }

    public void setMinX(double max){
        nax.setLowerBound(max);
    }
    public void setcat(String... cat){
        cax.getCategories().addAll(cat);
    }

    //public boolean
    public void initLayout(){
        for(String s:data.keySet())
        {
            XYChart.Series series = new XYChart.Series();
            series.setName(s);
            Map<String,Number> donnees=data.get(s);
            for(String st:donnees.keySet())
            {
                series.getData().add(new XYChart.Data( st,donnees.get(st)));
            }
            chart.getData().add(series);
        }
        this.getChildren().addAll(chart);
        chartbar.setTopAnchor(chart, 10.0);
        chartbar.setBottomAnchor(chart, 10.0);
        chartbar.setLeftAnchor(chart, 10.0);
        chartbar.setRightAnchor(chart, 10.0);
    }
}
