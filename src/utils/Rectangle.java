package utils;

public class Rectangle {

    private Vector2 m_min;
    private Vector2 m_max;

    public Rectangle( Vector2 min, Vector2 max ) {
        m_min = min;
        m_max = min.add( max );
    }

    @Override
    public String toString( ) { return "[ min : " + m_min + ", max : " + m_max + " ]"; }

    public Vector2 getMin( ) { return m_min; }

    public Vector2 getMax( ) { return m_max; }

}
