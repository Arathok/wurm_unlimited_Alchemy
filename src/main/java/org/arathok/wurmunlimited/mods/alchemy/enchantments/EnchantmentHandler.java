package org.arathok.wurmunlimited.mods.alchemy.enchantments;

import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.Players;
import com.wurmonline.server.WurmCalendar;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.zones.VolaTile;
import com.wurmonline.server.zones.Zones;
import org.arathok.wurmunlimited.mods.alchemy.Config;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class EnchantmentHandler {

    public EnchantmentHandler() {

    }

    public static List<Enchantment> enchantments = new LinkedList<>();

    public static void RemoveEnchantment() throws SQLException, NoSuchItemException {
        long time = WurmCalendar.getCurrentTime();
        // Iterate over me Baby!

        Iterator<Enchantment> enchantmentsIterator = enchantments.iterator();
        Enchantment enchantedItem;
        while (enchantmentsIterator.hasNext()) {
            enchantedItem = enchantmentsIterator.next();
            // enchantedItem.itemId
            long playerId = enchantedItem.playerId;
            Player enchanter;
            enchanter = Players.getInstance().getPlayerOrNull(playerId);
            // TODO: Sollte jetzt keine NO Such Item Exception mehr verursachen, testen
            if (Items.getItemOptional(enchantedItem.itemId).isPresent()) {
                Item realItem = Items.getItem(enchantedItem.itemId);

                // IF ITEM IS MAILED OR IN LETTERBOX
                if (realItem.getTopParentOrNull() != null && realItem.getParentOrNull() != null) {
                    if (realItem.isMailed() || realItem.getParent().isMailBox()
                            || realItem.getParent().getTemplate().getName().contains("spirit")) {
                        try {
                            Items.getItem(enchantedItem.itemId).getSpellEffects()
                                    .removeSpellEffect(enchantedItem.enchantmentType);

                            if (enchanter != null && enchanter.getWurmId() != -10)
                                enchanter.getCommunicator().sendAlertServerMessage(
                                        "Traveling through dimensions via mail used up all the magic of the oil coating your weapon...");
                            realItem.setName(enchantedItem.itemNameBeforeEnchantment);
                            enchantmentsIterator.remove();
                            Enchantment.remove(Alchemy.dbconn, enchantedItem.itemId);
                            if (Config.verboseLogging)
                                Alchemy.logger.log(Level.INFO, "Oil on Item " + enchantedItem.itemId
                                        + realItem.getName() + "was removed because the player was mailing it");

                        }
                        catch (NoSuchItemException e) {
                            Alchemy.logger.log(Level.SEVERE, "No item found for the id" + enchantedItem.itemId, e);

                        }
                    }
                }

                if (realItem.isMailed())
                    try {
                        Items.getItem(enchantedItem.itemId).getSpellEffects()
                                .removeSpellEffect(enchantedItem.enchantmentType);

                        if (enchanter != null && enchanter.getWurmId() != -10)
                            enchanter.getCommunicator().sendAlertServerMessage(
                                    "Traveling through dimensions via mail used up all the magic of the oil coating your weapon...");
                        realItem.setName(enchantedItem.itemNameBeforeEnchantment);
                        enchantmentsIterator.remove();
                        Enchantment.remove(Alchemy.dbconn, enchantedItem.itemId);
                        if (Config.verboseLogging)
                            Alchemy.logger.log(Level.INFO, "Oil on Item " + enchantedItem.itemId + realItem.getName()
                                    + "was removed because the player was mailing it");

                    }
                    catch (NoSuchItemException e) {
                        Alchemy.logger.log(Level.SEVERE, "No item found for the id" + enchantedItem.itemId, e);

                    }

                // IF ITEM IS TOO CLOSE TO THE SERVER BORDER

                if (realItem.getTileX() < 50 || realItem.getTileX() > Zones.worldTileSizeX - 50
                        || realItem.getTileY() < 50 || realItem.getTileY() > Zones.worldTileSizeY - 50) {
                    try {
                        Items.getItem(enchantedItem.itemId).getSpellEffects()
                                .removeSpellEffect(enchantedItem.enchantmentType);

                        if (enchanter != null && enchanter.getWurmId() != -10)
                            enchanter.getCommunicator().sendAlertServerMessage(
                                    "Magicks of the world used up all the magic power left in the oil.");
                        realItem.setName(enchantedItem.itemNameBeforeEnchantment);
                        enchantmentsIterator.remove();
                        Enchantment.remove(Alchemy.dbconn, enchantedItem.itemId);
                        if (Config.verboseLogging)
                            Alchemy.logger.log(Level.INFO,
                                    "Oil on Item " + enchantedItem.itemId + " " + realItem.getName()
                                            + "was removed because the player was too close to the world Border");
                    }
                    catch (NoSuchItemException e) {
                        Alchemy.logger.log(Level.SEVERE, "No item found for the id" + enchantedItem.itemId, e);

                    }
                }

                // IF ITEM IS NEAR A PORTAL
                int itemTileX = realItem.getTileX();
                int itemTileY = realItem.getTileY();
                VolaTile[] itemTiles;
                if (realItem.isOnSurface()) {
                    itemTiles = Zones.getTilesSurrounding(itemTileX, itemTileY, true, 5);
                }
                else {
                    itemTiles = Zones.getTilesSurrounding(itemTileX, itemTileY, false, 5);
                }
                boolean portalFound = false;
                for (VolaTile onetile : itemTiles) {
                    Item[] items = onetile.getItems();
                    for (Item oneItem : items) {
                        if (oneItem.getTemplate().getName().contains("portal")
                                || oneItem.getTemplate().getName().contains("Portal")) {
                            portalFound = true;
                            break;
                        }
                    }
                }
                if (portalFound) {

                    try {
                        Items.getItem(enchantedItem.itemId).getSpellEffects()
                                .removeSpellEffect(enchantedItem.enchantmentType);

                        if (enchanter != null && enchanter.getWurmId() != -10)
                            enchanter.getCommunicator().sendAlertServerMessage(
                                    "Portal Magicks of the world used up all the magic power left in the oil.");
                        realItem.setName(enchantedItem.itemNameBeforeEnchantment);
                        enchantmentsIterator.remove();
                        Enchantment.remove(Alchemy.dbconn, enchantedItem.itemId);
                        if (Config.verboseLogging)
                            Alchemy.logger.log(Level.INFO, "Oil on Item " + enchantedItem.itemId + realItem.getName()
                                    + "was removed because the player was too close to a portal");
                    }
                    catch (NoSuchItemException e) {
                        Alchemy.logger.log(Level.SEVERE, "No item found for the id" + enchantedItem.itemId, e);

                    }
                }
                // IF time ran out
                if (enchantedItem.timeOfEnchantment + Config.oilDuration * 8L < time) {
                    try {
                        Items.getItem(enchantedItem.itemId).getSpellEffects()
                                .removeSpellEffect(enchantedItem.enchantmentType);

                        if (enchanter != null && enchanter.getWurmId() != -10)
                            enchanter.getCommunicator().sendAlertServerMessage(
                                    "The power of the oil vanishes as the liquid is not able to carry energy any more");

                        realItem.setName(enchantedItem.itemNameBeforeEnchantment);
                        if (Config.verboseLogging)
                            Alchemy.logger.log(Level.INFO,
                                    "Oil on Item " + enchantedItem.itemId + " " + realItem.getName()
                                            + "was removed because the " + Config.oilDuration
                                            + " seconds timer on it ran out");
                        enchantmentsIterator.remove();
                        Enchantment.remove(Alchemy.dbconn, enchantedItem.itemId);
                    }
                    catch (NoSuchItemException e) {
                        Alchemy.logger.log(Level.SEVERE, "No item found for the id" + enchantedItem.itemId, e);

                    }

                }
            }
        }
    }
}
