package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import java.util.Random;

import static com.badlogic.gdx.Gdx.graphics;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {


	//private Block block1, block2, block3, block4, block5;
	private Block[] blocks;
	private StartMenu startMenu;
	private LostMenu lostMenu;
	private TimeText timeText;

	private float timePast;		//o the time that passed since game started
	private boolean isGame;		//o checks if the game has started
	private boolean gameLost;		//o checks if the game has lost


	private int posX;		//o the x position of the finger on the screen
	private int posY;		//o //o the y position of the finger on the screen
	private int[] blockLocation;
	private int[] height;
	private int[] width;
	private int posYB2;
	private int posYB3;



	@Override
	public void create ()
	{
		blocks = new Block[4];
		blockLocation = new int[4];
		height = new int[4];
		width = new int[4];


		//o sets the places for the first blocks
		for (int i = 0; i<4; i++)
		{
			blocks[i] = new Block();
			blocks[i].setHeight(randomHeighth());
			blocks[i].setWidth(randomWidth());
		}

		blocks[0].setEnable(true);

		blocks[0].setPosX(randomPos(blocks[0].getWidth()));
		blocks[0].setPosY(Gdx.graphics.getHeight());


		blocks[1].setPosX(randomPos(blocks[1].getWidth()));
		blocks[1].setPosY(0);

		blocks[2].setPosX(0);
		blocks[2].setPosY(randomPos2(blocks[2].getHeight()));

		blocks[3].setPosX(Gdx.graphics.getWidth());
		blocks[3].setPosY(randomPos2(blocks[3].getHeight()));


		startMenu = new StartMenu();
		lostMenu = new LostMenu();
		timeText = new TimeText();
		timePast = 0;

		isGame = false;
		gameLost = false;

		posX = 0;
		posY = 0;



	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);		//o clearing the screen and making it white
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.input.setInputProcessor(this);		//o setting the input from the phone (pressing the screen)
		timePast += graphics.getDeltaTime();		//o calculate the time that past simce game has began


		//o if the player had started the game
		if (isGame)
		{
			timeText.drawText(timePast);		//o shows the time text

			//o resets the blocks values every round
			for (int i=0; i<4; i++)
			{
				//o if 1st block reached all the way down
				if ((blocks[i].getPosY() <= 0 - blocks[i].getHeight()) || (blocks[i].getPosX() <= 0 - blocks[i].getWidth()))
				{
					blocks[i].setHeight(randomHeighth());
					blocks[i].setWidth((randomWidth()));


					blocks[0].setPosX(randomPos(blocks[0].getWidth()));
					blocks[0].setPosY(Gdx.graphics.getHeight());

					blocks[1].setPosX(randomPos(blocks[1].getWidth()));
					blocks[1].setPosY(0);

					blocks[2].setPosX(0);
					blocks[2].setPosY(randomPos2(blocks[2].getHeight()));

					blocks[3].setPosX(Gdx.graphics.getWidth());
					blocks[3].setPosY(randomPos2(blocks[3].getHeight()));


					blocks[i].setSpeed(randomSpeed());
				}
			}


			blocks [0].drawBlock(blocks[0].getPosX(), blocks[0].getPosY() - blocks[0].getSpeed(), blocks[0].getHeight(), blocks[0].getWidth());
			//blocks [1].drawBlock(blocks[1].getPosX(), blocks[1].getPosY() + blocks[0].getSpeed(), height[0], width[0]);




			//o checks if finger hits the blocks and game is lost
			for (int i=0; i<4; i++)
			{
				if (didTouchBlock(blocks[i], posX, posY))
				{
					gameLost = true;
					isGame = false;
				}
			}



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
			lostMenu.getBatch().enableBlending();
			lostMenu.drawMenu();
		}






	}





	@Override
	public void dispose ()
	{
		startMenu.getBatch().dispose();
		startMenu.getTexture().dispose();
		lostMenu.getBatch().dispose();
		lostMenu.getTexture().dispose();

	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		posX = screenX;
		posY = Gdx.graphics.getHeight() - screenY;

		isGame = true;
		gameLost = false;
		timePast = 0;

		blocks[0].setPosY(Gdx.graphics.getHeight());
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
	public static int randomSpeed()
	{
		Random rnd = new Random();
		int speed = rnd.nextInt(25-10)+10;
		return speed;
	}

	//o generate a random Weidth for the block
	public static int randomWidth()
	{
		Random rnd = new Random();
		int weidth = rnd.nextInt(Gdx.graphics.getWidth() /3 - Gdx.graphics.getWidth() /6) + Gdx.graphics.getWidth() /5;
		return weidth;
	}

	//o generate a random Weidth for the block
	public static int randomHeighth()
	{
		Random rnd = new Random();
		int height = rnd.nextInt(Gdx.graphics.getHeight() /4 - Gdx.graphics.getHeight() /7 ) + Gdx.graphics.getHeight() /7;
		return height;
	}


	//o checks if finger touched the block
	public static boolean didTouchBlock(Block block, int posX, int posY)
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
