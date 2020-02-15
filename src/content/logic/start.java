package content.logic; // @Date: 2020/2/15 0015 17:43

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class start extends JFrame {
    int Circle_radius = 15;
    int[][] red = new int[20][20];
    int[][] black = new int[20][20];
    public start(){
        super("五子棋");//建立新窗体
        this.setSize(800,800);//设置窗体的宽和高
        this.setVisible(true);//设置窗体可见
        this.setLayout(new FlowLayout(FlowLayout.CENTER));//框架流布局且居中对齐
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置点击关闭按钮时的默认操作
        JLabel label = new JLabel("黑棋先走");
        this.add(label); // 将标签放入窗体
        paint_s();

        this.addMouseListener(new MouseAdapter(){  //匿名内部类，鼠标事件
            ArrayList total = new ArrayList();
            int RED = 1;
            int BLACK = 2;

            int X,Y;
            public int number = 0;
            public int count = 1;
            public void mouseClicked(MouseEvent e){//鼠标完成点击事件
                if(e.getButton() == MouseEvent.BUTTON1){ //e.getButton就会返回点鼠标的那个键，左键还是右健，3代表右键
                    number++;
                    int x = e.getX();  //得到鼠标x坐标
                    int y = e.getY();  //得到鼠标y坐标
                    int[] point = choose(x,y);
                    X=point[0];
                    Y=point[1];
                    coordinate coo = new coordinate();

                    if((X>=800)||(Y>=800)){
                        number--;
                    }

                    for(int i=0;i<total.size();i++){  //如果该点已有棋子,则重下棋
                        coordinate value = (coordinate) total.get(i);
                        if(((value.x_coordinate==X)&&(value.y_coordinate==Y))){
                            number--;
                            System.out.println(number);
                        }
                    }
                    if(count==number){      //如果新棋子则保存
                        coo.x_coordinate = X;
                        coo.y_coordinate = Y;
                        total.add(coo);
                        count++;
                        if(number%2==0){  //使双方交替下棋
                            piece_1(X,Y);
                            if(number>8){
                                win(X,Y,red,RED);
                            }
                        }
                        else{
                            piece_2(X,Y);
                            if(number>8){
                                win(X,Y,black,BLACK);
                            }
                        }
                    }

//                    System.out.print(X+"--");
//                    System.out.print(Y+"\n");

//                    label.setText("运行了="+number);
                }
            }
            public void piece_1(int x,int y){
                label.setText("请黑方下棋");
                Graphics g = getGraphics();
                g.setColor(Color.red);
                g.fillRoundRect(x-Circle_radius,y-Circle_radius,Circle_radius*2,Circle_radius*2,50,50);
                red[x/40][y/40] = 1;
            }
            public void piece_2(int x,int y){
                label.setText("请红方下棋");
                Graphics g = getGraphics();
                g.setColor(Color.black);
                g.fillRoundRect(x-Circle_radius,y-Circle_radius,Circle_radius*2,Circle_radius*2,50,50);
                black[x/40][y/40] = 1;
            }
        });
    }
    public void paint(Graphics g)
    {
    }
    public void paint_s(){ //画出棋盘
        for(int i=0;i<=800;i=i+40){
            this.getGraphics().drawLine(0,i,800,i);
            this.getGraphics().drawLine(i,0,i,800);
        }
    }

    public int[] choose(int x,int y){ //调整点击坐标
        int[] list = new int[2];
        int Integer_part_x,Integer_part_y, return_x,return_y;
        float x_float ,y_float , remainder_x, remainder_y;
        x_float = x;
        y_float = y;
        x_float = x_float/10;
        y_float = y_float/10;
        remainder_x = x_float%4;
        remainder_y = y_float%4;
        Integer_part_x = (int)(x_float - remainder_x);
        Integer_part_y = (int)(y_float - remainder_y);
        Integer_part_x = Integer_part_x*10;
        Integer_part_y = Integer_part_y*10;
        if(remainder_x<=2){
            return_x = Integer_part_x;
        }
        else{
            return_x = Integer_part_x + 40;
        }
        if(remainder_y<=2){
            return_y = Integer_part_y;
        }
        else{
            return_y = Integer_part_y + 40;
        }
        list[0] = return_x;
        list[1] = return_y;

        return list;
    }
    public void win(int X,int Y, int[][] list, int color){
        JLabel label = new JLabel();
        Font font = new Font("宋体", Font.PLAIN, 50);
        label.setFont(font);
        int x = X/40;
        int y = Y/40;
        for(int i=-4;i<1;i++){
            int[] list_sum = new int[4];
            for(int j = i;j<(i+5);j++){
                try{
                    list_sum[0] = list_sum[0] + list[x+j][y];
                    list_sum[1] = list_sum[1] + list[x][y+j];
                    list_sum[2] = list_sum[2] + list[x+j][y+j];
                    list_sum[3] = list_sum[3] + list[x+j][y-j];
                }catch (ArrayIndexOutOfBoundsException e){
                    continue;
                }
                finally {
                    if((list_sum[0]==5)||(list_sum[1]==5)||(list_sum[2]==5)||(list_sum[3]==5)){
                        for(int r=0;r<4;r++){
                            System.out.println(list_sum[r]);
                        }
                        if (color == 1) {
                            label.setForeground(new Color(255, 0, 0));
                            label.setText("红棋获胜");
                            add(label);
                        }
                        if (color == 2) {
                            label.setForeground(new Color(0, 0, 0));
                            label.setText("黑棋获胜");
                            add(label);
                        }
                    }
                }
            }
        }
//        for(int i=-4;i<1;i++){
//            int list_sum_2 = 0;
//            for(int j = i;j<(i+5);j++){
//                try{
//                    list_sum_2 = list_sum_2 + list[x][y+j];
//                }catch (ArrayIndexOutOfBoundsException e){
//                    break;
//                }
//                finally {
//                    if(list_sum_2==5){
//                        System.out.println(list_sum_2+"...2");
//                        if (color == 1) {
//                            label.setForeground(new Color(255, 0, 0));
//                            label.setText("红棋获胜");
//                            add(label);
//                        }
//                        if (color == 2) {
//                            label.setForeground(new Color(0, 0, 0));
//                            label.setText("黑棋获胜");
//                            add(label);
//                        }
//                    }
//                }
//            }
//        }
//        for(int i=-4;i<1;i++){
//            int list_sum_3 = 0;
//            for(int j = i;j<(i+5);j++){
//                try{
//                    list_sum_3 = list_sum_3 + list[x+j][y+j];
//                }catch (ArrayIndexOutOfBoundsException e){
//                    break;
//                }
//                finally {
//                    if(list_sum_3==5){
//                        System.out.println(list_sum_3+"...3");
//                        if (color == 1) {
//                            label.setForeground(new Color(255, 0, 0));
//                            label.setText("红棋获胜");
//                            add(label);
//                        }
//                        if (color == 2) {
//                            label.setForeground(new Color(0, 0, 0));
//                            label.setText("黑棋获胜");
//                            add(label);
//                        }
//                    }
//                }
//            }
//        }
//        for(int i=-4;i<1;i++){
//            int list_sum_4 = 0;
//            for(int j = i;j<(i+5);j++){
//                try{
//                    list_sum_4 = list_sum_4 + list[x+j][y-j];
//                }catch (ArrayIndexOutOfBoundsException e){
//                    break;
//                }
//                finally {
//                    if(list_sum_4==5){
//                        System.out.println(list_sum_4+"...4");
//                        if (color == 1) {
//                            label.setForeground(new Color(255, 0, 0));
//                            label.setText("红棋获胜");
//                            add(label);
//                        }
//                        if (color == 2) {
//                            label.setForeground(new Color(0, 0, 0));
//                            label.setText("黑棋获胜");
//                            add(label);
//                        }
//                    }
//                }
//
//            }
//        }
//        boolean win_1 = (list[x+1][y]+list[x+2][y]+list[x+3][y]+list[x+4][y]==4);
//        boolean win_2 = (list[x-1][y]+list[x+1][y]+list[x+2][y]+list[x+3][y]==4);
//        boolean win_3 = (list[x-2][y]+list[x-1][y]+list[x+1][y]+list[x+2][y]==4);
//        boolean win_4 = (list[x-3][y]+list[x-2][y]+list[x-1][y]+list[x+1][y]==4);
//        boolean win_5 = (list[x-4][y]+list[x-3][y]+list[x-2][y]+list[x-1][y]==4);
//        boolean win_6 = (list[x][y+1]+list[x][y+2]+list[x][y+3]+list[x][y+4]==4);
//        boolean win_7 = (list[x][y-1]+list[x][y+1]+list[x][y+2]+list[x][y+3]==4);
//        boolean win_8 = (list[x][y-2]+list[x][y-1]+list[x][y+1]+list[x][y+2]==4);
//        boolean win_9 = (list[x][y-3]+list[x][y-2]+list[x][y-1]+list[x][y+1]==4);
//        boolean win_10 = (list[x][y-4]+list[x][y-3]+list[x][y-2]+list[x][y-1]==4);
//        boolean win_11 = (list[x+1][y+1]+list[x+2][y+2]+list[x+3][y+3]+list[x+4][y+4]==4);
//        boolean win_12 = (list[x-1][y-1]+list[x+1][y+1]+list[x+2][y+2]+list[x+3][y+3]==4);
//        boolean win_13 = (list[x-2][y-2]+list[x-1][y-1]+list[x+1][y+1]+list[x+2][y+2]==4);
//        boolean win_14 = (list[x-3][y-3]+list[x-2][x-2]+list[x-1][y-1]+list[x+1][y+1]==4);
//        boolean win_15 = (list[x-4][y-4]+list[x-3][y-3]+list[x-2][y-2]+list[x-1][y-1]==4);
    }

    public static void main(String[] args) {
        new start();
    }
}

