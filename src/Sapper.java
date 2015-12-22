
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sapper extends JFrame implements ActionListener, ContainerListener{
    int blockr, blockc, width, heigth, num_of_mine,savedblockr,savedblockc,savednum_of_mine, savedlevel;
    int[][] colour;
    JButton reset = new JButton();
    boolean check = true;
    boolean start_time = false;
    Stopwatch sw;
    MouseHandler mh;
    JButton[][] blocks;
    int[][] count_mine;
    Point p;
    JPanel panelb = new JPanel();
    JPanel panelmt = new JPanel();
    JTextField field_mine,field_time;



    Sapper() {
        setLocation(650, 300);
        setpanel(1,0,0,0);
        setMenu();

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
        start_time = false;
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                colour[i][j] = 'w';
            }
        }
    }

    public void  setpanel(int level, int setr, int setc, int setm){
        if (level == 1){
            width = 350;
            heigth = 450;
            blockr = 11;
            blockc = 11;
            num_of_mine = 10;
        } else if (level == 2){
            width = 520;
            heigth = 616;
            blockr = 18;
            blockc = 18;
            num_of_mine = 70;
        } else if (level == 3){
            width = 640;
            heigth = 730;
            blockr = 22;
            blockc = 22;
            num_of_mine = 150;
        } else if (level == 4){
            width = (20 * setc);
            heigth = (24 * setr);
            blockr = setr;
            blockc = setc;
            num_of_mine = setm;

        }


        savednum_of_mine = num_of_mine;
        p = this.getLocation();

        setSize(width, heigth);
        setResizable(false);

        mh = new MouseHandler();

        blocks = new JButton[blockr][blockc];
        count_mine = new int[blockr][blockc];
        colour = new int[blockr][blockc];

        getContentPane().removeAll();
        panelb.removeAll(); // изменяется размер кубиков

        field_mine = new JTextField("   " + num_of_mine, 3);
        field_mine.setEditable(false);
        field_mine.setFont(new Font("", Font.BOLD, 35));
        field_mine.setBackground(Color.DARK_GRAY);
        field_mine.setForeground(Color.white);


        field_time = new JTextField("    0", 3);
        field_time.setEditable(false);
        field_time.setFont(new Font("", Font.BOLD, 35));
        field_time.setBackground(Color.DARK_GRAY);
        field_time.setForeground(Color.WHITE);



        panelmt.removeAll(); //панелька с временем и минами обретает еще одну кнопку
        panelmt.setLayout(new BorderLayout());
        panelmt.add(field_mine, BorderLayout.WEST);
        panelmt.add(new Button("to begin a new game"), BorderLayout.CENTER);
        panelmt.add(field_time, BorderLayout.EAST);



        Dimension d = new Dimension(width, heigth);
        panelb.setMinimumSize(panelb.getPreferredSize());// «не рекомендуется» увеличиваться или уменьшаться
        GridLayout gl = new GridLayout(0, blockc);
        panelb.setLayout(gl);
        panelb.addContainerListener(this);

        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                blocks[i][j] = new JButton();
                blocks[i][j].addMouseListener(mh);
                panelb.add(blocks[i][j]);
            }
        }
        reset();



        getContentPane().setLayout(new BorderLayout());
        getContentPane().addContainerListener((ContainerListener) this);

        getContentPane().repaint();
        getContentPane().add(panelb, BorderLayout.CENTER);
        getContentPane().add(panelmt, BorderLayout.NORTH);
        setVisible(true);
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

    @Override
    public void componentAdded(ContainerEvent containerEvent) {

    }

    @Override
    public void componentRemoved(ContainerEvent containerEvent) {

    }
}