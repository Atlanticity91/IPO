package ui;

import inputs.GameInputManager;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;

public class GameUIManager {

    private GameUIScreen m_screen;

    public GameUIManager( ) {
        m_screen = new GameUIScreenMenu( );
    }

    private void setScreen( GameState new_state ) {
        switch ( new_state ) {
            case GameState.MenuScreen -> m_screen = new GameUIScreenMenu( );
            case GameState.SelectionScreen -> m_screen = new GameUIScreenSelection( );
            case GameState.PlayScreen -> m_screen = new GameUIScreenPlay( );
            case GameState.WinScreen -> m_screen = new GameUIScreenWin( );
            case GameState.LoseScreen -> m_screen = new GameUIScreenLose( );
        }
    }

    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        final GameState old_state = state_manager.get( );

        m_screen.display( state_manager, input_manager, render_manager );

        final GameState new_state = state_manager.get( );

        if ( old_state != new_state )
            setScreen( new_state );
    }

}
