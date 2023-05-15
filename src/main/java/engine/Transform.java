package engine;

import org.joml.Vector2f;

public class Transform {
    public Vector2f position;
    public Vector2f scale;
    public Vector2f originalScale;
    public float rotation = 0.0f;

    public Transform(){
        this(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position){
        this(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale, null);
    }

    public Transform(Vector2f position, Vector2f scale, Vector2f originalScale) {
        init(position, scale, originalScale);
    }

    public void init(Vector2f position, Vector2f scale, Vector2f originalScale) {
        this.position = position;
        this.scale = scale;
        this.originalScale = new Vector2f(32,32);
        /*
        if (originalScale == null && (this.originalScale == null || this.originalScale.equals(new Vector2f()))) {
            this.originalScale = scale;
        }
        else if (this.originalScale != null && !this.originalScale.equals(new Vector2f()))
            this.originalScale = originalScale;

         */
    }

    public Transform copy() {
       return new Transform(new Vector2f(this.position), new Vector2f(this.scale), new Vector2f(this.originalScale));
    }

    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
        if (this.originalScale != null && to.originalScale != null)
           to.originalScale.set(new Vector2f(this.originalScale));
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Transform)) return false;

        Transform t = (Transform) o;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }
}
