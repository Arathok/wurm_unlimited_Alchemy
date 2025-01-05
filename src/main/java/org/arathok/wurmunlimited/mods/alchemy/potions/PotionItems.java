
package org.arathok.wurmunlimited.mods.alchemy.potions;

import com.wurmonline.server.items.*;
import org.arathok.wurmunlimited.mods.alchemy.AlchItems;
import org.arathok.wurmunlimited.mods.alchemy.Config;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.tyoda.wurm.Iconzz.Iconzz;

import java.io.IOException;

public class PotionItems {

    public static int mixtureHealId, mixtureGoatId, mixtureExcellId, mixtureOakshellId, mixtureMorningFogId,
            mixtureFranticChargeId, mixtureStrengthId, mixtureSixthSenseId, mixtureTruehitId, mixtureWillowspineId,
            mixtureRefreshId, mixtureVynoraId,

    precursorHealId, precursorGoatId, precursorExcellId, precursorOakshellId, precursorMorningFogId,
            precursorFranticChargeId, precursorStrengthId, precursorSixthSenseId, precursorTruehitId,
            precursorWillowspineId, precursorRefreshId, precursorVynoraId,

    potionLiquidHealId, potionLiquidGoatId, potionLiquidExcellId, potionLiquidOakshellId, potionLiquidKarmaId,
            potionLiquidManaId, potionLiquidMorningFogId, potionLiquidFranticChargeId, potionLiquidStrengthId,
            potionLiquidSixthSenseId, potionLiquidTruehitId, potionLiquidWillowspineId, potionLiquidRefreshId,
            potionLiquidVynoraId,

    potionIdHeal, potionIdGoat, potionIdExcell, potionIdOakshell, potionIdMorningFog, potionIdFranticCharge,
            potionIdStrength, potionIdSixthSense, potionIdTruehit, potionIdWillowspine, potionIdRefresh, potionIdVynora,
            potionKarmaId, potionManaId;

    public static ItemTemplate mixtureHeal, mixtureGoat, mixtureExcell, mixtureOakshell, mixtureMorningFog,
            mixtureFranticCharge, mixtureStrength, mixtureSixthSense, mixtureTruehit, mixtureWillowspine,
            mixtureRefresh, mixtureVynora,

    precursorHeal, precursorGoat, precursorExcell, precursorOakshell, precursorMorningFog,
            precursorFranticCharge, precursorStrength, precursorSixthSense, precursorTruehit, precursorWillowspine,
            precursorRefresh, precursorVynora,

    potionLiquidHeal, potionLiquidGoat, potionLiquidExcell, potionLiquidOakshell, potionLiquidMorningFog,
            potionLiquidFranticCharge, potionLiquidStrength, potionLiquidSixthSense, potionLiquidTruehit,
            potionLiquidWillowspine, potionLiquidRefresh, potionLiquidVynora, potionLiquidKarma, potionLiquidMana,

    potionHeal, potionGoat, potionExcell, potionOakshell, potionMorningFog, potionFranticCharge, potionStrength,
            potionSixthSense, potionTruehit, potionWillowspine, potionRefresh, potionVynora, potionKarma, potionMana;

    public static short pasteIcon, alchemicalCompoundIcon, mixtureHealingIcon, mixtureCombatIcon, mixtureProtectionIcon,
            mixtureUltimateIcon, mixtureNormalIcon, precursorHealIcon, precursorDefenseIcon, precursorCombatIcon,
            precursorNormalIcon, precursorUltimateIcon, potionLiquidHealIcon, potionLiquidDefenseIcon,
            potionLiquidCombatIcon, potionLiquidNormalIcon, potionLiquidUltimateIcon, potionHealIcon, potionDefenseIcon,
            potionCombatIcon, potionNormalIcon, potionUltimateIcon, weaponOilIcon, sludgeIcon, weaponOilLiquidIcon;

    private static void makeIcons() {
        pasteIcon = Iconzz.getInstance().addIcon("arathok.alchemy.pasteIcon", "org/arathok/wurmunlimited/mods/alchemy/icons/paste.png");
        alchemicalCompoundIcon = Iconzz.getInstance().addIcon("arathok.alchemy.alchemicalCompoundIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/alchemicalCompound.png");

        mixtureHealingIcon = Iconzz.getInstance().addIcon("arathok.alchemy.mixtureHealIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/mixtureHeal.png");
        mixtureCombatIcon = Iconzz.getInstance().addIcon("arathok.alchemy.mixtureCombatIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/mixtureCombat.png");
        mixtureProtectionIcon = Iconzz.getInstance().addIcon("arathok.alchemy.mixtureProtectionIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/mixtureProtection.png");
        mixtureUltimateIcon = Iconzz.getInstance().addIcon("arathok.alchemy.mixtureUltimateIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/mixtureUltimate.png");
        mixtureNormalIcon = Iconzz.getInstance().addIcon("arathok.alchemy.mixtureNormalIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/mixtureNormalBuff.png");

        precursorHealIcon = Iconzz.getInstance().addIcon("arathok.alchemy.precursorHealIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/precursorHeal.png");
        precursorCombatIcon = Iconzz.getInstance().addIcon("arathok.alchemy.precursorCombatIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/precursorCombat.png");
        precursorDefenseIcon = Iconzz.getInstance().addIcon("arathok.alchemy.precursorDefenseIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/precursorProtection.png");
        precursorUltimateIcon = Iconzz.getInstance().addIcon("arathok.alchemy.precursorUltimateIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/precurorUltimate.png");
        precursorNormalIcon = Iconzz.getInstance().addIcon("arathok.alchemy.precursorNormalIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/precursorNormal.png");

        potionLiquidHealIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionLiquidHealIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionLiquidHeal.png");
        potionLiquidCombatIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionLiquidCombatIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionLiquidCombat.png");
        potionLiquidDefenseIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionLiquidDefenseIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionLiquidProtection.png");
        potionLiquidUltimateIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionLiquidUltimateIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionLiquidUltimate.png");
        potionLiquidNormalIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionLiquidNormalIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionLiquidNormal.png");

        potionHealIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionHealIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionHeal.png");
        potionCombatIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionCombatIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionCombat.png");
        potionDefenseIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionDefenseIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionProtection.png");
        potionUltimateIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionUltimateIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionUltimate.png");
        potionNormalIcon = Iconzz.getInstance().addIcon("arathok.alchemy.potionNormalIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/potionNormal.png");
        weaponOilIcon = Iconzz.getInstance().addIcon("arathok.alchemy.weaponOilIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/weaponOil.png");
        sludgeIcon = Iconzz.getInstance().addIcon("arathok.alchemy.sludgeIcon", "org/arathok/wurmunlimited/mods/alchemy/icons/sludge.png");
        weaponOilLiquidIcon = Iconzz.getInstance().addIcon("arathok.alchemy.weaponOilLiquidIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/weaponOilLiquid.png");

    }

    private static void registermixtureDodging() throws IOException {
        mixtureWillowspine = new ItemTemplateBuilder("arathok.alchemy.mixtureDodge").name("mixture of phasing",
                        "mixtures of phasing",
                        "A fresh mixture of different alchemical and natural substances. One day it might be making a fine potion."
                                + "Smelling this mixture makes you feel safe. You feel like the universe is putting its protective hands around you, turn you into a shadow."
                                + " You feel like nothing can really hurt your new form "
                                + " you should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.protection.").imageNumber(mixtureProtectionIcon).itemTypes(new short[]{

                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        mixtureWillowspineId = mixtureWillowspine.getTemplateId();

        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.rosemary, ItemList.ivySeedling, mixtureWillowspineId,
                        true, true, 0f, true, false, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.pumpkin, 1, true))
                .addRequirement(new CreationRequirement(2, ItemList.eye, 1, true));
    }

    private static void registermixtureExcell() throws IOException {
        mixtureExcell = new ItemTemplateBuilder("arathok.alchemy.mixtureExcell").name("mixture of surpass",
                        "mixtures of surpass",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " Smelling it makes you feel energetic. Rubbing it on your fingers makes your skin seem to be softer and not as easy to cut."
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")
                .modelName("model.mixture.protection.").imageNumber(mixtureProtectionIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        mixtureExcellId = mixtureExcell.getTemplateId();

        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.ginger, ItemList.sassafras, mixtureExcellId, true, true,
                        0f, true, false, 0, 25, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.mushroomRed, 1, true));
    }

    private static void registermixtureFrenzy() throws IOException {
        mixtureFranticCharge = new ItemTemplateBuilder("arathok.alchemy.mixtureFrenzy").name("mixture of frenzy",
                        "mixtures of frenzy",
                        "A mixture of different alchemical and natural substances. One day it might be making a fine potion."
                                + " Smelling this mixture makes you aggressive, it seems to call to you to let out your inner beast."
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.combat.").imageNumber(mixtureCombatIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 35) // no
                // hard
                // lock
                .build();

        mixtureFranticChargeId = mixtureFranticCharge.getTemplateId();

        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.mushroomRed, ItemList.nutmeg, mixtureFranticChargeId,
                        true, true, 0f, true, false, 0, 35, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.tomato, 1, true))
                .addRequirement(new CreationRequirement(2, ItemList.tooth, 1, true));
    }

    private static void registerMixtureGoat() throws IOException {
        mixtureGoat = new ItemTemplateBuilder("arathok.alchemy.mixtureGoat")
                .name("mixture of Goat", "mixtures of Goat",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " It smells like a wet Goat... weird.")

                .modelName("model.mixture.normal.").imageNumber(mixtureNormalIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        mixtureGoatId = mixtureGoat.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, ItemList.branch, ItemList.oat, mixtureGoatId, true,
                true, 0f, false, false, 0, 20, CreationCategories.ALCHEMY);
    }

    private static void registermixtureOakshell() throws IOException {
        mixtureOakshell = new ItemTemplateBuilder("arathok.alchemy.mixtureOakshell").name("mixture of Woodskin",
                        "mixtures of Woodskin",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " It seems to make your skin somewhat woodifies and becomes harder when you touch it."
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.protection.").imageNumber(mixtureProtectionIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                // hard
                // lock
                .build();

        mixtureOakshellId = mixtureOakshell.getTemplateId();

        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.acorn, ItemList.rock, mixtureOakshellId, true, true, 0f,
                        true, false, 0, 40, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.mushroomBlue, 1, true))
                .addRequirement(new CreationRequirement(2, ItemList.sage, 1, true));
    }

    private static void registerMixtureHeal() throws IOException {
        mixtureHeal = new ItemTemplateBuilder("arathok.alchemy.mixtureHeal").name("mixture of health",
                        "mixtures of health",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " You put some of the mixture to the tip of your tongue. A small scratch you got starts to close. This seems to have some healing Properties."
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.heal.").imageNumber(mixtureHealingIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        mixtureHealId = mixtureHeal.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, ItemList.mushroomBrown, ItemList.wheat, mixtureHealId,
                true, true, 0f, false, false, 0, 15, CreationCategories.ALCHEMY);
    }

    private static void registermixtureMorningFog() throws IOException {
        mixtureMorningFog = new ItemTemplateBuilder("arathok.alchemy.mixtureFog").name("mixture of fog skin",
                        "mixtures of fog skin",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " Sniffing it your body seems to bend around sharp objects as if it wants to protect itself."
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.protection.").imageNumber(mixtureProtectionIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        mixtureMorningFogId = mixtureMorningFog.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, ItemList.lingonberry, ItemList.wemp,
                mixtureMorningFogId, true, true, 0f, false, false, 0, 10, CreationCategories.ALCHEMY);
    }

    private static void registermixtureRefresh() throws IOException {
        mixtureRefresh = new ItemTemplateBuilder("arathok.alchemy.mixtureRefresh").name("mixture of refreshing",
                        "mixtures of Refreshing",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " You sense it giving off a whiff of cool air. It seems to be able to strip some tiredness off you."
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.heal.").imageNumber(mixtureNormalIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 20) // no
                // hard
                // lock
                .build();

        mixtureRefreshId = mixtureRefresh.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, ItemList.carrot, ItemList.potato, mixtureRefreshId,
                true, true, 0f, false, false, 0, 10, CreationCategories.ALCHEMY);
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, ItemList.mint, ItemList.fennel, mixtureRefreshId, true,
                true, 0f, false, false, 0, 10, CreationCategories.ALCHEMY);
    }

    private static void registermixtureSixthSense() throws IOException {
        mixtureSixthSense = new ItemTemplateBuilder("arathok.alchemy.mixtureSixthSense").name("mixture of senses",
                        "mixtures of senses",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " The smell brings a certain picture before the inner eye. Like a voice telling you, to be careful with your surroundings"
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.protection.").imageNumber(mixtureProtectionIcon).itemTypes(new short[]{

                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        mixtureSixthSenseId = mixtureSixthSense.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, ItemList.lovage, ItemList.nettles, mixtureSixthSenseId,
                true, true, 0f, false, false, 0, 20, CreationCategories.ALCHEMY);
    }

    private static void registermixtureStrength() throws IOException {
        mixtureStrength = new ItemTemplateBuilder("arathok.alchemy.mixture").name("mixture of Strength",
                        "mixtures of Strength",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " The smell makes you feel like you could lift a mountain."
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.combat.").imageNumber(mixtureCombatIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,

                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 40) // no
                // hard
                // lock
                .build();

        mixtureStrengthId = mixtureStrength.getTemplateId();

        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.mushroomBlack, ItemList.paprika, mixtureStrengthId,
                        true, true, 0f, true, false, 0, 40, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.woad, 1, true));
    }

    private static void registermixtureTruehit() throws IOException {
        mixtureTruehit = new ItemTemplateBuilder("arathok.alchemy.mixtureTruehit").name("mixture of truehit",
                        "mixtures of truehit",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " The belladonna is widening your eyes, uncovering weak spots on enemies"
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.combat.").imageNumber(mixtureCombatIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        mixtureTruehitId = mixtureTruehit.getTemplateId();

       /*CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.belladonna, ItemList.garlic, mixtureTruehitId, true,
                        true, 0f, true, false, 0, 25, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.mushroomGreen, 1, true));

        */
    }

    private static void registermixtureVyn() throws IOException {
        mixtureVynora = new ItemTemplateBuilder("arathok.alchemy.mixtureVyn").name("mixture of Knowledge",
                        "mixtures of Knowledge",
                        "A mixture of different alchemical substances. One day it might be making a fine potion."
                                + " This mixture has captured the essence of knowledge itself. You can feel Vynora smile down on you. You are grasping for the summit of Alchemy"
                                + " You should work with it soon, since the magic energy in the herbs is dissipating fast.")

                .modelName("model.mixture.ultimate.").imageNumber(mixtureUltimateIcon).itemTypes(new short[]{
                        // ItemTypes.ITEM_TYPE_BULK,
                        ItemTypes.ITEM_TYPE_HERB, ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(14400L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 90) // no
                // hard
                // lock
                .build();

        mixtureVynoraId = mixtureVynora.getTemplateId();

        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.mushroomYellow, ItemList.turmeric, mixtureVynoraId,
                        true, true, 0f, true, false, 0, 60, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.strawberries, 1, true))
                .addRequirement(new CreationRequirement(2, ItemList.thyme, 1, true))
                .addRequirement(new CreationRequirement(2, ItemList.cumin, 1, true))
                .addRequirement(new CreationRequirement(2, ItemList.coinSilver, 1, true));

    }

    private static void registerPrecursorDodge() throws IOException {
        precursorWillowspine = new ItemTemplateBuilder("arathok.alchemy.precursorDodge")
                .name("potion precursor of phasing", "precursors of phasing",
                        "A potion precursor. It got the essence of agility stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.dodge.").imageNumber(precursorDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorWillowspineId = precursorWillowspine.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureWillowspineId, precursorWillowspineId, true, true, 0f, false, false,
                CreationCategories.ALCHEMY);
    }

    private static void registerPrecursorExcell() throws IOException {
        precursorExcell = new ItemTemplateBuilder("arathok.alchemy.precursorExcell").name("potion precursor surpassing",
                        "precursors of surpassing",
                        "A potion precursor. It got the essence of surpassing your foes through enduring pain stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.excell.").imageNumber(precursorDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorExcellId = precursorExcell.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureExcellId, precursorExcellId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorGoat() throws IOException {
        precursorGoat = new ItemTemplateBuilder("arathok.alchemy.precursorGoat")
                .name("potion precursor of goat", "precursors of goat",
                        "A potion precursor. It got the essence of a goat stored within. Weird."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.goat.").imageNumber(precursorNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorGoatId = precursorGoat.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureGoatId, precursorGoatId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorHeal() throws IOException {
        precursorHeal = new ItemTemplateBuilder("arathok.alchemy.precursorHeal")
                .name("potion precursor healing", "precursors of healing",
                        "A potion precursor. It got the essence of Healing stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.heal").imageNumber(precursorHealIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorHealId = precursorHeal.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureHealId, precursorHealId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorFranticCharge() throws IOException {
        precursorFranticCharge = new ItemTemplateBuilder("arathok.alchemy.precursorFranticCharge")
                .name("potion precursor frenzy", "precursors of frenzy",
                        "A potion precursor. It got the essence of frenzy stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.FranticChange").imageNumber(precursorCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorFranticChargeId = precursorFranticCharge.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureFranticChargeId, precursorFranticChargeId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorMorningFog() throws IOException {
        precursorMorningFog = new ItemTemplateBuilder("arathok.alchemy.precursorMorningFog")
                .name("potion precursor of fog skin", "precursors of fog skin",
                        "A potion precursor. It got the essence of mist stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.fog.").imageNumber(precursorNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorMorningFogId = precursorMorningFog.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureMorningFogId, precursorMorningFogId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorOakshell() throws IOException {
        precursorOakshell = new ItemTemplateBuilder("arathok.alchemy.precursorOakshell")
                .name("potion precursor of woodskin", "precursors of woodskin",
                        "A potion precursor. It got the essence of hard tree bark stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.oakshell.").imageNumber(precursorDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorOakshellId = precursorOakshell.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureOakshellId, precursorOakshellId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorRefresh() throws IOException {
        precursorRefresh = new ItemTemplateBuilder("arathok.alchemy.precursorRefresh")
                .name("potion precursor of refresh", "precursors of refresh",
                        "A potion precursor. It got the essence of stamina stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.refresh.").imageNumber(precursorNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorRefreshId = precursorRefresh.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureRefreshId, precursorRefreshId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorSixthSense() throws IOException {
        precursorSixthSense = new ItemTemplateBuilder("arathok.alchemy.precursorSixthSense")
                .name("potion precursor of senses", "precursors of senses",
                        "A potion precursor. It got the essence of heightening your senses stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.senses.").imageNumber(precursorNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorSixthSenseId = precursorSixthSense.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureSixthSenseId, precursorSixthSenseId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorStrength() throws IOException {
        precursorStrength = new ItemTemplateBuilder("arathok.alchemy.precursorStrength")
                .name("potion precursor of demon blood", "precursors of demon blood",
                        "A potion precursor. It got the essence of demonic blood stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.strength.").imageNumber(precursorCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorStrengthId = precursorStrength.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureStrengthId, precursorStrengthId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorTruehit() throws IOException {
        precursorTruehit = new ItemTemplateBuilder("arathok.alchemy.precursorTruehit")
                .name("potion precursor of truehit", "precursors of truehit",
                        "A potion precursor. It got the essence of stamina stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.truehit.").imageNumber(precursorCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorTruehitId = precursorTruehit.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureTruehitId, precursorTruehitId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPrecursorVynora() throws IOException {
        precursorVynora = new ItemTemplateBuilder("arathok.alchemy.precursorVynora")
                .name("potion precursor of vynora", "precursors of vynora",
                        "A potion precursor. It got the essence of knowledge stored within."
                                + " You think its magical properties could be activated by heating it.")

                .modelName("model.precursor.vynora.").imageNumber(precursorUltimateIcon).itemTypes(new short[]{
                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        precursorVynoraId = precursorVynora.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.alchemicalCompoundId,
                PotionItems.mixtureVynoraId, precursorVynoraId, true, true, 0f, false, false,
                CreationCategories.DECORATION);
    }

    private static void registerPotionLiquidDodge() throws IOException {
        potionLiquidWillowspine = new ItemTemplateBuilder("arathok.alchemy.potionLiquidDodge").name(
                        " potion liquid of phasing", "potion liquids of phasing",
                        "A potion liquid." + " This is almost a finished dodging potion. "
                                + " It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.dodge.").imageNumber(potionLiquidDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidWillowspineId = potionLiquidWillowspine.getTemplateId();
    }

    private static void registerPotionLiquidExcell() throws IOException {
        potionLiquidExcell = new ItemTemplateBuilder("arathok.alchemy.potionLiquidExcell").name(
                        " potion liquid surpassing", "potion liquids of surpassing",
                        "A potion liquid. This is almost a finished eelskin potion."
                                + " It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")
                .modelName("model.potionLiquid.excell.").imageNumber(potionLiquidDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(9072000L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidExcellId = potionLiquidExcell.getTemplateId();
    }

    private static void registerPotionLiquidGoat() throws IOException {
        potionLiquidGoat = new ItemTemplateBuilder("arathok.alchemy.potionLiquidGoat").name(" potion liquid of goat",
                        "potion liquids of goat",
                        "A potion liquid. This is almost a finished Goatshape potion."
                                + " It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.goat.").imageNumber(potionLiquidNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidGoatId = potionLiquidGoat.getTemplateId();
    }

    private static void registerPotionLiquidHeal() throws IOException {
        potionLiquidHeal = new ItemTemplateBuilder("arathok.alchemy.potionLiquidHeal").name(" potion liquid healing",
                        "potion liquids of healing",
                        "A potion liquid. This is almost a finished health Potion. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.heal").imageNumber(potionLiquidHealIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidHealId = potionLiquidHeal.getTemplateId();
    }

    private static void registerPotionLiquidFranticCharge() throws IOException {
        potionLiquidFranticCharge = new ItemTemplateBuilder("arathok.alchemy.potionLiquidFranticCharge").name(
                        " potion liquid frenzy", "potion liquids of frenzy",
                        "A potionLiquid. This is almost a finished frenzy potion."
                                + " It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")
                .modelName("model.potionLiquid.FranticChange").imageNumber(potionLiquidCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidFranticChargeId = potionLiquidFranticCharge.getTemplateId();
    }

    private static void registerPotionLiquidKarma() throws IOException {
        potionLiquidKarma = new ItemTemplateBuilder("arathok.alchemy.potionLiquidKarma").name(" potion liquid karma",
                        "potion liquids of karma",
                        "A potion liquid. This is almost a finished karma Potion. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.mana").imageNumber(potionLiquidNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidKarmaId = potionLiquidKarma.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, PotionItems.potionLiquidHealId, ItemList.sourceSalt,
                potionLiquidKarmaId, true, true, 0f, false, false, 0, 50.0, CreationCategories.DECORATION);
    }

    private static void registerPotionLiquidMana() throws IOException {
        potionLiquidMana = new ItemTemplateBuilder("arathok.alchemy.potionLiquidMana").name(" potion liquid mana",
                        "potion liquids of mana",
                        "A potion liquid. This is almost a finished mana Potion. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.mana").imageNumber(potionLiquidDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidManaId = potionLiquidMana.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, PotionItems.potionLiquidHealId, AlchItems.gemPowderId,
                potionLiquidManaId, true, true, 0f, false, false, 0, 40.0, CreationCategories.DECORATION);
    }

    private static void registerPotionLiquidMorningFog() throws IOException {
        potionLiquidMorningFog = new ItemTemplateBuilder("arathok.alchemy.potionLiquidMorningFog").name(
                        " potion liquid of fog skin", "potion liquids of fog skin",
                        "A potion liquid. This is almost a finished fog skin potion. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")
                .modelName("model.potionLiquid.fog.").imageNumber(potionLiquidNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidMorningFogId = potionLiquidMorningFog.getTemplateId();
    }

    private static void registerPotionLiquidOakshell() throws IOException {
        potionLiquidOakshell = new ItemTemplateBuilder("arathok.alchemy.potionLiquidOakshell").name(
                        " potion liquid of wood skin", "potionLiquids of wood skin",
                        "A potion Liquid. This is almost a finished wood skin potion. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.oakshell.").imageNumber(potionLiquidDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidOakshellId = potionLiquidOakshell.getTemplateId();
    }

    private static void registerPotionLiquidRefresh() throws IOException {
        potionLiquidRefresh = new ItemTemplateBuilder("arathok.alchemy.potionLiquidRefresh").name(
                        " potion liquid of refresh", "potion liquids of refresh",
                        "A potion liquid. This is almost a finished potion of stamina. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.refresh.").imageNumber(potionLiquidNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidRefreshId = potionLiquidRefresh.getTemplateId();
    }

    private static void registerPotionLiquidSixthSense() throws IOException {
        potionLiquidSixthSense = new ItemTemplateBuilder("arathok.alchemy.potionLiquidSixthSense").name(
                        " potion liquid of senses", "potion liquids of senses",
                        "A potion liquid. This is almost a finished potion of senses. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.senses.").imageNumber(potionLiquidNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,

                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidSixthSenseId = potionLiquidSixthSense.getTemplateId();

    }

    private static void registerPotionLiquidStrength() throws IOException {
        potionLiquidStrength = new ItemTemplateBuilder("arathok.alchemy.potionLiquidStrength").name(
                        " potion liquid of demon blood", "potion liquids of demon blood",
                        "A potion liquid. This is almost a finished demon blood potion."
                                + " It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.strength.").imageNumber(potionLiquidCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidStrengthId = potionLiquidStrength.getTemplateId();
    }

    private static void registerPotionLiquidTruehit() throws IOException {
        potionLiquidTruehit = new ItemTemplateBuilder("arathok.alchemy.potionLiquidTruehit").name(
                        " potion liquid of truehit", "potion liquids of truehit",
                        "A potion liquid. This is almost a finished truehit potion. "
                                + "It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.truehit.").imageNumber(potionLiquidCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidTruehitId = potionLiquidTruehit.getTemplateId();
    }

    private static void registerPotionLiquidVynora() throws IOException {
        potionLiquidVynora = new ItemTemplateBuilder("arathok.alchemy.potionLiquidVynora").name(
                        " potion Liquid of vynora", "potion Liquids of vynora",
                        "A potionLiquid. This is almost a finished knowledge potion."
                                + " It just needs to be bottled in a crystal phial because only the crystal structure of the glass can activate and properly catalyse the magical properties.")

                .modelName("model.potionLiquid.vynora.").imageNumber(potionLiquidUltimateIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,})
                .decayTime(691200L).dimensions(3, 3, 3).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionLiquidVynoraId = potionLiquidVynora.getTemplateId();
    }

    private static void registerPotionDodge() throws IOException {
        potionWillowspine = new ItemTemplateBuilder("arathok.alchemy.potionDodge")
                .name(" potion of phasing", "potions of phasing",
                        "An alchemistic potion, drinking it will bestow the power of phasing to you.")

                .modelName("model.potion.dodge.").imageNumber(potionDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdWillowspine = potionWillowspine.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId,
                PotionItems.potionLiquidWillowspineId, potionIdWillowspine, true, true, 0f, false, false,
                CreationCategories.ALCHEMY);
    }

    private static void registerPotionExcell() throws IOException {
        potionExcell = new ItemTemplateBuilder("arathok.alchemy.potionExcell").name(" potion of eelskin",
                        "potions of eelskin",
                        "An alchemistic potion, drinking it will bestow the power of eelskin to you. Your enemies wont hit as good!")

                .modelName("model.potion.excell.").imageNumber(potionDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdExcell = potionExcell.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidExcellId,
                potionIdExcell, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionGoat() throws IOException {
        potionGoat = new ItemTemplateBuilder("arathok.alchemy.potionGoat").name(" potion of goatshape",
                        "potions of goatshape",
                        "An alchemistic potion, drinking it will bestow the power of a goat to you. Whatever that means?")

                .modelName("model.potion.goat.").imageNumber(potionNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock

                .build();

        potionIdGoat = potionGoat.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidGoatId,
                potionIdGoat, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionHeal() throws IOException {
        potionHeal = new ItemTemplateBuilder("arathok.alchemy.potionHeal")
                .name(" potion of heal", "potions of heal", "An alchemistic potion, drinking it will heal you.")

                .modelName("model.potion.heal.").imageNumber(potionHealIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(1, 1, 2).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdHeal = potionHeal.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidHealId,
                potionIdHeal, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionFrenzy() throws IOException {
        potionFranticCharge = new ItemTemplateBuilder("arathok.alchemy.potionFranticCharge")
                .name(" potion of frenzy", "potions of frenzy",
                        "An alchemistic potion, drinking it will bestow the power of rage to you.")

                .modelName("model.potion.FranticCharge.").imageNumber(potionCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 0) // no
                // hard
                // lock
                .build();

        potionIdFranticCharge = potionFranticCharge.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId,
                PotionItems.potionLiquidFranticChargeId, potionIdFranticCharge, true, true, 0f, false, false,
                CreationCategories.ALCHEMY);
    }

    private static void registerPotionMorningFog() throws IOException {
        potionMorningFog = new ItemTemplateBuilder("arathok.alchemy.potionMorningFog")
                .name(" potion of fog skin", "potions of fog skin",
                        "An alchemistic potion, drinking it will bestow the power of Fog to you.")

                .modelName("model.potion.fog.").imageNumber(potionNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdMorningFog = potionMorningFog.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId,
                PotionItems.potionLiquidMorningFogId, potionIdMorningFog, true, true, 0f, false, false,
                CreationCategories.ALCHEMY);
    }

    private static void registerPotionOakshell() throws IOException {
        potionOakshell = new ItemTemplateBuilder("arathok.alchemy.potionOakshell")
                .name(" potion of Woodskin", "potions of Woodskin",
                        "An alchemistic potion, drinking it will bestow the power of wood skin to you.")

                .modelName("model.potion.Oakshell.").imageNumber(potionDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdOakshell = potionOakshell.getTemplateId();

        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidOakshellId,
                potionIdOakshell, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionRefresh() throws IOException {
        potionRefresh = new ItemTemplateBuilder("arathok.alchemy.potionRefresh")
                .name(" potion of stamina", "potions of stamina",
                        "An alchemistic potion, drinking it will regenerate some stamina for you.")

                .modelName("model.potion.stamina.").imageNumber(potionNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdRefresh = potionRefresh.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidRefreshId,
                potionIdRefresh, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionSixthSense() throws IOException {
        potionSixthSense = new ItemTemplateBuilder("arathok.alchemy.potionSixthSense")
                .name(" potion of senses", "potions of senses",
                        "An alchemistic potion, drinking it will bestow superior situational awareness to you.")

                .modelName("model.potion.SixthSense.").imageNumber(potionNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdSixthSense = potionSixthSense.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId,
                PotionItems.potionLiquidSixthSenseId, potionIdSixthSense, true, true, 0f, false, false,
                CreationCategories.ALCHEMY);
    }

    private static void registerPotionStrength() throws IOException {
        potionStrength = new ItemTemplateBuilder("arathok.alchemy.potionStrength").name(" potion of demon blood",
                        "potions of demon blood",
                        "An alchemistic potion, drinking it will bestow the power of demonic blood and superior strength to you.")

                .modelName("model.potion.strength.").imageNumber(potionCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdStrength = potionStrength.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidStrengthId,
                potionIdStrength, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionTruehit() throws IOException {
        potionTruehit = new ItemTemplateBuilder("arathok.alchemy.potionTruehit")
                .name(" potion of truehit", "potions of truehit",
                        "An alchemistic potion, drinking it will bestow the power superior battle focus to you.")

                .modelName("model.potion.truehit.").imageNumber(potionCombatIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 30) // no
                // hard
                // lock
                .build();

        potionIdTruehit = potionTruehit.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidTruehitId,
                potionIdTruehit, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionVynora() throws IOException {
        potionVynora = new ItemTemplateBuilder("arathok.alchemy.potionVynora")
                .name(" potion of knowledge", "potions of knowledge",
                        "This potion is the epitome of alchemy. It is bottled knowledge. ")

                .modelName("model.potion.vynora.").imageNumber(potionUltimateIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 90) // no
                // hard
                // lock
                .build();

        potionIdVynora = potionVynora.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidVynoraId,
                potionIdVynora, true, true, 0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerPotionMana() throws IOException {
        potionMana = new ItemTemplateBuilder("arathok.alchemy.potionMana")
                .name(" potion of mana", "potions of mana",
                        "This potion is in the upper echelons of alchemy. It contains the power of the gods. ")

                .modelName("model.potion.mana.").imageNumber(potionDefenseIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 40) // no
                // hard
                // lock
                .build();

        potionManaId = potionMana.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidManaId,
                potionManaId, true, true, 0f, false, false, 0, 40.0, CreationCategories.ALCHEMY);
    }

    private static void registerPotionKarma() throws IOException {
        potionKarma = new ItemTemplateBuilder("arathok.alchemy.potionKarma")
                .name(" potion of karma", "potions of karma",
                        "This potion is in the upper echelons of alchemy. It contains the power of the one true self. ")

                .modelName("model.potion.karma.").imageNumber(potionNormalIcon)
                .itemTypes(new short[]{ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_HERB,
                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_WEAPON, ItemTypes.ITEM_TYPE_WEAPON_KNIFE,

                }).decayTime(2073600L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 40) // no
                // hard
                // lock
                .build();

        potionKarmaId = potionKarma.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, AlchItems.phialId, PotionItems.potionLiquidKarmaId,
                potionKarmaId, true, true, 0f, false, false, 0, 50.0, CreationCategories.ALCHEMY);
    }

    public static void register() throws IOException {

        makeIcons();

        registerMixtureHeal();
        registermixtureDodging();
        registermixtureExcell();
        registermixtureFrenzy();
        registerMixtureGoat();
        registermixtureMorningFog();
        registermixtureOakshell();
        registermixtureRefresh();
        registermixtureSixthSense();
        registermixtureStrength();
        registermixtureTruehit();
        registermixtureVyn();

        registerPrecursorDodge();
        registerPrecursorExcell();
        registerPrecursorGoat();
        registerPrecursorFranticCharge();
        registerPrecursorHeal();
        registerPrecursorMorningFog();
        registerPrecursorOakshell();
        registerPrecursorRefresh();
        registerPrecursorSixthSense();
        registerPrecursorStrength();
        registerPrecursorTruehit();
        registerPrecursorVynora();

        registerPotionLiquidDodge();
        registerPotionLiquidExcell();
        registerPotionLiquidGoat();
        registerPotionLiquidFranticCharge();
        registerPotionLiquidHeal();
        registerPotionLiquidMorningFog();
        registerPotionLiquidOakshell();
        registerPotionLiquidRefresh();
        registerPotionLiquidSixthSense();
        registerPotionLiquidStrength();
        registerPotionLiquidTruehit();
        registerPotionLiquidVynora();
        registerPotionLiquidMana();
        registerPotionLiquidKarma();

        registerPotionDodge();
        registerPotionExcell();
        registerPotionGoat();
        registerPotionFrenzy();
        registerPotionHeal();
        registerPotionMorningFog();
        registerPotionOakshell();
        registerPotionRefresh();
        registerPotionSixthSense();
        registerPotionStrength();
        registerPotionTruehit();
        registerPotionVynora();
        registerPotionKarma();
        registerPotionMana();

        TempStates.addState(new TempState(PotionItems.precursorExcellId, PotionItems.potionLiquidExcellId, (short) 4000,
                true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorFranticChargeId, PotionItems.potionLiquidFranticChargeId,
                (short) 4000, true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorGoatId, PotionItems.potionLiquidGoatId, (short) 4000,
                true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorHealId, PotionItems.potionLiquidHealId, (short) 4000,
                true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorMorningFogId, PotionItems.potionLiquidMorningFogId,
                (short) 4000, true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorOakshellId, PotionItems.potionLiquidOakshellId,
                (short) 4000, true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorRefreshId, PotionItems.potionLiquidRefreshId,
                (short) 4000, true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorTruehitId, PotionItems.potionLiquidTruehitId,
                (short) 4000, true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorSixthSenseId, PotionItems.potionLiquidSixthSenseId,
                (short) 4000, true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorStrengthId, PotionItems.potionLiquidStrengthId,
                (short) 4000, true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorVynoraId, PotionItems.potionLiquidVynoraId, (short) 4000,
                true, true, false));
        TempStates.addState(new TempState(PotionItems.precursorWillowspineId, PotionItems.potionLiquidWillowspineId,
                (short) 4000, true, true, false));
    }

}