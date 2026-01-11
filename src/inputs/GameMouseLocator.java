package inputs;

import utils.Vector2;

import javax.swing.*;
import java.awt.*;

public class GameMouseLocator {

    private Vector2 m_old_location;
    private Vector2 m_new_location;

    public GameMouseLocator( ) {
        m_old_location = new Vector2( );
        m_new_location = new Vector2( );
    }

    public void notify( Point position ) {
        final float x = (float)position.getX( );
        final float y = (float)position.getY( );

        m_old_location.set( m_new_location );
        m_new_location.set( x, y );
    }

    public Vector2 getLocation( ) { return m_new_location; }

    public Vector2 getDirection( ) {
        final Vector2 direction = Vector2.direction( m_old_location, m_new_location );

        m_old_location.set( m_new_location );

        return direction;
    }

}
