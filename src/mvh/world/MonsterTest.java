package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Test
    void chooseMove_test1() {
        World world=new World(5,5); //creating a world object
        Monster monster=new Monster(3,'M', WeaponType.AXE); //creating a monster object
        Hero hero=new Hero(3,'H',10,5); //creating a hero object
        world.addEntity(2,2,monster); //adding monster to world
        world.addEntity(3,4,hero); //adding hero to world
        assertEquals(Direction.getDirection(1,2),monster.chooseMove(world)); //check if expected and actual output matches

    }
    @Test
    void chooseMove_test2() {
        World world=new World(5,5); //creating a world object
        Monster monster=new Monster(3,'M', WeaponType.AXE); //creating a monster object
        Hero hero=new Hero(3,'H',10,5); //creating a hero object
        world.addEntity(0,3,hero); //adding hero to world
        world.addEntity(2,2,monster); //adding monster to world
        assertEquals(Direction.getDirection(-2,1),monster.chooseMove(world)); //check if expected and actual output matches

    }

    @Test
    void attackWhere_test1() {

            World world = new World(3, 3); //creating a world object
            Monster monster = new Monster(3, 'M', WeaponType.AXE); //creating a monster object
            Hero hero = new Hero(3, 'H', 10, 5); //creating a hero object
            world.addEntity(0, 1, hero); //adding hero to world
            world.addEntity(1, 1, monster); //adding monster to world
            assertEquals(Direction.getDirection(-1, 0), monster.attackWhere(world)); //check if expected and actual output matches

    }
    @Test
        void attackWhere_test2() {
            {
                World world=new World(3,3); //creating a world object
                Monster monster=new Monster(3,'M', WeaponType.AXE); //creating a monster object
                Hero hero=new Hero(3,'H',10,5); //creating a hero object
                world.addEntity(1,1,monster); //adding monster to world
                world.addEntity(2,2,hero); //adding hero to world
                assertEquals(Direction.getDirection(1,1),monster.attackWhere(world)); //check if expected and actual output matches

            }
    }
}