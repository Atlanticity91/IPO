package ui;

import inputs.GameInputManager;
import inputs.GameInputState;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;

import java.awt.event.KeyEvent;

public class GameUIScreenSelection extends GameUIScreen {

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        render_manager.setOrigin( Globals.WIN_WIDTH / 2, Globals.WIN_HEIGHT / 2 );
        render_manager.drawText( "Selection !" );

        if ( input_manager.isKey( KeyEvent.VK_SPACE, GameInputState.Pressed ) ) {
            state_manager.set( GameState.PlayScreen );
            state_manager.loadLevel( "" );
        }
    }

}
