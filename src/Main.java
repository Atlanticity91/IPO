import utils.Golbals;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main( String[] args ) {
        JFrame frame = new JFrame( Golbals.WIN_TITLE );
        Game game    = new Game( );

        Container pane = frame.getContentPane( );
        pane.add( game );
        pane.addKeyListener( game );
        pane.addMouseListener( game );

        frame.pack( );
        frame.setVisible( true );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

}
