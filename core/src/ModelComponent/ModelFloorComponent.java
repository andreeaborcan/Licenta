package ModelComponent;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import org.lwjgl.util.vector.Vector2f;

import static com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;
import static java.lang.Math.atan;

public class ModelFloorComponent extends ModelComponent {

    public ModelFloorComponent(int floor) {
        final Texture texture = new Texture(Gdx.files.internal("floor.png"));
        texture.setWrap(Repeat, Repeat);
        final Material material = new Material(TextureAttribute.createDiffuse(texture),
                ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));
        final long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(1000f,0.5f, 1000f, material, attributes);
        instance = new ModelInstance(model);
        instance.transform.setTranslation(0f, floor * 100f - 3f, 0f);
    }

}
