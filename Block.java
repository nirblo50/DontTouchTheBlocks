package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by nirbl on 21/11/2016.
 */

public class Block
{
    private ShapeRenderer shapeRenderer;        //o the block
    private int posX;       //o the x position of the block
    private int posY;       //o the y position of the block
    private int width;     //o the weidth of the block
    private int height;     //o the height of the block
    private int speed;
    private boolean enable;     //o enables the block

    public Block ()
    {
        this.shapeRenderer = new ShapeRenderer();
        speed = 15;
        posX = 0;
        posY = Gdx.graphics.getHeight();
        enable = false;
    }


    //o draws the block
    public void drawBlock(int x, int y,  int height, int width)
    {
       if(enable) {
           this.width = width;
           this.height = height;
           shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
           shapeRenderer.setColor(Color.BLACK);
           shapeRenderer.rect(x, y, width, height);
           shapeRenderer.end();

           this.posX = x;
           this.posY = y;
       }
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
