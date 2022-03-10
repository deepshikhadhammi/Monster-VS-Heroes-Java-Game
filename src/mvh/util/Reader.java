package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {
    public static World loadWorld(File fileWorld) {
        int row = 0, column = 0;
        String line = "";
        try {
            FileReader file_reader = new FileReader(fileWorld);
            BufferedReader buffered_reader = new BufferedReader(file_reader);

            for (int i = 0; i < 2; i++) {
                line = buffered_reader.readLine();
                if (i == 0) {
                    row = Integer.parseInt(line);
                } else {
                    column = Integer.parseInt(line);
                }
            }
            World world1 = new World(row, column);
            for (int p = 0; p < (row * column); p++) {
                line = buffered_reader.readLine();
                String[] array = line.split(",");
                if (array.length > 2) {
                    if (array[2].equals("MONSTER")) {
                        int r = Integer.parseInt(array[0]);
                        int c = Integer.parseInt(array[1]);
                        int health = Integer.parseInt(array[4]);
                        char symbol = array[3].charAt(0);
                        char weapon = array[5].charAt(0);
                        Monster monster = new Monster(health, symbol, WeaponType.getWeaponType(weapon));
                        world1.addEntity(r, c, monster);


                    } else if (array[2].equals("HERO")) {
                        int r = Integer.parseInt(array[0]);
                        int c = Integer.parseInt(array[1]);
                        int health = Integer.parseInt(array[4]);
                        char symbol = array[3].charAt(0);
                        int weapon_strength = Integer.parseInt(array[5]);
                        int arm_strength = Integer.parseInt(array[6]);
                        Hero hero = new Hero(health, symbol, weapon_strength, arm_strength);
                        world1.addEntity(r, c, hero);

                    }
                }
            }
            return world1;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
