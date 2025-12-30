package ui;

import inputs.GameInputManager;
import inputs.GameInputState;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class GameUIScreenPlay extends GameUIScreen {

    private long m_elapsed = 0;

    public GameUIScreenPlay( ) {
        Timer timer = new Timer(
                1000,
                e -> { m_elapsed += 1; }
        );

        timer.start( );
    }

    private String getElapsed( ) {
        //final long hours = m_elapsed / 3600;
        final long mins = ( m_elapsed % 3600 ) / 60;
        final long secs = m_elapsed % 60;

        return String.format( "%02d:%02d", mins, secs );
    }

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        render_manager.setOrigin( 0, Globals.WIN_HEIGHT - 64 );
        render_manager.drawTextCentered( getElapsed( ), new Vector2( ), new Vector2( 128, 64 ) );
        render_manager.setOrigin( 128, Globals.WIN_HEIGHT - 64 );
        render_manager.drawTextCentered( state_manager.getLevelName( ), new Vector2( 128, 0), new Vector2( Globals.WIN_WIDTH - 128, 64 ) );

        if ( input_manager.isKey( KeyEvent.VK_SPACE, GameInputState.Pressed ) )
            state_manager.set( GameState.LoseScreen );
    }

}
