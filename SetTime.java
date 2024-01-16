import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SetTime extends Thread{
    public void run(){
        while (true) {
            DateTimeFormatter format=DateTimeFormatter.ofPattern("E, dd MMM yyyy       HH:mm:ss a");
            mainwindow.toppaneltime.setText(LocalDateTime.now().format(format));
        }
    }
    
}
