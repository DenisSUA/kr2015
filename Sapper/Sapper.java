
import java.awt.event.*;
import javax.swing.*;

public class Sapper extends JFrame implements ActionListener{
    int blockr, blockc;
    int[][] colour;
    JButton reset = new JButton("");
    boolean check = true;
    boolean starttime = false;


    Sapper() {
        setLocation(400, 300);



        reset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent a) {
                try {

                } catch (Exception e) {

                }
                reset();

            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void reset() {
        check = true;
        starttime = false;
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                colour[i][j] = 'w';
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}