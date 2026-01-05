package ui;

import inputs.GameInputManager;
import graphics.GameRenderManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;
import utils.Vector2;

public class GameUIScreenSelection extends GameUIScreen {

    private void loadLevel( GameStateManager state_manager, String path ) {
        if ( state_manager.loadLevel( path ) )
            state_manager.set( GameState.PlayScreen );
        else {
            System.out.println( "Can't load the level !" );

            state_manager.set( GameState.MenuScreen );
        }
    }

    @Override
    public void display(
            GameStateManager state_manager,
            GameInputManager input_manager,
            GameRenderManager render_manager
    ) {
        final Vector2 button_dimensions = new Vector2( 256, 64 );
        Vector2 origin = new Vector2(
                ( Globals.WIN_WIDTH - button_dimensions.getX( ) ) / 2,
                ( Globals.WIN_HEIGHT - (6*button_dimensions.getY( ) +60.f)) / 2
        );

        if ( button( input_manager, render_manager, "Lancer level_1", origin, button_dimensions ) )
            loadLevel( state_manager, "/assets/maps/level_1.txt" );

        origin = origin.add( 0.f, button_dimensions.getY( ) + 10.f );
        if ( button( input_manager, render_manager, "Lancer level_2", origin, button_dimensions ) )
            loadLevel( state_manager, "/assets/maps/level_2.txt" );

        origin = origin.add( 0.f, button_dimensions.getY( ) + 10.f );
        if ( button( input_manager, render_manager, "Lancer level_3", origin, button_dimensions ) )
            loadLevel( state_manager, "/assets/maps/level_3.txt" );

        origin = origin.add( 0.f, button_dimensions.getY( ) + 10.f );
        if ( button( input_manager, render_manager, "Lancer level_4", origin, button_dimensions ) )
            loadLevel( state_manager, "/assets/maps/level_4.txt" );

        origin = origin.add( 0.f, button_dimensions.getY( ) + 10.f );
        if ( button( input_manager, render_manager, "Lancer level5", origin, button_dimensions ) )
            loadLevel( state_manager, "/assets/maps/level_5.txt" );

        origin = origin.add( 0.f, button_dimensions.getY( ) + 10.f );
        if ( button( input_manager, render_manager, "Lancer labyrinth", origin, button_dimensions ) )
            loadLevel( state_manager, "/assets/maps/level_labyrinth.txt" );
    }

}
