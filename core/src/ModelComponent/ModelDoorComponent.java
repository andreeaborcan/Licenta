package ModelComponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import org.lwjgl.util.vector.Vector2f;

import static java.lang.Math.atan;

/**
 * Created by ada on 21.04.2018.
 */

public class ModelDoorComponent extends ModelComponent {

    public float distance(Vector2f point1,Vector2f point2)
    {
        return (float) Math.sqrt((point1.getX()-point2.getX())*(point1.getX()-point2.getX())+
                (point1.getY()-point2.getY())*(point1.getY()-point2.getY()));
    }
    
    public ModelDoorComponent(Vector2f point1, Vector2f point2, int floor) {
        ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox((float) (distance(point1,point2)),6,(float)0.02,
                new Material(ColorAttribute.createDiffuse(Color.BROWN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        this.instance = new ModelInstance(model);
        this.instance.transform.rotate((float)0,
                (float) atan((point2.getY() - point1.getY())/ (point2.getX() - point1.getX())),
                (float) 0,
                (float) Math.toDegrees( atan((point2.getY() - point1.getY())/ (point2.getX() - point1.getX()))));
        this.instance.transform.setTranslation((float)((point1.getX()+point2.getX())/2.0),
                100f * floor,
                (float)((point1.getY()+point2.getY())/2.0) );
    }
}
