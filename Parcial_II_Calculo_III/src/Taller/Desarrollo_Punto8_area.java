package Taller;

import java.awt.Color;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class Desarrollo_Punto8_area {

    // Definimos la función que queremos integrar
    public static double f(double x, double y) {
        return 3-x;
    }

    // Implementamos el método de Monte Carlo para integrales dobles
    public static double monteCarloIntegration(double xMax, double xMin, double yMin, double yMax, int numPoints) {

        // Creamos un objeto Random para generar números aleatorios
        Random random = new Random();

        // Inicializamos el contador de puntos que caen dentro de la región
        double insideRegion = 0;

        // Definimos las variables x e y

        // Creamos una serie para almacenar los puntos generados
        XYSeries series = new XYSeries("Puntos generados");

        // Generamos numPoints puntos aleatorios
        for (int i = 0; i < numPoints; i++) {

            // Generamos un valor aleatorio para x dentro del rango [xMin, xMax]
            double x = xMin + (xMax - xMin) * random.nextDouble();

            // Generamos un valor aleatorio para y dentro del rango [yMin, yMax]
            double y = yMin + (yMax - yMin) * random.nextDouble();

            // Si el punto (x,y) cae dentro de la región, incrementamos el contador y añadimos el punto a la serie
            //Math.sqrt((36-4*Math.pow(x, 2))/9))
            
            if (y < Math.sqrt(1 - Math.pow(x, 2))&& y > -Math.sqrt(1 - Math.pow(x, 2))){
                series.add(x, y);
                insideRegion++;
            }
            

        }

        // Calculamos el área de la región


        // Estimamos la integral de f(x,y) utilizando el método de Monte Carlo
        double avg = insideRegion / numPoints;
        double area = Math.abs((xMax - xMin) * (yMax - yMin));
        double integralEstimate = Math.abs(avg * area);

        // Creamos el gráfico de los puntos generados
        JFreeChart chart = ChartFactory.createScatterPlot("Gráfico de puntos generados", "X", "Y", createDataset(series), PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRenderer(new XYDotRenderer());
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        //RectangleInsets offset = new RectangleInsets(5, 5, 5, 5);
        //plot.setAxisOffset(offset);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        ChartFrame frame = new ChartFrame("Gráfico de puntos generados", chart);
        frame.pack();
        frame.setVisible(true);
        
        return integralEstimate;
    }

    public static XYSeriesCollection createDataset(XYSeries series) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public static void main(String[] args) {
        double xMin = -1;
        double xMax = 1;
        double yMin = -1;
        double yMax = 1;
        double integralEstimate = monteCarloIntegration(xMin, xMax, yMin, yMax, 100000);
        System.out.println("Estimación de la integral: " + integralEstimate);
    }
}