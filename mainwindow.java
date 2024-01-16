import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

class mainwindow {
    public static void main(String[] args) {
        try {
            new mainwindow("shhh", "Shivam"); // TODO remove main class
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int currbal;
    String name, username, accNo;
    JPanel topPanel, centrePanel, bottomPanel; // panels for frame window
    JLabel topPanelText, exitmainwindow, minimize, uicoimg;
    static JLabel toppaneltime;// labels for top panel
    CardLayout c1; // card layout in centre panel
    JPanel homePanel, transactPanel, historyPanel, personalPanel;// panels for card layout(centre panel)
    JLabel homeicon, transicon, historyicon, myaccicon, logouticon; // icons for bottom panel
    Connection con;
    JLabel homerror;
    JPanel homepanel1, homepanel2;
    JLabel homeaccbal;

    JTable jt;
    // transact panel
    JTextField transact;
    JPasswordField enterpin;
    JComboBox<String> transactmenu;
    JButton transactButton;
    JLabel transacterror, transacttop,transactavlbal;

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
        toppaneltime=new JLabel("");

        SetTime settime=new SetTime();
        settime.start();
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
        toppaneltime.setForeground(Color.WHITE);

        uicoimg.setBounds(450, 20, 180, 110);
        exitmainwindow.setBounds(20, 20, 15, 15);
        minimize.setBounds(41, 15, 15, 15);
        topPanelText.setBounds(25, 55, 470, 50);
        toppaneltime.setBounds(10, 130, 470, 20);

        topPanel.setPreferredSize(new Dimension(650, 150));
        topPanel.setBackground(new Color(52, 32, 72));
        topPanel.add(topPanelText);
        topPanel.add(exitmainwindow);
        topPanel.add(minimize);
        topPanel.add(uicoimg);
        topPanel.add(toppaneltime);
        // TODO add curr date time in top panel

        homePanel = new JPanel(new GridLayout(2, 0, 0, 0));
        transactPanel = new JPanel();
        historyPanel = new JPanel();
        personalPanel = new JPanel();
        homerror = new JLabel();
        homerror.setForeground(Color.red);

        // centre panels

        centrePanel.add(homePanel, "home");
        centrePanel.add(transactPanel, "transaction");
        centrePanel.add(historyPanel, "history");
        centrePanel.add(personalPanel, "personal");

        // homepanel
        homepanel1 = new JPanel(new GridLayout(4, 0));
        homepanel2 = new JPanel();
        home();

        homePanel.add(homepanel1);
        homePanel.add(homepanel2);

        homepanel1.add(new JLabel("          Name                         :              " + name));
        homepanel1.add(new JLabel("Username                      :              " + username));
        homepanel1.add(new JLabel("          Account number               :              " + accNo));
        homeaccbal=new JLabel("Account balance                 :              " + currbal+" Rs");
        homepanel1.add(homeaccbal);
        homepanel1.add(homerror);

        // transactpanel
        transactPanel.setLayout(null);
        transacttop = new JLabel("Add or Widthdraw from your account");
        transactavlbal=new JLabel("Current Bal : "+currbal+" Rs");
        JLabel transactemp1 = new JLabel("Enter Amount :");
        JLabel transactemp2 = new JLabel("Enter your pin :");
        JLabel transactemp3 = new JLabel("Enter mode :");
        transactemp1.setBounds(20, 100, 100, 20);
        transactemp2.setBounds(20, 150, 100, 20);
        transactemp3.setBounds(400, 100, 100, 20);

        transactavlbal.setBounds(450, 250, 200, 20);

        transacttop.setFont(new Font("", Font.PLAIN, 30));
        transacttop.setBounds(80, 20, 500, 30);

        transact = new JTextField();
        transact.setBounds(150, 100, 150, 20);

        transactmenu = new JComboBox<>(new String[] { "Dr.", "Cr." });
        transactmenu.setBounds(510, 100, 80, 20);

        enterpin = new JPasswordField();
        enterpin.setBounds(150, 150, 150, 20);

        transactButton = new JButton("Transact");
        transactButton.setBounds(330, 150, 100, 20);
        transactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = String.valueOf(enterpin.getPassword());
                if (transact.getText().isBlank() || pass.isBlank()) {
                    transacterror.setText("*Field cannot be empty >_<");
                } else {
                    if (pass.length() > 4) {
                        transacterror.setText("*Pin cannot be more than 4 chars 0_0");
                    } else if (transact.getText().length() > 8) {
                        transacterror.setText("*Transaction <1cr not allowed by RBI-");
                    } else {
                        transact(pass);
                    }
                }
            }
        });

        transacterror = new JLabel();
        transacterror.setForeground(Color.red);
        transacterror.setBounds(150, 220, 400, 20);

        transactPanel.add(transacttop);
        transactPanel.add(transactemp1);
        transactPanel.add(transactemp2);
        transactPanel.add(transactemp3);
        transactPanel.add(transact);
        transactPanel.add(enterpin);
        transactPanel.add(transactmenu);
        transactPanel.add(transactButton);
        transactPanel.add(transacterror);
        transactPanel.add(transactavlbal);


        // history
        JButton refreshhistoy=new JButton("History");
        refreshhistoy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            deletentry();
            }
        });
        historyPanel.add(refreshhistoy);
        // personal

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
        f.setSize(650, 500);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        f.setShape(new RoundRectangle2D.Double(0, 0, 650, 500, 25, 25));
        f.setVisible(true);
    }

    void home() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankjava", "root", "imemyselfshivam");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select acno,acbal from userdata where username ='" + username + "';");
            while (rs.next()) {
                accNo = "" + rs.getInt("acno");
                currbal = rs.getInt("acbal");
            }
        } catch (SQLException e) {
            homerror.setText(e.getMessage());
        }
    }

    void transact(String p) {
        int mode = transactmenu.getSelectedIndex(); // mode=0 dr.,1=cr.
        int amt ,pin;
        boolean temp=false; 
        
        try {
            amt = Integer.parseInt(transact.getText());
            pin = Integer.parseInt(p);
        } catch (Exception e) {
            transacterror.setText("*Pin/Amount can only be numeric");
            return;
        }
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select pin from userdata where username='"+username+"';");
               rs.next();
               if(pin==(rs.getInt("pin"))){
                temp=updatetransaction(amt);
               }else{
                JOptionPane.showMessageDialog(null,"Incorrect Pin","Error",JOptionPane.ERROR_MESSAGE);
                return;
               }
               
        } catch (Exception e) {
            transacterror.setText(e.getMessage());
            return;
        }

        if(temp)
        {if (mode == 0) {
            JOptionPane.showMessageDialog(null, "Rs. " + amt + " Debited from your bank account",
                    "Transaction Succesfull", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Rs. " + amt + " Credited to your bank account",
                    "Transaction Succesfull", JOptionPane.INFORMATION_MESSAGE);
        }}
    }

    void history() {
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from transactions where username ='" + username + "';");
            String[][] tabledata = new String[50][6];
            String[] coloums = { "SNo.", "Acc Number", "Amount", "Date", "Transfer Mode", "Balance" };
            int s = 1;
            while (rs.next()) {
                tabledata[s][0] = "" + s;
                tabledata[s][1] = accNo;
                tabledata[s][2] = rs.getString("amt");
                tabledata[s][3] = rs.getString("dateoftrans");
                tabledata[s][4] = rs.getString("transmode");
                tabledata[s][5] = rs.getString("acbal");
                s++;
            }
            jt = new JTable(tabledata, coloums);
            JScrollPane js = new JScrollPane(jt);
            historyPanel.add(js);
            jt.setEnabled(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void personal() {
    }

    boolean updatetransaction(int amt){
        try (Statement st = con.createStatement()) {
               if(currbal<=amt && transactmenu.getSelectedIndex()==0){
                JOptionPane.showMessageDialog(null,"Insufficient Balance","Error",JOptionPane.ERROR_MESSAGE);
                return false;
               }
               else{
                if(transactmenu.getSelectedIndex()==0){
                    currbal=currbal-amt;
                }
                if(transactmenu.getSelectedIndex()==1){
                    currbal=currbal+amt;
                }
                st.execute("update userdata set acbal="+currbal+" where username='"+username+"';");
                st.execute("insert into transactions value ("+accNo+",curDate(),"+amt+","+"'"+transactmenu.getSelectedItem()+"',"+currbal+",'"+username+"');");
                transactavlbal.setText("Current Bal : "+currbal+" Rs");
                homeaccbal.setText("Account balance                 :              " + currbal+" Rs");
            }
        } catch (SQLException e) {
            transacterror.setText(e.getMessage());
            return false;
        }
        return true; 
    }

    void deletentry(){
        try {//TODO deltee
            // Statement st=con.createStatement();
            // System.out.println(jt.getSelectedRow());
            // jt.removeAll();
            // jt.remove(3);
            // jt.repaint();
            // jt.put
            // history();
        } catch (Exception e) {

        }
    }

    void setTime(){
        Thread t1=new Thread();
        LocalDateTime d=LocalDateTime.now();
        System.out.println(d);
    }
}
