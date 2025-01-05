
package org.arathok.wurmunlimited.mods.alchemy.addiction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.arathok.wurmunlimited.mods.alchemy.Alchemy;

public class Addiction {

    public long playerId;
    public int currentAddictionLevel = 0;
    public int previousAddictionLevel = 0;
    public long coolDownHealEnd = 0;
    public long coolDownBuffEnd = 0;
    public int toxicityWarningLevel = 0;

    public static void readFromSQL(Connection dbconn) throws SQLException {
        AddictionHandler.addictions.clear();
        PreparedStatement ps = dbconn.prepareStatement("SELECT * FROM Alchemy_Addictions");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Addiction addiction = new Addiction();

            addiction.playerId = rs.getLong("playerId"); // liest quasi den Wert von der Spalte
            addiction.currentAddictionLevel = rs.getInt("currentAddiction");
            addiction.previousAddictionLevel = rs.getInt("previousAddiction");
            addiction.coolDownBuffEnd = rs.getLong("cooldownBuffEnd");
            addiction.coolDownHealEnd = rs.getLong("cooldownHealEnd");
            addiction.toxicityWarningLevel = 0;
            AddictionHandler.addictions.add(addiction);

        }
        Alchemy.finishedDbReadingAddictions = true;
    }

    public void insert(Connection dbconn) throws SQLException {

        PreparedStatement ps = dbconn.prepareStatement(
                "insert or replace into Alchemy_Addictions (playerId,currentAddiction,previousAddiction,cooldownHealEnd,cooldownBuffEnd) values "
                        + "(?,?,?,?,?)");
        ps.setLong(1, this.playerId);
        ps.setInt(2, this.currentAddictionLevel);
        ps.setInt(3, this.previousAddictionLevel);
        ps.setLong(4, this.coolDownHealEnd);
        ps.setLong(5, this.coolDownBuffEnd);

        ps.executeUpdate();

    }

    public static void remove(Connection dbconn, long aplayerId) {
        try {
            PreparedStatement ps = dbconn.prepareStatement("DELETE FROM Alchemy_Addictions WHERE playerId = ?");
            ps.setLong(1, aplayerId);
            ps.execute();
            ps.close();
        }
        catch (SQLException throwables) {
            Alchemy.logger.log(Level.SEVERE, "something went wrong deleting an Addiction from the DB!", throwables);

        }
    }
}