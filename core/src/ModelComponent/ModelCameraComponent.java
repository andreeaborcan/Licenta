package ModelComponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static java.lang.Math.atan;

/**
 * Created by ada on 02.06.2018.
 */

public class ModelCameraComponent extends ModelComponent {

    public void setPosition(Vector3f v)
    {
        this.instance.transform.setTranslation(v.x,v.y,v.z );
    }

    public ModelCameraComponent () {
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox((float) 2f,2f,2f,
                new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        this.instance = new ModelInstance(model);
    }
}
