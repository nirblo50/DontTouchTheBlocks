package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nirbl on 23/11/2016.
 */

public class LostMenu
{
    private Texture texture;
    private Batch batch;

    public LostMenu()
    {
        texture = new Texture(Gdx.files.internal("lostMenu.png"));
        batch = new SpriteBatch();
    }

    public void drawMenu()
    {
        int x = 0;
        int y = Gdx.graphics.getHeight()/3;

        batch.begin();
        batch.draw(texture,x, y-150, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2-50 );
        batch.end();

    }

    public Texture getTexture()
    {
        return texture;
    }

    public Batch getBatch() {
        return batch;
    }


}
