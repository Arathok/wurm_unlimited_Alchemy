package org.arathok.wurmunlimited.mods.alchemy.enchantments;


import org.arathok.wurmunlimited.mods.alchemy.Alchemy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class Enchantment {



    public long itemId=0L;
    public long timeOfEnchantment=0;
    public byte enchantmentType=0;
    public boolean hasOil=false;
    public long playerId = 0;
    public String itemNameBeforeEnchantment="";


 public static void readFromSQL(Connection dbconn) throws SQLException {

        PreparedStatement ps = dbconn.prepareStatement("SELECT * FROM Alchemy_OiledWeapons");
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {


            Enchantment e=new Enchantment();
            e.itemId = rs.getLong("itemId"); // liest quasi den Wert von der Spalte
            e.timeOfEnchantment = rs.getLong("timeOfEnchantment");
            e.enchantmentType = rs.getByte("enchantmentType");
            e.hasOil = rs.getBoolean("hasOil");
            e.playerId = rs.getLong("playerId");
            e.itemNameBeforeEnchantment=rs.getString("itemNameBeforeEnchantment");
            EnchantmentHandler.enchantments.add(e);

        }
        Alchemy.finishedDbReadingEnchantments = true;
    }

    public void insert(Connection dbconn) throws SQLException {

        PreparedStatement ps = dbconn.prepareStatement("insert into Alchemy_OiledWeapons (itemID,playerId,timeOfEnchantment,enchantmentType,hasOil,itemNameBeforeEnchantment) " +
                                                               "values " +
                                                               "(?,?,?,?,?,?)");
        ps.setLong(1,this.itemId);
        ps.setLong(2, this.playerId);
        ps.setLong(3, this.timeOfEnchantment);
        ps.setByte(4, this.enchantmentType);
        ps.setBoolean(5,this.hasOil);
        ps.setString(6,this.itemNameBeforeEnchantment);

        ps.executeUpdate();



    }


    public static void remove(Connection dbconn, long aItemId) {
        try {
            PreparedStatement ps = dbconn.prepareStatement("DELETE FROM Alchemy_OiledWeapons WHERE itemID = ?");
            ps.setLong(1, aItemId);
            ps.execute();
            ps.close();
        } catch (SQLException throwables) {
            Alchemy.logger.log(Level.SEVERE, "something went wrong deleting an enchanted Item from the DB!", throwables);

        }
    }






}