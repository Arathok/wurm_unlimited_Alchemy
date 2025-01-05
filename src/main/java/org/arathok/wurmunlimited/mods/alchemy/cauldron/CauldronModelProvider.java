package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import org.arathok.wurmunlimited.mods.alchemy.AlchItems;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import org.gotti.wurmunlimited.modsupport.items.ModelNameProvider;

public class CauldronModelProvider implements ModelNameProvider {
    @Override
    public String getModelName(Item item) {

        StringBuilder sb = null;
        sb = new StringBuilder(item.getTemplate().getModelName());
        if (Cauldrons.cauldrons.containsKey(item.getWurmId())) {

            CauldronData theCauldron = Cauldrons.cauldrons.get(item.getWurmId());
            Item[] itemsInCauldron = (item.getItemsAsArray());

            if (theCauldron.insertedItems.isEmpty() && itemsInCauldron.length == 0)
                sb.append("empty.");
            else if (theCauldron.insertedItems.isEmpty() && itemsInCauldron.length != 0) {
                boolean hasWater = false;
                boolean hasPurifiedWater = false;
                for (Item oneItem : itemsInCauldron) {
                    if (oneItem.getTemplateId() == ItemList.water) {
                        hasWater = true;

                    }
                    if (oneItem.getTemplateId() == AlchItems.purifiedWaterId) {
                        hasPurifiedWater = true;
                        break;
                    }
                }
                if (hasWater)
                    sb.append("water.");
                if (hasPurifiedWater)
                    sb.append("purifedWater.");
            } else if (item.getData1() > 2 && item.getData1() < 5) {
                sb.append("pink.");
            } else if (item.getData1() > 2 && item.getData1() < 5) {
                sb.append("blue.");
            } else if (item.getData1() > 2 && item.getData1() < 5) {
                sb.append("red.");
            } else if (item.getData1() > 2 && item.getData1() < 5) {
                sb.append("violet.");
            } else if (item.getData1() > 2 && item.getData1() < 5) {
                sb.append("ultimate."); // OILS AND ESSENCES TOO!

                return sb.toString();
            } else return item.getTemplate().

                    getModelName();

        }
        return sb.toString();
    }

}
