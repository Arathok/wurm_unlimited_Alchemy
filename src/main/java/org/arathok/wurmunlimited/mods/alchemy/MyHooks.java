package org.arathok.wurmunlimited.mods.alchemy;

import org.arathok.wurmunlimited.mods.alchemy.enchantments.Enchantment;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.EnchantmentHandler;

import com.wurmonline.server.items.Item;

public class MyHooks {

    public static String getNameHook(Item item, String base) {
        String ret = base;

        for (Enchantment e : EnchantmentHandler.enchantments) {
            if (e.itemId == item.getWurmId()) {
                base += "oiled";
                break;
            }
        }

        return ret;
    }
}
