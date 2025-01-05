
package org.arathok.wurmunlimited.mods.alchemy.potions;

import org.arathok.wurmunlimited.mods.alchemy.AlchItems;
import org.arathok.wurmunlimited.mods.alchemy.Alchemy;
import org.arathok.wurmunlimited.mods.alchemy.addiction.Addiction;
import org.arathok.wurmunlimited.mods.alchemy.addiction.AddictionHandler;
import com.wurmonline.server.Items;
import com.wurmonline.server.Players;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.bodys.Wound;
import com.wurmonline.server.bodys.Wounds;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.SpellEffects;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.sounds.SoundPlayer;
import com.wurmonline.server.spells.SpellEffect;
import org.arathok.wurmunlimited.mods.alchemy.Config;
import org.gotti.wurmunlimited.modsupport.actions.ActionEntryBuilder;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.ActionPropagation;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;

public class PotionPerformer implements ActionPerformer {

    public ActionEntry actionEntry;

    Addiction playerInQuestion = null;
    Addiction playerAddiction = new Addiction(); // maybe making a variable here is bad as its stored through the entire
                                                 // performer.

    int seconds = Config.potionDuration;
    float power = 0;

    boolean playedOpeningSound = false;

    public PotionPerformer() {
        int[] types;
        if (Config.potionsDuringFighting) {
            types = new int[] { 6 /* ACTION_TYPE_NOMOVE */, 48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                    35 /* DON'T CARE WHETHER SOURCE OR TARGET */,
                    // 27, // NONSTACK

                    // 28 // NOSTACK IN FIGHT
            };
            actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Consume Potion", "consuming",
                    types)

                            .range(4).priority(1000).build();
        } else {
            types = new int[] { 6 /* ACTION_TYPE_NOMOVE */, 48 /* ACTION_TYPE_ENEMY_ALWAYS */,
                    35 /* DON'T CARE WHETHER SOURCE OR TARGET */,
                    // 27, // NONSTACK
                    // 0, // quick
                    // NOSTACK IN FIGHT
            };
            actionEntry = new ActionEntryBuilder((short) ModActions.getNextActionId(), "Consume Potion", "consuming",
                    types)

                            .range(4).priority(1).build();
        }

        ModActions.registerAction(actionEntry);

    }

    @Override
    public boolean action(Action action, Creature performer, Item source, Item target, short num, float counter) {
        return action(action, performer, target, num, counter);
    } // NEEDED OR THE ITEM WILL ONLY ACTIVATE IF YOU HAVE NO ITEM ACTIVE

    @Override
    public short getActionId() {
        return actionEntry.getNumber();
    }

    public static boolean canUse(Creature performer, Item target) // precondition
    {
        return performer.isPlayer() && target.getOwnerId() == performer.getWurmId() && !target.isTraded();

    }

    @Override
    public boolean action(Action action, Creature performer, Item target, short num, float counter) {

        if (counter == 1.0F) {

            action.setTimeLeft(30);
            performer.sendActionControl(action.getActionString(), true, 30);
            playedOpeningSound = false;
        } else if (counter > 1.0F && action.getSecond() == 1) {
            if (!playedOpeningSound) {
                SoundPlayer.playSound("sound.openFlask", performer, 1.6F);

                performer.playAnimation("drink", false, target.getWurmId());

                playedOpeningSound = true;

            }
            return propagate(action, ActionPropagation.CONTINUE_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        } else if (counter > 1.0F && action.getSecond() == 3) {
            SoundPlayer.playSound("sound.drink", performer, 1.6F);

            boolean heal = false;
            boolean playerFound = false;

            // Alchemy.logger.log(Level.INFO, "BLAH BLAH HE PERFORMS");

            if (!canUse(performer, target)) {
                performer.getCommunicator().sendAlertServerMessage("You are not allowed to do that");
                return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                        ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);

            }
            //////////////// TOXICITY/////////////////////

            Iterator<Addiction> toxicity = AddictionHandler.addictions.iterator();
            long time = System.currentTimeMillis();
            while (toxicity.hasNext()) {
                playerInQuestion = toxicity.next();
                int index = AddictionHandler.addictions.indexOf(playerInQuestion);
                if (target.getOwnerId() == playerInQuestion.playerId) {
                    if (playerInQuestion.toxicityWarningLevel > 0
                            && (playerInQuestion.coolDownBuffEnd > time || playerInQuestion.coolDownHealEnd > time)) {
                        performer.getCommunicator().sendAlertServerMessage(
                                "A rush of magic energy fills every nerve in your body, for a moment you feel euphoric"
                                        + ", godlike almost. Then the pain starts. Your entire existence is turned into a burning pain. You feel the darkness"
                                        + "approaching from every direction. Your tongue turns black, your vision fades. "
                                        + "Finally your body collapses under the heavy toxication of two potions of the same kind."
                                        + " Don't worry. Many alchemists where here and many will be. May the gods be merciful and give you another chance");
                        Player thePlayer = Players.getInstance().getPlayerOrNull(playerInQuestion.playerId);
                        Wounds pWounds = thePlayer.getBody().getWounds();
                        float severity = 0;
                        if (pWounds != null) {
                            for (Wound w : pWounds.getWounds()) {
                                severity = severity + w.getSeverity();
                            }
                        }

                        if (severity < 9830) {
                            thePlayer.addWoundOfType(thePlayer, (byte) 5, 23, false, 1.0F, false, 54000, 1, 0.0F, false,
                                    true);
                            playerInQuestion.toxicityWarningLevel = 1;
                        } else {
                            playerInQuestion.toxicityWarningLevel = 0;
                            AddictionHandler.addictions.set(index, playerInQuestion);
                            thePlayer.die(false, "toxicity");

                        }

                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
                    } else if (playerInQuestion.coolDownHealEnd > time
                            && (target.getTemplateId() == PotionItems.potionKarmaId
                                    || target.getTemplateId() == PotionItems.potionManaId
                                    || target.getTemplateId() == PotionItems.potionIdHeal
                                    || target.getTemplateId() == PotionItems.potionIdRefresh)
                            && playerInQuestion.toxicityWarningLevel < 1) {
                        performer.getCommunicator()
                                .sendAlertServerMessage("You stop your self from drinking that potion. "
                                        + "You are still under the effect of a potion and should wait another "
                                        + ((playerInQuestion.coolDownHealEnd - time) / 1000)
                                        + " seconds. Your body could collapse under "
                                        + "the toxins otherwise if you tried to drink again!");
                        playerInQuestion.toxicityWarningLevel = 1;
                        AddictionHandler.addictions.set(index, playerInQuestion);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
                    } else if (playerInQuestion.coolDownBuffEnd > time && playerInQuestion.toxicityWarningLevel < 1
                            && (target.getTemplateId() == PotionItems.potionIdExcell
                                    || target.getTemplateId() == PotionItems.potionIdGoat
                                    || target.getTemplateId() == PotionItems.potionIdFranticCharge
                                    || target.getTemplateId() == PotionItems.potionIdMorningFog
                                    || target.getTemplateId() == PotionItems.potionIdSixthSense
                                    || target.getTemplateId() == PotionItems.potionIdTruehit
                                    || target.getTemplateId() == PotionItems.potionIdWillowspine
                                    || target.getTemplateId() == PotionItems.potionIdOakshell
                                    || target.getTemplateId() == PotionItems.potionIdVynora
                                    || target.getTemplateId() == PotionItems.potionIdStrength)) {
                        performer.getCommunicator()
                                .sendAlertServerMessage("You stop your self from drinking that potion. "
                                        + "You are still under the effect of a potion and should wait another "
                                        + ((playerInQuestion.coolDownBuffEnd - time) / 1000)
                                        + " seconds. Your body could collapse under "
                                        + "the toxins otherwise if you tried to drink again!");
                        playerInQuestion.toxicityWarningLevel = 1;
                        AddictionHandler.addictions.set(index, playerInQuestion);
                        return propagate(action, ActionPropagation.FINISH_ACTION,
                                ActionPropagation.NO_SERVER_PROPAGATION,
                                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
                    }
                }

            }

            // EFFECT STUFF GOES HERE
            ////////// BUFFS/////////////

            seconds = (int) (Config.potionDuration * (target.getCurrentQualityLevel() / 100));
            power = target.getCurrentQualityLevel() * Config.alchemyPower;
            if (target.getRarity() == 1) {
                power = power * Config.rarityFactorRare;
                seconds = (int) (seconds * Config.rarityFactorRare);
            }

            if (target.getRarity() == 2) {
                power = power * Config.rarityFactorSupreme;
                seconds = (int) (seconds * Config.rarityFactorSupreme);
            }

            if (target.getRarity() == 3) {
                power = power * Config.rarityFactorFantastic;
                seconds = (int) (seconds * Config.rarityFactorFantastic);
            }

            if (target.getTemplateId() == PotionItems.potionIdExcell) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 28);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 28, power, Math.max(1, seconds));

                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }

                performer.getCommunicator().sendAlertServerMessage("You feel the power of the Potion flow through you! "
                        + "You feel your skin becoming slick and silky!");

            }

            if (target.getTemplateId() == PotionItems.potionIdMorningFog) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 19);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 19, power, Math.max(1, seconds));

                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }

                performer.getCommunicator()
                        .sendAlertServerMessage("You feel the power of the Potion flow through you! "
                                + "You feel your skin sizzling as if your body wants to turn into a cloud"
                                + "Thorns can not pierce you!");

            }
            if (target.getTemplateId() == PotionItems.potionIdFranticCharge) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 39);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 39, power, Math.max(1, seconds));

                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }
                performer.getCommunicator()
                        .sendAlertServerMessage("You feel the power of the Potion flow through you! "
                                + "You feel your skin burning with the fire of hate, the beast in you is taking over!"
                                + "You fall into a frenzy moving lightning fast!");
            }

            if (target.getTemplateId() == PotionItems.potionIdGoat) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 30);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 38, power, Math.max(1, seconds));
                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }
                performer.getCommunicator()
                        .sendAlertServerMessage("You feel the power of the potion rushing through your body! "
                                + "You feel the joyful pride of a goat, weird!");

            }
            if (target.getTemplateId() == PotionItems.potionIdSixthSense) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 21);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 21, power, Math.max(1, seconds));

                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }
                performer.getCommunicator()
                        .sendAlertServerMessage("You feel the power of the Potion flow through you! "
                                + "You feel your inner eye getting a better picture of the world."
                                + "You have superior situational awareness!");
            }

            if (target.getTemplateId() == PotionItems.potionIdStrength) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 40);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 40, power, Math.max(1, seconds));

                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }

                performer.getCommunicator()
                        .sendAlertServerMessage("You feel the power of the Potion flow through you! "
                                + "You feel blood boiling and a demonic strength is raging through you!"
                                + "You got superior strength!");
            }

            if (target.getTemplateId() == PotionItems.potionIdTruehit) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 30);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 30, power, Math.max(1, seconds));
                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }

                performer.getCommunicator().sendAlertServerMessage("You feel the power of the Potion flow through you! "
                        + "You feel like you can hit your enemies better!");
            }

            if (target.getTemplateId() == PotionItems.potionIdVynora) {

                if (performer.getFatigueLeft() < 100) {
                    performer.getCommunicator().sendNormalServerMessage(
                            " You are too tired mentally, to store your brains capacity as sleep bonus.");

                } else {

                    double toconvert;
                    toconvert = Math.max(20.0D, power);
                    toconvert = Math.min(99.0D, toconvert);
                    toconvert /= 100.0D;
                    int numsecondsToMove = Math.min((int) ((performer.getFatigueLeft() / 10.0F) * toconvert), 3600);
                    performer.setFatigue(-numsecondsToMove);
                    numsecondsToMove = (int) (numsecondsToMove * 0.2F);
                    numsecondsToMove *= 2;
                    if (performer.isPlayer()) {
                        ((Player) performer).getSaveFile().addToSleep(numsecondsToMove);
                    }
                }
                Items.destroyItem(target.getWurmId());
                performer.getCommunicator().sendAlertServerMessage("You feel the power of the Potion flow through you! "
                        + "You feel enlightened. Vynora is proud of you. You have found the final boundaries of knowledge"
                        + " and surpassed them! After having flow so much knowledge through you, you feel more exhausted than usual."
                        + "You should wait quite a while before drinking the next potion, to make sure your body does not"
                        + "succumb to toxicity.");

            }

            if (target.getTemplateId() == PotionItems.potionIdWillowspine) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 23);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 23, power, Math.max(1, seconds));

                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }
                performer.getCommunicator()
                        .sendAlertServerMessage("You feel the power of the Potion flow through you! "
                                + "You feel your whole body shifting phases, you are only partly in this reality now. "
                                + "You will have an easier time to dodge hits");
            }

            if (target.getTemplateId() == PotionItems.potionIdOakshell) {

                SpellEffects effs = performer.getSpellEffects();

                if (effs == null) {
                    effs = performer.createSpellEffects();
                }

                SpellEffect eff = effs.getSpellEffect((byte) 22);

                if (eff == null) {
                    eff = new SpellEffect(performer.getWurmId(), (byte) 22, power, Math.max(1, seconds));

                    effs.addSpellEffect(eff);
                } else if (eff.getPower() < power || eff.timeleft < seconds) {
                    eff.setPower(power);
                    eff.setTimeleft(Math.max(eff.timeleft, Math.max(1, seconds)));
                    performer.sendUpdateSpellEffect(eff);
                }
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }
                performer.getCommunicator()
                        .sendAlertServerMessage("You feel the power of the Potion flow through you! "
                                + "You feel your skin drying up and its starts to look like wood!"
                                + "You will take less damage overall!");
            }

            /////////////////////// HEALING//////////////////////

            if (target.getTemplateId() == PotionItems.potionIdHeal) {

                double healingPool = ((Math.max(5.0D, power)) / 100.0D) * 65535.0D * 1.0D;
                Wounds tWounds = performer.getBody().getWounds();
                if (tWounds == null) {
                    performer.getCommunicator()
                            .sendNormalServerMessage("You have no wounds to heal and wasted your potion!");
                    if (target.getWeightGrams() - 80 > 0) {
                        target.setWeight(target.getWeightGrams() - 80, true);
                    } else if (target.getWeightGrams() - 80 > 0) {
                        target.setWeight(target.getWeightGrams() - 80, true);
                    } else {
                        Items.destroyItem(target.getWurmId());
                    }

                } else
                    for (Wound w : tWounds.getWounds()) {
                        if (w.getSeverity() <= healingPool) {
                            healingPool -= w.getSeverity();
                            w.heal();
                            performer.getCommunicator()
                                    .sendNormalServerMessage("You heal one of your " + w.getName() + "Wounds!");
                        }
                        if (w.getSeverity() > healingPool) {
                            w.modifySeverity((int) -healingPool);
                            performer.getCommunicator().sendNormalServerMessage("You heal only a part your "
                                    + w.getName() + "wounds as the Power of the Potion is used up");
                        }
                    }
                heal = true;
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }
            }
            if (target.getTemplateId() == PotionItems.potionIdRefresh) {

                double healingPool = ((Math.max(5.0D, power)) / 100.0D) * 65535.0D * 1.0D;
                performer.getStatus().modifyStamina((float) healingPool);

                performer.getCommunicator().sendAlertServerMessage(
                        "You feel the power of the Potion flow through you! " + "You feel refreshed!");
                heal = true;
                if (target.getWeightGrams() - 80 > 0) {
                    target.setWeight(target.getWeightGrams() - 80, true);
                } else {
                    Items.destroyItem(target.getWurmId());
                }

            }
            if (target.getTemplateId() == PotionItems.potionKarmaId) {

                double karmaPool = (Math.max(0.01, (power / 100)) * 1000);
                performer.setKarma(performer.getKarma() + (int) karmaPool);

                performer.getCommunicator().sendAlertServerMessage(
                        "You feel the power of the Potion flow through you! " + "You feel your inner power growing!");
                heal = true;

                Items.destroyItem(target.getWurmId());

            }

            if (target.getTemplateId() == PotionItems.potionManaId) {

                int manaPool = (Math.max(5, (int) power));
                try {
                    performer.setFavor(performer.getFavor() + manaPool);
                } catch (IOException e) {
                    Alchemy.logger.log(Level.WARNING,"IoException",e);

                }

                performer.getCommunicator().sendAlertServerMessage("You feel the power of the Potion flow through you! "
                        + "You feel your religious power growing!");
                heal = true;

                Items.destroyItem(target.getWurmId());

            }

            ///////////////// ADDDICTION/////////////////////

            if (Config.becomeAddicted) {

                // Go over all addicted Players, maybe the one consuming a potion right now is
                // already addicted
                Iterator<Addiction> handleAddictionLevel = AddictionHandler.addictions.iterator();

                while (handleAddictionLevel.hasNext()) {
                    playerInQuestion = handleAddictionLevel.next();
                    int index = AddictionHandler.addictions.indexOf(playerInQuestion);
                    if (performer.getWurmId() == playerInQuestion.playerId) {

                        // If we find the player is already Addicted we raise his addiction level by 1
                        // and set the previous level to whatever he had until now
                        // Also update his DB entry.
                        playerFound = true;
                        playerInQuestion.previousAddictionLevel = playerInQuestion.currentAddictionLevel;
                        playerInQuestion.currentAddictionLevel += 1;

                        // add the cooldown seconds. Add more if it was an ultimate potion Also if
                        // chugging potion was succesful, reset toxicity level.
                        if (heal) {
                            playerInQuestion.coolDownHealEnd = time + (Config.cooldownPotion * 1000L);
                        } else {
                            if (target.getTemplateId() == PotionItems.potionIdVynora) {
                                playerInQuestion.coolDownBuffEnd = time + (Config.cooldownPotion * 4000L);
                            } else
                                playerInQuestion.coolDownBuffEnd = time + (Config.cooldownPotion * 1000L);
                        }
                        playerInQuestion.toxicityWarningLevel = 0;

                        // Update the player in the DB
                        try {
                            playerInQuestion.insert(Alchemy.dbconn);
                        } catch (SQLException e) {
                            Alchemy.logger.log(Level.WARNING,"SQLEXception",e);;
                        }
                        AddictionHandler.addictions.set(index, playerInQuestion);
                    }
                    if (playerFound)
                        break;
                }

                // If you can't find a player with this id that is already addicted make a new
                // one! and add it to the DB
                if (!playerFound) {
                    playerAddiction.currentAddictionLevel = 1;
                    playerAddiction.previousAddictionLevel = 0;
                    if (heal) {
                        playerAddiction.coolDownHealEnd = time + (Config.cooldownPotion * 1000L);
                    } else {
                        playerAddiction.coolDownBuffEnd = time + (Config.cooldownPotion * 1000L);
                    }

                    playerAddiction.playerId = performer.getWurmId();
                    playerAddiction.toxicityWarningLevel = 0;

                    // Insert the player into the DB
                    try {
                        playerAddiction.insert(Alchemy.dbconn);
                    } catch (SQLException e) {

                        Alchemy.logger.log(Level.SEVERE, "couldn't write addiction to DB!");
                    }
                    AddictionHandler.addictions.add(playerAddiction);
                }
                Item[] items = performer.getInventory().getItemsAsArray();

                // Since Player fuels their addiction remove the weak legs

                for (Item item : items) {
                    if (item.getTemplateId() == AlchItems.weakLegsId) {
                        Items.destroyItem(item.getWurmId());
                    }
                }
            }

            performer.getCommunicator().sendAlertServerMessage(
                    "You feel your body is coming a bit more addicted to the magic power of the substances. ");

            if (Config.verboseLogging)
                Alchemy.logger.log(Level.INFO,
                        String.format("%s Drank a potion! :%s", performer.getName(), target.getName()));
            return propagate(action, ActionPropagation.FINISH_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                    ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
        }
        return propagate(action, ActionPropagation.CONTINUE_ACTION, ActionPropagation.NO_SERVER_PROPAGATION,
                ActionPropagation.NO_ACTION_PERFORMER_PROPAGATION);
    }
}
