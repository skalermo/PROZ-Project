package engine;

import java.io.Serializable;

public class Point implements Serializable
{
    public final double x;
    public final double y;

    Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}