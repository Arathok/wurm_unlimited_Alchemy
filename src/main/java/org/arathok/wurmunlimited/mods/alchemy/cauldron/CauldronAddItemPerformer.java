
package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import org.arathok.wurmunlimited.mods.alchemy.AlchItems;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.sounds.SoundPlayer;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class CauldronAddItemPerformer implements ActionPerformer {

    public ActionEntry actionEntry;


    public CauldronAddItemPerformer() {

        int[] types = new int[] { 6 /* ACTION_TYPE_NOMOVE */, 48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                36 /* USE SOURCE AND TARGET */, 28, // nonstack in fight

        };

        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Add item to Cauldron", "adding",
                types).range(4).priority(1000).build();

        ModActions.registerAction(actionEntry);

    }


    @Override
    public short getActionId() {
        return actionEntry.getNumber();
    }

    public static boolean canUse(Creature performer, Item target) // precondition
    {
        return !target.isTraded();

    }

    @Override
    public boolean action(Action action, Creature performer,Item source, Item target, short num, float counter) {
        CauldronData cauldron = Cauldrons.cauldrons.get(target.getWurmId());

        if (counter == 1) {

            if (cauldron == null) {
                cauldron = new CauldronData();
                Cauldrons.cauldrons.put(target.getWurmId(), cauldron);
            }
            action.setTimeLeft(30);
            performer.sendActionControl(action.getActionString(), true, 30);

        } else if (counter > 1 && action.getSecond() == 3) {
            if (cauldron.cauldronState == 0) {
                if (source.getTemplateId() == ItemList.water) {
                    cauldron.cauldronState = 1;
                    Cauldrons.cauldrons.put(target.getWurmId(), cauldron);
                    SoundPlayer.playSound("sound.liquid.fillcontainer.bucket", performer, 1.6F);
                } else if (source.getTemplateId() == AlchItems.purifiedWaterId) {
                    cauldron.cauldronState = 2;
                    Cauldrons.cauldrons.put(target.getWurmId(), cauldron);
                    SoundPlayer.playSound("sound.liquid.fillcontainer.bucket", performer, 1.6F);
                } else
                    performer.getCommunicator().sendNormalServerMessage("The Cauldron is empty. You hesitate to throw in that item. Maybe you should start with some water?");

            }
            if (cauldron.cauldronState == 2 && cauldron.fireBelow) {

                if (!cauldron.insertedItems.containsKey(source.getTemplateId()))
                    cauldron.insertedItems.put(source.getTemplateId(), source.getWeightGrams());
                else {
                    int weight = cauldron.insertedItems.get(source.getTemplateId());
                    cauldron.insertedItems.put(source.getTemplateId(), weight + source.getWeightGrams());
                }
                SoundPlayer.playSound("sound.fishing.bite",performer,1.6F);
            }

            if (cauldron.cauldronState > 3) {
                performer.getCommunicator().sendNormalServerMessage("You refrain from putting another item in the cauldron as the reaction already started.");
            }

            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

        }
        return propagate(action, ActionPropagation.CONTINUE_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }
}
