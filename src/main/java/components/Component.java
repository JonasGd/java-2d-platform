package components;

import editor.EImGui;
import engine.GameObject;
import imgui.ImGui;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Component {

    private static int ID_COUNTER = 0;
    @Getter
    private int uid = -1;

    public transient GameObject gameObject = null;

    public void start() {

    }
    public void update(float dt) {

    }

    public void imgui() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean isTransient = Modifier.isTransient(field.getModifiers());
                if (isTransient) {
                    continue;
                }
                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                if (isPrivate) {
                    field.setAccessible(true);
                }
                Class type = field.getType();
                Object value = field.get(this);
                String name = field.getName();

                if (type == int.class) {
                    int val = (int) value;
                    field.set(this, EImGui.dragInt(name, val));
                } else if (type == float.class) {
                    float val = (float) value;
                    field.set(this, EImGui.dragFloat(name, val));
                } else if (type == boolean.class) {
                    boolean val = (boolean) value;
                    if (ImGui.checkbox(name + ": ", val))
                        field.set(this, !val);
                } else if (type == Vector2f.class) {
                    Vector2f val = (Vector2f) value;
                    EImGui.drawVec2Control(name, val);
                } else if (type == Vector3f.class) {
                    Vector3f val = (Vector3f) value;
                    EImGui.drawVec3Control(name, val);
                } else if (type == Vector4f.class) {
                    Vector4f val = (Vector4f) value;
                    float[] imVec = {val.x, val.y, val.z, val.w};
                    if (ImGui.dragFloat4(name + ": ", imVec))
                        val.set(imVec[0], imVec[1], imVec[2], imVec[3]);
                }

                if(isPrivate) {
                    field.setAccessible(false);
                }
            }
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void generateId(){
        if (this.uid == -1) {
            this.uid = ID_COUNTER++;
        }
    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }
}
