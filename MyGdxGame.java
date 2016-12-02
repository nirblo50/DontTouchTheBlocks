package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;

import java.util.Random;

import static com.badlogic.gdx.Gdx.graphics;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {


	//o the Preferences of the high score
	Preferences prefs;

	private Blocks[] blocks;
	private StartMenu startMenu;
	private LostMenu lostMenu;
	private TimeText timeText;
	private Background background;

	private float timePast;		//o the time that passed since game started
	private boolean isGame;		//o checks if the game has started
	private boolean gameLost;		//o checks if the game has lost


	private int posX;		//o the x position of the finger on the screen
	private int posY;		//o the y position of the finger on the screen
	private int dir1;		//o down or up (and speed) for 3rd block
	private int dir2;		//o down or up (and speed) for 4th block
	private int dir3;		//o down or up (and speed) for 5th block
	private int dir4;		//o down or up (and speed) for 6th block
	private int dir5;		//o down or up (and speed) for 7th block
	private int dir6;		//o down or up (and speed) for 8th block

	private float highScore;
	private boolean lostFlag;		//o so high score will not continue after loosing
	private float score;


	@Override
	public void create ()
	{
		background = new Background();

		//o the high score
		prefs = Gdx.app.getPreferences("highScore");
		highScore = prefs.getFloat("highScore");
		lostFlag = false;

		blocks = new Blocks[8];

		//o sets the places for the first blocks
		for (int i = 0; i<blocks.length; i++)
		{
			blocks[i] = new Blocks();
			blocks[i].setHeight(randomHeighth());
			blocks[i].setWidth(randomWidth());
		}

		blocks[0].setEnable(true);
		blocks[1].setEnable(true);
		blocks[2].setEnable(true);

		blocks[0].setPosX(randomPos(blocks[0].getWidth()));			//o up to zig down
		blocks[0].setPosY(Gdx.graphics.getHeight());


		blocks[1].setPosX(randomPos(blocks[1].getWidth()));			//o down to up
		blocks[1].setPosY(0-blocks[1].getHeight());

		blocks[2].setPosX(0-blocks[2].getWidth());
		blocks[2].setPosY(randomPos2(blocks[2].getHeight()));

		blocks[3].setPosX(Gdx.graphics.getWidth());
		blocks[3].setPosY(randomPos2(blocks[3].getHeight()));

		blocks[4].setPosX(0-blocks[4].getWidth());
		blocks[4].setPosY(randomPos2(blocks[4].getHeight()));

		blocks[5].setPosX(Gdx.graphics.getWidth());
		blocks[5].setPosY(randomPos2(blocks[5].getHeight()));

		blocks[6].setPosX(Gdx.graphics.getWidth());
		blocks[6].setPosY(-blocks[6].getHeight());

		blocks[7].setPosX(randomPos(blocks[7].getWidth()));
		blocks[7].setPosY(Gdx.graphics.getHeight());


		startMenu = new StartMenu();
		lostMenu = new LostMenu();
		timeText = new TimeText();
		timePast = 0;

		isGame = false;
		gameLost = false;

		posX = 0;
		posY = 0;

		dir1 = randomDir();
		dir2 = randomDir();
		dir3 = randomDir();
		dir4 = randomDir();

	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);		//o clearing the screen and making it white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.input.setInputProcessor(this);		//o setting the input from the phone (pressing the screen)
		//o draws the background
		background.drawBackground();

		timePast += graphics.getDeltaTime();		//o calculate the time that past simce game has began


		//o if the player had started the game
		if (isGame)
		{
			if (timePast > 2.5)
				blocks[3].setEnable(true);
			if (timePast > 5.0)
				blocks[4].setEnable(true);
			if (timePast > 7.5)
				blocks[5].setEnable(true);
			if (timePast > 10.0)
				blocks[6].setEnable(true);
			if (timePast > 12.0)
				blocks[7].setEnable(true);


			//o if 1st block reach end
			if ((blocks[0].getPosY() <= 0 - blocks[0].getHeight())) {
				blocks[0].setHeight(randomHeighth());
				blocks[0].setWidth((randomWidth()));
				blocks[0].setPosX(randomPos(blocks[0].getWidth()));
				blocks[0].setPosY(Gdx.graphics.getHeight());
				blocks[0].setSpeed(randomSpeed(timePast));
			}

			//o if second block reach end
			if ((blocks[1].getPosY() >= Gdx.graphics.getHeight())) {
				blocks[1].setHeight(randomHeighth());
				blocks[1].setWidth((randomWidth()));
				blocks[1].setPosX(randomPos(blocks[1].getWidth()));
				blocks[1].setPosY(0-blocks[1].getHeight());
				blocks[1].setSpeed(randomSpeed(timePast));
			}

			//o if 3rd block reach end
			if (((blocks[2].getPosY() <= 0 - blocks[2].getHeight()) || (blocks[2].getPosX() <= 0 - blocks[2].getWidth())) || (blocks[2].getPosY() >= Gdx.graphics.getHeight()) || (blocks[2].getPosX() >= Gdx.graphics.getWidth()) || (blocks[2].getPosY() <= -blocks[2].getHeight())) {
				blocks[2].setHeight(randomHeighth());
				blocks[2].setWidth((randomWidth()));
				blocks[2].setPosX(0-blocks[2].getWidth());
				blocks[2].setPosY(randomPos2(blocks[2].getHeight()));
				blocks[2].setSpeed(randomSpeed(timePast));
				dir1 = randomDir();
			}

			//o if 4th block reach end
			if (((blocks[3].getPosY() <= 0 - blocks[3].getHeight()) || (blocks[3].getPosX() <= 0 - blocks[3].getWidth())) || (blocks[3].getPosY() >= Gdx.graphics.getHeight()) || (blocks[3].getPosX() >= Gdx.graphics.getWidth())) {
				blocks[3].setHeight(randomHeighth());
				blocks[3].setWidth((randomWidth()));
				blocks[3].setPosX(Gdx.graphics.getWidth());
				blocks[3].setPosY(randomPos2(blocks[3].getHeight()));
				blocks[3].setSpeed(randomSpeed(timePast));
				dir2 = randomDir();
			}

			//o if 5th block reach end
			if (((blocks[4].getPosY() <= 0 - blocks[4].getHeight()) || (blocks[4].getPosX() <= 0 - blocks[4].getWidth())) || (blocks[4].getPosY() >= Gdx.graphics.getHeight()) || (blocks[4].getPosX() >= Gdx.graphics.getWidth())) {
				blocks[4].setHeight(randomHeighth());
				blocks[4].setWidth((randomWidth()));
				blocks[4].setPosX(0-blocks[4].getWidth());
				blocks[4].setPosY(randomPos2(blocks[4].getHeight()));
				blocks[4].setSpeed(randomSpeed(timePast));
				dir3 = randomDir();
			}

			//o if 6th block reach end
			if (((blocks[5].getPosY() <= 0 - blocks[5].getHeight()) || (blocks[5].getPosX() <= 0 - blocks[5].getWidth())) || (blocks[5].getPosY() >= Gdx.graphics.getHeight()) || (blocks[5].getPosX() >= Gdx.graphics.getWidth())) {
				blocks[5].setHeight(randomHeighth());
				blocks[5].setWidth((randomWidth()));
				blocks[5].setPosX(Gdx.graphics.getWidth());
				blocks[5].setPosY(randomPos2(blocks[5].getHeight()));
				blocks[5].setSpeed(randomSpeed(timePast));
				dir4 = randomDir();
			}

			//o if 7th block reach end
			if ( (blocks[6].getPosY() >= Gdx.graphics.getHeight()) || (blocks[1].getPosY() >= Gdx.graphics.getHeight()) || (blocks[6].getPosY() <= -blocks[6].getHeight()))
			{
				blocks[6].setHeight(randomHeighth());
				blocks[6].setWidth((randomWidth()));
				blocks[6].setPosX(Gdx.graphics.getWidth());
				blocks[6].setPosY(-blocks[6].getHeight());
				blocks[6].setSpeed(randomSpeed(timePast));
				dir5 = randomDir();
			}

			//o if 8th block reach end
			if ((blocks[7].getPosY() <= - blocks[7].getHeight()) || (blocks[7].getPosY() >= Gdx.graphics.getHeight()) || (blocks[7].getPosY() <= -blocks[7].getHeight())) {
				blocks[7].setHeight(randomHeighth());
				blocks[7].setWidth((randomWidth()));
				blocks[7].setPosX(randomPos(blocks[7].getWidth()));
				blocks[7].setPosY(Gdx.graphics.getHeight());
				blocks[7].setSpeed(randomSpeed(timePast));
				dir6 = randomDir();
			}



			//o draw the blocks in their position
			blocks [0].drawBlock(blocks[0].getPosX(), blocks[0].getPosY() - blocks[0].getSpeed(), blocks[0].getHeight(), blocks[0].getWidth());
			blocks [1].drawBlock(blocks[1].getPosX(), blocks[1].getPosY() + blocks[1].getSpeed(), blocks[1].getHeight(), blocks[1].getWidth());
			blocks [2].drawBlock(blocks[2].getPosX() + blocks[2].getSpeed(), blocks[2].getPosY() + dir1 , blocks[2].getHeight(), blocks[2].getWidth());
			blocks [3].drawBlock(blocks[3].getPosX() - blocks[3].getSpeed(), blocks[3].getPosY() + dir2 , blocks[3].getHeight(), blocks[3].getWidth());
			blocks [4].drawBlock(blocks[4].getPosX() + blocks[4].getSpeed(), blocks[4].getPosY() + dir3 , blocks[4].getHeight(), blocks[4].getWidth());
			blocks [5].drawBlock(blocks[5].getPosX() - blocks[5].getSpeed(), blocks[5].getPosY() + dir4 , blocks[5].getHeight(), blocks[5].getWidth());
			blocks [6].drawBlock(blocks[6].getPosX() + dir5, blocks[6].getPosY() + blocks[6].getSpeed() , blocks[6].getHeight(), blocks[6].getWidth());
			blocks [7].drawBlock(blocks[7].getPosX() + dir6, blocks[7].getPosY() - blocks[7].getSpeed() , blocks[7].getHeight(), blocks[7].getWidth());

			//o checks if finger hits the blocks and game is lost
			for (int i=0; i<blocks.length; i++)
			{
				if (didTouchBlock(blocks[i], posX, posY))
				{
					gameLost = true;
					isGame = false;
				}
			}


			timeText.drawText(timePast);        //o shows the time text
		}



		//o if did not started the game yet
		else if (!gameLost)
		{
			startMenu.getBatch().enableBlending();
			startMenu.drawMenu();
		}

		//o if the game was lost (finger lifted)
		if(gameLost)
		{
			//o check if new score is better than the old high score
			if (timePast > highScore && !lostFlag)
			{
				//o put the new score as the new high score
				highScore = timePast;
				prefs.putFloat("highScore", highScore);

				//o update your preferences
				prefs.flush();

			}



			if(!lostFlag)
				score = timePast;

			lostMenu.getBatch().enableBlending();
			lostMenu.drawMenu();
			timeText.drawScoreText(highScore, "highScore");
			timeText.drawScoreText(score, "score");
			lostFlag = true;
		}



	}





	@Override
	public void dispose ()
	{
		startMenu.getBatch().dispose();
		startMenu.getTexture().dispose();
		lostMenu.getBatch().dispose();
		lostMenu.getTexture().dispose();
		background.getBatch().dispose();
		background.getTexture().dispose();
		timeText.getSpriteBatch().dispose();
		timeText.getFont().dispose();
		background.getTexture().dispose();
		for(int i=0; i<blocks.length; i++)
		{
			blocks[i].getBatch().dispose();
			blocks[i].getTexture().dispose();
		}
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		posX = screenX;
		posY = Gdx.graphics.getHeight() - screenY;

		//o if lost game and restart
		if(gameLost)
		{
			dispose();
			create();
		}
		isGame = true;
		gameLost = false;
		timePast = 0;
		lostFlag = false;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		gameLost = true;
		isGame = false;
		startMenu.getBatch().disableBlending();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		posX = screenX;
		posY = Gdx.graphics.getHeight() - screenY;
		return true;
	}










	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}






	//o randomize a x place to start the block
	public static int randomPos(int weidth)
	{
		Random rnd = new Random();
		int pos = rnd.nextInt(Gdx.graphics.getWidth() - weidth);
		return pos;
	}


	//o randomize a x place to start the block
	public static int randomPos2(int height)
	{
		Random rnd = new Random();
		int pos = rnd.nextInt(Gdx.graphics.getHeight() - height);
		return pos;
	}


	//o randomize a x place to start the block
	public static int randomSpeed(float lv)
	{
		int lvl = (int) lv;
		Random rnd = new Random();
		int speed = rnd.nextInt(13 + lvl /2 -10)+10;
		return speed;
	}

	//o generate a random Weidth for the block
	public static int randomWidth()
	{
		Random rnd = new Random();
		int weidth = rnd.nextInt(Gdx.graphics.getWidth() /3 - Gdx.graphics.getWidth() /4) + Gdx.graphics.getWidth() /4;
		return weidth;
	}

	//o generate a random Weidth for the block
	public static int randomHeighth()
	{
		Random rnd = new Random();
		int height = rnd.nextInt(Gdx.graphics.getHeight() /4 - Gdx.graphics.getHeight() /5 ) + Gdx.graphics.getHeight() /5;
		return height;
	}


	//o generate a random Weidth for the block
	public static int randomDir()
	{
		Random rnd = new Random();
		int dir = rnd.nextInt(7+7)-7;
		return dir;
	}


	//o checks if finger touched the block
	public static boolean didTouchBlock(Blocks block, int posX, int posY)
	{
		int height = block.getHeight();
		int width = block.getWidth();

		if ( (posX >= block.getPosX()) && (posX <= block.getPosX() + width) )
		{
			if ( (posY >= block.getPosY()) && (posY <= block.getPosY() + height) )
			{
				if(block.isEnable())
					return true;
			}
		}
		return false;
	}






}
