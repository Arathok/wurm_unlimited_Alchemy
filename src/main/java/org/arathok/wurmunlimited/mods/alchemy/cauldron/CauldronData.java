package org.arathok.wurmunlimited.mods.alchemy.cauldron;

import com.wurmonline.server.creatures.Creature;

import java.util.*;

//TODO: Make Lore return possible Recipes, stir goes into add alchemical compoound phase, add Item adds to the Item List, which is checked against a lookup Table of possible Recipes, use sets with arraysAsList to check contents

public class CauldronData {
    boolean purified = false;
    boolean fireBelow = false;
    boolean compoundReadyToAdd = false;
    // max 20L of potion liquid -> Max 10L of Water
    //100g potionPrecursorLiquid = 1 of each item
    //0 = nothing in it
    //1= water in it
    //2= water is purified can add ingredients
    //3= add alchemical compound
    //4= potion precurser liquid there, turning slowly into potion liquid
    byte cauldronState = 0;

    float finalql;


    HashMap<Integer, Integer> insertedItems = new HashMap<Integer, Integer>();

    public void lore(Creature performer) {
        Set<Integer> insertedItemsSet = new HashSet<>((Collection) this.insertedItems);

        if (cauldronState == 0) {
            performer.getCommunicator().sendNormalServerMessage("You look at the empty cauldron... what could you make today? You could start by adding water.");
        }


        if (cauldronState == 1) {
            performer.getCommunicator().sendNormalServerMessage("The Cauldron is filled with water. Make a fire below the Cauldron to start purifying the water.");
        }

        if (cauldronState == 2) {
            if (insertedItems.isEmpty())
                performer.getCommunicator().sendNormalServerMessage("The Water has been purified it seems. You can now start adding items, and check again, or throw in animal hearts to make alchemical compound which is required for any potion. You've read that somewhere.");


            if (!insertedItems.isEmpty()) {
                List<String> yourRecipes = new ArrayList<>();
                for (Map.Entry<String, Integer[]> oneRecipe : Cauldrons.possibleRecipes.entrySet()) {
                    String recipeName = oneRecipe.getKey();
                    List<Integer> requiredIngredients = Arrays.asList(oneRecipe.getValue());


                    if (insertedItems.size() == 1) {

                        {
                            for (int ingredient : insertedItems.keySet()) {
                                if (requiredIngredients.contains(ingredient))
                                    yourRecipes.add(recipeName);

                            }
                        }
                    }

                    if (insertedItems.size() == 1) {

                        for (int ingredient : insertedItems.keySet()) {
                            if (requiredIngredients.contains(ingredient))
                                yourRecipes.add(recipeName);
                            else yourRecipes.remove(recipeName);

                        }
                    }

                }
                if (yourRecipes.isEmpty())
                    performer.getCommunicator().sendNormalServerMessage("You think you would make something really awful, should you start over? Or do you want to go through with your experiments?");
                else {
                    performer.getCommunicator().sendSafeServerMessage("With the items you have added you think you can make the following recipies:");
                    for (String oneRecipe : yourRecipes)
                        performer.getCommunicator().sendSafeServerMessage(oneRecipe);
                }

            }

        }

    }
}
