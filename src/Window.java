import utils.Globals;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window( Game game ) {
        super( Globals.WIN_TITLE );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable( false );

        Container pane = getContentPane( );

        pane.add( game );

        setFocusable( true );
        addKeyListener( game );
        addMouseMotionListener( game );
        addMouseListener( game );
        requestFocusInWindow( );
    }

}
