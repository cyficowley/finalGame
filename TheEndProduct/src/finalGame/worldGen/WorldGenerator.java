package finalGame.worldGen;
import finalGame.Screen;
import finalGame.enemies.*;
import finalGame.usefulClasses.AddObjects;
import finalGame.worldGen.Block;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.pow;

public class WorldGenerator
{
	public static int spawnPointX = 5;
	public static int spawnPointY= 0;
	public static int holeLocation =50;
	public static boolean ranOnce = false;


    static int dirtLevel = 60;
    static int darkDirtLevel = 70;
    static int stoneLevel = 80;
    static Block pastBlock;//types == {0 : air; 1 : stone; 2 : lightDirt; 3 : mediumDirt; 4 : Grass; 5: darkDirt}
    static ArrayList<double[]> worldData = new ArrayList<double[]>(); // each one has a startXIndex, amplitude, a length
    static ArrayList<double[]> cavernSeeding = new ArrayList<double[]>(); // x, y, width of the cavern, direction,
    static ArrayList<Double> stoneLevels = new ArrayList<Double>(); // x, y, height, width of the cavern, length
    static ArrayList<Row> rows = new ArrayList<Row>();




    public void run(Block block)
	{
        if(!ranOnce)
        {
            ranOnce = !ranOnce;
            for(double currentX = 0; currentX < Screen.chunks.size() * 20;)
            {
                double amplitude;
                double length;
                double power;
                if(Math.random() > .6)//flatish area;
                {
                    amplitude = pow(Math.random(),2) *4;
                    power = (Math.random() + .8)/(Math.random() + .8);
                    length = 10 + Math.random() * 15;
                }
                else if(Math.random() > .3) // small hill
                {
                    amplitude = pow(Math.random(),.5) *5;
                    power = (Math.random() + .5)/(Math.random() + .5);
                    length = 10 + amplitude *2;
                }
                else // large hill
                {
                    amplitude = pow(Math.random(),.7) *6;
                    power = (Math.random() + .3)/(Math.random() + .3);
                    length = 10 + amplitude *2;
                }
                worldData.add(new double[]{currentX, amplitude, length, power}); // this controls the height and width of the hills
                currentX += worldData.get(worldData.size()-1)[2];
            }
        }
        double previousAmplitude;
        double amplitude=0;
        double length=0;
        double startXIndex=0;
        double power = 0;
        for(double[] each : worldData)
        {
            if(each[0] + each[2] >= block.xIndex)
            {
                startXIndex = each[0];
                amplitude = each[1];
                length = each[2];
                power = each[3];
                break;
            }
        }
        double dirt = -pow((Math.cos((block.xIndex-startXIndex) * 2 * Math.PI / length + Math.PI) +1),power) * amplitude + dirtLevel;
        double stone = -pow((Math.cos((block.xIndex-startXIndex) * 2 * Math.PI / length + Math.PI) +1),power) * amplitude/1.5 + stoneLevel;
        if(block.yIndex == 0 || block.xIndex == 0 || block.xIndex == Screen.currentXChunks * 20-1 || block.yIndex == Screen.currentYChunks * 20 -1)
        {
            block.rebuild(6);
        }
        else if(block.yIndex < (int)(dirt))
        {
            block.rebuild(0);
        }
        else if(block.yIndex < (int)(dirt+1))
        {
            block.rebuild(4);
            stoneLevels.add(stone); // so it only runs once
        }
        else if(block.yIndex < (int)(stone))
        {
            if(block.yIndex < darkDirtLevel)
            {
                if(Math.random() * (darkDirtLevel - dirtLevel) < block.yIndex - dirtLevel)
                {
                    block.rebuild(2);
                }
                else
                {
                    block.rebuild(3);
                }
            }
            else
            {
                if(Math.random() * (stoneLevel - darkDirtLevel) < block.yIndex - darkDirtLevel)
                {
                    block.rebuild(5);
                }
                else
                {
                    block.rebuild(2);
                }
            }
        }
        else
        {
            block.rebuild(1);
        }
        pastBlock = block;
	}
	public void finalChanges()
	{
		double currentWidth=9;
		double currentWidthVelocity = 0;
		int accelLength = (int)(30 * pow(Math.random(), 3)) +1;
        double currentWidthAccelerration = (9 - currentWidth- currentWidthVelocity * (double)accelLength)*2/(pow((double)accelLength,2)) +(Math.random()-.5)*.1;
        double currentPosition=1;
        double currentPositionVelocity = 0;
        int accelLength2 = (int)(30 * pow(Math.random(), 3)) +1;
        double currentPositionAccelerration = ( - currentPosition - currentPositionVelocity * (double)accelLength2)*2/(pow((double)accelLength2,2)) +(Math.random()-.5)*.1;


		for(int i = 1; i < Screen.currentYChunks*20 -1; i ++)
		{
			currentWidthVelocity += currentWidthAccelerration;
			currentWidth += currentWidthVelocity;

            currentPositionVelocity += currentPositionAccelerration;
            currentPosition += currentPositionVelocity;

			if(accelLength <= 1)
			{
				accelLength = (int)(30 * pow(Math.random(), 3)) +5;
				currentWidthAccelerration = (9 - currentWidth- currentWidthVelocity * (double)accelLength)*2/(pow((double)accelLength,2))+(Math.random()-.5)*.1;
                if(currentWidthAccelerration>2.5)
                {
                    currentWidthAccelerration = 2.5;
                }
                else if(currentWidthAccelerration< -2.5)
                {
                    currentWidthAccelerration = -2.5;
                }
			}
            if(accelLength2 <= 1)
            {
                accelLength2 = (int)(30 * pow(Math.random(), 3)) +5;
                currentPositionAccelerration = ( - currentPosition - currentPositionVelocity * (double)accelLength2)*2/(pow((double)accelLength2,2)) +(Math.random()-.5)*.1;
                if(currentPositionAccelerration>2)
                {
                    currentPositionAccelerration = 2;
                }
                else if(currentPositionAccelerration< -2)
                {
                    currentPositionAccelerration = -2;
                }
            }

			accelLength --;
            accelLength2 --;
            int j = holeLocation - (int)Math.abs(currentWidth) - (int)currentPosition -6;
            int startPos = j;
            int endj = (int)(holeLocation + Math.abs(currentWidth) - currentPosition +6);
			while(j < endj) {
                if (j == 0) {

                } else if (j == Screen.currentXChunks * 20 - 1) {

                } else {
                    Screen.getBlock(j, i).rebuild(0);
                }
                j ++;
            }
            if(rows.size() > 0)
            {
                for(int k = rows.get(0).startX; k < rows.get(0).startX + rows.get(0).input.length; k ++)
                {
                    if(rows.get(0).input[k - rows.get(0).startX] == 1)
                    {
                        Screen.getBlock(k,i).rebuild(1);
                    }

                }
                rows.remove(0);
            }
            else if(i > stoneLevel)
            {
                if((endj - startPos)/20 >Math.random() && Math.random() > .8)
                {
                    int width = (int)(pow(Math.random(),2) * .5 * (endj - startPos) + .25 * (endj - startPos));
                    int[] temp = new int[width];
                    for(int o = 0; o < temp.length; o ++)
                    {
                        temp[o] = 1;
                    }
                    rows.add(new Row((int)(startPos + (endj - startPos- width)*Math.random()),temp));
                    double mult = 1;
                    while(pow(Math.random(), pow((double)i/((double) Screen.currentYChunks*20),.2)) < .8 * mult)
                    {
                        mult *=.8;

                        double enemyWidth = 100;
                        double enemyHeight = 100;
                        double enemyDamage = pow((double) i/(Screen.currentYChunks*20),3)*50 + 5;
                        for(double k = rows.get(rows.size() -1).startX * Screen.blockWidth; k < endj* Screen.blockWidth - enemyWidth; k += 20)
                        {
                            if(Screen.getBlockType(k, (i-1.5) * Screen.blockWidth - enemyHeight)== 0 && Screen.getBlockType(k + enemyWidth, (i-1.5) * Screen.blockWidth - enemyWidth)== 0)
                            {
                                Enemy temp2;
                                int enemyChoice = (int) (8 * (Math.pow(Math.random(),1.0/Math.pow((double) (i+50)/(Screen.currentYChunks*20 + 50),1.5))));
                                if(enemyChoice == 1)
                                {
                                    double tempy = 80 + 20 * Math.random();
                                    temp2 = new BasicAiEnemy(k, (i-1.5) * Screen.blockWidth - enemyHeight,tempy,tempy,enemyDamage);
                                }
                                else if(enemyChoice == 0)
                                {
                                    double tempy = 80 + Math.random() * 20;
                                    temp2 = new LedgeEnemy(k, (i-1.5) * Screen.blockWidth - enemyHeight,tempy,tempy,enemyDamage);
                                }
                                else if(enemyChoice == 2)
                                {
                                    double tempy = 50 + Math.random() * 10;
                                    temp2 = new ShootingEnemy(k, (i - 1.5) * Screen.blockWidth - enemyHeight, tempy, tempy, enemyDamage);
                                }
                                else if(enemyChoice == 4)
                                {
                                    double tempy = 70 + Math.random() * 20;
                                    temp2 = new MovingShootingEnemy(k, (i - 1.5) * Screen.blockWidth - enemyHeight, tempy, tempy, enemyDamage);
                                }
                                else if(enemyChoice == 3)
                                {
                                    double tempy = 60 + Math.random() * 20;
                                    temp2 = new FlyingEnemy(k, (i - 1.5) * Screen.blockWidth - enemyHeight, tempy, tempy, enemyDamage);
                                }
                                else if(enemyChoice == 5)
                                {
                                    double tempy = 80 + Math.random() * 20;
                                    temp2 = new LedgeShootingEnemy(k, (i - 1.5) * Screen.blockWidth - enemyHeight, enemyWidth, enemyHeight, enemyDamage);
                                }
                                else if(enemyChoice == 6)
                                {
                                    double tempy = 50 + Math.random() * 20;
                                    temp2 = new FlyingShootingEnemy(k, (i - 1.5) * Screen.blockWidth - enemyHeight, tempy, tempy, enemyDamage);
                                }
                                else
                                {
                                    double tempy = 40 + Math.random() * 20;
                                    temp2 = new Sniper(k, (i - 1.5) * Screen.blockWidth - enemyHeight, tempy, tempy, enemyDamage);
                                }
                                AddObjects.addEnemy(temp2);
                                break;
                            }
                        }
                    }
                    boolean emptyRow = false;
                    int rowCount = 0;
                    while (!emptyRow)
                    {
                        emptyRow = true;
                        rowCount ++;
                        rows.add(new Row(rows.get(rows.size() -1).startX,new int[width]));
                        int currentChange = 0;
                        while(currentChange < width/2 - rowCount)
                        {
                            if(rows.get(rows.size() -2).input[(int)(width/2 + currentChange)] == 1 && ((double)currentChange +8) /(double)(width/2- rowCount + 8) < pow(Math.random(),1-(double)rows.get(rows.size() -1).input[(int)(width/2 + currentChange-1)]*.4))
                            {
                                rows.get(rows.size() -1).input[(int)(width/2 + currentChange)] = 1;
                                emptyRow = false;
                            }
                            if(rows.get(rows.size() -2).input[(int)(width/2 - currentChange)] == 1 && ((double)currentChange +8) /(double)(width/2- rowCount + 8) < pow(Math.random(),1-(double)rows.get(rows.size() -1).input[(int)(width/2 - currentChange+1)]*.4))
                            {
                                rows.get(rows.size() -1).input[(int)(width/2 - currentChange)] = 1;
                                emptyRow = false;
                            }
                            currentChange ++;
                        }
                    }
                    rows.add(new Row((startPos),new int[0]));
                    rows.add(new Row((startPos),new int[0]));
                    rows.add(new Row((startPos),new int[0]));
                }
            }
		}
	}
	public class Row
    {
        int[] input;
        int startX;
        public Row(int startX, int[] input)
        {
            this.startX = startX;
            this.input = input;
        }
    }
}