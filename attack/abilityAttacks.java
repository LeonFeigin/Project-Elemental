package attack;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import player.sprite;

public class abilityAttacks {
    public static final int NO_ELEMENT = 0;
    public static final int FIRE_ELEMENT = 1;
    public static final int WATER_ELEMENT = 2;
    public static final int EARTH_ELEMENT = 3;
    public static final int ICE_ELEMENT = 4;
    public static final int LIGHTNING_ELEMENT = 5;

    
    // Get the image for the specified element
    public static BufferedImage getElementImage(int element) {
        switch (element) {
            case FIRE_ELEMENT:
                return sprite.getImages("attack/elementsImages/fire/", 16);
            case WATER_ELEMENT:
                return sprite.getImages("attack/elementsImages/water/", 16);
            case EARTH_ELEMENT:
                return sprite.getImages("attack/elementsImages/earth/", 16);
            case ICE_ELEMENT:
                return sprite.getImages("attack/elementsImages/ice/", 16);
            case LIGHTNING_ELEMENT:
                return sprite.getImages("attack/elementsImages/lightning/", 16);
            default:
                return null; // No element or unknown element
        }
    }

    // Elemental interactions and their damage multipliers
    public static int getDamageMultiplier(int initDamage, ArrayList<Integer> elementsList, int applyingElement) { 
        // superconduct (ice and lightning) x1.5
        if((elementsList.contains(ICE_ELEMENT) || applyingElement == ICE_ELEMENT) && (elementsList.contains(LIGHTNING_ELEMENT) || applyingElement == LIGHTNING_ELEMENT)) {
            return (int)(initDamage * 1.5);
        }
        // overloaded (fire and lightning) x2.5
        if((elementsList.contains(FIRE_ELEMENT) || applyingElement == FIRE_ELEMENT) && (elementsList.contains(LIGHTNING_ELEMENT) || applyingElement == LIGHTNING_ELEMENT)) {
            return (int)(initDamage * 2.5);
        }
        // electro-charged (lightning and water) x2
        if((elementsList.contains(LIGHTNING_ELEMENT) || applyingElement == LIGHTNING_ELEMENT) && (elementsList.contains(WATER_ELEMENT) || applyingElement == WATER_ELEMENT)) {
            return (int)(initDamage * 2);
        }
        // melt (fire on ice) x2
        if(applyingElement == FIRE_ELEMENT && elementsList.contains(ICE_ELEMENT)) {
            return (int)(initDamage * 2);
        }
        // reversed melt (ice on fire) x1.5
        if(applyingElement == ICE_ELEMENT && elementsList.contains(FIRE_ELEMENT)) {
            return (int)(initDamage * 1.5);
        }
        // vaporized (water on fire) x2
        if(applyingElement == WATER_ELEMENT && elementsList.contains(FIRE_ELEMENT)) {
            return (int)(initDamage * 2);
        }
        // reversed vaporized (fire on water) x1.5
        if(applyingElement == FIRE_ELEMENT && elementsList.contains(WATER_ELEMENT)) {
            return (int)(initDamage * 1.5);
        }
        // burning (fire and earth) x2
        if((elementsList.contains(FIRE_ELEMENT) || applyingElement == FIRE_ELEMENT) && (elementsList.contains(EARTH_ELEMENT) || applyingElement == EARTH_ELEMENT)) {
            return (int)(initDamage * 2);
        }
        // frozen (ice and water) x2
        if((elementsList.contains(ICE_ELEMENT) || applyingElement == ICE_ELEMENT) && (elementsList.contains(WATER_ELEMENT) || applyingElement == WATER_ELEMENT)) {
            return (int)(initDamage * 2);
        }

        // grow (earth and anything) 1.25x
        if((elementsList.contains(EARTH_ELEMENT) && applyingElement != EARTH_ELEMENT) || (applyingElement == EARTH_ELEMENT && (elementsList.contains(FIRE_ELEMENT) || elementsList.contains(WATER_ELEMENT) || elementsList.contains(ICE_ELEMENT) || elementsList.contains(LIGHTNING_ELEMENT)))) {
            return (int)(initDamage * 1.25);
        }

        return initDamage; // If no elemental interaction applies, return the initial damage
    }
}
