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


public class login {
    public static void main(String[] args) throws Exception {
        new loginframe();
    }
}

class loginframe {
    private String jdbcpassword="shivam123";
    JFrame framelogin;
    JPanel panel1,panel2;
    JTextField username=new JTextField();
    JPasswordField password=new JPasswordField();
    JLabel usernameLabel = new JLabel("Username"),passwordLabel = new JLabel("Password"),errormsg=new JLabel(),
    uicoimg,iconimg,exit,minimize,signuplink,creatorcred;
    JButton Login = new JButton("Login");

    
    loginframe() throws Exception{
        BufferedImage usericon=ImageIO.read(new File("startup-resources/login-user-icon1.png"));
        uicoimg=new JLabel(new ImageIcon(usericon));

        BufferedImage mainicon=ImageIO.read(new File("startup-resources/mainicon.png"));
        iconimg=new JLabel(new ImageIcon(mainicon));  
        
        panel1=new JPanel(null);
        panel2=new JPanel(null);
        panel1.setBackground(new Color(52,32,72));

        exit=new JLabel("X");
        minimize=new JLabel("_");
        signuplink=new JLabel("<HTML><U>New? Sign up...</U></HTML>");
        creatorcred=new JLabel("Created by Shivam");

        creatorcred.setForeground(Color.WHITE);
        signuplink.setForeground(Color.WHITE);

        minimize.setForeground(Color.white);
        exit.setForeground(Color.white);
        minimize.setSize(15, 15);
        exit.setSize(15, 15);
        
        
        exit.setBounds(10, 10, 15, 15);
        minimize.setBounds(31, 5, 15, 15);
        iconimg.setBounds(75, 100, 200, 160);
        signuplink.setBounds(200, 260, 100, 15);
        creatorcred.setBounds(10, 425, 120, 20);

        signuplink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    new signup();
                } catch (Exception ex) {}
                framelogin.dispose();
            }
        });

         exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
       
        });
        minimize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                framelogin.setState(Frame.ICONIFIED);
            }
       
        });

        panel1.add(exit);
        panel1.add(minimize);
        panel1.add(iconimg);
        panel1.add(signuplink);
        panel1.add(creatorcred);


        uicoimg.setBounds(75, 60, 200, 160);
        usernameLabel.setBounds(145, 260, 200, 20);
        username.setBounds(75, 285, 200, 20);
        passwordLabel.setBounds(145, 312, 200, 20);
        password.setBounds(75, 337, 200, 20);
        Login.setBounds(135, 363, 75, 20);

        Login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String pass=new String(password.getPassword());
                dbconnection(username.getText(),pass);
            }
            
        });

        errormsg.setBounds(50, 400, 230, 20);
        errormsg.setForeground(Color.red);
        errormsg.setVisible(false);
        
        panel2.add(usernameLabel);
        panel2.add(passwordLabel);
        panel2.add(username);
        panel2.add(password);
        panel2.add(Login);
        panel2.add(uicoimg);
        panel2.add(errormsg);

        framelogin=new JFrame();
        framelogin.setSize(700,450);
        framelogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framelogin.setLocationRelativeTo(null);
        framelogin.setUndecorated(true);
        framelogin.setLayout(new GridLayout(0, 2));
        framelogin.add(panel1);
        framelogin.add(panel2);
        framelogin.setVisible(true);
    }

    void dbconnection(String uname,String pass){
        try {
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankjava", "root",jdbcpassword);
            Statement st=con.createStatement();

            if(uname!="" && pass!=""){
                ResultSet rs =st.executeQuery("select name from userdata where username='"+uname+"' and password ='"+pass+"'");
                if(rs.next()){
                    String name=rs.getString("name");
                    new mainwindow(uname,name);
                    framelogin.dispose();
                }else{
                    errormsg.setText("*Username or Password is incorrect.");
                }
                errormsg.setVisible(true);
            }
        } catch (Exception e) {
            errormsg.setText(e.getMessage());
            errormsg.setVisible(true);
        }
    }
}
