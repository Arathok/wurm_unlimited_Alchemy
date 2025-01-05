package org.arathok.wurmunlimited.mods.alchemy.oils;

import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.Enchantment;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.EnchantmentHandler;
import com.wurmonline.server.Items;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.WurmCalendar;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemSpellEffects;
import com.wurmonline.server.sounds.SoundPlayer;
import com.wurmonline.server.spells.SpellEffect;
import org.arathok.wurmunlimited.mods.alchemy.Config;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class OilPerformer implements ActionPerformer {

    public ActionEntry actionEntry;
    private boolean playedOpeningSound;
    private boolean playedGlugGlugSound;

    public OilPerformer() {
        int[] types = new int[0];
        if (Config.oilsDuringFighting) {
            types = new int[] { 6 /* ACTION_TYPE_NOMOVE */, 48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                    36 /* USE SOURCE AND TARGET */, 28, // nonstack in fight

            };

            actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "apply weapon oil", "applying",
                    types).range(4).priority(1000).build();
        }
        else {
            types = new int[] { 6 /* ACTION_TYPE_NOMOVE */, 48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                    36 /* USE SOURCE AND TARGET */, 28, // nonstack in fight

            };

            actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "apply weapon oil", "applying",
                    types).range(4).build();
        }
        ModActions.registerAction(actionEntry);
    }

    int seconds = Config.oilDuration * 8;
    float power = 0;

    @Override
    public short getActionId() {
        return actionEntry.getNumber();
    }

    public static boolean canUse(Creature performer, Item source) {

        return performer.isPlayer() && source.getOwnerId() == performer.getWurmId() && !source.isTraded();
    }

    public static boolean isEnchantable(Item target) throws NoSuchItemException {
        Enchantment oiledWeapon;
        boolean hasOil = false;
        Iterator<Enchantment> oilChecker = EnchantmentHandler.enchantments.iterator();
        if (!Config.enchantmentsStack)
            return ((target.getSpellEffects().getEffects().length == 0) || (target.getSpellEffects() == null));
        else
            while (oilChecker.hasNext()) {
                oiledWeapon = oilChecker.next();
                if (Items.getItem(oiledWeapon.itemId) == target && oiledWeapon.hasOil)
                    hasOil = true;
            }

        return !hasOil && !target.isWeaponBow() && (target.isWeapon() || target.isArrow() || target.isQuiver());
    }

    public static boolean isWeapon(Item target) {
        return !target.isWeaponBow() && (target.isWeapon() || target.isArrow() || target.isQuiver());
    }

    @Override
    public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter) { // Since
                                                                                                                   // we
                                                                                                                   // use
                                                                                                                   // target
                                                                                                                   // and
                                                                                                                   // source
                                                                                                                   // this
                                                                                                                   // time,
                                                                                                                   // only
                                                                                                                   // need
                                                                                                                   // that
                                                                                                                   // override
        if (counter == 1.0F) {
            action.setTimeLeft(50);
            performer.sendActionControl(action.getActionString(), true, 50);
            playedOpeningSound = false;
            playedGlugGlugSound = false;
        }
        else if (counter > 1.0F && action.getSecond() == 1) {
            if (!playedOpeningSound) {
                SoundPlayer.playSound("sound.openFlask", performer, 1.6F);

                performer.playAnimation("create", false, target.getWurmId());
                playedOpeningSound = true;

            }
            return propagate(action, ActionPropagation.CONTINUE_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }
        else if (counter > 1.0F && action.getSecond() == 3) {
            if (!playedGlugGlugSound) {
                SoundPlayer.playSound("sound.oilWeapon", performer, 1.6F);
                playedGlugGlugSound = true;
            }
        }
        else if (counter > 1.0F && action.getSecond() == 5) {
            /*
             * if (target.getTemplateId() != OilItems.weaponOilDemiseAnimalId)
             *
             * return propagate(action, ActionPropagation.SERVER_PROPAGATION,
             * ActionPropagation.ACTION_PERFORMER_PROPAGATION);
             */
            if (!canUse(performer, source)) {
                performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
                return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                        ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
            }

            try {
                if (!isEnchantable(target)) {
                    performer.getCommunicator().sendAlertServerMessage(
                            "There is already a lot of magical power stored inside this weapon, you should"
                                    + " consult a priest to disenchant it or wait until the current oil has dried off");
                    return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                            ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                }
            }
            catch (NoSuchItemException e) {
                Alchemy.logger.log(Level.SEVERE, "no item found for" + target.getName(), e);

            }

            // EFFECT STUFF GOES HERE
            seconds = Config.oilDuration;
            power = source.getCurrentQualityLevel() * Config.alchemyPower;
            if (source.getRarity() == 1) {
                power = power * Config.rarityFactorRare;
            }

            if (source.getRarity() == 2) {
                power = power * Config.rarityFactorSupreme;
            }

            if (source.getRarity() == 3) {
                power = power * Config.rarityFactorFantastic;
            }

            boolean hasPelt = false;
            Enchantment e = new Enchantment();
            Set<Item> items;
            items = performer.getInventory().getItems();

            for (Item item : items) {
                if (item.getTemplate().getName().contains("pelt") || item.getTemplate().getName().contains("Pelt")) {
                    hasPelt = true;
                    break;
                }
                else if (item.getTemplate().getName().contains("back")) {
                    Set<Item> backpackItems;
                    backpackItems = item.getItems();
                    for (Item itemInBackpack : backpackItems) {
                        if (itemInBackpack.getTemplate().getName().contains("pelt")
                                || itemInBackpack.getTemplate().getName().contains("Pelt")) {
                            hasPelt = true;
                            break;
                        }
                    }
                }
            }

            ItemSpellEffects effs = target.getSpellEffects();
            SpellEffect eff = null;
            if (effs != null) // if the weapon is Enchanted
                if (effs.getEffects().length > 0)
                    seconds = (int) (seconds * Config.oilDurationOnEnchanted); // oil only lasts a fifth of the set time
            e.itemNameBeforeEnchantment = target.getName();
            // DEMISE ANIMAL
            if (source.getTemplateId() == OilItems.weaponOilDemiseAnimalId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 11);
                    if (eff == null && effs.getSpellEffect((byte) 9) == null && effs.getSpellEffect((byte) 10) == null
                            && effs.getSpellEffect((byte) 12) == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 11, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);

                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }

                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {

                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows? "
                                + " You stash the rest of the oil in your pocket", (byte) 2);

                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    target.setName((target.getName() + " (oil,hunt)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now be effective against animals"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }

                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 11);
                            if (eff == null && effs.getSpellEffect((byte) 9) == null
                                    && effs.getSpellEffect((byte) 10) == null
                                    && effs.getSpellEffect((byte) 12) == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 11, power, (seconds));
                                effs.addSpellEffect(eff);
                                Enchantment arrowEnchantment = new Enchantment();
                                arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                arrow.setName((arrow.getName() + " (oil,hunt)"));
                                try {

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.hasOil = true;
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);

                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator().sendNormalServerMessage("You pour the " + source.getName() + " in your "
                            + target.getName() + "and see how Oil spreads across the Arrows coating them nicely."
                            + " Your arrows will now be effective against animals for a short time.", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            // DEMISE HUMAN
            if (source.getTemplateId() == OilItems.weaponOilDemiseHumanId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 9);
                    if (eff == null && effs.getSpellEffect((byte) 11) == null && effs.getSpellEffect((byte) 10) == null
                            && effs.getSpellEffect((byte) 12) == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 9, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }

                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    else if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil, Murder)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now be effective against Humans"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 9);
                            if (eff == null && effs.getSpellEffect((byte) 11) == null
                                    && effs.getSpellEffect((byte) 10) == null
                                    && effs.getSpellEffect((byte) 12) == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 9, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil,Murder)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {

                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator().sendNormalServerMessage("You pour the " + source.getName() + " in your "
                            + target.getName() + "and see how Oil spreads across the Arrows coating them nicely."
                            + " Your arrows will now be effective against humans for a short time.", (byte) 2);
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            // DEMISE MONSTER
            if (source.getTemplateId() == OilItems.weaponOilDemiseMonsterId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();
                    // ALREADY ENCHANTED? YOU GET NOTHING!

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 10);
                    if (eff == null && effs.getSpellEffect((byte) 9) == null && effs.getSpellEffect((byte) 11) == null
                            && effs.getSpellEffect((byte) 12) == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 10, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }

                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil,monster hunt)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now be effective against monsters"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }

                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 10);
                            if (eff == null && effs.getSpellEffect((byte) 9) == null
                                    && effs.getSpellEffect((byte) 11) == null
                                    && effs.getSpellEffect((byte) 12) == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 10, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil,monster hunt)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator().sendNormalServerMessage(
                            "You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now be effective against monsters for a short time.",
                            (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            // DEMISE LEGENDARY
            if (source.getTemplateId() == OilItems.weaponOilDemiseLegendaryId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();
                    // ALREADY ENCHANTED? YOU GET NOTHING!

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 12);
                    if (eff == null && effs.getSpellEffect((byte) 9) == null && effs.getSpellEffect((byte) 10) == null
                            && effs.getSpellEffect((byte) 11) == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 12, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }
                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil, legendary hunt)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now be effective against legendaries"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 12);
                            if (eff == null && effs.getSpellEffect((byte) 9) == null
                                    && effs.getSpellEffect((byte) 10) == null
                                    && effs.getSpellEffect((byte) 11) == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 12, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil, legendary hunt)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator()
                            .sendNormalServerMessage("You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now be effective against legendaries for a short time.",
                                    (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }
            // Fires Kiss
            if (source.getTemplateId() == OilItems.weaponOilLickOfFireId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 14);
                    if (eff == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 14, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }
                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil,flaming)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now be creating flaming wounds!"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 14);
                            if (eff == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 14, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil, flaming)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator()
                            .sendNormalServerMessage("You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now cause flaming wounds!", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }
            // Frost
            if (source.getTemplateId() == OilItems.weaponOilKissOfFrostId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 33);
                    if (eff == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 33, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }
                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil, frost)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil \nand will cause icy wounds!"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 33);
                            if (eff == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 33, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();
                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil, frost)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator()
                            .sendNormalServerMessage("You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now cause icy wounds!", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            // Leech
            if (source.getTemplateId() == OilItems.weaponOilLeechId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 26);
                    if (eff == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 26, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }
                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil, leech)"));
                    performer.getCommunicator().sendNormalServerMessage("The " + target.getName()
                            + " is now glistening from the oil and will now suck out life energy from your opponents!"
                            + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 26);
                            if (eff == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 26, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil, leech)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator()
                            .sendNormalServerMessage("You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now suck out live energy of your opponents", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            // DEMISE Plague
            if (source.getTemplateId() == OilItems.weaponOilPlagueId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 18);
                    if (eff == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 18, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }
                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil, plague)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now cause infected wounds"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)

                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 18);
                            if (eff == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 18, power, (seconds));
                                eff.timeleft = Config.oilDuration;
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil, plague)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator()
                            .sendNormalServerMessage("You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now cause infected wounds!", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            // Poison
            if (source.getTemplateId() == OilItems.weaponOilPoisonId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 27);
                    if (eff == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 27, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }
                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil, poison)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now poison your enemy"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 27);
                            if (eff == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 27, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil, poison)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);

                                }
                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator()
                            .sendNormalServerMessage("You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now poison your enemy!", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            // Heartseeker
            if (source.getTemplateId() == OilItems.weaponOilHeartseekerId) {
                if (target.isWeapon() || target.isArrow() || target.isQuiver()) {
                    effs = target.getSpellEffects();

                    if (effs == null)
                        effs = new ItemSpellEffects(target.getWurmId());
                    eff = effs.getSpellEffect((byte) 32);
                    if (eff == null) {
                        eff = new SpellEffect(target.getWurmId(), (byte) 32, power, (seconds));
                        effs.addSpellEffect(eff);
                    }
                    else {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but it already has an enchantment of that same type on it. A priest has poured a piece of "
                                + "their soul into this weapon and the enchantment repels the oil", (byte) 2);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

                    }
                    if (target.isArrow()) // IS ARROW? THEN ONLY DESTROY A 10th
                    {
                        performer.getCommunicator().sendAlertServerMessage(" You pour the " + source.getName()
                                + " over the " + target.getName()
                                + " but much of the oil falls of to the side and leaves a messy stain on the ground."
                                + " Maybe there is a way to make this more efficient if you "
                                + " pour the oil into something that can hold multiple arrows?", (byte) 2);
                        source.setWeight((source.getWeightGrams() - 20), true);
                        if (source.getWeightGrams() <= 20)
                            Items.destroyItem(source.getWurmId());
                    }
                    if (hasPelt) {
                        performer.getCommunicator().sendNormalServerMessage(
                                "You drench your pelt in some oil and use it to coat the weapon evenly. "
                                        + "You saved some oil and put the phial back into your pocket. It should be good for a few more uses.",
                                (byte) 2);
                        if (source.getWeightGrams() - 80 > 0)
                            source.setWeight(source.getWeightGrams() - 80, true);
                        else
                            Items.destroyItem(source.getWurmId());
                    }
                    else
                        Items.destroyItem(source.getWurmId());

                    e.itemNameBeforeEnchantment = target.getName();
                    target.setName((target.getName() + " (oil, heartseeker)"));
                    performer.getCommunicator()
                            .sendNormalServerMessage("The " + target.getName()
                                    + " is now glistening from the oil and will now cause wounds to be way worse"
                                    + " for a short time.(" + seconds + "seconds)", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());

                }
                if (target.isQuiver()) {
                    int fails = 0;
                    Item[] arrows = target.getItemsAsArray();
                    for (Item arrow : arrows) {

                        if (arrow.isArrow()) {

                            effs = arrow.getSpellEffects();
                            if (effs != null)
                                if (!Config.enchantmentsStack)
                                    fails++;
                            if (effs == null)
                                effs = new ItemSpellEffects(arrow.getWurmId());
                            eff = effs.getSpellEffect((byte) 32);
                            if (eff == null) {

                                eff = new SpellEffect(arrow.getWurmId(), (byte) 32, power, (seconds));
                                effs.addSpellEffect(eff);

                                try {

                                    Enchantment arrowEnchantment = new Enchantment();

                                    arrowEnchantment.itemId = arrow.getWurmId(); // liest quasi den Wert von der Spalte
                                    arrowEnchantment.timeOfEnchantment = WurmCalendar.getCurrentTime()
                                            - ((Config.oilDuration - seconds) * 8);
                                    arrowEnchantment.enchantmentType = eff.type;
                                    arrowEnchantment.playerId = performer.getWurmId();
                                    arrowEnchantment.hasOil = true;
                                    arrowEnchantment.itemNameBeforeEnchantment = arrow.getName();
                                    arrow.setName((arrow.getName() + " (oil, heartseeker)"));
                                    EnchantmentHandler.enchantments.add(arrowEnchantment);
                                    arrowEnchantment.insert(Alchemy.dbconn);
                                    // update ModSupportDb

                                }
                                catch (RuntimeException | SQLException ex) {
                                    Alchemy.logger.log(Level.INFO,
                                            "RuntimeException or SQLException happened or database closed", ex);
                                   
                                }

                            }
                            else {
                                fails++;
                            }
                        }
                    }
                    performer.getCommunicator()
                            .sendNormalServerMessage("You pour the " + source.getName() + " in your " + target.getName()
                                    + "and see how Oil spreads across the Arrows coating them nicely."
                                    + " Your arrows will now cause wounds to be way worse.", (byte) 2);
                    if (Config.verboseLogging)
                        Alchemy.logger.log(Level.INFO, "Player " + performer.getName() + " used an oil "
                                + source.getName() + " on their " + target.getName());
                    if (fails > 0)
                        performer.getCommunicator().sendNormalServerMessage(fails
                                + " arrow(s) did not accept the oil. Probably "
                                + "because they were already coated or enchanted already. The excess oil, drips from your "
                                + target.getName() + " and dries up on the ground.", (byte) 2);
                    Items.destroyItem(source.getWurmId());

                }

            }

            if (eff != null) {

                try {

                    e.itemId = target.getWurmId(); // liest quasi den Wert von der Spalte
                    e.timeOfEnchantment = WurmCalendar.getCurrentTime() - ((Config.oilDuration - seconds) * 8);
                    e.enchantmentType = eff.type;
                    e.hasOil = true;
                    e.playerId = performer.getWurmId();

                    e.insert(Alchemy.dbconn);
                    EnchantmentHandler.enchantments.add(e);
                    // update ModSupportDb

                }
                catch (RuntimeException | SQLException ex) {
                    Alchemy.logger.log(Level.SEVERE, "RuntimeException or SQLException happened", ex);

                }

            }

            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }

        return propagate(action, ActionPropagation.CONTINUE_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

    }
}
