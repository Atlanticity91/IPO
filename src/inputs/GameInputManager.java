package inputs;

import utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameInputManager {

    private GameInputHandler m_keys;
    private GameInputHandler m_buttons;
    private GameMouseLocator m_mouse_locator;
    private long m_delta_time = 0;
    private boolean m_is_locked = false;

    public GameInputManager( ) {
        m_keys = new GameInputHandler( );
        m_buttons = new GameInputHandler( );
        m_mouse_locator = new GameMouseLocator( );
    }

    public GameInputManager addKey( int key ) {
        m_keys.addHandle( key );

        return this;
    }

    public GameInputManager addButton( int button ) {
        m_buttons.addHandle( button );

        return this;
    }

    public void notifyKeyEvent( KeyEvent key_event ) {
        boolean is_pressed = key_event.getID( ) == KeyEvent.KEY_PRESSED;
        int key_code = key_event.getKeyCode( );

        m_keys.push( key_code, is_pressed );
    }

    public void notifyMouseEvent( MouseEvent mouse_event ) {
        boolean is_pressed = mouse_event.getID( ) == MouseEvent.MOUSE_PRESSED;
        int button_code = mouse_event.getButton( );

        m_buttons.push( button_code, is_pressed );
    }

    public void notifyMouseMoved( MouseEvent mouse_event, JPanel panel ) {
        Point mouse_position = mouse_event.getPoint( );

        SwingUtilities.convertPointFromScreen( mouse_position, panel );

        m_mouse_locator.notify( mouse_position );
    }

    public void setDeltaTime( long delta_time ) { m_delta_time = delta_time; }

    public void lock( ) { m_is_locked = true; }

    public void unlock( ) { m_is_locked = false; }

    public void tick( ) {
        m_keys.tick( );
        m_buttons.tick( );
    }

    public boolean isKey( int key, GameInputState input_state ) {
        if ( m_is_locked )
            return false;

        return m_keys.isHandle( key, input_state );
    }

    public boolean isMouseButton( int button, GameInputState input_state ) {
        if ( m_is_locked )
           return false;

        return m_buttons.isHandle( button, input_state );
    }

    public Vector2 getMouseLocation( ) { return m_mouse_locator.getLocation( ); }

    public Vector2 getMouseDirection( ) { return m_mouse_locator.getDirection( ); }

    public long getDeltaTime( ) { return m_delta_time; }

}
