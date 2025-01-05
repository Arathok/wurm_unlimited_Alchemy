package org.arathok.wurmunlimited.mods.alchemy.essences;

import java.util.Iterator;

import org.arathok.wurmunlimited.mods.alchemy.enchantments.Enchantment;
import org.arathok.wurmunlimited.mods.alchemy.Config;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;

public class EssencesPerformerItem implements ActionPerformer {

    public ActionEntry actionEntry;

    public EssencesPerformerItem() {

        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "imbue essence on item", "imbueing",
                new int[] { 6 /* ACTION_TYPE_NOMOVE */, 48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                        36 /* USE SOURCE AND TARGET */,

                }).range(4).build();

        ModActions.registerAction(actionEntry);
    }

    int seconds = Config.oilDuration;
    float power = 0;
    Enchantment e = new Enchantment();

    @Override
    public short getActionId() {
        return actionEntry.getNumber();
    }

    public static boolean canUse(Creature performer, Item source) {

        return performer.isPlayer() && source.getOwnerId() == performer.getWurmId() && !source.isTraded();
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
        /*
         * if (target.getTemplateId() != AlchItems.weaponOilDemiseAnimalId)
         *
         * return propagate(action, ActionPropagation.SERVER_PROPAGATION,
         * ActionPropagation.ACTION_PERFORMER_PROPAGATION);
         */
        if (!canUse(performer, source)) {
            performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }

        // EFFECT STUFF GOES HERE
        if (source.getTemplate().getName().contains("extract")) {
            Conversion listEntry = new Conversion();
            listEntry.wurmId = target.getWurmId();
            listEntry.essenceQl = source.getCurrentQualityLevel();
            listEntry.itemQl = source.getCurrentQualityLevel();
            listEntry.conversionPercent = 0;
            listEntry.nameBeforeConversion = target.getName();

            if (target.isItem() && source.getWeightGrams() >= target.getWeightGrams()) {
                if (target.getMaterial() != source.getMaterial()) {
                    Iterator<Conversion> conversionIterator = ConversionHandler.conversions.iterator();
                    Conversion conversion;
                    while (conversionIterator.hasNext()) {
                        conversion = conversionIterator.next();
                        if (conversion.wurmId == target.getWurmId()
                                && conversion.targetMaterialbyte != source.getMaterial()) {
                            performer.getCommunicator().sendAlertServerMessage(
                                    "Your Item is already on its way being transmuted into another Material."
                                            + "Moving on with yet another material would certainly destroy the integrity of your item and shatter it.");
                            return propagate(action, ActionPropagation.FINISH_ACTION,
                                    ActionPropagation.NO_SERVER_PROPAGATION,
                                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
                        }
                    }
                    if (target.getCurrentQualityLevel() <= source.getCurrentQualityLevel()) {
                        performer.getCommunicator().sendAlertServerMessage(
                                "You start imbuing the Item with the essence and it transforms its material to "
                                        + Item.getMaterialString(source.getMaterial()) + "!");
                        target.setMaterial(source.getMaterial());
                    }
                    else {
                        Conversion conversionToCheck = new Conversion();
                        for (Conversion oneConversion : ConversionHandler.conversions)
                            if (oneConversion.wurmId == target.getWurmId()) {
                                conversionToCheck = oneConversion;
                                break;
                            }

                        float multiplier = source.getCurrentQualityLevel() / target.getCurrentQualityLevel();
                        multiplier *= 100;
                        if (multiplier + conversionToCheck.conversionPercent > 100.0F) {
                            performer.getCommunicator().sendAlertServerMessage(
                                    "You start imbuing the Item with the essence and it transforms its material to "
                                            + Item.getMaterialString(source.getMaterial()) + "!");
                            target.setMaterial(source.getMaterial());
                            target.setName(conversionToCheck.nameBeforeConversion);
                            ConversionHandler.conversions.remove(conversionToCheck);
                        }
                        else {
                            target.setName(target.getName() + " transmuting ( "
                                    + Item.getMaterialString(source.getMaterial()) + " " + multiplier + " %");
                            performer.getCommunicator().sendAlertServerMessage(
                                    "You start imbuing the Item with the essence, but due to the poor Quality of your essence you might need to apply some more essence");

                            listEntry.conversionPercent = multiplier;
                            listEntry.targetMaterialbyte = source.getMaterial();
                            ConversionHandler.conversions.add(listEntry);
                        }

                    }
                }
                else
                    performer.getCommunicator().sendAlertServerMessage("Your Item is already of this material!");
                return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                        ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
            }
            else
                performer.getCommunicator()
                        .sendAlertServerMessage("Your dont have enough Transmutation Liquid to transorm your item!");

        }
        return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }
}
