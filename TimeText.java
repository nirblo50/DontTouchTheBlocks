package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nirbl on 26/11/2016.
 */

public class TimeText
{
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private String text;

    public TimeText()
    {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
    }

    public void drawText (float time )
    {
        spriteBatch.begin();
        font.setColor(Color.BLUE);
        font.getData().setScale(6);
        font.draw(spriteBatch, subText(time), font.getScaleX() , Gdx.graphics.getHeight()-font.getScaleY());
        spriteBatch.end();
    }

    public void drawScoreText (float score, String type )
    {
        spriteBatch.begin();
        font.setColor(Color.BLACK);
        font.getData().setScale(6);

        if (type.equals("highScore"))
            font.draw(spriteBatch, "high score: " + subText(score)+"(s)", Gdx.graphics.getWidth()/2 - font.getScaleX()*50 ,Gdx.graphics.getHeight()/2-20);
        else
            font.draw(spriteBatch, "score: " + subText(score)+ "(s)", Gdx.graphics.getWidth()/2 - font.getScaleX()*50 ,Gdx.graphics.getHeight()/2 + font.getScaleY()*14);

        spriteBatch.end();
    }


    // returns the time with only two digits after the dot
    private String subText(float time)
    {
        text = String.valueOf(time);
        int mone = 0;
        for (int i =0; i< text.length(); i++)
        {
            if((text.charAt(i) == '.'))
                mone++;
        }

            text = text.substring(0, mone+3);
        return text;
}
}

