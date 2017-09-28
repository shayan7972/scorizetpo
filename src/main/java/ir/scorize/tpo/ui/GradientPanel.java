package ir.scorize.tpo.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mjafar on 8/25/17.
 */
public class GradientPanel extends JPanel {
    private static final Color color1 = new Color(50, 86, 138);
    private static final Color color2 = new Color(126, 49, 59);

    private GradientPaint gp;

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        gp = new GradientPaint(0, 0, color1, width, 0, color2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        if (gp == null) {
            gp = new GradientPaint(0, 0, color1, w, 0, color2);
        }
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
