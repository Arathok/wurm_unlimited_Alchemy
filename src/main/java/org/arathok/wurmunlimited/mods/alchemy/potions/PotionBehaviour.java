package org.arathok.wurmunlimited.mods.alchemy.potions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;

public class PotionBehaviour implements BehaviourProvider {

    private final List<ActionEntry> consume;
    private final PotionPerformer potionPerformer;

    public PotionBehaviour() {
        this.potionPerformer = new PotionPerformer();
        this.consume = Collections.singletonList(potionPerformer.actionEntry);
        ModActions.registerActionPerformer(potionPerformer);
        Alchemy.logger.log(Level.INFO, "Registering Potion Actions...");

    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item target) {

        if (target.getTemplateId() == PotionItems.potionIdHeal) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdGoat) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdExcell) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdFranticCharge) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdMorningFog) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdOakshell) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdSixthSense) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdTruehit) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdStrength) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdWillowspine) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdVynora) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionIdRefresh) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionManaId) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else if (target.getTemplateId() == PotionItems.potionKarmaId) {
            if (PotionPerformer.canUse(performer, target))
                return new ArrayList<>(consume);

        }
        else
            return null;

        return null;
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        return getBehavioursFor(performer, target);
    }
}
