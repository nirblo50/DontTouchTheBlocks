package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nirbl on 27/11/2016.
 */

public class Blocks
{
    private Texture texture;
    private Batch batch;

    private int posX;       //o the x position of the block
    private int posY;       //o the y position of the block
    private int width;     //o the weidth of the block
    private int height;     //o the height of the block
    private int speed;
    private boolean enable;     //o enables the block

    public Blocks()
    {
        texture = new Texture(Gdx.files.internal("block.png"));
        batch = new SpriteBatch();
        enable = false;
    }

    public void drawBlock(int x, int y,  int height, int width)
    {
        if(enable)
        {
            this.width = width;
            this.height = height;

            this.posX = x;
            this.posY = y;

            batch.begin();
            batch.draw(texture, x, y, width, height);
            batch.end();
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public Batch getBatch() {
        return batch;
    }
    //o return the x position of the block;
    public int getPosX()
    {
        return this.posX;
    }

    //o return the y position of the block;
    public int getPosY()
    {
        return this.posY;
    }

    //o return the width of the block;
    public int getWidth()
    {
        return this.width;
    }

    //o return the height of the block;
    public int getHeight()
    {
        return this.height;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int weidth) {
        this.width = weidth;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }


}


