import javax.swing.*;

/**
 * Created by Администратор on 20.12.2015.
 */
public class Stopwatch extends JFrame implements Runnable{
    boolean isRunning = false;
    Thread updater;
    public void start() {
        long startTime = System.currentTimeMillis();
        isRunning = true;
        updater = new Thread(this);
        updater.start();

    }

    public void stop(){
        isRunning = false;

    }

    @Override
    public void run() {

    }
}
