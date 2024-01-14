import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

class mainwindow {
    public static void main(String[] args) {
        try {
            new mainwindow("shhh", "Shivam"); // TODO remove main class
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String name, username;
    JPanel topPanel, centrePanel, bottomPanel; // panels for frame window
    JLabel topPanelText, exitmainwindow, minimize, uicoimg;// labels for top panel
    CardLayout c1; // card layout in centre panel
    JPanel homePanel, transactPanel, historyPanel, personalPanel;// panels for card layout(centre panel)
    JLabel homeicon, transicon, historyicon, myaccicon, logouticon; // icons for bottom panel
    Connection con;
    JLabel accNo, accBal; //centrehome

    mainwindow(String username, String name) throws Exception {
        this.name = name;
        this.username = username;
        JFrame f = new JFrame();

        // icon declarations-->
        BufferedImage usericon = ImageIO.read(new File("mainwindow-icon.png"));
        uicoimg = new JLabel(new ImageIcon(usericon));

        BufferedImage icon1 = ImageIO.read(new File("HOMEICON.png"));
        homeicon = new JLabel(new ImageIcon(icon1));
        homeicon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                home();
                c1.show(centrePanel, "home");
            }
        });

        BufferedImage icon2 = ImageIO.read(new File("TRANSACTICON.png"));
        transicon = new JLabel(new ImageIcon(icon2));
        transicon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                transact();
                c1.show(centrePanel, "transaction");
            }
        });

        BufferedImage icon3 = ImageIO.read(new File("HISTORYICON.png"));
        historyicon = new JLabel(new ImageIcon(icon3));
        historyicon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                history();
                c1.show(centrePanel, "history");
            }
        });

        BufferedImage icon4 = ImageIO.read(new File("MYACCICON.png"));
        myaccicon = new JLabel(new ImageIcon(icon4));
        myaccicon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                personal();
                c1.show(centrePanel, "personal");
            }
        });

        BufferedImage icon5 = ImageIO.read(new File("LOGOUTICON.png"));
        logouticon = new JLabel(new ImageIcon(icon5));
        logouticon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "Are you sure to logout?", "LogOut", 0);
                if (n == 0) {
                    f.dispose();
                    try {
                        new loginframe();

                    } catch (Exception exp) {
                        exp.printStackTrace();
                    }
                }
            }
        });

        topPanel = new JPanel(null);
        centrePanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        c1 = new CardLayout();
        centrePanel.setLayout(c1);

        // top panel shit-->
        topPanelText = new JLabel("<HTML><U>Welcome " + name + " have a great day :)</U></HTML>");
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
        // TODO add curr date time in top panel

        // centre panels

        homePanel = new JPanel(new GridLayout(4, 2, 0, 10));
        transactPanel = new JPanel();
        historyPanel = new JPanel(new GridLayout(11, 6));
        personalPanel = new JPanel();
        
        accNo=new JLabel("");
        accBal=new JLabel("");
        home();
        homePanel.add(new JLabel("Name :"));
        homePanel.add(new JLabel(name));
        homePanel.add(new JLabel("Username :"));
        homePanel.add(new JLabel(username));
        homePanel.add(new JLabel("Account number :"));
        homePanel.add(accNo);
        homePanel.add(new JLabel("Account balance :"));
        homePanel.add(accBal);


        transactPanel.add(new JLabel("sno"));
        transactPanel.add(new JLabel("accNo"));
        transactPanel.add(new JLabel("amt"));
        transactPanel.add(new JLabel("dateoftrans"));
        transactPanel.add(new JLabel("transmode"));
        transactPanel.add(new JLabel("accBal"));

        centrePanel.add(homePanel, "home");
        centrePanel.add(transactPanel, "transaction");
        centrePanel.add(historyPanel, "history");
        centrePanel.add(personalPanel, "personal");

        // bottom panel-->
        bottomPanel.setPreferredSize(new Dimension(150, 70));
        bottomPanel.setBackground(new Color(52, 32, 72));

        bottomPanel.add(homeicon);
        bottomPanel.add(transicon);
        bottomPanel.add(historyicon);
        bottomPanel.add(myaccicon);
        bottomPanel.add(logouticon);

        // frame setting->
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

    void home() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankjava", "root", "imemyselfshivam");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select acno,acbal from transactions where username ='" + username + "';");
            while (rs.next()) {
                accNo.setText("" + rs.getInt("acno"));
                accBal.setText("" + rs.getInt("acbal") + " Rs");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void transact() {
        transactPanel.setBackground(Color.black);


    }

    void history() {
        Statement st;
        try {
            int s=0;
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from transactions where username ='" + username + "';");
            while (rs.next()) {
                historyPanel.add(new JLabel(""+s++));
                historyPanel.add(new JLabel(accNo.getText()));
                historyPanel.add(new JLabel(rs.getString("amt")));
                historyPanel.add(new JLabel(rs.getString("dateoftrans")));
                historyPanel.add(new JLabel(rs.getString("transmode")));
                historyPanel.add(new JLabel(rs.getString("acbal")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void personal() {

    }

}
