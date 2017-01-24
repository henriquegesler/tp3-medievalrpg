package medievalRPG.screens;

/**
 * Created by Henrique on 22/11/2016.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TransitionEffect {
    private float alpha;
    private float delta;
    private float x;

    public TransitionEffect() {
        this.alpha = 0;
        this.x = 0;
        this.delta = 0;
    }

    public void update(float dt) {
        x += delta;
    }

    public void fadeOut(Batch batch, Sprite sprite) {
        alpha = timeFunction(x);
        sprite.draw(batch, Math.max(Math.min(alpha, 1), 0));
    }

    public void fadeIn(Batch batch, Sprite sprite) {
        sprite.draw(batch, Math.max(Math.min(1 - alpha, 1), 0));
        alpha = timeFunction(x);
    }

    public boolean isFinished() {
        return x >= 1;
    }

    public void setDelta(float value) {
        this.delta = value;
    }

    public void setX(float value) {
        this.x = value;
    }

    public float timeFunction(float x) {
        return (float) x * x * x * x;
    }
}