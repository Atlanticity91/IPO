package ui;

import inputs.GameInputManager;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

public class GameUIScreenMenu extends GameUIScreen {

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        final Vector2 button_dimensions = new Vector2( 256, 64 );
        final Vector2 origin = new Vector2(
                ( Globals.WIN_WIDTH - button_dimensions.getX( ) ) / 2,
                ( Globals.WIN_HEIGHT - button_dimensions.getY( ) ) / 2
        );

        if ( button( input_manager, render_manager, "Jouer", origin, button_dimensions ) )
            state_manager.set( GameState.SelectionScreen );
    }

}
