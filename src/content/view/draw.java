package content.view; // @Date: 2020/2/12 0012 22:09

import javax.swing.*;
import java.awt.*;

public class draw extends JFrame {
    int distance = 40;
    public draw()
    {
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.red);
        for(int i=0;i<=800;i=i+distance){
                g.drawLine(0,i,800,i);
                g.drawLine(i,0,i,800);
        }
        piece_1(g);
        piece_2(g);
    }
    public void piece_1(Graphics p_1){
        p_1.setColor(Color.black);
        p_1.fillRoundRect(distance+25,distance+25,30,30,50,50);
    }
    public void piece_2(Graphics p_2){
        p_2.setColor(Color.pink);
        p_2.fillRoundRect(distance*2+25,distance*2+25,30,30,50,50);
    }
    public static void main(String[] args)
    {
        new draw().setVisible(true);
    }
}
