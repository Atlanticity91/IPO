package ui;

import inputs.GameInputManager;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Vector2;

public class GameUIScreenMenu extends GameUIScreen {

    private Vector2 m_button_dimensions;

    public GameUIScreenMenu( ) {
        m_button_dimensions = new Vector2( 128, 32 );
    }

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        render_manager.drawText( "Menu !", new Vector2( 10, 10 ) );

        if ( button( input_manager, render_manager, "Jouer", new Vector2( 10, 20 ), m_button_dimensions ) )
            state_manager.set( GameState.SelectionScreen );
    }

}
