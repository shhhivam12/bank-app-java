import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class signup {
    JFrame signpFrame;
        JPanel panel1,panel2;
        JLabel uicoimg,iconimg,exit,minimize,loginlink,creatorcred;
    signup() throws Exception{
        BufferedImage mainicon=ImageIO.read(new File("mainicon.png"));
        iconimg=new JLabel(new ImageIcon(mainicon));

        panel1=new JPanel(null);
        panel2=new JPanel(null);
        panel2.setBackground(new Color(52,32,72));

        exit=new JLabel("X");
        minimize=new JLabel("_");
        loginlink=new JLabel("<HTML><U>Existing user?Log In..</U></HTML>");
        creatorcred=new JLabel("Created by Shivam");

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
                    
                } catch (Exception ex) {}
                signpFrame.dispose();
            }
        });

         exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
       
        });
        minimize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                signpFrame.setState(Frame.ICONIFIED);
            }
       
        });

        panel2.add(exit);
        panel2.add(minimize);
        panel2.add(iconimg);
        panel2.add(loginlink);
        panel2.add(creatorcred);

        signpFrame=new JFrame();
        signpFrame.setSize(700,450);
        signpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signpFrame.setLocationRelativeTo(null);
        signpFrame.setUndecorated(true);
        signpFrame.setLayout(new GridLayout(0, 2));
        signpFrame.add(panel1);
        signpFrame.add(panel2);
        signpFrame.setVisible(true);
    }
}
