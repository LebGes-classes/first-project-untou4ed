import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App {
    public static void main(String[] args) {
        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                char key = keyEvent.getKeyChar();
                System.out.println("Нажата и отпущена клавиша - " + keyEvent.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                char key = keyEvent.getKeyChar();
                System.out.println("Нажата клавиша - " + keyEvent.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        };

        JTextField textField = new JTextField(20);
        textField.addKeyListener(listener);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(textField, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(false);
    }
}