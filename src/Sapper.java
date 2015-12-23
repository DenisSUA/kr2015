
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Sapper extends JFrame implements ActionListener, ContainerListener{
    int blockr, blockc, width, heigth, mines,savedblockr,savedblockc,savednum_of_mine, savedlevel, detectedmine;
    int[][] colour;
    JButton reset = new JButton();
    boolean check = true;
    boolean start_time = false;
    Stopwatch sw;
    MouseHandler mh;
    JButton[][] blocks;
    int[][] count_mine;
    Point p;
    JPanel blocks_panel = new JPanel();
    JPanel mine_time_panel = new JPanel();
    JTextField field_mine,field_time;
    ImageIcon[] icons = new ImageIcon[14];
    int var1,var2;



    Sapper() {
        setTitle("Sapper");
        setLocation(650, 300);
        setIcon();
        setPanel(1, 0, 0, 0);
        setMenu();

        sw = new Stopwatch();

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                Button b= new Button("Restart");
                try {
                    sw.stop();
                    setPanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
                } catch (Exception e) {
                    setPanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
                }
                reset();

            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        show();
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

    public void  setPanel(int level, int setr, int setc, int setm){
        if (level == 1){
            width = 350;
            heigth = 450;
            blockr = 11;
            blockc = 11;
            mines = 10;
        } else if (level == 2){
            width = 520;
            heigth = 616;
            blockr = 18;
            blockc = 18;
            mines = 70;
        } else if (level == 3){
            width = 640;
            heigth = 730;
            blockr = 22;
            blockc = 22;
            mines = 150;
        } else if (level == 4){
            width = (20 * setc);
            heigth = (24 * setr);
            blockr = setr;
            blockc = setc;
            mines = setm;

        }


        savednum_of_mine = mines;
        detectedmine = mines;
        p = this.getLocation();

        setSize(width, heigth);
        setResizable(false);

        mh = new MouseHandler();

        blocks = new JButton[blockr][blockc];
        count_mine = new int[blockr][blockc];
        colour = new int[blockr][blockc];

        getContentPane().removeAll();
        blocks_panel.removeAll(); // изменяется размер кубиков

        field_mine = new JTextField("   " + mines, 3);
        field_mine.setEditable(false);
        field_mine.setFont(new Font("", Font.BOLD, 35));
        field_mine.setBackground(Color.DARK_GRAY);
        field_mine.setForeground(Color.white);


        field_time = new JTextField("    0", 3);
        field_time.setEditable(false);
        field_time.setFont(new Font("", Font.BOLD, 35));
        field_time.setBackground(Color.DARK_GRAY);
        field_time.setForeground(Color.WHITE);


        mine_time_panel.setBorder(BorderFactory.createLoweredBevelBorder());
        mine_time_panel.removeAll(); //панелька с временем и минами обретает еще одну кнопку
        BorderLayout bl = new BorderLayout();
        mine_time_panel.setLayout(bl);
        mine_time_panel.add(field_mine, bl.WEST);
        //Button b = new Button("Restart");
        mine_time_panel.add(reset, bl.CENTER);
        mine_time_panel.add(field_time, bl.EAST);
        mine_time_panel.setBackground(Color.BLACK);




        Dimension d = new Dimension(width, heigth);
        blocks_panel.setPreferredSize(d);
        blocks_panel.setMinimumSize(blocks_panel.getPreferredSize());// «не рекомендуется» увеличиваться или уменьшаться
        GridLayout gl = new GridLayout(0, blockc);
        blocks_panel.setLayout(gl);
        blocks_panel.addContainerListener(this);
        blocks_panel.setBackground(Color.DARK_GRAY);
        blocks_panel.setBorder(BorderFactory.createEmptyBorder(0,3,0,1));


        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                blocks[i][j] = new JButton();
                blocks[i][j].addMouseListener(mh);
                blocks_panel.add(blocks[i][j]);
            }
        }
        reset();

        blocks_panel.revalidate();

        BorderLayout bl1 = new BorderLayout();
        getContentPane().setLayout(bl1);
        getContentPane().addContainerListener((ContainerListener) this);

        getContentPane().repaint();
        getContentPane().add(blocks_panel, bl1.CENTER);
        getContentPane().add(mine_time_panel, bl1.NORTH);
        setVisible(true);


    }





    public void setMenu() {
        JMenuBar bar = new JMenuBar();
        //JButton center = new JButton("dddd");


        JMenu instr = new JMenu("Rules");
        final JMenuItem rules = new JMenuItem("Rules");

        JMenu game = new JMenu("Levels");
        final JMenuItem menuitem = new JMenuItem("new game");
        final JMenuItem beginner = new JMenuItem("Beginner");
        final JMenuItem intermediate = new JMenuItem("Intermediate");
        final JMenuItem expert = new JMenuItem("Expert");
        final JMenuItem exit = new JMenuItem("Exit");


        //Button rest = new Button("dddd");





        /*rest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setpanel(1,0,0,0);
            }
        });*/


        menuitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setPanel(1, 0, 0, 0);
            }
        });

        beginner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reset();
                setPanel(1, 0, 0, 0);
                beginner.setSelected(true);
                savedlevel = 1;
            }
        });

        intermediate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
                setPanel(2, 0, 0, 0);
                intermediate.setSelected(true);
                savedlevel = 2;
            }
        });
        expert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
                setPanel(3, 0, 0, 0);
                expert.setSelected(true);
                savedlevel = 3;
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
        game.addSeparator();
        game.add(exit);

        instr.add(rules);

        bar.add(game);
        bar.add(instr);



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


    class MouseHandler extends MouseAdapter {
        int blockr, blockc,var1,var2;
        JButton[][] blocks;
        boolean check = true;
        Stopwatch sw;
        boolean start_time = false;


        public void mouseClicked(MouseEvent me) {
            if (check == true) {
                for (int i = 0; i < blockr; i++) {
                    for (int j = 0; j < blockc; j++) {
                        if (me.getSource() == blocks[i][j]) {
                            var1 = i;
                            var2 = j;
                            i = blockr;
                            break;
                        }
                    }
                }

                setBomb();
                calculation();
                check = false;
            }

            showValue(me);
            winner();

            if (start_time == false) {
                sw.start(); //время не хочет идти
                start_time = true;
            }

        }



    }
    public void winner() {
        int q = 0;
        for (int k = 0; k < blockr; k++) {
            for (int l = 0; l < blockc; l++) {
                if (colour[k][l] == 'w') {
                    q = 1;
                }
            }
        }


        if (q == 0) {
            for (int k = 0; k < blockr; k++) {
                for (int l = 0; l < blockc; l++) {
                    blocks[k][l].removeMouseListener(mh);
                }
            }

            sw.stop();
            JOptionPane.showMessageDialog(this, "Congratulations!");
        }
    }

    public void showValue(MouseEvent e) {
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {

                if (e.getSource() == blocks[i][j]) {
                    if (e.isMetaDown() == false) {
                        if (blocks[i][j].getIcon() == icons[10]) {
                            if (detectedmine < mines) {
                                detectedmine++;
                            }
                            field_mine.setText("" + detectedmine);
                        }

                        if (count_mine[i][j] == -1) {
                            for (int k = 0; k < blockr; k++) {
                                for (int l = 0; l < blockc; l++) {
                                    if (count_mine[k][l] == -1) {
                                        blocks[k][l].setIcon(new ImageIcon("mine.gif"));
                                        blocks[k][l].removeMouseListener(mh);
                                    }
                                    blocks[k][l].removeMouseListener(mh);
                                }
                            }
                            sw.stop();
                            reset.setIcon(new ImageIcon(""));
                            JOptionPane.showMessageDialog(null, "Try one more time!");
                        } else if (count_mine[i][j] == 0) {
                            setEmpties(i, j);
                        } else {
                            blocks[i][j].setIcon(icons[count_mine[i][j]]);
                            colour[i][j] = 'b';
                            break;
                        }
                    } else {
                        if (detectedmine != 0) {
                            if (blocks[i][j].getIcon() == null) {
                                detectedmine--;
                                blocks[i][j].setIcon(new ImageIcon("flag.gif"));
                            }
                            field_mine.setText("" + detectedmine);
                        }


                    }
                }

            }
        }

    }

    int[] r = {-1, 1, -1, 0, 1, 1, -1, 0};
    int[] c = {1, 0, 0, 1, 1, -1, -1, -1};

    public void calculation() {
        int row, col;

        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                int value = 0;
                int R, C;
                row = i;
                col = j;
                if (count_mine[row][col] != -1) {
                    for (int k = 0; k < 8; k++) {
                        R = row + r[k];
                        C = col + c[k];

                        if (R >= 0 && C >= 0 && R < blockr && C < blockc) {
                            if (count_mine[R][C] == -1) {
                                value++;
                            }

                        }
                    }
                    count_mine[row][col] = value;

                }
            }
        }
    }


    public void setEmpties(int row, int col) {

        int R, C;
        colour[row][col] = 'b';

        blocks[row][col].setBackground(Color.GRAY);
        blocks[row][col].setIcon(icons[count_mine[row][col]]);
        blocks[row][col].setText("");
        for (int i = 0; i < 8; i++) {
            R = row + r[i];
            C = col + c[i];
            if (R >= 0 && R < blockr && C >= 0 && C < blockc && colour[R][C] == 'w') {
                if (count_mine[R][C] == 0) {
                    setEmpties(R, C);
                } else {
                    blocks[R][C].setIcon(icons[count_mine[R][C]]);
                    colour[R][C] = 'b';
                }
            }


        }
    }

    /*public void setIcon() {
        String name;

        for (int i = 0; i <= 8; i++) {
            name = i + ".gif";
            icons[i] = new ImageIcon(name);
        }
    }*/
    Random random_rows = new Random();
    Random random_columns = new Random();

    public void setBomb() {
        int row = 0, col = 0;
        Boolean[][] flag = new Boolean[blockr][blockc];


        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                flag[i][j] = true;
                count_mine[i][j] = 0;
            }
        }

        flag[var1][var2] = false;
        colour[var1][var2] = 'r';

        for (int i = 0; i < mines; i++) {
            row = random_rows.nextInt(blockr);
            col = random_columns.nextInt(blockc);

            if (flag[row][col] == true) {

                count_mine[row][col] = -1;
                flag[row][col] = false;
            } else {
                i--;
            }
        }
    }
    public void setIcon() {
        String name;

        for (int i = 0; i <= 8; i++) {
            name = i + ".gif";
            icons[i] = new ImageIcon(name);
        }
        //icons[10] = new ImageIcon("flag.gif");
    }
}