package ModelComponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by ada on 27.06.2018.
 */

public class ModelPathComponent extends ModelComponent {

    public void setPosition(Vector3f v) {
        this.instance.transform.setTranslation(v.x,v.y,v.z );
    }

    public ModelPathComponent (Vector2f pos, int floor) {
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox((float) 0.2f,0.2f,0.2f,
                new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        this.instance = new ModelInstance(model);
        this.instance.transform.setTranslation(pos.x, 100f * floor + 0.5f, pos.y);
    }
}
