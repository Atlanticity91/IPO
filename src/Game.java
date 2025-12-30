import entities.GameEntityManager;
import inputs.GameInputManager;
import graphics.GameRenderManager;
import terrain.GameTilemap;
import ui.GameUIManager;
import utils.GameState;
import utils.GameStateManager;
import utils.Globals;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class Game
        extends JPanel
        implements KeyListener, MouseListener, MouseMotionListener
{

    private GameInputManager m_input_manager;
    private GameRenderManager m_render_manager;
    private GameEntityManager m_entities;
    private GameTilemap m_tilemap;
    private GameStateManager m_state_manager;
    private GameUIManager m_ui;

    public Game( ) {
        super( );

        setupWindow( );

        m_input_manager = new GameInputManager( );
        m_render_manager = new GameRenderManager( );
        m_entities = new GameEntityManager( );
        m_tilemap = new GameTilemap( );
        m_state_manager = new GameStateManager( m_tilemap, m_entities );
        m_ui = new GameUIManager( );

        init( );
        loadTextures( );
    }

    private void setupWindow( ) {
        Dimension dimension = new Dimension( Globals.WIN_WIDTH, Globals.WIN_HEIGHT );

        setPreferredSize( dimension );
    }

    private void init( ) {
        m_input_manager.addKey( KeyEvent.VK_UP );
        m_input_manager.addKey( KeyEvent.VK_DOWN );
        m_input_manager.addKey( KeyEvent.VK_LEFT );
        m_input_manager.addKey( KeyEvent.VK_RIGHT );
        m_input_manager.addKey( KeyEvent.VK_SPACE );
        m_input_manager.addKey( KeyEvent.VK_ESCAPE );

        m_input_manager.addButton( MouseEvent.BUTTON1 );
    }

    private void loadTextures( ) {
        m_render_manager.load( "ui_borders", "/assets/ui_borders.png", 4, 4 );
        m_render_manager.load( "ui_lives", "/assets/ui_lives.png", 2, 1 );

        m_render_manager.setButtonTexture( "ui_borders" );
    }

    @Override
    public void keyTyped( KeyEvent key_event ) {
        m_input_manager.notifyKeyEvent( key_event );
    }

    @Override
    public void keyPressed( KeyEvent key_event ) {
        m_input_manager.notifyKeyEvent( key_event );
    }

    @Override
    public void keyReleased( KeyEvent key_event ) {
        m_input_manager.notifyKeyEvent( key_event );
    }

    @Override
    public void mouseDragged( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseMoved( mouse_event, this );
    }

    @Override
    public void mouseMoved( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseMoved( mouse_event, this );
    }

    @Override
    public void mouseClicked( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );
        m_input_manager.notifyMouseMoved( mouse_event, this );
    }

    @Override
    public void mousePressed( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );
        m_input_manager.notifyMouseMoved( mouse_event, this );
    }

    @Override
    public void mouseReleased( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );
        m_input_manager.notifyMouseMoved( mouse_event, this );
    }

    @Override
    public void mouseEntered( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );
        m_input_manager.notifyMouseMoved( mouse_event, this );
    }

    @Override
    public void mouseExited( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );
        m_input_manager.notifyMouseMoved( mouse_event, this );
    }

    @Override
    protected void paintComponent( Graphics graphics ) {
        super.paintComponent( graphics );

        m_render_manager.prepare( graphics );

        if ( m_state_manager.is( GameState.PlayScreen ) )
            m_tilemap.tick( m_state_manager, m_input_manager, m_entities );

        m_tilemap.display( m_render_manager );
        m_entities.display( m_render_manager );
        m_ui.display( m_state_manager, m_input_manager, m_render_manager );

        m_input_manager.tick( );
    }

}
