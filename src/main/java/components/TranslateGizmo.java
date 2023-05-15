package components;

import editor.PropertiesWindow;
import engine.MouseListener;
import util.Settings;

public class TranslateGizmo extends Gizmo{
    public TranslateGizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow){
        super(arrowSprite, propertiesWindow);
    }

    @Override
    public void update(float dt){
        if (activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                cumulativeTransX += MouseListener.getWorldDx();
                if (Math.abs(cumulativeTransX) >= Settings.GRID_WIDTH) {
                    activeGameObject.transform.position.x -= Math.signum(cumulativeTransX) * Settings.GRID_WIDTH;
                    cumulativeTransX = 0.0f;
                }
            } else if (yAxisActive && !xAxisActive) {
                cumulativeTransY += MouseListener.getWorldDy();
                if (Math.abs(cumulativeTransY) >= Settings.GRID_HEIGHT) {
                    activeGameObject.transform.position.y -= Math.signum(cumulativeTransY) * Settings.GRID_HEIGHT;
                    cumulativeTransY = 0.0f;
                }
            }
        }

        super.update(dt);
    }


}
