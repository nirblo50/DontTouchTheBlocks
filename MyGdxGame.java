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
	private int posY;		//o the y position of the finger on the screen
	private int dir1;		//o down or up (and speed) for 3rd block
	private int dir2;		//o down or up (and speed) for 4th block



	@Override
	public void create ()
	{
		blocks = new Block[4];

		//o sets the places for the first blocks
		for (int i = 0; i<4; i++)
		{
			blocks[i] = new Block();
			blocks[i].setHeight(randomHeighth());
			blocks[i].setWidth(randomWidth());
		}

		blocks[0].setEnable(true);
		blocks[1].setEnable(true);
		blocks[2].setEnable(true);
		blocks[3].setEnable(true);

		blocks[0].setPosX(randomPos(blocks[0].getWidth()));
		blocks[0].setPosY(Gdx.graphics.getHeight());


		blocks[1].setPosX(randomPos(blocks[1].getWidth()));
		blocks[1].setPosY(0-blocks[1].getHeight());

		blocks[2].setPosX(0-blocks[2].getWidth());
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

		dir1 = randomDir();
		dir2 = randomDir();

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
			timeText.drawText(timePast);        //o shows the time text

			//o if 1st block reach end
			if ((blocks[0].getPosY() <= 0 - blocks[0].getHeight())) {
				blocks[0].setHeight(randomHeighth());
				blocks[0].setWidth((randomWidth()));
				blocks[0].setPosX(randomPos(blocks[0].getWidth()));
				blocks[0].setPosY(Gdx.graphics.getHeight());
				blocks[0].setSpeed(randomSpeed());
			}

			//o if second block reach end
			if ((blocks[1].getPosY() >= Gdx.graphics.getHeight())) {
				blocks[1].setHeight(randomHeighth());
				blocks[1].setWidth((randomWidth()));
				blocks[1].setPosX(randomPos(blocks[1].getWidth()));
				blocks[1].setPosY(0-blocks[1].getHeight());
				blocks[1].setSpeed(randomSpeed());
			}

			//o if 3rd block reach end
			if (((blocks[2].getPosY() <= 0 - blocks[2].getHeight()) || (blocks[2].getPosX() <= 0 - blocks[2].getWidth())) || (blocks[2].getPosY() >= Gdx.graphics.getHeight()) || (blocks[2].getPosX() >= Gdx.graphics.getWidth())) {
				blocks[2].setHeight(randomHeighth());
				blocks[2].setWidth((randomWidth()));
				blocks[2].setPosX(0-blocks[2].getWidth());
				blocks[2].setPosY(randomPos2(blocks[2].getHeight()));
				blocks[2].setSpeed(randomSpeed());
				dir1 = randomDir();
			}

			//o if 4th block reach end
			if (((blocks[3].getPosY() <= 0 - blocks[3].getHeight()) || (blocks[3].getPosX() <= 0 - blocks[3].getWidth())) || (blocks[3].getPosY() >= Gdx.graphics.getHeight()) || (blocks[3].getPosX() >= Gdx.graphics.getWidth())) {
				blocks[3].setHeight(randomHeighth());
				blocks[3].setWidth((randomWidth()));
				blocks[3].setPosX(Gdx.graphics.getWidth());
				blocks[3].setPosY(randomPos2(blocks[3].getHeight()));
				blocks[3].setSpeed(randomSpeed());
				dir2 = randomDir();
			}

			//o draw the blocks in their position
			blocks [0].drawBlock(blocks[0].getPosX(), blocks[0].getPosY() - blocks[0].getSpeed(), blocks[0].getHeight(), blocks[0].getWidth());
			blocks [1].drawBlock(blocks[1].getPosX(), blocks[1].getPosY() + blocks[1].getSpeed(), blocks[1].getHeight(), blocks[1].getWidth());
			blocks [2].drawBlock(blocks[2].getPosX() + blocks[2].getSpeed(), blocks[2].getPosY() + dir1 , blocks[2].getHeight(), blocks[2].getWidth());
			blocks [3].drawBlock(blocks[3].getPosX() - blocks[3].getSpeed(), blocks[3].getPosY() + dir2 , blocks[3].getHeight(), blocks[3].getWidth());


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

		if(gameLost)
			create();

		isGame = true;
		gameLost = false;
		timePast = 0;
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
		int speed = rnd.nextInt(13-10)+10;
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


	//o generate a random Weidth for the block
	public static int randomDir()
	{
		Random rnd = new Random();
		int dir = rnd.nextInt(7+7)-7;
		return dir;
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
