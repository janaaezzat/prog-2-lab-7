package main;
import javax.swing.SwingUtilities;
import gui.MainFrame;
public class Main {
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {  //SwingUtilities.invokeLater(...)  This tells Swing to create and show our GUI on
                                        //  the Swing Event Dispatch Thread (EDT), which is the thread Swing uses for GUI work.
                                        // ()->    This is a lambda expression it is a concise way to provide the task that Swing should execute.
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        });

    }
}