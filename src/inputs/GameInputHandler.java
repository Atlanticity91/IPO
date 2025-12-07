package inputs;

import java.util.HashMap;
import java.util.Map;

public class GameInputHandler {

    private Map<Integer, Boolean> m_old_handles;
    private Map<Integer, Boolean> m_new_handles;

    public GameInputHandler( ) {
        m_old_handles = new HashMap<>( );
        m_new_handles = new HashMap<>( );
    }

    public void addHandle( int handle ) {
        if ( hasHandle( handle ) )
            return;

        m_old_handles.put( handle, false );
        m_new_handles.put( handle, false );
    }

    public void push( int handle, boolean value ) {
        if ( !hasHandle( handle ) )
            return;

        boolean temp_value = m_new_handles.get( handle );

        m_old_handles.put( handle, temp_value );
        m_new_handles.put( handle, value );
    }

    public void tick( ) {
        //m_old_handles.clear( );
        m_old_handles.putAll( m_new_handles );
    }

    public boolean isHandle( int handle, GameInputState input_state ) {
        if ( !hasHandle( handle ) )
            return false;

        boolean old_state = m_old_handles.get( handle );
        boolean new_state = m_new_handles.get( handle );

        return switch ( input_state ) {
            case GameInputState.Pressed-> new_state && !old_state;
            case GameInputState.Released -> !new_state && old_state;
            case GameInputState.Up -> !new_state && !old_state;
            case GameInputState.Down -> new_state && old_state;
        };
    }

    public boolean hasHandle( int handle ) {
        return m_new_handles.containsKey( handle );
    }

}
