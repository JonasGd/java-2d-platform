package components;

import editor.PropertiesWindow;
import engine.MouseListener;
import util.Settings;

public class ScaleGizmo extends Gizmo{

    public ScaleGizmo(Sprite scaleSprite, PropertiesWindow propertiesWindow) {
        super(scaleSprite, propertiesWindow);
    }

    @Override
    public void update(float dt){
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                cumulativeScaleX -= MouseListener.getWorldDx();
                if (cumulativeScaleX <= - Settings.GRID_WIDTH &&
                        activeGameObject.transform.scale.x/activeGameObject.transform.originalScale.x >= 0.5f) {
                    if (activeGameObject.transform.scale.x <= activeGameObject.transform.originalScale.x) activeGameObject.transform.scale.x /= 2;
                    else activeGameObject.transform.scale.x -= Settings.GRID_WIDTH;
                    cumulativeScaleX = 0.0f;
                } else if (cumulativeScaleX >= Settings.GRID_WIDTH) {
                    if (activeGameObject.transform.scale.x < activeGameObject.transform.originalScale.x) activeGameObject.transform.scale.x *= 2;
                    else activeGameObject.transform.scale.x += Settings.GRID_WIDTH;
                    cumulativeScaleX = 0.0f;
                }
            } else if (yAxisActive && !xAxisActive) {
                cumulativeScaleY -= MouseListener.getWorldDy();
                if (cumulativeScaleY <= - Settings.GRID_HEIGHT &&
                        activeGameObject.transform.scale.y/activeGameObject.transform.originalScale.y >= 0.5f) {
                    if (activeGameObject.transform.scale.y <= activeGameObject.transform.originalScale.y) activeGameObject.transform.scale.y /= 2;
                    else activeGameObject.transform.scale.y -= Settings.GRID_HEIGHT;
                    cumulativeScaleY = 0.0f;
                } else if (cumulativeScaleY >= Settings.GRID_HEIGHT) {
                    if (activeGameObject.transform.scale.y < activeGameObject.transform.originalScale.y) activeGameObject.transform.scale.y *= 2;
                    else activeGameObject.transform.scale.y += Settings.GRID_HEIGHT;
                    cumulativeScaleY = 0.0f;
                }
            }
        }

        super.update(dt);
    }
}
