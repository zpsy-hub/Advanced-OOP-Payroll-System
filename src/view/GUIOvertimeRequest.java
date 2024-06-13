package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Arrays;
import javax.swing.JFrame;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;
import com.toedter.calendar.JCalendar;
import model.User;
import util.SessionManager;

public class GUIOvertimeRequest {

    private JFrame overtimerequest;
    private static User loggedInEmployee;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    User loggedInEmployee = SessionManager.getLoggedInUser();
                    GUIOvertimeRequest window = new GUIOvertimeRequest(loggedInEmployee);
                    window.overtimerequest.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * @param loggedInEmployee2 
     */
    public GUIOvertimeRequest(User loggedInEmployee) {
        GUIOvertimeRequest.setLoggedInEmployee(loggedInEmployee);
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        overtimerequest = new JFrame();
        overtimerequest.setBounds(100, 100, 1315, 770);
        overtimerequest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fetch data for the chart (replace with your actual data retrieval logic)
        Integer[] categories = { 0, 1, 2, 3, 4 };
        Integer[] values = { 4, 5, 9, 6, 5 };

        // Create the bar chart
        CategoryChart chart = new CategoryChartBuilder()
            .width(800)
            .height(600)
            .title("Overtime Histogram")
            .xAxisTitle("Employee")
            .yAxisTitle("Overtime Hours")
            .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);

        // Series (replace "test 1" with the appropriate label)
        chart.addSeries("Overtime Data", Arrays.asList(categories), Arrays.asList(values)); 

        // Embed the chart in a Swing panel
        XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
        overtimerequest.add(chartPanel, BorderLayout.CENTER); // Add to your main frame

        // Add the calendar
        JCalendar calendar = new JCalendar();
        overtimerequest.add(calendar, BorderLayout.EAST); // Add to the frame
    }

    public static User getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public static void setLoggedInEmployee(User loggedInEmployee) {
        GUIOvertimeRequest.loggedInEmployee = loggedInEmployee;
    }
}
