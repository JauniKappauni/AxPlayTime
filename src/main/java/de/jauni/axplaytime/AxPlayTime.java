package de.jauni.axplaytime;

import de.jauni.axplaytime.command.PlayTimeCommand;
import de.jauni.axplaytime.listener.PlayerJoinListener;
import de.jauni.axplaytime.listener.PlayerQuitListener;
import de.jauni.axplaytime.manager.DatabaseManager;
import de.jauni.axplaytime.manager.PlayTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class AxPlayTime extends JavaPlugin {
    DatabaseManager databaseManager;
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }
    PlayTimeManager playTimeManager;
    public PlayTimeManager getPlayTimeManager(){
        return playTimeManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        try {
            playTimeManager = new PlayTimeManager(this);
            databaseManager = new DatabaseManager(this);
            if(databaseManager.initDatabaseTable1() == false){
                Bukkit.getLogger().severe("Failed to create the players table!");
                Bukkit.getServer().shutdown();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getCommand("playtime").setExecutor(new PlayTimeCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
