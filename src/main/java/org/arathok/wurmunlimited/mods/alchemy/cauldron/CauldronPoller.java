package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import java.util.Map;

public class CauldronPoller {

    public static void pollCauldrons()
    {
        for (Map.Entry<Long,CauldronData> oneCauldron: Cauldrons.cauldrons.entrySet())
        {
            long itemId = oneCauldron.getKey();
            CauldronData data= oneCauldron.getValue();

            if (data.cauldronState==1)
            {



            }

        }
    }

}
