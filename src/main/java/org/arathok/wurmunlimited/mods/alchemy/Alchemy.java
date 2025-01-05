package org.arathok.wurmunlimited.mods.alchemy;

<<<<<<< Updated upstream:src/org/arathok/wurmunlimited/mods/alchemy/Alchemy.java
package org.arathok.wurmunlimited.mods.alchemy; // HELLO GITHUB!

import org.arathok.wurmunlimited.mods.alchemy.potions.PotionItems;
import com.wurmonline.server.FailedException;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import org.arathok.wurmunlimited.mods.alchemy.addiction.Addiction;
import org.arathok.wurmunlimited.mods.alchemy.addiction.AddictionHandler;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.Enchantment;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.EnchantmentHandler;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.ServerTravelHook;
import org.arathok.wurmunlimited.mods.alchemy.essences.EssencesBehaviourItem;
import org.arathok.wurmunlimited.mods.alchemy.essences.EssencesBehaviourTile;
import org.arathok.wurmunlimited.mods.alchemy.oils.OilBehaviour;
import org.arathok.wurmunlimited.mods.alchemy.oils.OilItems;
import org.arathok.wurmunlimited.mods.alchemy.potions.PotionBehaviour;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Alchemy implements WurmServerMod, Initable, PreInitable, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener, PlayerMessageListener,PlayerLoginListener{
	public static final Logger logger = Logger.getLogger("Alchemy");
	public long timer=0;
	public static Connection dbconn;
	public static boolean finishedDbReadingAddictions = false;
	public static boolean finishedDbReadingEnchantments =false;





	@Override
	public void configure (Properties properties) {
		Config.useOils = Boolean.parseBoolean(properties.getProperty("useOils", "true"));
		Config.alchemyPower = Float.parseFloat(properties.getProperty("alchemyPower", "1.0F"));
		Config.becomeAddicted = Boolean.parseBoolean(properties.getProperty("becomeAddicted", "true"));
		Config.potionDuration = Integer.parseInt(properties.getProperty("potionDuration", "300"));
		Config.oilDuration = Integer.parseInt(properties.getProperty("oilDuration", "300"));
		Config.cooldownPotion = Integer.parseInt(properties.getProperty("cooldownPotion", "300"));
		Config.cooldownHeal = Integer.parseInt(properties.getProperty("cooldownHeal", "300"));

		Config.purifiedWaterCooking = Boolean.parseBoolean(properties.getProperty("purifiedWaterCooking", "true"));
		Config.addictionTimer = Integer.parseInt(properties.getProperty("addictionTimer", "900"));
		Config.enchantmentsStack = Boolean.parseBoolean(properties.getProperty("enchantmentsStack", "true"));
		Config.oilDurationOnEnchanted = Float.parseFloat(properties.getProperty("oilDurationOnEnchanted", "1.0F"));
		Config.skillUsed = Integer.parseInt(properties.getProperty("skillUsed", "10042"));
		Config.baseDifficulty = Float.parseFloat(properties.getProperty("baseDifficulty", "0.5F"));
		Config.verboseLogging = Boolean.parseBoolean(properties.getProperty("verboseLogging","false"));
		Config.dbtest = Boolean.parseBoolean(properties.getProperty("dbTest","false"));
		Config.potionsDuringFighting = Boolean.parseBoolean(properties.getProperty("potionsDuringFighting","false"));
		Config.oilsDuringFighting = Boolean.parseBoolean(properties.getProperty("oilsDuringFighting","false"));
		Config.rarityFactorRare = Float.parseFloat(properties.getProperty("rarityFactorRare", "1.05F"));
		Config.rarityFactorSupreme = Float.parseFloat(properties.getProperty("rarityFactorSupreme", "1.1F"));
		Config.rarityFactorRare = Float.parseFloat(properties.getProperty("rarityFactorFantastic", "1.25F"));
	//	Config.worldMaxX = Integer.parseInt(properties.getProperty("worldMaxX", "4096"));
	//	Config.worldMaxY = Integer.parseInt(properties.getProperty("worldMaxY", "4096"));

	}


	@Override
	public void preInit() {
	/*	try {
			ClassPool classPool = HookManager.getInstance().getClassPool();

			CtClass ctItem = classPool.getCtClass("com.wurmonline.server.items.Item");
			ctItem.getMethod("getName", "(Z)Ljava/lang/String;").insertAfter(
					"return path.to.MyHooks.getNameHook(this, $_);"
			);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}*/
		 ModActions.init();

		
	
	}

	@Override
	public boolean onPlayerMessage(Communicator communicator, String message) {
		if (message != null&&message.startsWith("#AlchemyVersion"))
		{

			communicator.sendSafeServerMessage("You are on Alchemy Version 1.8 ");

		}
		return false;
	}

	@Override
	public void onItemTemplatesCreated() {
		
		
	            try{

					AlchItems.register();
					PotionItems.register();
					OilItems.register();
					//EssencesItems.register(); NO SOUP FOR YOU, XYPE!

					logger.log(Level.INFO, "Alchemy is Done loading its Item Templates! Thank you Coldie!");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.log(Level.WARNING,"io Exception",e);


				}
	    
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerStarted() {
		// TODO Auto-generated method stub


		//new EnchantmentHandler();
		//new AddictionHandler();

		logger.log(Level.INFO, "Alchemy is registering Actions");
		ModActions.registerBehaviourProvider(new PotionBehaviour());
		ModActions.registerBehaviourProvider(new OilBehaviour());
		ModActions.registerBehaviourProvider(new EssencesBehaviourItem());
		ModActions.registerBehaviourProvider(new EssencesBehaviourTile());

		logger.log(Level.INFO, "Alchemy is done registering its Actions! Thank you Bdew!");
		logger.log(Level.INFO, "Alchemy mod and has finished being loaded to your server! ");
		logger.log(Level.INFO, "Alchemy Version: 1.0");
	}


		@Override
	public void init() {
			logger.log(Level.INFO, "Hooking ServerTravel Oils");
			ServerTravelHook.insert();
		
	}

	@Override
	public void onServerPoll() {
		try
		{
			dbconn = ModSupportDb.getModSupportDb();

			// check if the ModSupportDb table exists
			// if not, create the table and update it with the server's last crop poll time
			if (!ModSupportDb.hasTable(dbconn, "Alchemy_OiledWeapons")&&!finishedDbReadingEnchantments&&!finishedDbReadingAddictions) {
				// table create
				try (PreparedStatement ps = dbconn.prepareStatement("CREATE TABLE Alchemy_OiledWeapons (itemId LONG PRIMARY KEY NOT NULL DEFAULT 0, playerId LONG NOT NULL " +
						"DEFAULT 0 " +
						", timeOfEnchantment LONG NOT NULL DEFAULT 0, enchantmentType BYTE NOT NULL DEFAULT 0, hasOil BOOLEAN NOT NULL DEFAULT false, itemNameBeforeEnchantment STRING NOT NULL DEFAULT 0)")) {
					ps.execute();



				}

				try (PreparedStatement ps = dbconn.prepareStatement("CREATE TABLE Alchemy_Addictions (playerId LONG PRIMARY KEY NOT NULL DEFAULT 0, currentAddiction INTEGER NOT NULL " +
						"DEFAULT 0, previousAddiction INT NOT NULL DEFAULT 0, cooldownHealEnd INT NOT NULL DEFAULT 0, " +
						"cooldownBuffEnd INT NOT NULL DEFAULT 0)")) {
					ps.execute();



				}

				catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}



			if (!finishedDbReadingEnchantments)
			{
				logger.log(Level.INFO, "Alchemy is pulling DB entries");
				new EnchantmentHandler();
				Enchantment.readFromSQL(dbconn);

			}


			if(!finishedDbReadingAddictions) {

				Addiction.readFromSQL(dbconn);
				logger.log(Level.INFO, "Alchemy is done pulling DB entries");
			}

			if (finishedDbReadingAddictions)
			AddictionHandler.AddictionEffects();

			if(finishedDbReadingEnchantments)
			EnchantmentHandler.RemoveEnchantment();

		} catch (SQLException e) {
			Alchemy.logger.log(Level.INFO,"Database was closed or could not be written to!",e);

		} catch (NoSuchItemException e) {
			Alchemy.logger.log(Level.INFO,"No item found for that id! ",e);

		}


	}


	@Override
	public void onPlayerLogin(Player player) {
		if (Config.dbtest)
		try {
			player.getInventory().insertItem(ItemFactory.createItem(PotionItems.potionIdHeal,90,null));
			player.addWoundOfType((Creature) null, (byte) 9,14,false,1.0F,false,4000D,0F,0F,false,false);
		} catch (FailedException | NoSuchTemplateException e) {
			logger.log(Level.WARNING,"FailedException",e);
			throw new RuntimeException(e);
		}

    }
}
=======
package org.arathok.wurmunlimited.mods.alchemy; // HELLO GITHUB!

import com.wurmonline.server.FailedException;
import com.wurmonline.server.NoSuchItemException;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.ItemFactory;
import com.wurmonline.server.items.NoSuchTemplateException;
import com.wurmonline.server.players.Player;
import org.arathok.wurmunlimited.mods.alchemy.addiction.Addiction;
import org.arathok.wurmunlimited.mods.alchemy.addiction.AddictionHandler;
import org.arathok.wurmunlimited.mods.alchemy.cauldron.CauldronBehaviour;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.Enchantment;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.EnchantmentHandler;
import org.arathok.wurmunlimited.mods.alchemy.enchantments.ServerTravelHook;
import org.arathok.wurmunlimited.mods.alchemy.essences.EssencesBehaviourItem;
import org.arathok.wurmunlimited.mods.alchemy.essences.EssencesBehaviourTile;
import org.arathok.wurmunlimited.mods.alchemy.oils.OilBehaviour;
import org.arathok.wurmunlimited.mods.alchemy.oils.OilItems;
import org.arathok.wurmunlimited.mods.alchemy.potions.PotionBehaviour;
import org.arathok.wurmunlimited.mods.alchemy.potions.PotionItems;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.ModSupportDb;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Alchemy implements WurmServerMod, Initable, PreInitable, Configurable, ItemTemplatesCreatedListener, ServerStartedListener, ServerPollListener, PlayerMessageListener,PlayerLoginListener{
	public static final Logger logger = Logger.getLogger("Alchemy");
	public long timer=0;
	public static Connection dbconn;
	public static boolean finishedDbReadingAddictions = false;
	public static boolean finishedDbReadingEnchantments =false;





	@Override
	public void configure (Properties properties) {
		Config.useOils = Boolean.parseBoolean(properties.getProperty("useOils", "true"));
		Config.alchemyPower = Float.parseFloat(properties.getProperty("alchemyPower", "1.0F"));
		Config.becomeAddicted = Boolean.parseBoolean(properties.getProperty("becomeAddicted", "true"));
		Config.potionDuration = Integer.parseInt(properties.getProperty("potionDuration", "300"));
		Config.oilDuration = Integer.parseInt(properties.getProperty("oilDuration", "300"));
		Config.cooldownPotion = Integer.parseInt(properties.getProperty("cooldownPotion", "300"));
		Config.cooldownHeal = Integer.parseInt(properties.getProperty("cooldownHeal", "300"));

		Config.purifiedWaterCooking = Boolean.parseBoolean(properties.getProperty("purifiedWaterCooking", "true"));
		Config.addictionTimer = Integer.parseInt(properties.getProperty("addictionTimer", "900"));
		Config.enchantmentsStack = Boolean.parseBoolean(properties.getProperty("enchantmentsStack", "true"));
		Config.oilDurationOnEnchanted = Float.parseFloat(properties.getProperty("oilDurationOnEnchanted", "1.0F"));
		Config.skillUsed = Integer.parseInt(properties.getProperty("skillUsed", "10042"));
		Config.baseDifficulty = Float.parseFloat(properties.getProperty("baseDifficulty", "0.5F"));
		Config.verboseLogging = Boolean.parseBoolean(properties.getProperty("verboseLogging","false"));
		Config.dbtest = Boolean.parseBoolean(properties.getProperty("dbTest","false"));
		Config.potionsDuringFighting = Boolean.parseBoolean(properties.getProperty("potionsDuringFighting","false"));
		Config.oilsDuringFighting = Boolean.parseBoolean(properties.getProperty("oilsDuringFighting","false"));
		Config.rarityFactorRare = Float.parseFloat(properties.getProperty("rarityFactorRare", "1.05F"));
		Config.rarityFactorSupreme = Float.parseFloat(properties.getProperty("rarityFactorSupreme", "1.1F"));
		Config.rarityFactorRare = Float.parseFloat(properties.getProperty("rarityFactorFantastic", "1.25F"));
	//	Config.worldMaxX = Integer.parseInt(properties.getProperty("worldMaxX", "4096"));
	//	Config.worldMaxY = Integer.parseInt(properties.getProperty("worldMaxY", "4096"));

	}


	@Override
	public void preInit() {
	/*	try {
			ClassPool classPool = HookManager.getInstance().getClassPool();

			CtClass ctItem = classPool.getCtClass("com.wurmonline.server.items.Item");
			ctItem.getMethod("getName", "(Z)Ljava/lang/String;").insertAfter(
					"return path.to.MyHooks.getNameHook(this, $_);"
			);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}*/
		 ModActions.init();
		 Player test;


		
	
	}

	@Override
	public boolean onPlayerMessage(Communicator communicator, String message) {
		if (message != null&&message.startsWith("#AlchemyVersion"))
		{

			communicator.sendSafeServerMessage("You are on Alchemy Version 1.8 ");

		}
		return false;
	}

	@Override
	public void onItemTemplatesCreated() {
		
		
	            try{

					AlchItems.register();
					PotionItems.register();
					OilItems.register();
					//EssencesItems.register(); NO SOUP FOR YOU, XYPE!

					logger.log(Level.INFO, "Alchemy is Done loading its Item Templates! Thank you Coldie!");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.log(Level.WARNING,"io Exception",e);


				}
	    
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerStarted() {
		// TODO Auto-generated method stub


		//new EnchantmentHandler();
		//new AddictionHandler();

		logger.log(Level.INFO, "Alchemy is registering Actions");
		ModActions.registerBehaviourProvider(new PotionBehaviour());
		ModActions.registerBehaviourProvider(new OilBehaviour());
		ModActions.registerBehaviourProvider(new EssencesBehaviourItem());
		ModActions.registerBehaviourProvider(new EssencesBehaviourTile());
		ModActions.registerBehaviourProvider(new CauldronBehaviour());

		logger.log(Level.INFO, "Alchemy is done registering its Actions! Thank you Bdew!");
		logger.log(Level.INFO, "Alchemy mod and has finished being loaded to your server! ");
		logger.log(Level.INFO, "Alchemy Version: 1.0");
	}


		@Override
	public void init() {
			logger.log(Level.INFO, "Hooking ServerTravel Oils");
			ServerTravelHook.insert();
		
	}

	@Override
	public void onServerPoll() {
		try
		{
			dbconn = ModSupportDb.getModSupportDb();

			// check if the ModSupportDb table exists
			// if not, create the table and update it with the server's last crop poll time
			if (!ModSupportDb.hasTable(dbconn, "Alchemy_OiledWeapons")&&!finishedDbReadingEnchantments&&!finishedDbReadingAddictions) {
				// table create
				try (PreparedStatement ps = dbconn.prepareStatement("CREATE TABLE Alchemy_OiledWeapons (itemId LONG PRIMARY KEY NOT NULL DEFAULT 0, playerId LONG NOT NULL " +
						"DEFAULT 0 " +
						", timeOfEnchantment LONG NOT NULL DEFAULT 0, enchantmentType BYTE NOT NULL DEFAULT 0, hasOil BOOLEAN NOT NULL DEFAULT false, itemNameBeforeEnchantment STRING NOT NULL DEFAULT 0)")) {
					ps.execute();



				}

				try (PreparedStatement ps = dbconn.prepareStatement("CREATE TABLE Alchemy_Addictions (playerId LONG PRIMARY KEY NOT NULL DEFAULT 0, currentAddiction INTEGER NOT NULL " +
						"DEFAULT 0, previousAddiction INT NOT NULL DEFAULT 0, cooldownHealEnd INT NOT NULL DEFAULT 0, " +
						"cooldownBuffEnd INT NOT NULL DEFAULT 0)")) {
					ps.execute();



				}

				catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}



			if (!finishedDbReadingEnchantments)
			{
				logger.log(Level.INFO, "Alchemy is pulling DB entries");
				new EnchantmentHandler();
				Enchantment.readFromSQL(dbconn);

			}


			if(!finishedDbReadingAddictions) {

				Addiction.readFromSQL(dbconn);
				logger.log(Level.INFO, "Alchemy is done pulling DB entries");
			}

			if (finishedDbReadingAddictions)
			AddictionHandler.AddictionEffects();

			if(finishedDbReadingEnchantments)
			EnchantmentHandler.RemoveEnchantment();

		} catch (SQLException e) {
			Alchemy.logger.log(Level.INFO,"Database was closed or could not be written to!",e);

		} catch (NoSuchItemException e) {
			Alchemy.logger.log(Level.INFO,"No item found for that id! ",e);

		}


	}


	@Override
	public void onPlayerLogin(Player player) {
		if (Config.dbtest)
		try {
			player.getInventory().insertItem(ItemFactory.createItem(PotionItems.potionIdHeal,90,null));
			player.addWoundOfType((Creature) null, (byte) 9,14,false,1.0F,false,4000D,0F,0F,false,false);
		} catch (FailedException | NoSuchTemplateException e) {
			logger.log(Level.WARNING,"FailedException",e);
			throw new RuntimeException(e);
		}

    }
}
>>>>>>> Stashed changes:src/main/java/org/arathok/wurmunlimited/mods/alchemy/Alchemy.java
