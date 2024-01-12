import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

class mainwindow {
    public static void main(String[] args) {
        try {
            new mainwindow("Shivam"); //TODO remove main class
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String name;
    CardLayout c1;
    JPanel topPanel, centrePanel, bottomPanel; // panels for frame window
    JPanel homePanel, transactPanel, tHistoryPanel, personalPanel; // panels for card layout(centre panel)
    JLabel topPanelText, exitmainwindow, minimize, uicoimg;
    JLabel homeicon, transicon, historyicon, myaccicon, logouticon; // panels for bottom panel

    mainwindow(String name) throws Exception {
        this.name = name;
        JFrame f = new JFrame();

        //icon declarations-->
        BufferedImage usericon = ImageIO.read(new File("mainwindow-icon.png"));
        uicoimg = new JLabel(new ImageIcon(usericon));

        BufferedImage icon1 = ImageIO.read(new File("HOMEICON.png"));
        homeicon = new JLabel(new ImageIcon(icon1));

        BufferedImage icon2 = ImageIO.read(new File("TRANSACTICON.png"));
        transicon = new JLabel(new ImageIcon(icon2));

        BufferedImage icon3 = ImageIO.read(new File("HISTORYICON.png"));
        historyicon = new JLabel(new ImageIcon(icon3));

        BufferedImage icon4 = ImageIO.read(new File("MYACCICON.png"));
        myaccicon = new JLabel(new ImageIcon(icon4));

        BufferedImage icon5 = ImageIO.read(new File("LOGOUTICON.png"));
        logouticon = new JLabel(new ImageIcon(icon5));

        topPanel = new JPanel(null);
        centrePanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        c1 = new CardLayout();
        centrePanel.setLayout(c1);

        // top panel shit-->
        topPanelText = new JLabel("<HTML><U>Welcome " + name+" have a great day :)</U></HTML>");
        exitmainwindow = new JLabel("X");
        minimize = new JLabel("_");

        minimize.setForeground(Color.white);
        exitmainwindow.setForeground(Color.white);
        minimize.setSize(15, 15);
        exitmainwindow.setSize(15, 15);
        exitmainwindow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        minimize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                f.setState(Frame.ICONIFIED);
            }

        });
        topPanelText.setFont(new Font("", Font.BOLD, 20));
        topPanelText.setForeground(Color.WHITE);

        uicoimg.setBounds(25, 30, 180, 110);
        exitmainwindow.setBounds(20, 20, 15, 15);
        minimize.setBounds(41, 15, 15, 15);
        topPanelText.setBounds(250, 5, 470, 50);

        topPanel.setPreferredSize(new Dimension(750, 150));
        topPanel.setBackground(new Color(52, 32, 72));
        topPanel.add(topPanelText);
        topPanel.add(exitmainwindow);
        topPanel.add(minimize);
        topPanel.add(uicoimg);
        //TODO add curr date time in top panel

        // centre panels

        // bottom pamnel-->
        bottomPanel.setPreferredSize(new Dimension(150, 70));
        bottomPanel.setBackground(new Color(52, 32, 72));

        bottomPanel.add(homeicon);
        bottomPanel.add(transicon);
        bottomPanel.add(historyicon);
        bottomPanel.add(myaccicon);
        bottomPanel.add(logouticon);

        //frame setting->
        f.setLayout(new BorderLayout());
        f.add(topPanel, BorderLayout.NORTH);
        f.add(centrePanel, BorderLayout.CENTER);
        f.add(bottomPanel, BorderLayout.SOUTH);
        f.setSize(750, 500);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        f.setShape(new RoundRectangle2D.Double(0, 0, 750, 500, 45, 45));
        f.setVisible(true);
    }
}
