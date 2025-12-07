package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameInputManager {

    private GameInputHandler m_keys;
    private GameInputHandler m_buttons;
    private boolean m_is_locked;

    public GameInputManager( ) {
        m_is_locked = false;

        m_keys = new GameInputHandler( );
        m_buttons = new GameInputHandler( );
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

    public void getMouseLocation( ) {
    }

}
