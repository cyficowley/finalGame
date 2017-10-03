package finalGame.usefulClasses;

import finalGame.Screen;
import finalGame.enemies.Enemy;

/**
 * Created by cyrus on 5/10/17.
 */
public class AddObjects
{
	public static void addEnemy(Enemy input)
	{
		Screen.enemies.add(input);
		Screen.movingObjects.add(input);
	}
}
