package org.arathok.wurmunlimited.mods.alchemy.addiction;

import org.arathok.wurmunlimited.mods.alchemy.AlchItems;
import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.Players;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.bodys.Wounds;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import org.arathok.wurmunlimited.mods.alchemy.Config;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

///TODO:create potion that stops addiction
public class AddictionHandler {
    public AddictionHandler() {

    }

    public static long addictionTimer = 30;
    public static List<Addiction> addictions = new LinkedList<>();

    public static void AddictionEffects() throws SQLException {
        long time = System.currentTimeMillis();
        Addiction addiction;
        if (addictionTimer < time) {
            Iterator<Addiction> addictionHandler = addictions.iterator();
            while (addictionHandler.hasNext()) {

                boolean itemFound;

                addiction = addictionHandler.next();
                Player aPlayer = Players.getInstance().getPlayerOrNull(addiction.playerId);
                if (aPlayer != null) {
                    int index = AddictionHandler.addictions.indexOf(addiction);
                    if (addiction.currentAddictionLevel == 0) {
                        Item[] items = aPlayer.getInventory().getItemsAsArray();
                        if (items.length > 0)
                            for (Item item : items) {
                                if (item.getTemplateId() == AlchItems.weakLegsId)
                                    Items.destroyItem(item.getWurmId());
                            }
                    }
                    if (addiction.currentAddictionLevel < addiction.previousAddictionLevel) {
                        if (!aPlayer.isLoggedOut())
                            aPlayer.getCommunicator()
                                    .sendAlertServerMessage("You are slowly coming down from your addiction:");
                        if (addiction.previousAddictionLevel <= 2) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is marginal. You feel it is now safe to consume "
                                            + "potions "

                            );
                        }
                        if (addiction.previousAddictionLevel == 3) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is very low. You are on the verge of being addicted"
                                            + ". You should wait a little while before drinking potions again.");

                        }

                        if (addiction.previousAddictionLevel < 4) {
                            Item[] items = aPlayer.getInventory().getItemsAsArray();
                            for (Item item : items) {
                                if (item.getTemplateId() == AlchItems.weakLegsId)
                                    Items.destroyItem(item.getWurmId());
                            }
                        }

                        if (addiction.previousAddictionLevel >= 4) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is low. The withdrawal causes you to tremble and "
                                            + "you feel weak. Maybe if you come down from your addiction your legs won't be weak anymore.");
                            itemFound = false;
                            Item[] items = aPlayer.getInventory().getItemsAsArray();
                            if (items.length > 0)
                                for (Item item : items) {
                                    if (item.getTemplateId() == AlchItems.weakLegsId) {
                                        itemFound = true;
                                    }

                                }

                            if (!itemFound)
                                try {
                                    Item legs = ItemFactory.createItem(AlchItems.weakLegsId, 99.0F, "Addiction");
                                    double strength = aPlayer.getStrengthSkill();
                                    // create a "weighing down item" which garantees to bring youb below your 100%
                                    // limit if carrying nothing. Nice and annoying
                                    int yourWeight = (int) ((strength / 5) * 7 * 1.5);
                                    legs.setWeight(yourWeight * 1000, true);
                                    aPlayer.getInventory().insertItem(legs, true);
                                }
                                catch (FailedException e) {
                                    Alchemy.logger.log(Level.WARNING,"FailedException",e);

                                }
                                catch (NoSuchTemplateException e) {

                                    Alchemy.logger.log(Level.SEVERE,
                                            "I was trying to create an item but that template didn't exist! Weird!");
                                }

                        }

                        if (addiction.previousAddictionLevel >= 6 && addiction.previousAddictionLevel < 12) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is moderate. You are suffering from a negligible poisoning");
                            Wounds pWounds = aPlayer.getBody().getWounds();
                            float severity = 0;
                            if (pWounds != null)
                                for (Wound w : pWounds.getWounds()) {
                                    severity = severity + w.getSeverity();
                                }

                            if (severity + 5000 < 65500) {
                                aPlayer.addWoundOfType(aPlayer, (byte) 5, 23, false, 1.0F, false, 5000, 1, 0.0F, false,
                                        true);
                                Alchemy.logger.log(Level.INFO,
                                        "added Poison wound to player " + aPlayer.getName()
                                                + ". \nCurrent Addiction Level: " + addiction.currentAddictionLevel
                                                + "\n Previous Addiction Level: " + addiction.previousAddictionLevel
                                                + "\n Next Effect Polled for this player: " + (addictionTimer) / 1000);
                            }
                            else {
                                aPlayer.die(false, "withdrawal effects");
                                Alchemy.logger.log(Level.INFO,
                                        "player " + aPlayer + " died because of addiction. Addiction Level was: "
                                                + addiction.currentAddictionLevel);
                            }

                        }

                        if (addiction.previousAddictionLevel >= 12 && addiction.previousAddictionLevel < 15) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is moderate. You are suffering from a mild poisoning");
                            Wounds pWounds = aPlayer.getBody().getWounds();
                            float severity = 0;
                            if (pWounds != null)
                                for (Wound w : pWounds.getWounds()) {
                                    severity = severity + w.getSeverity();
                                }

                            if (severity + 10000 < 65500) {
                                aPlayer.addWoundOfType(aPlayer, (byte) 5, 23, false, 1.0F, false, 10000, 1, 0.0F, false,
                                        true);
                                Alchemy.logger.log(Level.INFO,
                                        "added Poison wound to player " + aPlayer.getName()
                                                + ". \nCurrent Addiction Level: " + addiction.currentAddictionLevel
                                                + "\n Previous Addiction Level: " + addiction.previousAddictionLevel
                                                + "\n Next Effect Polled for this player: " + (addictionTimer) / 1000);
                            }
                            else {
                                aPlayer.die(false, "withdrawal effects");
                                Alchemy.logger.log(Level.INFO,
                                        "player " + aPlayer + " died because of addiction. Addiction Level was: "
                                                + addiction.currentAddictionLevel);
                            }

                        }

                        if (addiction.previousAddictionLevel >= 15 && addiction.previousAddictionLevel < 20) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is high. You are suffering from a hefty poisoning and you feel a sharp pain inside your head");
                            Wounds pWounds = aPlayer.getBody().getWounds();
                            float severity = 0;
                            if (pWounds != null)
                                for (Wound w : pWounds.getWounds()) {
                                    severity = severity + w.getSeverity();
                                }

                            if (severity + 20000 < 65500) {
                                aPlayer.addWoundOfType(aPlayer, (byte) 5, 23, false, 1.0F, false, 15000, 1, 0.0F, false,
                                        true);
                                aPlayer.addWoundOfType(aPlayer, (byte) 9, 1, false, 1.0F, false, 5000, 0.0F, 0.0F,
                                        false, true);
                                Alchemy.logger.log(Level.INFO,
                                        "added Poison wound to player " + aPlayer.getName()
                                                + ". \nCurrent Addiction Level: " + addiction.currentAddictionLevel
                                                + "\n Previous Addiction Level: " + addiction.previousAddictionLevel
                                                + "\n Next Effect Polled for this player: " + (addictionTimer) / 1000);
                            }
                            else {
                                aPlayer.die(false, "withdrawal effects");
                                Alchemy.logger.log(Level.INFO,
                                        "player " + aPlayer + " died because of addiction. Addiction Level was: "
                                                + addiction.currentAddictionLevel);
                            }

                        }

                        if (addiction.previousAddictionLevel >= 20 && addiction.previousAddictionLevel < 25) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is high. You are suffering from a severe poisoning and your brain feels like its full of knives");
                            Wounds pWounds = aPlayer.getBody().getWounds();
                            float severity = 0;
                            if (pWounds != null)
                                for (Wound w : pWounds.getWounds()) {
                                    severity = severity + w.getSeverity();
                                }

                            if (severity + 30000 < 65500) {
                                aPlayer.addWoundOfType(aPlayer, (byte) 5, 23, false, 1.0F, false, 20000, 1, 0.0F, false,
                                        true);
                                aPlayer.addWoundOfType(aPlayer, (byte) 9, 1, false, 1.0F, false, 10000, 0.0F, 0.0F,
                                        false, true);
                                Alchemy.logger.log(Level.INFO,
                                        "added Poison wound to player " + aPlayer.getName()
                                                + ". \nCurrent Addiction Level: " + addiction.currentAddictionLevel
                                                + "\n Previous Addiction Level: " + addiction.previousAddictionLevel
                                                + "\n Next Effect Polled for this player: " + (addictionTimer) / 1000);
                            }
                            else {
                                aPlayer.die(false, "withdrawal effects");
                                Alchemy.logger.log(Level.INFO,
                                        "player " + aPlayer + " died because of addiction. Addiction Level was: "
                                                + addiction.currentAddictionLevel);
                            }

                        }
                        if (addiction.previousAddictionLevel >= 25) {
                            aPlayer.getCommunicator().sendAlertServerMessage(
                                    "Your addiction to magical energy is deadly high. You are suffering from a severe poisoning and your brain loses the connection to reality");
                            Wounds pWounds = aPlayer.getBody().getWounds();
                            float severity = 0;
                            if (pWounds != null)
                                for (Wound w : pWounds.getWounds()) {
                                    severity = severity + w.getSeverity();
                                }

                            if (severity + 45000 < 65500) {
                                aPlayer.addWoundOfType(aPlayer, (byte) 5, 23, false, 1.0F, false, 25000, 1, 0.0F, false,
                                        true);
                                aPlayer.addWoundOfType(aPlayer, (byte) 9, 1, false, 1.0F, false, 20000, 0.0F, 0.0F,
                                        false, true);
                                Alchemy.logger.log(Level.INFO,
                                        "added Poison wound to player " + aPlayer.getName()
                                                + ". \nCurrent Addiction Level: " + addiction.currentAddictionLevel
                                                + "\n Previous Addiction Level: " + addiction.previousAddictionLevel
                                                + "\n Next Effect Polled for this player: " + (addictionTimer) / 1000);
                            }
                            else {
                                aPlayer.die(false, "withdrawal effects");
                                Alchemy.logger.log(Level.INFO,
                                        "player " + aPlayer + " died because of addiction. Addiction Level was: "
                                                + addiction.currentAddictionLevel);
                            }

                        }
                    }
                    else {
                        Item[] items = aPlayer.getAllItems();
                        if (items.length > 0)
                            for (Item item : items) {
                                if (item.getTemplateId() == AlchItems.weakLegsId)
                                    Items.destroyItem(item.getWurmId());
                            }
                    }
                    Player thePlayer = Players.getInstance().getPlayerOrNull(addiction.playerId);

                    if (thePlayer != null && !thePlayer.isLoggedOut())
                        addiction.previousAddictionLevel = addiction.currentAddictionLevel;
                    addiction.currentAddictionLevel = addiction.currentAddictionLevel - 1;
                    if (addiction.currentAddictionLevel < 0) {
                        addiction.currentAddictionLevel = 0;
                    }
                    addiction.insert(Alchemy.dbconn);
                    AddictionHandler.addictions.set(index, addiction);

                }
                addictionTimer = (System.currentTimeMillis() + (Config.addictionTimer * 1000L));
            }

        }
    }
}