import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import view.GUIlogin;

public class Main {
    public static void main(String[] args) {
        // Simply instantiate GUIlogin and display the login screen
        GUIlogin guiLogin = new GUIlogin();
        guiLogin.loginScreen1.setVisible(true);
        guiLogin.loginScreen1.setLocationRelativeTo(null); // Center the login screen
    }
    
    /**
     * Creates a simple Chart using QuickChart
     */
    public class Example0 {

      public static void main(String[] args) throws Exception {

        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        // Show it
        new SwingWrapper(chart).displayChart();*/

      }
    }
}
