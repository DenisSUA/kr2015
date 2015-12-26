import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Stopwatch extends JFrame{

    JTextField field_time;
    int sec = 0;


    Timer t = new Timer(1000, new Listener());

    public void setSec(int sec){
        this.sec = sec;
        field_time.setText(Integer.toString(sec));
    }

    public int getSec(){
        return sec;
    }

    class Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int sec = getSec();
            sec++;
            setSec(sec);
        }
    }



    public void Start(){
        t.start();
    }

    public void stop(){
        t.stop();
    }

}
