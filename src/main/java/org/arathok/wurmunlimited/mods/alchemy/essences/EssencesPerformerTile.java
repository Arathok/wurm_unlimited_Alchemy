package org.arathok.wurmunlimited.mods.alchemy.essences;

import org.arathok.wurmunlimited.mods.alchemy.enchantments.Enchantment;
import org.arathok.wurmunlimited.mods.alchemy.Config;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.Players;
import com.wurmonline.server.Server;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Terraforming;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.ItemMaterials;

public class EssencesPerformerTile implements ActionPerformer {

    public ActionEntry actionEntry;

    public EssencesPerformerTile() {

        actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "imbue essence on wall", "imbueing",
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
    public boolean action(Action action, Creature performer, Item source, int tilex, int tiley, boolean onSurface,
            int heightOffset, int tile, short num, float counter) {

        if (!canUse(performer, source)) {
            performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }

        // EFFECT STUFF GOES HERE

        byte type = Tiles.decodeType(tile);

        if ((type == (byte) 203 || (type >= (byte) 205 && type <= (byte) 245))
                && source.getTemplate() == EssencesItems.acidicExtract && source.getWeightGrams() > 10000) {
            // remove walls
            Terraforming.setAsRock(tilex, tiley, false);
            source.setWeight(source.getWeightGrams() - 10000, false);
            performer.getCommunicator().sendSafeServerMessage(
                    "The acid fizzles through the wall and dissolves any material that is not rock.");

        }
        else if (type == (byte) Tiles.TILE_TYPE_CAVE_WALL && source.getWeightGrams() > 100000) {
            // create vein

            if (source.getMaterial() == ItemMaterials.MATERIAL_SANDSTONE)
                type = (byte) Tiles.TILE_TYPE_CAVE_WALL_SANDSTONE;
            if (source.getMaterial() == ItemMaterials.MATERIAL_MARBLE)
                type = (byte) Tiles.TILE_TYPE_CAVE_WALL_MARBLE;
            if (source.getMaterial() == ItemMaterials.MATERIAL_SLATE)
                type = (byte) Tiles.TILE_TYPE_CAVE_WALL_SLATE;
            if (source.getMaterial() == ItemMaterials.MATERIAL_IRON)
                type = (byte) Tiles.TILE_TYPE_CAVE_WALL_ORE_IRON;

            float difficulty = 0.0f; // TODO unlock diff = difficulty
            float bonus = (float) ((performer.getSkills().getSkillOrLearn(SkillList.ALCHEMY_NATURAL).getKnowledge())
                    / 10D);
            double amountOfOre = performer.getSkills().getSkillOrLearn(SkillList.ALCHEMY_NATURAL).skillCheck(difficulty,
                    bonus, false, 1.5F);
            if (amountOfOre > 0) {
                Server.setCaveResource(tilex, tiley, Math.max(1, (int) amountOfOre * 50));
                Server.caveMesh.setTile(tilex, tiley,
                        Tiles.encode(Tiles.decodeHeight(tile), type, Tiles.decodeData(tile)));

                Players.getInstance().sendChangedTile(tilex, tiley, false, true);
                source.setWeight(source.getWeightGrams() - 100000, false);
                performer.getCommunicator().sendSafeServerMessage(
                        "The transmutation Liquid seeps through the rock and transforms it into your desired material!");
            }
            else {
                performer.getCommunicator()
                        .sendSafeServerMessage("Sadly your liquid only fizzles through the stone...");
            }

        }
        else
            performer.getCommunicator()
                    .sendAlertServerMessage("you don't have enough Transmutation Liquid to transform the wall!");
        return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }
}
