package utils;

public class Rectangle {

    private final Vector2 m_min;
    private final Vector2 m_max;

    public Rectangle( Vector2 position, Vector2 dimensions ) {
        m_min = position;
        m_max = position.add( dimensions );
    }

    @Override
    public String toString( ) { return "[ min : " + m_min + ", max : " + m_max + " ]"; }

    public Vector2 getMin( ) { return m_min; }

    public Vector2 getMax( ) { return m_max; }

}
