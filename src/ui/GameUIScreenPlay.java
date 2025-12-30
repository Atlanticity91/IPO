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
        System.out.println( "t" );
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
        final float origin_y = Globals.WIN_HEIGHT - Globals.PLAY_UI_HEIGHT;
        final Vector2 left_dimensions = new Vector2( Globals.PLAY_UI_LEFT_WIDTH, Globals.PLAY_UI_HEIGHT );
        final Vector2 right_dimensions = new Vector2( Globals.WIN_WIDTH - Globals.PLAY_UI_LEFT_WIDTH, Globals.PLAY_UI_HEIGHT );

        render_manager.setOrigin( 0, origin_y );
        render_manager.drawBorders( Vector2.Zero, left_dimensions );
        render_manager.drawTextCentered( getElapsed( ), Vector2.Zero, left_dimensions );

        render_manager.setOrigin( Globals.PLAY_UI_LEFT_WIDTH, origin_y );
        render_manager.drawBorders( Vector2.Zero, right_dimensions );
        render_manager.drawTextCentered( state_manager.getLevelName( ), Vector2.Zero, right_dimensions );

        for ( int i = 0; i < Globals.MAX_LIVE_COUNT; i++  ) {
            final float diameter = 40;
            final int sprite_column = ( i < state_manager.getLiveCount( ) ) ? 0 : 1;
            render_manager.drawSprite( "ui_lives", new Vector2(Globals.PLAY_UI_HEIGHT + i * diameter + diameter / 2, ( Globals.PLAY_UI_HEIGHT - diameter ) / 2), new Vector2( diameter ), sprite_column, 0 );
        }

        if ( input_manager.isKey( KeyEvent.VK_SPACE, GameInputState.Pressed ) )
            state_manager.set( GameState.LoseScreen );
    }

}
