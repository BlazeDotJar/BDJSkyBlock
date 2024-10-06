package de.bdj.sb.utlility;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

public class EntityChecker {

    /**
     * Return true, if the giving entity type is a monster
     * @param et EntityType
     * @return is Monster
     */
    public static boolean isMonster(EntityType et) {
        if(et.getEntityClass() == null) return false;
        else if(Monster.class.isAssignableFrom(et.getEntityClass())) return true;
        else return false;
    }
    /**
     * Return true, if the giving entity is a monster
     * @param ent Entity
     * @return is Monster
     */
    public static boolean isMonster(Entity ent) {
        if(ent == null) return false;
        else if(Monster.class.isAssignableFrom(ent.getClass())) return true;
        else return false;
    }
    /**
     * Return true, if the giving entity type is an animal
     * @param et EntityType
     * @return is Animal
     */
    public static boolean isAnimal(EntityType et) {
        if(et.getEntityClass() == null) return false;
        else if(Animals.class.isAssignableFrom(et.getEntityClass())) return true;
        else return false;
    }
    /**
     * Return true, if the giving entity is an animal
     * @param ent Entity
     * @return is Animal
     */
    public static boolean isAnimal(Entity ent) {
        if(ent == null) return false;
        else if(Animals.class.isAssignableFrom(ent.getClass())) return true;
        else return false;
    }

}
