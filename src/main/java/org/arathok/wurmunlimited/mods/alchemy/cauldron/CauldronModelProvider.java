package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import com.wurmonline.server.items.Item;
import org.gotti.wurmunlimited.modsupport.items.ModelNameProvider;

public class CauldronModelProvider implements ModelNameProvider {
    @Override
    public String getModelName(Item item) {

        StringBuilder sb = null;
        sb = new StringBuilder(item.getTemplate().getModelName());
        if (Cauldrons.cauldrons.containsKey(item.getWurmId())) {

            CauldronData theCauldron = Cauldrons.cauldrons.get(item.getWurmId());
            Item[] itemsInCauldron = (item.getItemsAsArray());

            if (theCauldron.insertedItems.isEmpty() && itemsInCauldron.length == 0 && theCauldron.cauldronState == 0)
                sb.append("empty.");
            else if (theCauldron.cauldronState > 0) {
                boolean hasWater = false;
                boolean hasPurifiedWater = false;

                if (theCauldron.cauldronState == 1)
                    sb.append("water.");

                if (theCauldron.cauldronState == 2)
                    sb.append("purifedWater.");
                if (theCauldron.cauldronState == 4)
                    sb.append("white.");
                if (theCauldron.cauldronState == 5)
                    sb.append("pink.");
                if (theCauldron.cauldronState == 6)
                    sb.append("blue.");
                if (theCauldron.cauldronState == 7)
                    sb.append("violet.");
                if (theCauldron.cauldronState == 8)
                    sb.append("red.");
                if (theCauldron.cauldronState == 9)
                    sb.append("yellow.");


            }

            return sb.toString();
        } else return item.getTemplate().

                getModelName();

    }

}


