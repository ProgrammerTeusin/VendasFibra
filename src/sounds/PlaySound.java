package sounds;

import javazoom.jl.player.Player;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;

public class PlaySound {
 Player ply;
    public void run(String path) throws InterruptedException{
        try {
            InputStream input = this.getClass().getResourceAsStream(path);
            try {
                ply = new Player(input);
            } catch (JavaLayerException ex) {
                Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
            }
            ply.play();
        } catch (JavaLayerException ex) {
            ex.printStackTrace();
        }
    }
    
}
