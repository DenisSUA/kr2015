import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by citrus on 27.12.15.
 */
public class Sapper implements ActionListener {
    int blockr, blockc, width, height,mines,savedlevel;

    JFrame frame = new JFrame("Sapper");
    JButton reset = new JButton("reset");
    JButton [][] buttons;
    Container grid = new Container();
    int [][] count_mine;
    int saved_mines;
    JPanel button_panel = new JPanel();
    JPanel mine_time_panel = new JPanel();
    JTextField field_mine;
    JTextField field_time;


    public Sapper() {
        setPanel(1);
        setMenu();
        frame.setLocation(550,200);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        reset.addActionListener(this);

        grid.setLayout(new GridLayout(blockr, blockc));
        for (int a = 0; a < blockr; a++) {
            for (int b = 0; b < blockc; b++) {/////////////////////////////////
                buttons[a][b] = new JButton();
                buttons[a][b].addActionListener(this);
                grid.add(buttons[a][b]);
            }

        }

        frame.add(grid, BorderLayout.CENTER);
        setMines();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }



    public void  setPanel(int level) {
        if (level == 1) {
            width = 350;
            height = 450;
            blockr = 11;
            blockc = 11;
            mines = 10;
        } else if (level == 2) {
            width = 520;
            height = 616;
            blockr = 18;
            blockc = 18;
            mines = 70;
        } else if (level == 3) {
            width = 640;
            height = 730;
            blockr = 22;
            blockc = 22;
            mines = 150;
        } else if (level == 4) {
            width = 640;
            height = 730;
            blockr = 22;
            blockc = 22;
            mines = 482;
        }
        buttons = new JButton[blockr][blockc];
        count_mine = new int[blockr][blockc];


        /*mine_time_panel.setBorder(BorderFactory.createLoweredBevelBorder());
        mine_time_panel.removeAll(); //панелька с временем и минами обретает еще одну кнопку
        BorderLayout bl = new BorderLayout();
        mine_time_panel.setLayout(bl);
        mine_time_panel.add(field_mine, bl.WEST);
        //Button b = new Button("Restart");
        mine_time_panel.add(reset, bl.CENTER);
        mine_time_panel.add(field_time, bl.EAST);
        mine_time_panel.setBackground(Color.BLACK);*/

    }

    public void setMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu game = new JMenu("Levels");
        final JMenuItem menuitem = new JMenuItem("new game");
        final JMenuItem beginner = new JMenuItem("Beginner");
        final JMenuItem intermediate = new JMenuItem("Intermediate");
        final JMenuItem expert = new JMenuItem("Expert");
        final JMenuItem hell = new JMenuItem("HEEEELLL!!!");
        final JMenuItem exit = new JMenuItem("Exit");

        menuitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setPanel(1);
            }
        });

        beginner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                setPanel(1);
                savedlevel = 1;
            }
        });

        intermediate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                setPanel(2);
                savedlevel = 2;
            }
        });
        expert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                setPanel(3);
                savedlevel = 3;
            }
        });


        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        hell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                setPanel(4);
                JOptionPane.showMessageDialog(null, "Die, die,die, my darling!");
                savedlevel = 4;
            }
        });


        frame.setJMenuBar(bar);


        game.add(menuitem);
        game.addSeparator();
        game.add(beginner);
        game.add(intermediate);
        game.add(expert);
        game.add(hell);
        game.addSeparator();
        game.add(exit);

        bar.add(game);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reset)) {
            // reset
            for (int x = 0; x < buttons.length; x++) {
                for (int y = 0; y < buttons[0].length; y++) {
                    buttons[x][y].setEnabled(true);
                    buttons[x][y].setText("");
                }
            }
            setMines();

        } else {
            for (int x = 0; x < buttons.length; x++) {
                for (int y = 0; y < buttons[0].length; y++) {
                    if (e.getSource().equals(buttons[x][y])) {
                        if (count_mine[x][y] == mines){
                            buttons[x][y].setForeground(Color.red);
                            buttons[x][y].setText("X");
                            lose();

                        }
                        else if(count_mine[x][y] == 0){
                            buttons[x][y].setText(count_mine[x][y] + "");
                            buttons[x][y].setEnabled(false);
                            ArrayList<Integer> empty = new ArrayList<Integer>();
                            empty.add(x*100+y);
                            setEmpties(empty);
                            checkWin();
                        }
                        else{
                            buttons[x][y].setText(count_mine[x][y] + "");
                            buttons[x][y].setEnabled(false);
                            checkWin();
                        }
                    }
                }
            }
        }
    }

    public void setMines() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < count_mine.length; x++) {
            for (int y = 0; y < count_mine[0].length; y++) {
                list.add(x * 100 + y);
            }
        }


        count_mine = new int[blockr][blockc];
        saved_mines = mines;
        for (int a = 0; a < saved_mines; a++) {
            int choice = (int) (Math.random() * list.size());
            count_mine[list.get(choice)/100][list.get(choice)%100] = mines;  // [.../100][...%100]
            list.remove(choice);
        }

        for (int x = 0; x < count_mine.length; x++) {
            for (int y = 0; y < count_mine[0].length; y++) {
                if (count_mine[x][y] != mines) {

                    int neighbourcount = 0;
                    if (x > 0 && y > 0 && count_mine[x - 1][y - 1] == mines) { //верх лево
                        neighbourcount++;
                    }
                    if (y > 0 && count_mine[x][y - 1] == mines) {  //верх
                        neighbourcount++;
                    }
                    if (x < count_mine.length - 1 && y < count_mine[0].length - 1 && count_mine[x + 1][y + 1] == mines) {  //низ право
                        neighbourcount++;
                    }
                    if (x > 0 && count_mine[x-1][y] == mines){ // влево
                        neighbourcount++;
                    }
                    if (x < count_mine.length - 1 && count_mine[x+1][y] == mines){ //вправо
                        neighbourcount++;
                    }
                    if (x > 0 && y < count_mine[0].length - 1 && count_mine[x-1][y+1] == mines){ // вниз влево
                        neighbourcount++;
                    }
                    if (y < count_mine[0].length - 1 && count_mine[x][y+1] == mines){ // вниз
                        neighbourcount++;
                    }
                    if (x < count_mine.length -1 && y > 0 && count_mine[x + 1][y - 1] == mines){ //вверх вправо
                        neighbourcount++;
                    }

                    count_mine[x][y] = neighbourcount;

                }
            }
        }
    }



    public void setEmpties(ArrayList<Integer> empty){
        if (empty.size() == 0){
            return;
        }
        else{
            int x = empty.get(0) / 100;
            int y = empty.get(0) % 100;
            empty.remove(0);
            if(count_mine[x][y] == 0){
                if(x > 0 && y > 0 && buttons[x-1][y-1].isEnabled()){  // вверх влево
                    buttons[x-1][y-1].setText(count_mine[x-1][y-1] + "");
                    buttons[x-1][y-1].setEnabled(false);
                    if(count_mine[x-1][y-1] == 0){
                        empty.add((x-1) * 100 + (y-1));
                    }
                }
                if(y > 0 && buttons[x][y-1].isEnabled()){ // вверх
                    buttons[x][y-1].setText(count_mine[x][y-1] + "");
                    buttons[x][y-1].setEnabled(false);
                    if(count_mine[x][y-1] == 0){
                        empty.add(x * 100 + (y-1));
                    }
                }
                if(x < count_mine.length - 1 && y > 0 && buttons[x+1][y-1].isEnabled()){ //вверх вправо
                    buttons[x+1][y-1].setText(count_mine[x+1][y-1] + "");
                    buttons[x+1][y-1].setEnabled(false);
                    if(count_mine[x+1][y-1] == 0){
                        empty.add((x+1) * 100 + (y-1));
                    }
                }
                if(x > 0 && buttons[x-1][y].isEnabled()){  //  влево
                    buttons[x-1][y].setText(count_mine[x-1][y] + "");
                    buttons[x-1][y].setEnabled(false);
                    if(count_mine[x-1][y] == 0){
                        empty.add((x-1) * 100 + (y));
                    }
                }

                if(x < count_mine.length - 1 && buttons[x+1][y].isEnabled()){ // вправо
                    buttons[x+1][y].setText(count_mine[x+1][y] + "");
                    buttons[x+1][y].setEnabled(false);
                    if(count_mine[x+1][y] == 0){
                        empty.add((x+1) * 100 + y);
                    }
                }
                if(x > 0 && y < count_mine[0].length - 1 && buttons[x-1][y+1].isEnabled()){  // вниз влево
                    buttons[x-1][y+1].setText(count_mine[x-1][y+1] + "");
                    buttons[x-1][y+1].setEnabled(false);
                    if(count_mine[x-1][y+1] == 0){
                        empty.add((x-1) * 100 + (y+1));
                    }
                }
                if(y < count_mine[0].length - 1 && buttons[x][y+1].isEnabled()){ // вниз
                    buttons[x][y+1].setText(count_mine[x][y+1] + "");
                    buttons[x][y+1].setEnabled(false);
                    if(count_mine[x][y+1] == 0){
                        empty.add(x * 100 + (y+1));
                    }
                }
                if(x < count_mine.length - 1 && y < count_mine[0].length - 1 && buttons[x+1][y+1].isEnabled()){ // вниз вправо
                    buttons[x+1][y+1].setText(count_mine[x+1][y+1] + "");
                    buttons[x+1][y+1].setEnabled(false);
                    if(count_mine[x+1][y+1] == 0){
                        empty.add((x+1) * 100 + (y+1));
                    }
                }
            }
            setEmpties(empty);
        }
    }



    public static void main(String[] args) {
        new Sapper();

    }

    public void lose(){
        for (int x = 0; x < blockr; x++) {
            for (int y = 0; y < blockc; y++) {
                if(buttons[x][y].isEnabled()){
                    if(count_mine[x][y] != mines){
                        buttons[x][y].setText(count_mine[x][y] + "");
                        buttons[x][y].setEnabled(false);
                    }
                    else{
                        buttons[x][y].setText("X");
                        buttons[x][y].setEnabled(false);
                    }
                }
            }
        }
    }


    public void checkWin(){
        boolean won = true;
        for (int x = 0; x < count_mine.length; x++) {
            for (int y = 0; y < count_mine[0].length; y++) {
                if (count_mine[x][y] != mines && buttons[x][y].isEnabled() == true){
                    won = false;
                }
            }
        }
        if (won == true){
            JOptionPane.showMessageDialog(frame, "You win!");
        }
    }


}