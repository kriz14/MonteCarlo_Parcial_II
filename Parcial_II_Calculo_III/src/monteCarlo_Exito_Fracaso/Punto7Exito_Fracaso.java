package monteCarlo_Exito_Fracaso;

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

public class Punto7Exito_Fracaso {

    // Definimos la función que queremos integrar
    public static double f(double x, double y) {
        return 1000 - x * y; //función
    }

    // Implementamos el método de Monte Carlo para integrales dobles
    public static double monteCarloIntegration
        (double xMax, double xMin, 
            double yMin, double yMax, int numPoints){

        // Creamos un objeto Random para generar números aleatorios
        Random random = new Random();

        // Inicializamos el contador de puntos que caen dentro de la región
        double insideRegion = 0;

        // Definimos las variables x,y

        // Creamos una serie para almacenar los puntos generados
        XYSeries series = new XYSeries("Puntos Existos");
        XYSeries seriesFailure = new XYSeries("Puntos Fracasos");

        // Generamos los puntos aleatorios que se quieren, en este caso con la variable numPoints
        for (int i = 0; i < numPoints; i++) {

            // Generamos un valor aleatorio para x dentro del rango [xMin, xMax]
            // Generamos un valor aleatorio para y dentro del rango [yMin, yMax]
            double x = xMin + (xMax - xMin) * random.nextDouble();
            double y = yMin + (yMax - yMin) * random.nextDouble();

            // Si el punto (x,y) cae dentro de la región, incrementamos el contador
            //Añadimos el punto a la serie
            
            if (1 < x * y && x * y < 2 && 25 < x/y && x / y < 400){
                series.add(x, y);
                insideRegion += f(x,y);
            }else{
                seriesFailure.add(x , y);
            }
            

        }

        // Calculamos el área de la región


        // Estimamos la integral de f(x,y) utilizando el método de Monte Carlo
        double avg = insideRegion / numPoints;
        double area = Math.abs((xMax - xMin) * (yMax - yMin));
        double integralEstimate = Math.abs(avg * area);

        // Creamos el gráfico de los puntos generados
        JFreeChart graph = ChartFactory.createScatterPlot("Gráfico de puntos generados", "X", "Y", createDataset(series,seriesFailure), PlotOrientation.VERTICAL, true, true, false);
        graph.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) graph.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRenderer(new XYDotRenderer());
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        RectangleInsets offset = new RectangleInsets(5, 5, 5, 5);
        plot.setAxisOffset(offset);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        ChartFrame frame = new ChartFrame("Gráfico de puntos generados", graph);
        frame.pack();
        frame.setVisible(true);
        
        return integralEstimate;
    }

    public static XYSeriesCollection createDataset(XYSeries series, XYSeries seriesFail) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(seriesFail);
        return dataset;
    }

    public static void main(String[] args) {
        double xMin = -40;
        double xMax = 40;
        double yMin = -10;
        double yMax = 10;
        double integralEstimate = monteCarloIntegration(xMin, xMax, yMin, yMax, 100000);
        System.out.println("La aproximación de la integral es : " + integralEstimate);
    }
}
