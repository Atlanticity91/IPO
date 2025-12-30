package ui;

import inputs.GameInputManager;
import inputs.GameInputState;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

import java.awt.event.KeyEvent;

public class GameUIScreenWin extends GameUIScreen {

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        render_manager.drawTextCentered( "Vous avez gagner, appuyer sur espace !", new Vector2( ), new Vector2(Globals.WIN_WIDTH, Globals.WIN_HEIGHT )  );

        if ( input_manager.isKey( KeyEvent.VK_SPACE, GameInputState.Pressed ) )
            state_manager.set( GameState.SelectionScreen );
    }

}
