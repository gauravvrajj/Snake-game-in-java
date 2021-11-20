import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{

    private Image apple;
    private Image dot;
    private Image head;
    private int dots;
    private int apple_x;
    private int apple_y;
    private int score=0;
    private final int Random_pos=38;
    private final int Dot_size=10;  //400*400=160000/100=1600
    private final int All_Dots=1600;
    private final int x[]=new int[All_Dots];
    private final int y[]=new int[All_Dots];
    
    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;

    private boolean ingame=true;
    private boolean start_game=false;
    private Timer timer;

    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400,400));
        setFocusable(true);  //for the key listner to work
        
    }


    public void paintComponent(Graphics h){
        super.paintComponent(h);
        if(start_game==false)
        screenstart(h);
        draw(h);
        
    }

    public void screenstart(Graphics g)
    {
        String msg="START ";
        Font font=new Font("SAN SERIF",Font.BOLD,30);
        FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(200+metrices.stringWidth(msg))/2 ,150);

        String msgg="QUIT ";
        Font fontt=new Font("SAN SERIF",Font.BOLD,30);
        FontMetrics metricees=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msgg,(250+metricees.stringWidth(msgg))/2 ,200);
    }
    
 
    public void loadimage(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icon/apple.png"));
        apple=i1.getImage();
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("icon/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icon/head.png"));
        head=i3.getImage();
    }

    public void ggGame(){
       dots=3;

       for(int i=0;i<dots;i++)    //snake dots placement
       {
           x[i]=50-(i*Dot_size);  
           y[i]=50;
       }

       locateApple();

       timer=new Timer(40,this);
       timer.start();
    }

    public void locateApple(){
        int r=(int)(Math.random()*Random_pos);      //btw 0 & 1
        apple_x=(r*Dot_size);  //29*10=290 =frame size in x
        if(apple_x==0)
        apple_x=10;
        apple_y=(r*Dot_size); 
        if(apple_y==0)
        apple_y=10;
    }

    public void checkApple(){
   if(x[0]==apple_x&& y[0]==apple_y){
       dots++;
       score=score+5;
       locateApple();
     }
    }

   
    public void draw(Graphics g){
        if(ingame){
            g.drawImage(apple,apple_x,apple_y,this);
            drawscore(g);
            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head, x[i], y[i], this);
                }
                else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
            finalscore(g);
        }
    }
    public void drawscore(Graphics g)
    {
        String msg="SCORE :"+score;
        Font font=new Font("SAN SERIF",Font.BOLD,9);
        FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(10+metrices.stringWidth(msg))/12 ,10);
    }

    public void finalscore(Graphics g){

        String msg="Score: "+score;
        Font font=new Font("SAN SERIF",Font.BOLD,30);
        FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(400-metrices.stringWidth(msg))/2 ,200);
    }

    public void gameOver(Graphics g){

        String msg="Game Over";
        Font font=new Font("SAN SERIF",Font.BOLD,30);
        FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(400-metrices.stringWidth(msg))/2 ,150);

    }

    public void checkCollision(){
        for(int i=dots;i>0;i--)
        {
            if((dots>4) &&(x[0]==x[i])&&(y[0]==y[i]))
            {
                ingame=false;
            }
        }
        if(y[0]>400){
            ingame=false;
        }
        if(x[0]>400){
            ingame=false;
        }
        
        if(y[0]<0){
            ingame=false;
        }

        if(x[0]<0){
            ingame=false;
        }
        if(!ingame)
        {
            timer.stop();
        }
    }

    public void move(){
        
        for(int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        if(leftDirection){
            x[0]=x[0]-Dot_size;
        }

        if(rightDirection){
            x[0]=x[0]+Dot_size;
        }
        if(upDirection){
            y[0]=y[0]-Dot_size;
        }
        if(downDirection){
            y[0]=y[0]+Dot_size;
        }
       

    }

    public void actionPerformed(ActionEvent ae)
    {
       if(ingame){
        checkApple();
        checkCollision();
        move();

       }

       repaint();
    }


    private class TAdapter extends KeyAdapter
    {
       public void keyPressed(KeyEvent e){
            int key =e.getKeyCode();

            if(key==KeyEvent.VK_ENTER && start_game==false)
            {
                start_game=true;
                setBackground(Color.GRAY);
                loadimage();
                ggGame();

            }
           
            if(key==KeyEvent.VK_SPACE)
            {
                System.exit(0);
            }
            if(key==KeyEvent.VK_A&& (rightDirection==false)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_D&& (leftDirection==false)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_W&& (downDirection==false)){
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_S&& (upDirection==false)){
                downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
       }
    }
}
