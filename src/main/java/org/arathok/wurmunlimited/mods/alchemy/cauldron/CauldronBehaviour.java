package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class CauldronBehaviour implements BehaviourProvider {

    private final List<ActionEntry> consume;
    private final CauldronAddItemPerformer cauldronAddItemPerformer;

    public CauldronBehaviour() {
        this.cauldronAddItemPerformer = new CauldronAddItemPerformer();
        this.consume = Collections.singletonList(cauldronAddItemPerformer.actionEntry);
        ModActions.registerActionPerformer(cauldronAddItemPerformer);
        Alchemy.logger.log(Level.INFO, "Registering Potion Actions...");

    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {



        return null;
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        return getBehavioursFor(performer, target);
    }
}
