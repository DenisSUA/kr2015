import javax.swing.*;


public class Stopwatch extends JFrame implements Runnable{
    boolean isRunning = false;
    Thread updater;
    long a = 0;
    JTextField field_time;

    public void start() {
        long startTime = System.currentTimeMillis();
        isRunning = true;
        updater = new Thread(this);
        updater.start();
    }

    public void stop(){
        long elapsed = a;
        isRunning = false;
        try {
            updater.join();
        } catch (InterruptedException ie) {

        }
        displayElapsedTime(elapsed);
        a = 0;
    }

    public void displayElapsedTime(long elapsed) {

        if (elapsed >= 0 && elapsed < 9) {
            field_time.setText("00" + elapsed);
        } else if (elapsed > 9 && elapsed < 99) {
            field_time.setText("0" + elapsed);
        } else if (elapsed > 99 && elapsed < 999) {
            field_time.setText("" + elapsed);
        }
    }



    public void run() {

        try {
            while (isRunning) {
                SwingUtilities.invokeAndWait(displayUpdater);
                Thread.sleep(1000);
            }
        } catch (java.lang.reflect.InvocationTargetException ite) {
            ite.printStackTrace(System.err);
        } catch (InterruptedException ie) {
        }
    }

    Runnable displayUpdater = new Runnable() {

        public void run() {
            displayElapsedTime(a);
            a++;
        }
    };
}
