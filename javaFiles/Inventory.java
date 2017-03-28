package javaFiles;
import java.awt.Graphics2D;
public class Inventory{
  public static InventoryBlock[][] inventory = new InventoryBlock[4][8];
  public Inventory(){
    for(int r = 0; r < inventory.length; r++){
      for(int c = 0; c < inventory[r].length; c++){
        inventory[r][c] = new InventoryBlock(c *(InventoryBlock.blockSize + 10) + 15, r *(InventoryBlock.blockSize + 10) + 15);
      }
    }
  }
  public void drawInventory(Graphics2D g){
    for(int r = 0; r < inventory.length; r++){
      for(int c = 0; c < inventory[r].length; c++){
        inventory[r][c].drawMe(g);
      }
    }
  }
}