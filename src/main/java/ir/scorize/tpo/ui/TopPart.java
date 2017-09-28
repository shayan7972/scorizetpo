package ir.scorize.tpo.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mjafar on 8/4/17.
 */
public class TopPart {
    JPanel mPanel;
    JButton btnNext;
    JButton btnBack;
    JButton btnOk;
    JButton btnPreview;
    JButton btnHelp;
    JButton btnContinue;
    JButton btnHideTime;
    JButton btnPause;
    private JLabel lblTitle;
    private JLabel lblTime;
    private JPanel mRow1Panel;
    private JPanel mRow2Panel;

    public TopPart() {
        super();
        mRow1Panel.setOpaque(true);
        mRow1Panel.setBackground(new Color(0, 0, 0, 0));
        mRow2Panel.setOpaque(true);
        mRow2Panel.setBackground(new Color(0, 0, 0, 0));
    }

    private void createUIComponents() {
        mPanel = new GradientPanel();
    }

    public void setTitle(String title) {
        EventQueue.invokeLater(() -> lblTitle.setText(title));
    }

}
