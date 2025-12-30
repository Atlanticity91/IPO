package ui;

import inputs.GameInputManager;
import inputs.GameInputState;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameUIScreenWin extends GameUIScreen {

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        final float button_height = 64.f;
        final Vector2 button_dimensions = new Vector2( 256, button_height );
        Vector2 origin = new Vector2(
                ( Globals.WIN_WIDTH - button_dimensions.getX( ) ) / 2,
                ( Globals.WIN_HEIGHT - ( 4 * button_dimensions.getY( ) + 30.f ) ) / 2
        );

        render_manager.drawBorders( origin, button_dimensions );
        render_manager.drawTextCentered( "Vous avez gagner !", origin, button_dimensions );

        origin = origin.add( 0.f, 10.f + button_height );
        render_manager.drawBorders( origin, button_dimensions );
        render_manager.drawTextCentered( "En " + state_manager.getLevelTime( ) + " minutes !", origin, button_dimensions );

        origin = origin.add( 0.f, 10.f + button_height );
        render_manager.drawBorders( origin, button_dimensions );
        render_manager.drawTextCentered( state_manager.getLiveCount( ) + " vie restante !", origin, button_dimensions );

        origin = origin.add( 0.f, 10.f + button_height );

        if (
                input_manager.isKey( KeyEvent.VK_SPACE, GameInputState.Pressed ) ||
                button( input_manager, render_manager, "Choisir un autre niveau", origin, button_dimensions )
        )
            state_manager.set( GameState.SelectionScreen );
    }

}
