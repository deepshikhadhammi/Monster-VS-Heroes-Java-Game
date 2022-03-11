package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @Test
    void loadWorld() {
        World world=new World(3,3);
        Monster m=new Monster(10,'M', WeaponType.SWORD);
        Hero h=new Hero(10,'H',3,1);
        File file=new File("world.txt");
        world.addEntity(0,0,m);
        world.addEntity(2,2,h);
        System.out.println(Reader.loadWorld(new File("world.txt")));

        assertEquals(world,Reader.loadWorld(new File("world.txt")));
    }
}