/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mobileworld.service.ThongKeService;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import mobileworld.chart.Chart;
import mobileworld.viewModel.ThongKe.HoaDonTK;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Admin
 */
public class QuanLythongKeController {

    private ThongKeService thongKeService = null;

    public QuanLythongKeController() {
        thongKeService = new ThongKeServiceImpl();

    }

    public void setDateToChart(JPanel jpnItem) {
        List<HoaDonTK> listItem = thongKeService.getListByHoaDon();
        if (listItem != null) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            for (HoaDonTK item : listItem) {
                // Chuyển đổi từ LocalDate sang Date
                Date date = java.sql.Date.valueOf(item.getNgayTao());
                // Định dạng lại ngày
                String formattedDate = dateFormatter.format(date);
                dataset.addValue(item.getThanhTien(), "Doanh thu", formattedDate);
            }
            JFreeChart chart = ChartFactory.createBarChart("Thống kê doanh thu", "Thời gian", "Doanh thu", dataset);

            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            Color color = new Color(132, 189, 0);
            renderer.setSeriesPaint(0, color);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 300));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }

    public void setDateToChartPerYear(JPanel jpnItem, String Year) {
        List<HoaDonTK> listItem = thongKeService.timTheoNam(Year);
        if (listItem != null) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            for (HoaDonTK item : listItem) {
                // Chuyển đổi từ LocalDate sang Date
                Date date = java.sql.Date.valueOf(item.getNgayTao());
                // Định dạng lại ngày
                String formattedDate = dateFormatter.format(date);
                dataset.addValue(item.getThanhTien(), "Doanh thu", formattedDate);
            }
            JFreeChart chart = ChartFactory.createBarChart("Thống kê doanh thu", "Thời gian", "Doanh thu", dataset);

            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            Color color = new Color(132, 189, 0);
            renderer.setSeriesPaint(0, color);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 300));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }

    public void setDateToChartPerTime(JPanel jpnItem, LocalDate ngayBD, LocalDate ngayKT) {
        List<HoaDonTK> listItem = thongKeService.timTheoThoiGian(ngayBD, ngayKT);
        if (listItem != null) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            for (HoaDonTK item : listItem) {
                // Chuyển đổi từ LocalDate sang Date
                Date date = java.sql.Date.valueOf(item.getNgayTao());
                // Định dạng lại ngày
                String formattedDate = dateFormatter.format(date);
                dataset.addValue(item.getThanhTien(), "Doanh thu", formattedDate);
            }
            JFreeChart chart = ChartFactory.createBarChart("Thống kê doanh thu", "Thời gian", "Doanh thu", dataset);

            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            Color color = new Color(132, 189, 0);
            renderer.setSeriesPaint(0, color);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 300));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }
}
