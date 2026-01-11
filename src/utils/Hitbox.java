package utils;

public class Hitbox {

    private final Vector2 m_min;
    private final Vector2 m_max;

    public Hitbox( Vector2 position, Vector2 dimensions ) {
        Vector2 a = position;
        Vector2 b = position.add( dimensions );
        m_min = new Vector2( Math.min( a.getX( ), b.getX( ) ), Math.min( a.getY( ), b.getY( ) ) );
        m_max = new Vector2( Math.max( a.getX( ), b.getX( ) ), Math.max( a.getY( ), b.getY( ) ) );
    }

    public Hitbox move( Vector2 offset ) {
        return new Hitbox( m_min.add( offset ), m_max.sub( m_min ) );
    }

    @Override
    public String toString( ) { return "[ min : " + m_min + ", max : " + m_max + " ]"; }

    public Vector2 getMin( ) { return m_min; }

    public Vector2 getMax( ) { return m_max; }

    public float left( ) { return m_min.getX( ); }

    public float right( ) { return m_max.getX( ); }

    public float bottom( ) { return m_min.getY( ); }

    public float top( ) { return m_max.getY( ); }

}
