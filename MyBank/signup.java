import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class signup {
    private String jdbcpassword="shivam123";
    JFrame signpFrame;
    JPanel panel1, panel2;
    JLabel uicoimg, iconimg, exit, minimize, loginlink, creatorcred; // signup logo pane2
    // panel1 elements
    JLabel signuptext, nameJLabel, usernameJLabel, passwordJLabel, dobJLabel, pinJLabel, errormsg;
    JButton resetJButton, createJButton;
    JTextField usernameJTextField, nameJTextField, passwordJTextField, dobJTextField, pinJTextField;

    signup() throws Exception {
        BufferedImage mainicon = ImageIO.read(new File("startup-resources/mainicon.png"));
        iconimg = new JLabel(new ImageIcon(mainicon));

        panel1 = new JPanel(new GridLayout(14, 1));
        panel2 = new JPanel(null);
        panel2.setBackground(new Color(52, 32, 72));

        // panel1 shit-->
        signuptext = new JLabel("Sign up");
        signuptext.setHorizontalAlignment(JLabel.CENTER);

        nameJLabel = new JLabel("Enter your name : ");
        nameJLabel.setHorizontalAlignment(JLabel.CENTER);

        dobJLabel = new JLabel("Enter your date of birth(YYYY-MM-DD) : ");
        dobJLabel.setHorizontalAlignment(JLabel.CENTER);

        usernameJLabel = new JLabel("Set username : ");
        usernameJLabel.setHorizontalAlignment(JLabel.CENTER);

        passwordJLabel = new JLabel("Set password : ");
        passwordJLabel.setHorizontalAlignment(JLabel.CENTER);

        pinJLabel = new JLabel("Set Account pin(4 digits) : ");
        pinJLabel.setHorizontalAlignment(JLabel.CENTER);

        nameJTextField = new JTextField();
        nameJTextField.setHorizontalAlignment(JLabel.CENTER);

        dobJTextField = new JTextField();
        dobJTextField.setHorizontalAlignment(JLabel.CENTER);

        usernameJTextField = new JTextField();
        usernameJTextField.setHorizontalAlignment(JLabel.CENTER);

        passwordJTextField = new JTextField();
        passwordJTextField.setHorizontalAlignment(JLabel.CENTER);

        pinJTextField = new JTextField();
        pinJTextField.setHorizontalAlignment(JLabel.CENTER);

        errormsg = new JLabel("",SwingConstants.CENTER);
        errormsg.setForeground(Color.red);

        createJButton = new JButton("Create Account");
        createJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag=checkfeilds();
                if(flag)
                {dbconnection();}
            }

        });
        resetJButton = new JButton("reset");
        resetJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameJTextField.setText("");
                nameJTextField.setText("");
                passwordJTextField.setText("");
                dobJTextField.setText("");
                pinJTextField.setText("");
                errormsg.setText("");
            }

        });

        panel1.add(signuptext);
        panel1.add(nameJLabel);
        panel1.add(nameJTextField);
        panel1.add(dobJLabel);
        panel1.add(dobJTextField);
        panel1.add(usernameJLabel);
        panel1.add(usernameJTextField);
        panel1.add(passwordJLabel);
        panel1.add(passwordJTextField);
        panel1.add(pinJLabel);
        panel1.add(pinJTextField);
        panel1.add(createJButton);
        panel1.add(resetJButton);
        panel1.add(errormsg);

        // panel2 shit-->
        exit = new JLabel("X");
        minimize = new JLabel("_");
        loginlink = new JLabel("<HTML><U>Existing user?Log In..</U></HTML>");
        creatorcred = new JLabel("Created by Shivam");

        creatorcred.setForeground(Color.WHITE);
        loginlink.setForeground(Color.WHITE);

        minimize.setForeground(Color.white);
        exit.setForeground(Color.white);
        minimize.setSize(15, 15);
        exit.setSize(15, 15);

        exit.setBounds(330, 10, 15, 15);
        minimize.setBounds(310, 5, 15, 15);
        iconimg.setBounds(75, 100, 200, 160);
        loginlink.setBounds(180, 240, 140, 15);
        creatorcred.setBounds(10, 425, 120, 20);

        loginlink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    new loginframe();

                } catch (Exception ex) {
                }
                signpFrame.dispose();
            }
        });

        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

        });
        minimize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                signpFrame.setState(Frame.ICONIFIED);
            }

        });

        panel2.add(exit);
        panel2.add(minimize);
        panel2.add(iconimg);
        panel2.add(loginlink);
        panel2.add(creatorcred);

        signpFrame = new JFrame();
        signpFrame.setSize(700, 450);
        signpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signpFrame.setLocationRelativeTo(null);
        signpFrame.setUndecorated(true);
        signpFrame.setLayout(new GridLayout(0, 2));
        signpFrame.add(panel1);
        signpFrame.add(panel2);
        signpFrame.setVisible(true);
    }

    boolean checkfeilds(){
        if(usernameJTextField.getText().isEmpty() || nameJTextField.getText().isEmpty()|| passwordJTextField.getText().isEmpty()|| dobJTextField.getText().isEmpty()|| pinJTextField.getText().isEmpty())
        {errormsg.setText("*Fill all the feilds to continue");return false;}

        if(nameJTextField.getText().length()>15 || usernameJTextField.getText().length()>15 || passwordJTextField.getText().length() >15){
            errormsg.setText("Fields <15 not allowed");
            return false;
        }
        else if(pinJTextField.getText().length()>4){
            errormsg.setText("PIN must be less than or equal to 4 digits");
            return false;
        }
        try {
            Integer.parseInt(pinJTextField.getText());
        } catch (Exception e) {errormsg.setText("pin can only contain digits");return false;}
        try {
            LocalDate.parse(dobJTextField.getText());
        } catch (Exception e) {errormsg.setText("Date should be in YYYY-MM-DD");return false;}
        return true;
    }

    void dbconnection() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankjava", "root",
                    jdbcpassword);
            Statement st = con.createStatement();
            String uname = usernameJTextField.getText();
            String name = nameJTextField.getText();
            String pass = passwordJTextField.getText();
            String dob = dobJTextField.getText();
            String pin = pinJTextField.getText();
            String acno = "" + (int) (Math.random() * 100000);

            int temp = st.executeUpdate("insert into userdata values ('" + name + "','" + dob + "','" + uname + "','"
                    + pass + "'," + pin + "," + acno + ",0);"); 

            st.executeUpdate("insert into transactions values ("+acno+",curdate(),0 ,'Cr.',0,'"+uname+"');");
            if (temp == 1) {
                JOptionPane.showMessageDialog(null, "Account created sucessfully!");
                errormsg.setText("*successful");
            } else {
                errormsg.setText("*Unsuccessful");
            }
        } catch (Exception e) {
            errormsg.setText(e.getMessage());
        }
    }
}
