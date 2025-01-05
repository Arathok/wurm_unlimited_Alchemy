
package org.arathok.wurmunlimited.mods.alchemy; // now add calls to registerBlah in onItemTemplatesCreated

import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.IconConstants;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.tyoda.wurm.Iconzz.Iconzz;

import java.io.IOException;
//TODO: write insert before to SimpleCreationEntry run(Creature performer, Item source, long targetId, float counter) throws FailedException, NoSuchSkillException, NoSuchItemException, IllegalArgumentException {
public class AlchItems {
    public static int leaderId, phialId, mouldClayId, mouldPotteryId, purifiedWaterId, alchemicalCompoundId,
            glassMixtureId, glassId, weakLegsId, gemPowderId, coalDustId, coalFilterId, alchemicalCauldronId;

    public static ItemTemplate phial, mouldClay, mouldPottery, purifiedWater, alchemicalCompound, glassMixture, glass,
            weakLegs, gemPowder, coalDust, coalFilter, leader, alchemicalCauldron;
    public static short mouldClayIcon, mouldPotteryIcon, glassMixIcon, glassIcon, phialIcon;

    public static void makeIcons() {
        mouldClayIcon = Iconzz.getInstance().addIcon("arathok.alchemy.mouldcClayIcon",
                "mods/alchemy/icons/phialMouldClay.png");
        mouldPotteryIcon = Iconzz.getInstance().addIcon("arathok.alchemy.mouldcPotteryIcon",
                "mods/alchemy/icons/phialMouldPottery.png");
        glassMixIcon = Iconzz.getInstance().addIcon("arathok.alchemy.glassMixIcon", "mods/alchemy/icons/glassMix.png");
        glassIcon = Iconzz.getInstance().addIcon("arathok.alchemy.glassIcon", "mods/alchemy/icons/glas.png");
        phialIcon = Iconzz.getInstance().addIcon("arathok.alchemy.phialIcon", "mods/alchemy/icons/phial.png");
    }

    private static void registerLeader() throws IOException {
        leader = new ItemTemplateBuilder("arathok.alchemy.leader")
                .name("Alchemy EasterEgg", "useless bums", " Congratulations! You have found an EasterEgg!"
                        + " Thank you Unusualleader for being the best friend one could possibly find on the internet. "
                        + " Thank you Bdew for your tireless effort to teach me and the patience! Your help made this dream mod of mine possible and actually thought me quite a lot."
                        + " Thanks Coldie for your tools and tutorials on how to Model and how to write a basic mod! Thank you for your help with making a texture pack! "
                        + " Thanks EmmaGrace for pushing me over the edge and pushing me to make this mod.  "
                        + " Thank you Wurm Modding Community Discord for hours of help!")
                .modelName("model.decoration.statuette.magranon.").imageNumber((short) IconConstants.ICON_FOOD_PIGFOOD)
                .itemTypes(new short[] { ItemTypes.ITEM_TYPE_POTTERY, ItemTypes.ITEM_TYPE_PLANTABLE,
                        ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_NO_IMPROVE,
                        // ItemTypes.ITEM_TYPE_TRANSPORTABLE,
                        ItemTypes.ITEM_TYPE_TOOL, ItemTypes.ITEM_TYPE_METAL,

                }).decayTime(9072000L).dimensions(8, 8, 15).weightGrams(85000).material(Materials.MATERIAL_IRON)
                .behaviourType((short) 1).primarySkill(SkillList.POTTERY).difficulty(30) // no hard lock
                .build();

        leaderId = leader.getTemplateId();
    }

    private static void registerCauldron() throws IOException {
        alchemicalCauldron = new ItemTemplateBuilder("arathok.alchemy.alchemicalCauldron").name("alchemical cauldron", "alchemical cauldrons",
                        "A heavy alchemical cauldron made from steel, ready to be filled with water. It can be filled with all sorts of alchemical ingredients to turn them into potions!")
                .modelName("model.decoration.cauldron.").imageNumber((short) IconConstants.ICON_TOOL_CAULDRON).itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_HOLLOW,
                        ItemTypes.ITEM_TYPE_CONTAINER_LIQUID,
                        ItemTypes.ITEM_TYPE_USE_GROUND_ONLY,
                        ItemTypes.ITEM_TYPE_TRANSPORTABLE,
                        ItemTypes.ITEM_TYPE_METAL,
                        ItemTypes.ITEM_TYPE_NOPUT,

                }).decayTime(9072000L).dimensions(30, 25, 30).weightGrams(35000).material(Materials.MATERIAL_STEEL)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(10) // no hard lock
                .build();

        alchemicalCauldronId =  alchemicalCauldron.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(SkillList.SMITHING_BLACKSMITHING, ItemList.hammerMetal, ItemList.steelBar,  alchemicalCauldronId, false, true,
                        0.0f, false, false, 0, 5, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.steelBar, 35, true))
                .addRequirement(new CreationRequirement(2, ItemList.ironBand, 4, true));
    }

    private static void registerWeakLegs() throws IOException {
        weakLegs = new ItemTemplateBuilder("arathok.alchemy.addiction.weakLegs")
                .name("Weak Legs", "Weak Legs", " You look at your trembling legs. You are addicted "
                        + "to potions and feel weak. You feel like your whole body itself is too heavy to carry around.")
                .modelName("model.decoration.statuette.magranon.").imageNumber((short) IconConstants.ICON_ICON_BODY_LEG)
                .itemTypes(new short[] { ItemTypes.ITEM_TYPE_NODROP, ItemTypes.ITEM_TYPE_NODISCARD,
                        ItemTypes.ITEM_TYPE_NOPUT, ItemTypes.ITEM_TYPE_NOBANK, ItemTypes.ITEM_TYPE_NO_IMPROVE,
                        // ItemTypes.ITEM_TYPE_NO_CREATE,
                        ItemTypes.ITEM_TYPE_NORENAME, ItemTypes.ITEM_TYPE_NOTRADE, ItemTypes.ITEM_TYPE_NOMOVE,
                        ItemTypes.ITEM_TYPE_NOT_SPELL_TARGET
                // ItemTypes.ITEM_TYPE_TRANSPORTABLE,

                }).decayTime(90720000000L).dimensions(8, 8, 15).weightGrams(105000).material(Materials.MATERIAL_BONE)
                .behaviourType((short) 1).primarySkill(SkillList.POTTERY).difficulty(30) // no hard lock

                .build();

        weakLegsId = weakLegs.getTemplateId();
    }

    private static void registerGlassMixture() throws IOException {
        glassMixture = new ItemTemplateBuilder("arathok.alchemy.glassMixture").name("glass mixture", "glass mixtures",
                "A mixture made from sand, ash, and sandstone shards. Under high temperatures it will turn into a honey"
                        + "like paste. cooling it off you will get crystal clear glass. But what would a pile of glass be of use for? If you had some kind of a"
                        + "mould you could press the hot glass into shape...")
                .modelName("model.decoration.glassMixture.").imageNumber(glassMixIcon).itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,
                        // ItemTypes.ITEM_TYPE_TRANSPORTABLE,
                        ItemTypes.ITEM_TYPE_METAL,

                }).decayTime(9072000L).dimensions(20, 20, 20).weightGrams(24000).material(Materials.MATERIAL_STONE)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(0) // no hard lock

                .build();

        glassMixtureId = glassMixture.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(SkillList.ALCHEMY_NATURAL, ItemList.sand, ItemList.ash, glassMixtureId, true, true,
                        0.0f, true, false, 0, 5, CreationCategories.RESOURCES)
                .addRequirement(new CreationRequirement(1, ItemList.ash, 17, true))
                .addRequirement(new CreationRequirement(2, ItemList.salt, 17, true));
    }

    private static void registerGlass() throws IOException {
        glass = new ItemTemplateBuilder("arathok.alchemy.glass").name("glass", "glass",
                "Crystal clear glass. If you could make a phial out of it, it could contain magic stored in potions. "
                        + "You should heat it before pressing it.")
                .modelName("model.decoration.flask.").imageNumber(glassIcon).itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,
                        // ItemTypes.ITEM_TYPE_TRANSPORTABLE,

                        ItemTypes.ITEM_TYPE_METAL,

                }).decayTime(9072000L).dimensions(20, 20, 20).weightGrams(5000).material(Materials.MATERIAL_GLASS)
                .behaviourType((short) 1).primarySkill(SkillList.GROUP_ALCHEMY).difficulty(30) // no hard lock
                .build();

        glassId = glass.getTemplateId();
    }

    private static void registerMouldClay() throws IOException {
        mouldClay = new ItemTemplateBuilder("arathok.alchemy.mouldClay")
                .name("phial mould", "phial moulds",
                        "A clay mould that could be used to make phials, but it still needs to be fired.")
                .modelName("model.mouldClay.").imageNumber(mouldClayIcon)
                .itemTypes(new short[] { ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION,
                        ItemTypes.ITEM_TYPE_TURNABLE, ItemTypes.ITEM_TYPE_UNFIRED, ItemTypes.ITEM_TYPE_REPAIRABLE,
                // ItemTypes.ITEM_TYPE_TRANSPORTABLE,

                }).decayTime(9072000L).dimensions(8, 8, 15).weightGrams(500).material(Materials.MATERIAL_CLAY)
                .behaviourType((short) 1).primarySkill(SkillList.POTTERY).difficulty(30) // no hard lock
                .build();

        mouldClayId = mouldClay.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.POTTERY, ItemList.flaskPottery, ItemList.clay, mouldClayId,
                false, true, 0.0f, false, false, CreationCategories.POTTERY);
    }// nichts weiter

    private static void registerMouldPottery() throws IOException {
        mouldPottery = new ItemTemplateBuilder("arathok.alchemy.mouldPottery")
                .name("phial mould", "phial moulds", "A pottery mould that is used to make glass phials")
                .modelName("model.mouldPottery.").imageNumber(mouldPotteryIcon)
                .itemTypes(new short[] { ItemTypes.ITEM_TYPE_POTTERY, ItemTypes.ITEM_TYPE_PLANTABLE,
                        ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        // ItemTypes.ITEM_TYPE_TRANSPORTABLE,
                        ItemTypes.ITEM_TYPE_TOOL, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_METAL, })
                .decayTime(9072000L).dimensions(8, 8, 15).weightGrams(500).material(Materials.MATERIAL_POTTERY)
                .behaviourType((short) 1).primarySkill(SkillList.POTTERY).difficulty(30) // no hard lock
                .build();

        mouldPotteryId = mouldPottery.getTemplateId();
    }

    private static void registerPhial() throws IOException {
        phial = new ItemTemplateBuilder("arathok.alchemy.phial")
                .name("glass phial", "glass phials", "A crystal clear container for liquids, made from glass."
                        + " It is said Alchemists use these often to store their concoctions because the crystalline structure of glass is"
                        + "capable of holding strong magic powers.")

                .modelName("model.phial.").imageNumber(phialIcon)
                .itemTypes(new short[] { ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_PLANTABLE,
                        ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        // ItemTypes.ITEM_TYPE_TRANSPORTABLE,
                        ItemTypes.ITEM_TYPE_TOOL, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_METAL,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(100).material(Materials.MATERIAL_GLASS)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(0) // no hard lock

                .build();

        phialId = phial.getTemplateId();

        CreationEntryCreator.createMetallicEntries(SkillList.ALCHEMY_NATURAL, AlchItems.mouldPotteryId,
                AlchItems.glassId, phialId, false, true, 0f, false, false, 0, 10, CreationCategories.ALCHEMY);
    } // NO ADDITIONAL ITEMS NEEDED

    private static void registerCharcoalDust() throws IOException {
        coalDust = new ItemTemplateBuilder("arathok.alchemy.charcoalDust")
                .name("Coal Dust", "Coal Dust",
                        "Dust from Coal, that can be used in a filter of some sorts. It has good purifying abilities!")
                .modelName("model.coalDust.").imageNumber((short) IconConstants.ICON_LIQUID_DYE_BLACK)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(10).material(Materials.MATERIAL_COAL)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(5) // no hard lock
                .build();

        coalDustId = coalDust.getTemplateId();
        CreationEntryCreator.createSimpleEntry(SkillList.GROUP_ALCHEMY, ItemList.grindstone, ItemList.charcoal,
                coalDustId, false, true, 0.0f, false, false, CreationCategories.ALCHEMY);
    }

    private static void registerCharcoalFilter() throws IOException {
        coalFilter = new ItemTemplateBuilder("arathok.alchemy.charcoalFilter").name("Coal filter", "Coal filter",
                "A filter made from charcoal and Cloth. It can be used to purify water but will get used up over time.")
                .modelName("model.coalFilter.").imageNumber((short) IconConstants.ICON_WOOD_BED_FRAME)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_TOOL, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_NAMED,
                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_PLANTABLE,

                }).decayTime(619200L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_COAL)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(5) // no hard lock
                .build();

        coalFilterId = coalFilter.getTemplateId();

        CreationEntryCreator
                .createAdvancedEntry(SkillList.ALCHEMY_NATURAL, ItemList.shaft, ItemList.shaft, coalFilterId, true,
                        false, 0f, true, false, 0, 5, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, AlchItems.coalDustId, 5, true))
                .addRequirement(new CreationRequirement(2, ItemList.clothYard, 2, true))
                .addRequirement(new CreationRequirement(3, ItemList.shaft, 2, true))
                .addRequirement(new CreationRequirement(4, ItemList.nailsIronSmall, 4, true));

    }

    private static void registerPurifiedWater() throws IOException {
        purifiedWater = new ItemTemplateBuilder("arathok.alchemy.purifiedWater")
                .name("purified water", "purified water",
                        "Water that has been cleaned from mundane impurities"
                                + " and is ready to take in a powerful essence.")
                .modelName("model.purfiedWater.").imageNumber((short) IconConstants.ICON_LIQUID_WATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID, ItemTypes.ITEM_TYPE_METAL,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(5) // no hard lock
                .build();

        purifiedWaterId = purifiedWater.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.ALCHEMY_NATURAL, ItemList.water, AlchItems.coalFilterId,
                purifiedWaterId, true, false, 0f, false, false, 0, 5, CreationCategories.ALCHEMY);

    }

    private static void registerAlchemicalCompound() throws IOException {
        alchemicalCompound = new ItemTemplateBuilder("arathok.alchemy.alchemicalCompound")
                .name("alchemical compound", "alchemical compounds",
                        "A white-ish liquid smelling pretty bad. Its made from the essence of a beast"
                                + " and you can sense it holds potential to store the power of natural substances"
                                + "it should be used fresh but seems stable enough for a day.")

                .modelName("model.alchemicalCompound.").imageNumber((short) IconConstants.ICON_LIQUID_DYE_WHITE)
                .itemTypes(new short[] { ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_LIQUID,
                        ItemTypes.ITEM_TYPE_METAL, ItemTypes.ITEM_TYPE_NO_IMPROVE,

                }).decayTime(691200L).dimensions(10, 10, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(10) // no hard lock
                .build();

        alchemicalCompoundId = alchemicalCompound.getTemplateId();

        CreationEntryCreator.createSimpleEntry(SkillList.ALCHEMY_NATURAL, AlchItems.purifiedWaterId, ItemList.heart,
                alchemicalCompoundId, true, true, 0f, false, false, 0, 5, CreationCategories.ALCHEMY);
    }

    private static void registerGemPowder() throws IOException {
        gemPowder = new ItemTemplateBuilder("arathok.alchemy.gemPowder").name("Gem powder", "Gem powders",
                "Ground up gems that could be very well used in some potions that need some extra magical power")
                .modelName("model.gemPowder.").imageNumber((short) IconConstants.ICON_FOOD_SALT).itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_PLANTABLE, ItemTypes.ITEM_TYPE_DECORATION, ItemTypes.ITEM_TYPE_TURNABLE,
                        ItemTypes.ITEM_TYPE_NO_IMPROVE,
                        // ItemTypes.ITEM_TYPE_TRANSPORTABLE,

                        ItemTypes.ITEM_TYPE_MAGIC,

                }).decayTime(9072000L).dimensions(20, 20, 20).weightGrams(500).material(Materials.MATERIAL_GLASS)
                .behaviourType((short) 1).primarySkill(SkillList.ALCHEMY_NATURAL).difficulty(30) // no hard lock
                .build();

        gemPowderId = gemPowder.getTemplateId();
        CreationEntryCreator.createSimpleEntry(SkillList.ALCHEMY_NATURAL, ItemList.mortarAndPestle, ItemList.ruby,
                gemPowderId, false, true, 0f, false, false, 0, 30, CreationCategories.ALCHEMY);
        CreationEntryCreator.createSimpleEntry(SkillList.ALCHEMY_NATURAL, ItemList.mortarAndPestle, ItemList.emerald,
                gemPowderId, false, true, 0f, false, false, 0, 30, CreationCategories.ALCHEMY);
        CreationEntryCreator.createSimpleEntry(SkillList.ALCHEMY_NATURAL, ItemList.mortarAndPestle, ItemList.diamond,
                gemPowderId, false, true, 0f, false, false, 0, 30, CreationCategories.ALCHEMY);
        CreationEntryCreator.createSimpleEntry(SkillList.ALCHEMY_NATURAL, ItemList.mortarAndPestle, ItemList.sapphire,
                gemPowderId, false, true, 0f, false, false, 0, 30, CreationCategories.ALCHEMY);
        CreationEntryCreator.createSimpleEntry(SkillList.ALCHEMY_NATURAL, ItemList.mortarAndPestle, ItemList.opal,
                gemPowderId, false, true, 0f, false, false, 0, 30, CreationCategories.ALCHEMY);
    }

    /// PASTES

    public static void register() throws IOException {
        makeIcons();
        registerLeader();
        registerCauldron();
        registerWeakLegs();
        registerGlassMixture();
        registerGlass();
        TempStates
                .addState(new TempState(AlchItems.glassMixtureId, AlchItems.glassId, (short) 4000, true, false, false));
        registerMouldClay();
        registerMouldPottery();
        TempStates.addState(
                new TempState(AlchItems.mouldClayId, AlchItems.mouldPotteryId, (short) 4000, true, true, false));
        registerPhial();
        registerCharcoalDust();
        registerCharcoalFilter();
        registerPurifiedWater();
        if (Config.purifiedWaterCooking) {
            TempStates.addState(
                    new TempState(ItemList.water, AlchItems.purifiedWaterId, (short) 4000, true, true, false));
        }
        registerAlchemicalCompound();
        registerGemPowder();

    }


}