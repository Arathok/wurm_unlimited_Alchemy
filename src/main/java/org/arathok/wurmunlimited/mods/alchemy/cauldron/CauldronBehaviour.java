package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.arathok.wurmunlimited.mods.alchemy.AlchItems.alchemicalCauldronId;

public class CauldronBehaviour implements BehaviourProvider {

    private final List<ActionEntry> cauldronThings = new ArrayList<>();
    private final CauldronAddItemPerformer cauldronAddItemPerformer;
    private final CauldronLorePerformer cauldronLorePerformer;
    private final StartOverPerformer startOverPerformer;
    private final StirPerformer stirPerformer;

    public CauldronBehaviour() {
        this.cauldronAddItemPerformer = new CauldronAddItemPerformer();
        this.cauldronLorePerformer = new CauldronLorePerformer();
        this.stirPerformer = new StirPerformer();
        this.startOverPerformer = new StartOverPerformer();
        ModActions.registerActionPerformer(cauldronAddItemPerformer);
        ModActions.registerActionPerformer(cauldronLorePerformer);
        ModActions.registerActionPerformer(stirPerformer);
        ModActions.registerActionPerformer(startOverPerformer);
        Alchemy.logger.log(Level.INFO, "Registering Potion Actions...");

    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {
        if (target.getTemplateId() == alchemicalCauldronId) {

            cauldronThings.add(cauldronLorePerformer.actionEntry);
            cauldronThings.add(stirPerformer.actionEntry);
            cauldronThings.add(startOverPerformer.actionEntry);
            cauldronThings.add(0, new ActionEntry((short) -3, "Alchemy", "Alchemy"));
            return cauldronThings;
        } else
            return null;
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (target.getTemplateId() == alchemicalCauldronId) {
            cauldronThings.add(cauldronAddItemPerformer.actionEntry);
            cauldronThings.add(cauldronLorePerformer.actionEntry);
            cauldronThings.add(stirPerformer.actionEntry);
            cauldronThings.add(startOverPerformer.actionEntry);
            cauldronThings.add(0, new ActionEntry((short) -4, "Alchemy", "Alchemy"));
            return cauldronThings;
        } else
            return null;
    }
}
