
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sapper extends JFrame implements ActionListener{
    int blockr, blockc, width, heigth, num_of_mine,savedblockr,savedblockc,savednum_of_mine, savedlevel;
    int[][] colour;
    JButton reset = new JButton("");
    boolean check = true;
    boolean starttime = false;
    Stopwatch sw;
    MouseHandler mh;
    JButton[][] blocks;
    int[][] count_mine;
    Point p;
    JPanel panelb = new JPanel();


    Sapper() {
        setLocation(400, 300);
        setpanel(1,0,0,0);

        sw = new Stopwatch();

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                try {
                    sw.stop();
                    setpanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
                } catch (Exception e) {
                    setpanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
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

    public void  setpanel(int level, int setr, int setc, int setm){
        if (level == 1){
            width = 200;
            heigth = 300;
            blockr = 10;
            blockc = 10;
            num_of_mine = 10;
        } else if (level == 2){
            width = 320;
            heigth = 416;
            blockr = 16;
            blockc = 16;
            num_of_mine = 70;
        } else if (level == 3){
            width = 400;
            heigth = 520;
            blockr = 20;
            blockc = 20;
            num_of_mine = 150;
        } else if (level == 4){
            width = (20 * setc);
            heigth = (24 * setr);
            blockr = setr;
            blockc = setc;
            num_of_mine = setm;

        }

        savedblockr = blockr;
        savedblockc = blockc;
        savednum_of_mine = num_of_mine;
        p = this.getLocation();

        setSize(width, heigth);
        setResizable(false);

        mh = new MouseHandler();

        blocks = new JButton[blockr][blockc];
        count_mine = new int[blockr][blockc];
        colour = new int[blockr][blockc];

        getContentPane().removeAll();
        panelb.removeAll();
    }





    public void setMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu game = new JMenu("Game");

        JMenuItem menuitem = new JMenuItem("new game");
        final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Begineer");
        final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Intermediate");
        final JCheckBoxMenuItem expert = new JCheckBoxMenuItem("Expart");
        final JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Custom");
        final JMenuItem exit = new JMenuItem("Exit");
        final JMenu help = new JMenu("Help");
        final JMenuItem helpitem = new JMenuItem("Help");

        menuitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setpanel(1, 0, 0, 0);
            }
        });

        beginner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reset();
                setpanel(1, 0, 0, 0);
                beginner.setSelected(true);
                savedlevel = 1;
            }
        });

        intermediate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
                setpanel(2, 0, 0, 0);
                intermediate.setSelected(true);
                savedlevel = 2;
            }
        });
        expert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
                setpanel(3, 0, 0, 0);
                expert.setSelected(true);
                savedlevel = 3;
            }
        });

        custom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Customization cus = new Customization();
                reset();
                custom.setSelected(true);
                savedlevel = 4;
            }
        });

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setJMenuBar(bar);

        game.add(menuitem);
        game.addSeparator();
        game.add(beginner);
        game.add(intermediate);
        game.add(expert);
        game.add(custom);
        game.addSeparator();
        game.add(exit);
        help.add(helpitem);

        bar.add(game);
        bar.add(help);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}