package com.yahoo.legos2find2.magic;

import org.bukkit.plugin.java.JavaPlugin;
 
public final class Magic extends JavaPlugin{
 
	@Override
	public void onEnable(){
		getLogger().info("Magic has been enabled!");
		//Sets up config.yml file if one has not already been set
		saveDefaultConfig();
        //Registers our listening events
		getServer().getPluginManager().registerEvents(new WizardryListener(getConfig()), this);
		getServer().getPluginManager().registerEvents(new NatureListener(getConfig()), this);
	}
 
	@Override
	public void onDisable(){
		getLogger().info("Magic has been disabled!");
	}
	
	public void loadConfiguration(){
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
}
