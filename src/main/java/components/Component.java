package components;

import editor.EImGui;
import engine.GameObject;
import imgui.ImGui;
import imgui.type.ImInt;
import lombok.Getter;
import org.jbox2d.dynamics.contacts.Contact;
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

    public void editorUpdate(float dt) {

    }

    public void beginCollision(GameObject collidingObject, Contact contact, Vector2f hitNormal) {

    }

    public void endCollision(GameObject collidingObject, Contact contact, Vector2f hitNormal) {

    }

    public void preSolve(GameObject collidingObject, Contact contact, Vector2f hitNormal) {

    }

    public void postSolve(GameObject collidingObject, Contact contact, Vector2f hitNormal) {

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
                    EImGui.colorPicker4(name, val);
                } else if (type.isEnum()) {
                    String[] enumValues = getEnumValues(type);
                    String enumType = ((Enum)value).name();
                    ImInt index = new ImInt(indexOf(enumType, enumValues));
                    if (ImGui.combo(field.getName(), index, enumValues, enumValues.length)) {
                        field.set(this, type.getEnumConstants()[index.get()]);
                    }
                } else if (type == String.class) {
                    field.set(this, EImGui.inputText(field.getName() + ": ", (String) value));
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

    private <T extends Enum<T>> String[] getEnumValues(Class<T> enumType) {
        String[] enumValues = new String[enumType.getEnumConstants().length];
        int i = 0;
        for (T enumIntegerValue : enumType.getEnumConstants()) {
            enumValues[i] = enumIntegerValue.name();
            i++;
        }
        return enumValues;
    }

    private int indexOf(String str, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (str.equals(arr[i])) return i;
        }
        return -1;
    }

    public void destroy(){

    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }
}
