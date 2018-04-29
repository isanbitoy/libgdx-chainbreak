package com.chainbreak.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.chainbreak.game.entities.*;
import com.chainbreak.game.screens.GameScreen;
import com.chainbreak.game.utils.CameraUtils;
import com.chainbreak.game.utils.Constants;

import java.util.Iterator;

public class WorldMap
{
    private final GameScreen screen;
    private OrthogonalTiledMapRenderer mapRenderer;

    private int levelWidth = 0;
    private int levelHeight = 0;

    private Array<Actor> actors;
    private Array<Chain> chains;
    private Array<BouncePad> bouncePads;
    private Array<TrapSpike> trapSpikes;
    private Array<TrapFire> trapFires;
    private Array<TrapLava> trapLavas;
    private Array<TrapBarrel> trapBarrels;
    private Array<Portal> portals;
    private Array<ArrowA> arrowAs;
    private Array<ArrowB> arrowBs;

    public WorldMap(GameScreen screen, int level)
    {
        this.screen = screen;
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("maps/level" + level + ".tmx");
        for (TiledMapTileSet tileSet : map.getTileSets())
        {
            Iterator<TiledMapTile> tile = tileSet.iterator();
            tile.next().getTextureRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        MapProperties mapProps = map.getProperties();
        levelWidth = mapProps.get("width", Integer.class);
        levelHeight = mapProps.get("height", Integer.class);

        WorldBox2d(screen.getWorld(), map.getLayers().get("Polyline").getObjects());
        ActorBox2d(map.getLayers().get("Actor").getObjects());
        ChainBox2d(map.getLayers().get("Chain").getObjects());
        BouncePadBox2d( map.getLayers().get("Pad").getObjects());
        TrapSpikeBox2d(map.getLayers().get("Spike").getObjects());
        TrapFireBox2d(map.getLayers().get("Fire").getObjects());
        TrapLavaBox2d(map.getLayers().get("Lava").getObjects());
        TrapBarrelBox2d(map.getLayers().get("Explosive").getObjects());
        PortalBox2d(map.getLayers().get("Portal").getObjects());
        ArrowABox2d(map.getLayers().get("ArrowA").getObjects());
        ArrowBBox2d(map.getLayers().get("ArrowB").getObjects());
    }

    private void WorldBox2d(World world, MapObjects objects)
    {
        for (MapObject object : objects)
        {
            Shape shape;
            if (object instanceof PolylineMapObject)
            {
                shape = createPolyLine((PolylineMapObject) object);
            }
            else
            {
                continue;
            }
            BodyDef bodyDef = new BodyDef();
            FixtureDef fixtureDef = new FixtureDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bodyDef);
            fixtureDef.filter.categoryBits = Constants.GROUND_BIT;
            fixtureDef.filter.maskBits = Constants.ACTOR_BIT;
            fixtureDef.shape = shape;
            body.createFixture(shape, 1.0f).setUserData("Ground");
            shape.dispose();
        }
    }

    private static ChainShape createPolyLine(PolylineMapObject polylineMapObject)
    {
        float[] vertices = polylineMapObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for (int i = 0; i < worldVertices.length; i++)
        {
            worldVertices[i] = new Vector2(vertices[i*2] / Constants.PPM, vertices[i*2+1] / Constants.PPM);
        }
        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);

        return chainShape;
    }

    private void ActorBox2d(MapObjects objects)
    {
        actors = new Array<Actor>();
        for (MapObject object : objects)
        {
            Rectangle position = ((RectangleMapObject) object).getRectangle();
            actors.add(new Actor(screen, (position.getX() + position.getWidth() / 2) / Constants.PPM, (position.getY() + position.getHeight() / 2) / Constants.PPM));
        }
    }

    private void ChainBox2d(MapObjects objects)
    {
        chains = new Array<Chain>();
        for (MapObject object : objects)
        {
            Rectangle position = ((RectangleMapObject) object).getRectangle();
            chains.add(new Chain(screen, (position.getX() + position.getWidth() / 2) / Constants.PPM, (position.getY() + position.getHeight() / 2) / Constants.PPM));
        }
    }

    private void BouncePadBox2d(MapObjects objects)
    {
        bouncePads = new Array<BouncePad>();
        for (MapObject object : objects)
        {
            Rectangle position = ((RectangleMapObject) object).getRectangle();
            bouncePads.add(new BouncePad(screen, (position.getX() + position.getWidth() / 2) / Constants.PPM, (position.getY() + position.getHeight() / 2) / Constants.PPM));
        }
    }

    private void TrapSpikeBox2d(MapObjects objects)
    {
        trapSpikes = new Array<TrapSpike>();
        for (MapObject object : objects)
        {
            float rotation = 0, posY;
            if (object.getProperties().get("rotation", Float.class) != null)
                rotation = (object.getProperties().get("rotation", Float.class));

            if (rotation == 180)
                posY = 1.4f;
            else
                posY = 3.5f;

            Rectangle position = ((RectangleMapObject) object).getRectangle();
            trapSpikes.add(new TrapSpike(screen, rotation, (position.getX() + position.getWidth() / 2) / Constants.PPM, (position.getY() + position.getHeight() / posY) / Constants.PPM));
        }
    }

    private void TrapFireBox2d(MapObjects objects)
    {
        trapFires = new Array<TrapFire>();
        for (MapObject object : objects)
        {
            Rectangle position = ((RectangleMapObject) object).getRectangle();
            trapFires.add(new TrapFire(screen, (position.getX() + position.getWidth() / 2) / Constants.PPM, (position.getY() + position.getHeight() / 3.5f) / Constants.PPM));
        }
    }

    private void TrapLavaBox2d(MapObjects objects)
    {
        trapLavas = new Array<TrapLava>();
        for (MapObject object : objects)
        {
            Rectangle position = ((RectangleMapObject) object).getRectangle();
            trapLavas.add(new TrapLava(screen, (position.getX() + position.getWidth() / 2) / Constants.PPM, (position.getY() + position.getHeight() / 2) / Constants.PPM));
        }
    }

    private void TrapBarrelBox2d(MapObjects objects)
    {
        trapBarrels = new Array<TrapBarrel>();
        for (MapObject object : objects)
        {
            Rectangle position = ((RectangleMapObject) object).getRectangle();
            trapBarrels.add(new TrapBarrel(screen, (position.getX() + position.getWidth() / 2) / Constants.PPM, (position.getY() + position.getHeight() / 2) / Constants.PPM));
        }
    }

    private void PortalBox2d(MapObjects objects)
    {
        portals = new Array<Portal>();
        for (MapObject object : objects)
        {
            Ellipse position = ((EllipseMapObject) object).getEllipse();
            portals.add(new Portal(screen, (position.x + position.width / 2) / Constants.PPM, (position.y + position.height / 2) / Constants.PPM));
        }
    }

    private void ArrowABox2d(MapObjects objects)
    {
        arrowAs = new Array<ArrowA>();
        for (MapObject object : objects)
        {
            float rotation = 0;
            if (object.getProperties().get("rotation", Float.class) != null)
                rotation = (object.getProperties().get("rotation", Float.class));

            Ellipse position = ((EllipseMapObject) object).getEllipse();
            arrowAs.add(new ArrowA(screen, rotation, (position.x + position.width / 2) / Constants.PPM, (position.y + position.height / 2) / Constants.PPM));
        }
    }

    private void ArrowBBox2d(MapObjects objects)
    {
        arrowBs = new Array<ArrowB>();
        for (MapObject object : objects)
        {
            float rotation = 0;
            if (object.getProperties().get("rotation", Float.class) != null)
                rotation = (object.getProperties().get("rotation", Float.class));

            Ellipse position = ((EllipseMapObject) object).getEllipse();
            arrowBs.add(new ArrowB(screen, rotation, (position.x + position.width / 2) / Constants.PPM, (position.y + position.height / 2) / Constants.PPM));
        }
    }

    public void update(OrthographicCamera camera)
    {
        float camHalfWidth = camera.viewportWidth/2;
        float camHalfHeight = camera.viewportHeight/2;

        CameraUtils.mapBoundary(camera, camHalfWidth, camHalfHeight, levelWidth/Constants.PPM * 32 - camHalfWidth * 2, levelHeight/Constants.PPM * 32 - camHalfHeight * 2);
        mapRenderer.setView(camera);
    }

    public Array<Actor> getActor()
    {
        return actors;
    }
    public Array<Chain> getChains()
    {
        return chains;
    }
    public Array<BouncePad> getBouncePads()
    {
        return bouncePads;
    }
    public Array<Trap> getTrapObjects()
    {
        Array<Trap> traps = new Array<Trap>();
        traps.addAll(trapSpikes);
        traps.addAll(trapFires);
        traps.addAll(trapLavas);
        traps.addAll(trapBarrels);

        return traps;
    }
    public Array<Arrow> getArrows()
    {
        Array<Arrow> arrows = new Array<Arrow>();
        arrows.addAll(arrowAs);
        arrows.addAll(arrowBs);

        return arrows;
    }
    public Array<Portal> getPortal()
    {
        return portals;
    }

    public void render()
    {
        mapRenderer.render();
    }
    public void dispose()
    {
        mapRenderer.dispose();
    }
}
