package org.arathok.wurmunlimited.mods.alchemy.essences;

import java.io.IOException;

import org.arathok.wurmunlimited.mods.alchemy.Config;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.tyoda.wurm.Iconzz.Iconzz;

import com.wurmonline.server.items.CreationCategories;
import com.wurmonline.server.items.CreationEntryCreator;
import com.wurmonline.server.items.CreationRequirement;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.items.ItemTemplate;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.server.items.Materials;
import com.wurmonline.shared.constants.IconConstants;

public class EssencesItems {

    public static int vitriolId, sandpowderId, slatepowderId, marblepowderId, ironpowderId, zincpowderId, tinpowderId,
            leadpowderId, copperpowderId, silverpowderId, goldpowderId, adamantinepowderId, seryllpowderId,
            glimmerpowderId,
            // powder becomes available at:
            // 20 30, 40, 50, 60, 70, 80, 90, 95
            acidicEssenceId, sandEssenceId, slateEssenceId, marbleEssenceId, ironEssenceId, zincEssenceId, tinEssenceId,
            leadEssenceId, copperEssenceId, silverEssenceId, goldEssenceId, adamantineEssenceId, seryllEssenceId,
            glimmerEssenceId, acidicExtractId, sandExtractId, slateExtractId, marbleExtractId, ironExtractId,
            zincExtractId, tinExtractId, leadExtractId, copperExtractId, silverExtractId, goldExtractId,
            adamantineExtractId, seryllExtractId, glimmerExtractId;

    public static ItemTemplate vitriol, sandpowder, slatepowder, marblepowder, ironpowder, zincpowder, tinpowder,
            leadpowder, copperpowder, silverpowder, goldpowder, adamantinepowder, seryllpowder, glimmerpowder,
            acidicEssence, sandEssence, slateEssence, marbleEssence, ironEssence, zincEssence, tinEssence, leadEssence,
            copperEssence, silverEssence, goldEssence, adamantineEssence, seryllEssence, glimmerEssence, acidicExtract,
            sandExtract, slateExtract, marbleExtract, ironExtract, zincExtract, tinExtract, leadExtract, copperExtract,
            silverExtract, goldExtract, adamantineExtract, seryllExtract, glimmerExtract;

    public static short vitriolIcon, acidicEssenceIcon,

            sandpowderIcon, slatepowderIcon, marblepowderIcon, ironpowderIcon, zincpowderIcon, tinpowderIcon,
            leadpowderIcon, copperpowderIcon, silverpowderIcon, goldpowderIcon, adamantinepowderIcon, seryllpowderIcon,
            glimmerpowderIcon,

            rockCoreIcon, sandStoneCoreIcon, marbleCoreIcon, slateCoreIcon, ironCoreIcon, copperCoreIcon, tinCoreIcon,
            leadCoreIcon, silverCoreIcon, goldCoreIcon, adamantineCoreIcon, seryllCoreIcon, glimmersteelCoreIcon,

            sandStoneEssenceIcon, marbleEssenceIcon, slateEssenceIcon, ironEssenceIcon, copperEssenceIcon,
            tinEssenceIcon, leadEssenceIcon, silverEssenceIcon, goldEssenceIcon, adamantineEssenceIcon,
            seryllEssenceIcon, glimmersteelEssenceIcon;

    private static void makeIcons() {
        vitriolIcon = Iconzz.getInstance().addIcon("arathok.alchemy.vitriolIcon", "org/arathok/wurmunlimited/mods/alchemy/icons/ironEssence.png");
        acidicEssenceIcon = Iconzz.getInstance().addIcon("arathok.alchemy.acidicEssenceIcon",
                "org/arathok/wurmunlimited/mods/alchemy/icons/ironEssence.png");
    }

    private static void registerVitriol() throws IOException {
        vitriol = new ItemTemplateBuilder("arathok.alchemy.vitriol")
                .name("vitriol", "vitriol",
                        "A green crystal with highly acidic properties. Made from iron rocks charcoal and lye")
                .modelName("model.alchemy.vitriol.").imageNumber(vitriolIcon).itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        vitriolId = vitriol.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.lowQlIron, ItemList.lye, vitriolId, true, true, 0.0f,
                        true, false, 0, 20, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.charcoal, 1, true));

    }

    private static void registerAcidEssence() throws IOException {
        acidicEssence = new ItemTemplateBuilder("arathok.alchemy.acidEssence").name("acid essence", "acid essences",
                "A milky liquid that has some corrosive properties. Maybe Distilling it will give it even better corrosive properties, but who would want that? ")
                .modelName("model.alchemy.acidEssence.").imageNumber(acidicEssenceIcon).itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        acidicEssenceId = acidicEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, ItemList.gland, acidicEssenceId, true, true,
                        0.0f, true, false, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.fruitJuice, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerAcidExtract() throws IOException {
        acidicExtract = new ItemTemplateBuilder("arathok.alchemy.acidExtract").name("acid extract", "acid extracts",
                "A liquid that has strong corrosive properties. Can be used to reduce any ore vein / reinforced cave wall to rock or destroy a rock wall.")
                .modelName("model.alchemy.acidExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        acidicExtractId = acidicExtract.getTemplateId();

    }

    private static void registerSandpowder() throws IOException {
        sandpowder = new ItemTemplateBuilder("arathok.alchemy.sandpowder").name("sand powder", "sand powders",
                "A yellowy powder that has somehow contained the essence of sand. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.sandpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_SANDSTONE)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        sandpowderId = sandpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.sandstone, sandpowderId, true,
                        true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.sandstone, 4, true));

    }

    private static void registerSandEssence() throws IOException {
        sandEssence = new ItemTemplateBuilder("arathok.alchemy.sandEssence").name("sand essence", "sand essences",
                "A milky liquid that has some abrasive properties. Maybe Distilling it will give it even better abrasive properties, but who would want that? ")
                .modelName("model.alchemy.sandEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        sandEssenceId = sandEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, sandpowderId, sandEssenceId, true, true, 0.0f,
                        true, false, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerSandExtract() throws IOException {
        sandExtract = new ItemTemplateBuilder("arathok.alchemy.sandExtract").name("sand extract", "sand extracts",
                "A liquid that has strong abrasive properties. Can be used to reduce any ore vein to sand stone or create a sandstone vein from rock.")
                .modelName("model.alchemy.sandExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_SANDSTONE)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        sandExtractId = sandExtract.getTemplateId();

    }

    private static void registerslatepowder() throws IOException {
        slatepowder = new ItemTemplateBuilder("arathok.alchemy.slatepowder").name("slate powder", "slate powders",
                "A dark powder that has somehow contained the essence of slate. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.slatepowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        slatepowderId = slatepowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.slateShard, slatepowderId, true,
                        true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.slateShard, 4, true));

    }

    private static void registerslateEssence() throws IOException {
        slateEssence = new ItemTemplateBuilder("arathok.alchemy.slateEssence").name("slate essence", "slate essences",
                "A milky liquid that has some black dying properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.slateEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        slateEssenceId = slateEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, slatepowderId, slateEssenceId, true, true, 0.0f,
                        true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerslateExtract() throws IOException {
        slateExtract = new ItemTemplateBuilder("arathok.alchemy.slateExtract").name("slate extract", "slate extracts",
                "A liquid that has strong dark properties. Can be used to reduce any ore vein to slate or create a slate vein from rock.")
                .modelName("model.alchemy.slateExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_SLATE)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        slateExtractId = slateExtract.getTemplateId();

    }

    private static void registermarblepowder() throws IOException {
        marblepowder = new ItemTemplateBuilder("arathok.alchemy.marblepowder").name("marble powder", "marble powders",
                "A bright powder that has somehow contained the essence of marble. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.marblepowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_MARBLE)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        marblepowderId = marblepowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.marbleShard, marblepowderId, true,
                        true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.marbleShard, 4, true));

    }

    private static void registermarbleEssence() throws IOException {
        marbleEssence = new ItemTemplateBuilder("arathok.alchemy.marbleEssence").name("marble essence",
                "marble essences",
                "A milky liquid that has some white dying properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.marbleEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        marbleEssenceId = marbleEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, marblepowderId, marbleEssenceId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registermarbleExtract() throws IOException {
        marbleExtract = new ItemTemplateBuilder("arathok.alchemy.marbleExtract").name("marble extract",
                "marble extracts",
                "A liquid that has strong light properties. Can be used to reduce any ore vein to marble or create a marble vein from rock.")
                .modelName("model.alchemy.marbleExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_COAL)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        marbleExtractId = marbleExtract.getTemplateId();

    }

    private static void registerironpowder() throws IOException {
        ironpowder = new ItemTemplateBuilder("arathok.alchemy.ironpowder").name("iron powder", "iron powders",
                "A powder that has somehow contained the essence of iron. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.ironpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_IRON)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        ironpowderId = ironpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.ironBar, ironpowderId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.ironBar, 99, true));

    }

    private static void registerironEssence() throws IOException {
        ironEssence = new ItemTemplateBuilder("arathok.alchemy.ironEssence").name("iron essence", "iron essences",
                "A milky liquid that has magnetic properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.ironEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        ironEssenceId = ironEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, ironpowderId, ironEssenceId, true, true, 0.0f,
                        true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerironExtract() throws IOException {
        ironExtract = new ItemTemplateBuilder("arathok.alchemy.ironExtract").name("iron extract", "iron extracts",
                "A liquid that has strong magnetic metallic properties. Can be used to reduce any ore vein to iron or create a iron vein from rock.")
                .modelName("model.alchemy.ironExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_IRON)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        ironExtractId = ironExtract.getTemplateId();

    }

    private static void registertinpowder() throws IOException {
        tinpowder = new ItemTemplateBuilder("arathok.alchemy.tinpowder").name("tin powder", "tin powders",
                "A powder that has somehow contained the essence of tin. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.tinpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_TIN)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        tinpowderId = tinpowder.getTemplateId();
        CreationEntryCreator.createSimpleEntry(Config.skillUsed, ItemList.grindstone, ItemList.tinBar, tinpowderId,
                false, true, 0.0f, false, false, 0, 30, CreationCategories.ALCHEMY);

    }

    private static void registertinEssence() throws IOException {
        tinEssence = new ItemTemplateBuilder("arathok.alchemy.tinEssence").name("tin essence", "tin essences",
                "A milky liquid that has vey lightweight but not so durable properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.tinEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        tinEssenceId = tinEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, tinpowderId, tinEssenceId, true, true, 0.0f,
                        true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registertinExtract() throws IOException {
        tinExtract = new ItemTemplateBuilder("arathok.alchemy.tinExtract")
                .name("tin extract", "tin extracts",
                        "A liquid that has strong leightweight but also weak structural properties.")
                .modelName("model.alchemy.tinExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_TIN)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        tinExtractId = tinExtract.getTemplateId();

    }

    private static void registerzincpowder() throws IOException {
        zincpowder = new ItemTemplateBuilder("arathok.alchemy.zincpowder").name("zinc powder", "zinc powders",
                "A greenish powder that has somehow contained the essence of zinc. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.zincpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_ZINC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        zincpowderId = zincpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.zincBar, zincpowderId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.zincBar, 4, true));

    }

    private static void registerzincEssence() throws IOException {
        zincEssence = new ItemTemplateBuilder("arathok.alchemy.zincEssence").name("zinc essence", "zinc essences",
                "A milky liquid that has vey lightweight and swift properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.zincEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        zincEssenceId = zincEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, zincpowderId, zincEssenceId, true, true, 0.0f,
                        true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerzincExtract() throws IOException {
        zincExtract = new ItemTemplateBuilder("arathok.alchemy.zincExtract")
                .name("zinc extract", "zinc extracts",
                        "A liquid that has strong leightweight but also swift properties.")
                .modelName("model.alchemy.zincExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_ZINC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        zincExtractId = zincExtract.getTemplateId();

    }

    private static void registerleadpowder() throws IOException {
        leadpowder = new ItemTemplateBuilder("arathok.alchemy.leadpowder").name("lead powder", "lead powders",
                "A powder that has somehow contained the essence of lead. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.leadpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_LEAD)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        leadpowderId = leadpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.leadBar, leadpowderId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.leadBar, 99, true));

    }

    private static void registerleadEssence() throws IOException {
        leadEssence = new ItemTemplateBuilder("arathok.alchemy.leadEssence").name("lead essence", "lead essences",
                "A milky liquid that has vey heavy but also poisonous properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.leadEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        leadEssenceId = leadEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, leadpowderId, leadEssenceId, true, true, 0.0f,
                        true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerleadExtract() throws IOException {
        leadExtract = new ItemTemplateBuilder("arathok.alchemy.leadExtract")
                .name("lead extract", "lead extracts", "A liquid that is heavy but also has poisonous properties.")
                .modelName("model.alchemy.leadExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_LEAD)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        leadExtractId = leadExtract.getTemplateId();

    }

    private static void registercopperpowder() throws IOException {
        copperpowder = new ItemTemplateBuilder("arathok.alchemy.copperpowder").name("copper powder", "copper powders",
                "A powder that has somehow contained the essence of copper. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.copperpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_COPPER)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        copperpowderId = copperpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.copperBar, copperpowderId, true,
                        true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.copperBar, 99, true));

    }

    private static void registercopperEssence() throws IOException {
        copperEssence = new ItemTemplateBuilder("arathok.alchemy.copperEssence").name("copper essence",
                "copper essences",
                "A milky liquid that has vey lightweight but also poisonous properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.copperEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        copperEssenceId = copperEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, copperpowderId, copperEssenceId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registercopperExtract() throws IOException {
        copperExtract = new ItemTemplateBuilder("arathok.alchemy.copperExtract")
                .name("copper extract", "copper extracts",
                        "A liquid that has strong leightweight but poisonous properties.")
                .modelName("model.alchemy.copperExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_COPPER)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        copperExtractId = copperExtract.getTemplateId();

    }

    private static void registersilverpowder() throws IOException {
        silverpowder = new ItemTemplateBuilder("arathok.alchemy.silverpowder").name("silver powder", "silver powders",
                "A powder that has somehow contained the essence of silver. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.silverpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_SILVER)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        silverpowderId = silverpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.silverBar, silverpowderId, true,
                        true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.silverBar, 99, true));

    }

    private static void registersilverEssence() throws IOException {
        silverEssence = new ItemTemplateBuilder("arathok.alchemy.silverEssence").name("silver essence",
                "silver essences",
                "A milky liquid that has vey lightweight properties it seems to be aggressive against monster skin. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.silverEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        silverEssenceId = silverEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, silverpowderId, silverEssenceId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registersilverExtract() throws IOException {
        silverExtract = new ItemTemplateBuilder("arathok.alchemy.silverExtract")
                .name("silver extract", "silver extracts",
                        "A liquid that has strong leightweight properties. Animal smelling it flee.")
                .modelName("model.alchemy.silverExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_SILVER)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        silverExtractId = silverExtract.getTemplateId();

    }

    private static void registergoldpowder() throws IOException {
        goldpowder = new ItemTemplateBuilder("arathok.alchemy.goldpowder").name("gold powder", "gold powders",
                "A powder that has somehow contained the essence of gold. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.goldpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_GOLD)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        goldpowderId = goldpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.goldBar, goldpowderId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.goldBar, 99, true));

    }

    private static void registergoldEssence() throws IOException {
        goldEssence = new ItemTemplateBuilder("arathok.alchemy.goldEssence").name("gold essence", "gold essences",
                "A shiny liquid. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.goldEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(100).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        goldEssenceId = goldEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, goldpowderId, goldEssenceId, true, true, 0.0f,
                        true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registergoldExtract() throws IOException {
        goldExtract = new ItemTemplateBuilder("arathok.alchemy.goldExtract")
                .name("gold extract", "gold extracts", "A liquid that has strong magic affinites.")
                .modelName("model.alchemy.goldExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_GOLD)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        goldExtractId = goldExtract.getTemplateId();

    }

    private static void registeradamantiumpowder() throws IOException {
        adamantinepowder = new ItemTemplateBuilder("arathok.alchemy.adamantinepowder").name("adamantine powder",
                "adamantine powders",
                "A powder that has somehow contained the essence of adamantine. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.adamantinepowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_ADAMANTINE)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        adamantinepowderId = adamantinepowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.adamantineBar, adamantinepowderId,
                        true, true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.adamantineBar, 99, true));

    }

    private static void registeradamantineEssence() throws IOException {
        adamantineEssence = new ItemTemplateBuilder("arathok.alchemy.adamantineEssence").name("adamantine essence",
                "adamantine essences",
                "A milky liquid that has good magical properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.adamantineEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(400).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        adamantineEssenceId = adamantineEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, adamantinepowderId, adamantineEssenceId, true,
                        true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registeradamantineExtract() throws IOException {
        adamantineExtract = new ItemTemplateBuilder("arathok.alchemy.adamantineExtract")
                .name("adamantine extract", "adamantine extracts", "A liquid that has strong magic properties.")
                .modelName("model.alchemy.adamantineExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(400).material(Materials.MATERIAL_ADAMANTINE)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        adamantineExtractId = adamantineExtract.getTemplateId();

    }

    private static void registerglimmersteelpowder() throws IOException {
        glimmerpowder = new ItemTemplateBuilder("arathok.alchemy.glimmerpowder").name("glimmer powder",
                "glimmer powders",
                "A powder that has somehow contained the essence of glimmer. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.glimmerpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_GLIMMERSTEEL)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        glimmerpowderId = glimmerpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.glimmerSteelBar, glimmerpowderId,
                        true, true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.glimmerSteelBar, 99, true));

    }

    private static void registerglimmerEssence() throws IOException {
        glimmerEssence = new ItemTemplateBuilder("arathok.alchemy.glimmerEssence").name("glimmer essence",
                "glimmer essences",
                "A milky liquid that some say is the epitome of Alchemy. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.glimmerEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        glimmerEssenceId = glimmerEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, glimmerpowderId, glimmerEssenceId, true, true,
                        0.0f, true, true, 0, 50, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerglimmerExtract() throws IOException {
        glimmerExtract = new ItemTemplateBuilder("arathok.alchemy.glimmerExtract")
                .name("glimmer extract", "glimmer extracts",
                        "A liquid that is said to be the distilled epitome of alchemy.")
                .modelName("model.alchemy.glimmerExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(1000).material(Materials.MATERIAL_GLIMMERSTEEL)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        glimmerExtractId = glimmerExtract.getTemplateId();

    }

    private static void registerseryllpowder() throws IOException {
        seryllpowder = new ItemTemplateBuilder("arathok.alchemy.seryllpowder").name("seryll powder", "seryll powders",
                "A powder that has somehow contained the essence of seryll. Maybe you can use its slumbering power in some alchemic brew?")
                .modelName("model.alchemy.seryllpowder.").imageNumber((short) IconConstants.ICON_ARTIFACT_VALREI)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DECORATION,

                }).decayTime(9072000L).dimensions(5, 5, 5).weightGrams(1000).material(Materials.MATERIAL_SERYLL)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(20) // no hard lock
                .build();

        seryllpowderId = seryllpowder.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.grindstone, ItemList.seryllBar, seryllpowderId, true,
                        true, 0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.seryllBar, 99, true));

    }

    private static void registerseryllEssence() throws IOException {
        seryllEssence = new ItemTemplateBuilder("arathok.alchemy.seryllEssence").name("seryll essence",
                "seryll essences",
                "A milky liquid that has good swift properties. Maybe Distilling it will give it even better properties, but who would want that? ")
                .modelName("model.alchemy.seryllEssence.").imageNumber((short) IconConstants.ICON_FOOD_STEW)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_USES_FOOD_STATE,
                        ItemTypes.ITEM_TYPE_FERMENTED, ItemTypes.ITEM_TYPE_DISTILLED, ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_MAGIC)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(Config.baseDifficulty * 50) // no
                                                                                                                // hard
                                                                                                                // lock
                .build();

        seryllEssenceId = seryllEssence.getTemplateId();
        CreationEntryCreator
                .createAdvancedEntry(Config.skillUsed, ItemList.water, seryllpowderId, seryllEssenceId, true, true,
                        0.0f, true, true, 0, 30, CreationCategories.ALCHEMY)
                .addRequirement(new CreationRequirement(1, ItemList.salt, 1, true))
                .addRequirement(new CreationRequirement(2, vitriolId, 1, true));
    }

    private static void registerseryllExtract() throws IOException {
        seryllExtract = new ItemTemplateBuilder("arathok.alchemy.seryllExtract")
                .name("seryll extract", "seryll extracts",
                        "A liquid that has strong swift properties. It seems to be begging you to enchant it.")
                .modelName("model.alchemy.seryllExtract.").imageNumber((short) IconConstants.ICON_LIQUID_DISHWATER)
                .itemTypes(new short[] {

                        ItemTypes.ITEM_TYPE_BULK, ItemTypes.ITEM_TYPE_NO_IMPROVE, ItemTypes.ITEM_TYPE_DISTILLED,
                        ItemTypes.ITEM_TYPE_LIQUID,

                }).decayTime(9072000L).dimensions(5, 5, 10).weightGrams(200).material(Materials.MATERIAL_SERYLL)
                .behaviourType((short) 1).primarySkill(Config.skillUsed).difficulty(5) // no hard lock
                .build();

        seryllExtractId = seryllExtract.getTemplateId();

    }

    public static void register() throws IOException {
        makeIcons();

        registerVitriol();
        registerSandpowder();
        registerSandEssence();
        registerSandExtract();
        registerslatepowder();
        registerslateEssence();
        registerslateExtract();
        registermarblepowder();
        registermarbleEssence();
        registermarbleExtract();
        registerAcidEssence();
        registerAcidExtract();
        registeradamantiumpowder();
        registeradamantineEssence();
        registeradamantineExtract();
        registerironpowder();
        registerironEssence();
        registerironExtract();
        registerzincpowder();
        registerzincEssence();
        registerzincExtract();
        registertinpowder();
        registertinEssence();
        registertinExtract();
        registerleadpowder();
        registerleadEssence();
        registerleadExtract();
        registercopperpowder();
        registercopperEssence();
        registercopperExtract();
        registersilverpowder();
        registersilverEssence();
        registersilverExtract();
        registergoldpowder();
        registergoldEssence();
        registergoldExtract();
        registerglimmersteelpowder();
        registerglimmerEssence();
        registerglimmerExtract();
        registerseryllpowder();
        registerseryllEssence();
        registerseryllExtract();
    }
}
