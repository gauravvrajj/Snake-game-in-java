import javax.swing.*;

public class Snake extends JFrame{
    Snake(){   

            Board b=new Board();
            add(b);
            pack();
            setLocationRelativeTo(null);
            setTitle("SNAKE");
                                   
    }
    public static void main(String[] args) throws Exception {
        
        new Snake().setVisible(true);
    }
}
