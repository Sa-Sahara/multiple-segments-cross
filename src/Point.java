public class Point {
    float x;
    float y;

    Point (float iX, float iY) {
        this.x = iX;
        this.y = iY;
    }

    Point (Point other) {
        this.x = other.x;
        this.y = other.y;
    }
}