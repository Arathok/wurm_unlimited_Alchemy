package org.arathok.wurmunlimited.mods.alchemy.enchantments;

import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import com.wurmonline.server.Players;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import javassist.*;
import org.arathok.wurmunlimited.mods.alchemy.Config;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import java.util.Iterator;
import java.util.logging.Level;

public class ServerTravelHook {

    public static void insert() {
        ClassPool classPool = HookManager.getInstance().getClassPool();
        CtClass playerTravel;

        {
            try {
                playerTravel = classPool.getCtClass("com.wurmonline.server.intra.PlayerTransfer");
                CtMethod sendItem = playerTravel.getMethod("sendItem",
                        "(Lcom/wurmonline/server/items/Item;Ljava/io/DataOutputStream;Z)V");
                sendItem.insertBefore(
                        "org.arathok.wurmunlimited.mods.alchemy.enchantments.ServerTravelHook.insertBefore($1);");
            } catch (NotFoundException | CannotCompileException e) {
                Alchemy.logger.log(Level.WARNING, "Class  PlayerTravel not found, or Code for hook is incorrect", e);


            }
        }
    }

    public static void insertBefore(Item aplayersItem) {

        long itemId = aplayersItem.getWurmId();
        Iterator<Enchantment> enchantmentsIterator = EnchantmentHandler.enchantments.iterator();
        Enchantment enchantedItem;
        while (enchantmentsIterator.hasNext()) {
            enchantedItem = enchantmentsIterator.next();
            // enchantedItem.itemId
            if (enchantedItem.itemId == itemId) {
                aplayersItem.getSpellEffects().removeSpellEffect(enchantedItem.enchantmentType);
                Player owner = Players.getInstance().getPlayerOrNull(aplayersItem.getOwnerId());
                if (owner != null && owner.getWurmId() != -10)
                    owner.getCommunicator().sendAlertServerMessage(
                            "Portal Magicks of the world used up all the magic power left in the oil on your "
                                    + aplayersItem.getName() + ".");
                aplayersItem.setName(enchantedItem.itemNameBeforeEnchantment);
                enchantmentsIterator.remove();
                Enchantment.remove(Alchemy.dbconn, enchantedItem.itemId);
                if (Config.verboseLogging)
                    Alchemy.logger.log(Level.INFO, "Oil on Item " + enchantedItem.itemId + aplayersItem.getName()
                            + "was removed by the server travel hook");
            }

        }
    }
}
