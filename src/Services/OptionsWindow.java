package Services;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

public class OptionsWindow {

    private static HashMap<String, JFrame> openWindows = new HashMap<>();

    public static void CloseWindow(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                int response = JOptionPane.showConfirmDialog(
                        frame,
                        "Você realmente deseja fechar a aplicação?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    openWindows.remove(frame);
                    
                    if (openWindows.size() > 1) {
                        
                        frame.dispose();
                    } else {
                        System.exit(0);

                    }
                }
            }
        }
        );

        frame.setVisible(
                true);
    }

    public static void permissionToOpenWindow(String nomeTela, JFrame window) {
        if (openWindows.containsKey(nomeTela)) {
            JFrame telaExistente = openWindows.get(nomeTela);
            telaExistente.toFront(); // Traz a tela já aberta para a frente
        } else {
            // Minimiza todas as outras telas abertas
            for (JFrame t : openWindows.values()) {
                t.setState(JFrame.ICONIFIED);
            }
            openWindows.put(nomeTela, window);
            window.setVisible(true);
            //CloseWindow(tela);
        }
    }
    

    public static void mouseOpt(Component button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

}
