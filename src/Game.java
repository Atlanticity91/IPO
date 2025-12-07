import entities.GameEntityManager;
import inputs.GameInputManager;
import utils.GameRenderManager;
import terrain.GameTilemap;
import ui.GameUIManager;
import utils.GameState;
import utils.Golbals;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener, MouseListener {

    private GameState m_state = GameState.MenuScreen;
    private GameInputManager m_input_manager;
    private GameRenderManager m_render_manager;

    private GameEntityManager m_entities;
    private GameTilemap m_tilemap;
    private GameUIManager m_ui;

    public Game( ) {
        super( );

        setupWindow( );

        m_input_manager = new GameInputManager( );
        m_render_manager = new GameRenderManager( );
        m_entities = new GameEntityManager( );
        m_tilemap = new GameTilemap( );
        m_ui = new GameUIManager( );

        init( );
    }

    private void setupWindow( ) {
        Dimension dimension = new Dimension( Golbals.WIN_WIDTH, Golbals.WIN_HEIGHT );

        setPreferredSize( dimension );
    }

    private void init( ) {
        m_input_manager.addKey( KeyEvent.VK_UP );
        m_input_manager.addKey( KeyEvent.VK_DOWN );
        m_input_manager.addKey( KeyEvent.VK_LEFT );
        m_input_manager.addKey( KeyEvent.VK_RIGHT );
        m_input_manager.addKey( KeyEvent.VK_SPACE );

        m_input_manager.addButton( MouseEvent.BUTTON1 );
    }

    @Override
    public void keyTyped( KeyEvent key_event ) {
        m_input_manager.notifyKeyEvent( key_event );

        repaint( );
    }

    @Override
    public void keyPressed( KeyEvent key_event ) {
        m_input_manager.notifyKeyEvent( key_event );

        repaint( );
    }

    @Override
    public void keyReleased( KeyEvent key_event ) {
        m_input_manager.notifyKeyEvent( key_event );

        repaint( );
    }

    @Override
    public void mouseClicked( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );

        repaint( );
    }

    @Override
    public void mousePressed( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );

        repaint( );
    }

    @Override
    public void mouseReleased( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );

        repaint( );
    }

    @Override
    public void mouseEntered( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );

        repaint( );
    }

    @Override
    public void mouseExited( MouseEvent mouse_event ) {
        m_input_manager.notifyMouseEvent( mouse_event );

        repaint( );
    }

    @Override
    protected void paintComponent( Graphics graphics ) {
        super.paintComponent( graphics );

        if ( m_state != GameState.PlayScreen )
            m_input_manager.lock( );

        m_tilemap.tick( m_entities );

        m_input_manager.unlock( );
        m_render_manager.prepare( graphics );
        m_tilemap.display( m_render_manager );
        m_entities.display( m_render_manager );
        m_ui.display( m_input_manager, m_render_manager );

        m_input_manager.tick( );
    }

    public GameState getState( ) { return m_state; }

}
