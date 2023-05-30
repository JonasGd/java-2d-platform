package engine;

import components.Component;
import editor.EImGui;
import org.joml.Vector2f;
import util.Settings;

public class Transform extends Component {
    public Vector2f position;
    public Vector2f scale;
    public Vector2f originalScale;
    public float rotation = 0.0f;
    public int zIndex;

    public Transform(){
        this(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position){
        this(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        this(position, scale, null);
    }

    public Transform(Vector2f position, Vector2f scale, Vector2f originalScale) {
        init(position, scale, originalScale);
    }

    public void init(Vector2f position, Vector2f scale, Vector2f originalScale) {
        this.position = position;
        this.scale = scale;
        this.zIndex = 0;
        this.originalScale = new Vector2f(Settings.GRID_WIDTH,Settings.GRID_HEIGHT);
        /*
        if (originalScale == null && (this.originalScale == null || this.originalScale.equals(new Vector2f()))) {
            this.originalScale = scale;
        }
        else if (this.originalScale != null && !this.originalScale.equals(new Vector2f()))
            this.originalScale = originalScale;

         */
    }

    @Override
    public void imgui() {
        gameObject.name = EImGui.inputText("Name: ", gameObject.name);
        EImGui.drawVec2Control("Position", this.position);
        EImGui.drawVec2Control("Scale", this.scale, Settings.GRID_WIDTH);
        this.rotation = EImGui.dragFloat("Rotation", this.rotation, 0.02f);
        this.zIndex = EImGui.dragInt("Z-Index", this.zIndex);
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
        return t.position.equals(this.position) && t.scale.equals(this.scale) &&
                t.originalScale.equals(this.originalScale) && t.rotation == this.rotation
                & t.zIndex == this.zIndex;
    }
}
