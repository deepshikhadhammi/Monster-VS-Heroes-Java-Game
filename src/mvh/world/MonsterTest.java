package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Test
    void chooseMove_test1() {
        World world=new World(5,5);
        Monster monster=new Monster(3,'M', WeaponType.AXE);
        Hero hero=new Hero(3,'H',10,5);
        world.addEntity(0,0,Wall.getWall());
        world.addEntity(0,1,Wall.getWall());
        world.addEntity(0,2,null);
        world.addEntity(0,3,Wall.getWall());
        world.addEntity(0,4,Wall.getWall());
        world.addEntity(1,0,Wall.getWall());
        world.addEntity(1,1,null);
        world.addEntity(1,2,null);
        world.addEntity(1,3,null);
        world.addEntity(1,4,Wall.getWall());
        world.addEntity(2,0,null);
        world.addEntity(2,1,null);
        world.addEntity(2,2,monster);
        world.addEntity(2,3,null);
        world.addEntity(2,4,Wall.getWall());
        world.addEntity(3,0,null);
        world.addEntity(3,1,null);
        world.addEntity(3,2,null);
        world.addEntity(3,3,null);
        world.addEntity(3,4,hero);
        world.addEntity(4,0,Wall.getWall());
        world.addEntity(4,1,Wall.getWall());
        world.addEntity(4,2,Wall.getWall());
        world.addEntity(4,3,Wall.getWall());
        world.addEntity(4,4,Wall.getWall());
        assertEquals(Direction.getDirection(1,2),monster.chooseMove(world));

    }

    @Test
    void attackWhere_test1() {

            World world = new World(3, 3);
            Monster monster = new Monster(3, 'M', WeaponType.AXE);
            Hero hero = new Hero(3, 'H', 10, 5);
            world.addEntity(0, 0, Wall.getWall());
            world.addEntity(0, 1, hero);
            world.addEntity(0, 2, null);
            world.addEntity(1, 0, null);
            world.addEntity(1, 1, monster);
            world.addEntity(1, 2, null);
            world.addEntity(2, 0, null);
            world.addEntity(2, 1, Wall.getWall());
            world.addEntity(2, 2, null);

            assertEquals(Direction.getDirection(-1, 0), monster.attackWhere(world));


    }
    @Test
        void attackWhere_test2() {
            {
                World world=new World(3,3);
                Monster monster=new Monster(3,'M', WeaponType.AXE);
                Hero hero=new Hero(3,'H',10,5);
                world.addEntity(0,0,Wall.getWall());
                world.addEntity(0,1,null);
                world.addEntity(0,2,null);
                world.addEntity(1,0,null);
                world.addEntity(1,1,monster);
                world.addEntity(1,2,null);
                world.addEntity(2,0,null);
                world.addEntity(2,1,Wall.getWall());
                world.addEntity(2,2,hero);

                assertEquals(Direction.getDirection(1,1),monster.attackWhere(world));

            }
    }
}