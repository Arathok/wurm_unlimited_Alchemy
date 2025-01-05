package org.arathok.wurmunlimited.mods.alchemy.essences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import com.wurmonline.mesh.Tiles;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;

public class EssencesBehaviourTile implements BehaviourProvider {

    private final List<ActionEntry> imbue;
    private final EssencesPerformerTile essencesPerformerTile;

    public EssencesBehaviourTile() {
        this.essencesPerformerTile = new EssencesPerformerTile();
        this.imbue = Collections.singletonList(essencesPerformerTile.actionEntry);
        ModActions.registerActionPerformer(essencesPerformerTile);

    }

    // , , , , ,
    // , , , , ;

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, int tilex, int tiley, boolean onSurface,
            int tile, int dir) {
        byte type = Tiles.decodeType(tile);
        if (type == (byte) Tiles.TILE_TYPE_CAVE_WALL
                && (source.getTemplate() == EssencesItems.ironExtract
                        || source.getTemplate() == EssencesItems.slateExtract
                        || source.getTemplate() == EssencesItems.sandExtract
                        || source.getTemplate() == EssencesItems.marbleExtract)
                && EssencesPerformerTile.canUse(performer, source)) {

            return new ArrayList<>(imbue);
        } else if ((type >= (byte) 205 && type <= (byte) 245 || type == (byte) 203)
                && source.getTemplate() == EssencesItems.acidicExtract) {
            return new ArrayList<>(imbue);
        } else
            return null;

    }
}
