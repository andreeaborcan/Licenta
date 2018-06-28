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

/**
 * Created by ada on 21.04.2018.
 */
//aici modelezi peretii, geamurile,usile in functie de coordonatele din json
public class ModelWallComponent extends ModelComponent {

    private float distance(Vector2f point1,Vector2f point2)
    {
        return (float) Math.sqrt((point1.getX()-point2.getX())*(point1.getX()-point2.getX())+(point1.getY()-point2.getY())*(point1.getY()-point2.getY()));
    }
    public ModelWallComponent(Vector2f point1,Vector2f point2, int floor) {
        Texture texture = new Texture(Gdx.files.internal("white.png"));
        texture.setWrap(Repeat, Repeat);
        //disposables.add(texture);
        final Material material = new Material(TextureAttribute.createDiffuse(texture), ColorAttribute.createSpecular(1, 1, 1, 1),
                FloatAttribute.createShininess(8f));
        final long attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;

        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox((float) (distance(point1,point2)),6,(float)0.05,
                //new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                material, attributes);
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        this.instance = new ModelInstance(model);
        this.instance.transform.rotate((float)0, (float) atan((point2.getY() - point1.getY())/
                (point2.getX() - point1.getX())),(float) 0,
                (float) Math.toDegrees( atan((point2.getY() - point1.getY())/
                        (point2.getX() - point1.getX()))));
        this.instance.transform.setTranslation((float)((point1.getX()+point2.getX())/2.0),
                100f * floor,(float)((point1.getY()+point2.getY())/2.0) );
    }

}
