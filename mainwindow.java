import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

class mainwindow {
    public static void main(String[] args) {
        new mainwindow("hello");
    }

    String username;
    CardLayout c1;
    JPanel topPanel,centrePanel,bottomPanel;  //panels for frame window
    JPanel homePanel,transactPanel,tHistoryPanel,personalPanel; //panels for card layout(centre panel)
    mainwindow(String username){
        this.username = username;
        JFrame f=new JFrame();

        topPanel=new JPanel(null);
        centrePanel=new JPanel();
        bottomPanel=new JPanel();
        c1=new CardLayout();
        centrePanel.setLayout(c1);

        //top panel shit-->


        f.setLayout(new BorderLayout());
        f.add(topPanel,BorderLayout.NORTH);
        f.add(centrePanel,BorderLayout.CENTER);
        f.add(bottomPanel,BorderLayout.SOUTH);
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

