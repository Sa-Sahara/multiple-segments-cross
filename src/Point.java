
public class Point {
    float mY;
    float mX;

    Point (float iX, float iY) {
        this.mX = iX;
        this.mY = iY;
    }
    /*Point (float minWidth, float maxWidth, float minHeight, float maxHeight) {
        float x = new Random().nextFloat(maxWidth) + minWidth;
        float y = new Random().nextFloat(maxWidth) + minWidth;
    }*/

    Point (Point other) {
        this.mX = other.mX;
        this.mY = other.mY;
    }
}