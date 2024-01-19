public class banner extends Thread{
    public void run(){
        while(true){
            try {
                Thread.sleep(5000);
                mainwindow.c2.next(mainwindow.h22);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
