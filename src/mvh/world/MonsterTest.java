package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Test
    void chooseMove() {
    }

    @Test
    void attackWhere_test1() {
          {
            World world=new World(3,3);
            Monster monster=new Monster(3,'M', WeaponType.AXE);
            Hero hero=new Hero(3,'H',10,5);
            world.addEntity(0,0,Wall.getWall());
            world.addEntity(0,1,hero);
            world.addEntity(0,2,null);
            world.addEntity(1,0,null);
            world.addEntity(1,1,monster);
            world.addEntity(1,2,null);
            world.addEntity(2,0,null);
            world.addEntity(2,1,Wall.getWall());
            world.addEntity(2,2,null);

            assertEquals(Direction.getDirection(-1,0),monster.attackWhere(world));

        }
    }
}