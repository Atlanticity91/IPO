package ui;

import inputs.GameInputManager;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

public class GameUIScreenSelection extends GameUIScreen {

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

        if ( button( input_manager, render_manager, "Lancer level_1", origin, button_dimensions ) ) {
            if ( state_manager.loadLevel( "/assets/maps/level_1.txt" ) )
                state_manager.set( GameState.PlayScreen );
            else {
                System.out.println( "Can't load the level !" );

                state_manager.set( GameState.MenuScreen );
            }
        }
    }

}
