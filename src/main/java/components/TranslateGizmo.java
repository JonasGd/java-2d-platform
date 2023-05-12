package components;

import editor.PropertiesWindow;
import engine.GameObject;
import engine.Prefabs;
import engine.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class TranslateGizmo extends Component{
    private Vector4f xAxisColor = new Vector4f(1,0,0,1);
    private Vector4f xAxisColorHover = new Vector4f();
    private Vector4f yAxisColor = new Vector4f(0,1,0,1);
    private Vector4f yAxisColorHover = new Vector4f();

    private GameObject xAxisObject;
    private GameObject yAxisObject;
    private SpriteRenderer xAxisSprite;
    private SpriteRenderer yAxisSprite;
    private GameObject activeGameObject = null;

    private Vector2f xAxisOffset = new Vector2f(52.0f,-2.0f);
    private Vector2f yAxisOffset = new Vector2f(6.0f,52.0f);

    private PropertiesWindow propertiesWindow;

    public TranslateGizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow){
        this.xAxisObject = Prefabs.generateSpriteObject(arrowSprite, 8,24);
        this.yAxisObject = Prefabs.generateSpriteObject(arrowSprite, 8,24);
        this.xAxisSprite = this.xAxisObject.getComponent(SpriteRenderer.class);
        this.yAxisSprite = this.yAxisObject.getComponent(SpriteRenderer.class);
        this.propertiesWindow = propertiesWindow;

        Window.getScene().addGameObjectToScene(this.xAxisObject);
        Window.getScene().addGameObjectToScene(this.yAxisObject);
    }

    @Override
    public void start(){
        this.xAxisObject.transform.rotation = (float)Math.PI / 2.0f;
        this.yAxisObject.transform.rotation = (float)Math.PI;
        this.xAxisObject.setNoSerialize();
        this.yAxisObject.setNoSerialize();
    }

    @Override
    public void update(float dt){
        if (this.activeGameObject != null){
            this.xAxisObject.transform.position.set(this.activeGameObject.transform.position);
            this.yAxisObject.transform.position.set(this.activeGameObject.transform.position);
            this.xAxisObject.transform.position.add(this.xAxisOffset);
            this.yAxisObject.transform.position.add(this.yAxisOffset);
        }

        this.activeGameObject = this.propertiesWindow.getActiveGameObject();
        if (this.activeGameObject != null){
            this.setActive();
        } else {
            this.setInactive();
        }
    }

    private void setActive(){
        this.xAxisSprite.setColor(xAxisColor);
        this.yAxisSprite.setColor(yAxisColor);
    }

    private void setInactive(){
        this.activeGameObject = null;
        this.xAxisSprite.setColor(new Vector4f(0,0,0,0));
        this.yAxisSprite.setColor(new Vector4f(0,0,0,0));
    }

}