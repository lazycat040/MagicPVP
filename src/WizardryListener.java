package com.yahoo.legos2find2.magic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class WizardryListener implements Listener{
	
	FileConfiguration getConfig;
	
	//Params the config.yml file for access
	
	public WizardryListener(FileConfiguration getConfig){
		this.getConfig = getConfig;
	}
	
	@EventHandler
	public void onInteractWithBlock(PlayerInteractEvent event){
		Player player = event.getPlayer();
		PlayerInventory inventory = event.getPlayer().getInventory();
		
		//Sees if the event was caused by a left click onblock or onair
		if(event.getAction() == Action.LEFT_CLICK_AIR||event.getAction() == Action.LEFT_CLICK_BLOCK){
			
			//Strike Lightning Spell
			//Sees if the spell is enabled
			if(inventory.getItemInHand().getTypeId() == Material.GOLD_NUGGET.getId()){
				//Sets the specified value for the amount of gold nuggets to be used
				int gnAmount = getConfig.getInt("wizardry.summonLightning.amountGoldNugget");
				//Sees if the player has the gold_nugget item in their hand
				if(getConfig.getBoolean("wizardry.summonLightning.enabled") == true){
					//Sees if the player has specified amount or more gold nuggets in their inventory
					if(inventory.getItemInHand().getAmount() >= gnAmount){
						//Sees if the player has sufficient privileges to cast the spell
						if(player.hasPermission("magic.wizardry.lightning")){
						player.getWorld().strikeLightning(player.getTargetBlock(null, 200).getLocation());
						//Removes the item completely to ensure that no errors will occur via setting gold_nugget value to 0
							if(inventory.getItemInHand().getAmount() == gnAmount){
								ItemStack minGold = new ItemStack(Material.GOLD_NUGGET.getId(), gnAmount);
								inventory.remove(minGold);
							} else {
								//Removes gnAmount gold nuggets from the players inventory
								inventory.getItemInHand().setAmount(inventory.getItemInHand().getAmount() - gnAmount);
							}
							player.sendMessage("Summoned lightning!");
						} else {
							player.sendMessage("You do not have the power to summon lightning!");
						}
					} else {
						player.sendMessage("You must have at least " + gnAmount + " gold nuggets to perform this wizardry!");
					}
				} else {
					player.sendMessage("I'm sorry but that spell is not enabled on this server :(");
				}
			}
			
			//Cast Fire Spell
			//Sees if the spell is enabled
			if(inventory.getItemInHand().getTypeId() == Material.BLAZE_POWDER.getId()){
				//Sets the value for the amount of blaze powder used
				int bpAmount = getConfig.getInt("wizardry.summonFire.amountBlazePowder");
				//Sees if the player has the blaze_powder item in their hand
				if(getConfig.getBoolean("wizardry.summonFire.enabled") == true){
					//Sees if the player has set amount or more blaze powder selected
					if(inventory.getItemInHand().getAmount() >= bpAmount){
						//Sees if the player has sufficient privileges to cast the spell
						if(player.hasPermission("magic.wizardry.fire")){
							//Get's the location of where the player is looking
							Location currentBlock = player.getTargetBlock(null, 200).getLocation();
							//Changes fire destination to 1 block above target to prevent block destruction
							currentBlock.setY(currentBlock.getY() + 1);
							World w = currentBlock.getWorld();
							//Sees if the block is air and if it isn't it cancels the spell
							if(w.getBlockTypeIdAt(currentBlock) == Material.AIR.getId()){
								//Gets block at location
								Block b = w.getBlockAt(currentBlock);
								//Sets the block to fire
								b.setTypeId(51);
								player.sendMessage("Summonded fire!");
								//Removes the item completely to ensure that no errors will occur via setting gold_nugget value to 0
								if(inventory.getItemInHand().getAmount() == bpAmount){
									ItemStack minBlazePowder = new ItemStack(Material.BLAZE_POWDER.getId(), bpAmount);
									inventory.remove(minBlazePowder);
								} else {
									//Removes set amount of blaze powder from the player's inventory
									inventory.getItemInHand().setAmount(inventory.getItemInHand().getAmount() - bpAmount);
								}
							} else {
								player.sendMessage("A block is in the way! Spell canceled");
							}
						} else {
							player.sendMessage("You do have the power to summon fire!");
						}
					} else {
						player.sendMessage("You must have at least " + bpAmount + " blaze powder to perform this spell!");
					}
				} else {
					player.sendMessage("I'm sorry but that spell is not enabled on this server :(");
				}
			}
		}
	}
}
