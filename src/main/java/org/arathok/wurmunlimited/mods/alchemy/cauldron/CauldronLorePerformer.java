
package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class CauldronLorePerformer implements ActionPerformer {

    public ActionEntry actionEntry;


    public CauldronLorePerformer() {
        int[] types;

            types = new int[]{6 /* ACTION_TYPE_NOMOVE */, 48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                    35 /* DON'T CARE WHETHER SOURCE OR TARGET */,
                    // 27, // NONSTACK

                    // 28 // NOSTACK IN FIGHT
            };
                    actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Lore", "thinking",
                            types)

                            .range(4).priority(1).build();


        ModActions.registerAction(actionEntry);

    }

    @Override
    public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter) {
        return action(action, performer, target, num, counter);
    } // NEEDED OR THE ITEM WILL ONLY ACTIVATE IF YOU HAVE NO ITEM ACTIVE

    @Override
    public short getActionId() {
        return actionEntry.getNumber();
    }

    public static boolean canUse(Creature performer, Item target) // precondition
    {
        return !target.isTraded();

    }

    @Override
    public boolean action(Action action, Creature performer, Item target, short num, float counter) {

        if (canUse(performer,target))
        {
            CauldronData cauldron= Cauldrons.cauldrons.get(target.getWurmId());
            if (cauldron==null)
            {
                cauldron=new CauldronData();
                Cauldrons.cauldrons.put(target.getWurmId(),cauldron);
            }
            cauldron.lore(performer);
        }

        return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }
}
