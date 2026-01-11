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

    private void displayTimer( GameRenderManager render_manager, float origin_y ) {
        final Vector2 left_dimensions = new Vector2( Globals.PLAY_UI_LEFT_WIDTH, Globals.PLAY_UI_HEIGHT );

        render_manager.setOrigin( 0, origin_y );
        render_manager.drawBorders( Vector2.Zero, left_dimensions );
        render_manager.drawTextCentered( getElapsed( ), Vector2.Zero, left_dimensions );
    }

    private void displayLives(
            GameStateManager state_manager,
            GameRenderManager render_manager
    ) {
        final float diameter = 40;
        final Vector2 dimensions = new Vector2( diameter );

        Vector2 location = new Vector2( (float)Globals.PLAY_UI_HEIGHT, ( Globals.PLAY_UI_HEIGHT - diameter ) / 2.f );

        for ( int i = 0; i < Globals.MAX_LIVE_COUNT; i++ ) {
            final int sprite_column = ( i < state_manager.getLiveCount( ) ) ? 0 : 1;

            render_manager.drawSprite( "ui_lives", location, dimensions, sprite_column, 0 );

            location = location.add( diameter + diameter / 4.f, 0.f );
        }
    }

    private void displayInfo(
            GameStateManager state_manager,
            GameRenderManager render_manager,
            float origin_y
    ) {
        final Vector2 right_dimensions = new Vector2( Globals.WIN_WIDTH - Globals.PLAY_UI_LEFT_WIDTH, Globals.PLAY_UI_HEIGHT );

        render_manager.setOrigin( Globals.PLAY_UI_LEFT_WIDTH, origin_y );
        render_manager.drawBorders( Vector2.Zero, right_dimensions );
        render_manager.drawTextCentered( state_manager.getLevelName( ), Vector2.Zero, right_dimensions );

        displayLives( state_manager, render_manager );
    }

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        final float origin_y = Globals.WIN_HEIGHT - Globals.PLAY_UI_HEIGHT;

        displayTimer( render_manager, origin_y );
        displayInfo( state_manager, render_manager, origin_y );

        if ( state_manager.getLiveCount( ) == 0 ) {
            state_manager.setLevelTime( getElapsed( ) );
            state_manager.set( GameState.LoseScreen );
        }

        if ( state_manager.getHasWin( ) ) {
            state_manager.setLevelTime( getElapsed( ) );
            state_manager.set( GameState.WinScreen );
        }

        if ( input_manager.isKey( KeyEvent.VK_ESCAPE, GameInputState.Pressed ) )
            state_manager.set( GameState.SelectionScreen );
    }

}
