import utils.Globals;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main( String[] args ) {
        Game game     = new Game( );
        Window window = new Window( game );

        window.pack( );
        window.setVisible( true );

        Timer timer = new Timer(
                1000 / Globals.TARGET_FPS,
                e -> {
                    game.tick( );
                    window.repaint( );
                }
        );
        timer.start( );
    }

}
