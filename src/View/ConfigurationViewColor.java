package View;

import java.awt.Color;
import javax.swing.JPanel;


public class ConfigurationViewColor {
    protected boolean toChangeColor = false;
    
    public static void changeViewColor(Color colorBack,Color colorFore,JPanel panel){
        panel.setBackground(colorBack);
        panel.setForeground(colorBack);
    }
}
