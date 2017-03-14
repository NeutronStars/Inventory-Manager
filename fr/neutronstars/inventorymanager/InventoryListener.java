package fr.neutronstars.inventorymanager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author NeutronStars
 * @since 1.0
 */
final class InventoryListener implements Listener{

	private final InventoryManager inventoryManager;
	
	protected InventoryListener(InventoryManager inventoryManager){
		this.inventoryManager = inventoryManager;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void playerJoin(PlayerJoinEvent pje){
		if(inventoryManager.enableHotbar) inventoryManager.setPlayerHotbar(pje.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void playerInteract(PlayerInteractEvent pie){
		InteractItem ii = inventoryManager.getItemHotbar(pie.getPlayer().getInventory().getHeldItemSlot());
		if(ii == null || !ii.getActions().contains(pie.getAction())) return;
		inventoryManager.openInventory(pie.getPlayer(), ii.getInventoryKey());
		pie.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void clickItem(InventoryClickEvent ice){
		if(ice.getInventory() == null || ice.getCurrentItem() == null) return;
		for(AbstractInventory ai : inventoryManager.getInventories()){
			if(ice.getInventory().getTitle().equalsIgnoreCase(ai.getTitle())){
				ai.clickItem(inventoryManager, (Player)ice.getWhoClicked(), ice.getSlot());
				ice.setCancelled(ai.isCancelled());
				return;
			}			
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void dropItem(PlayerDropItemEvent pdie){
		pdie.setCancelled(inventoryManager.cancelPlayerDrop);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void pickupItem(PlayerPickupItemEvent ppie){
		ppie.setCancelled(inventoryManager.cancelPlayerPickup);
	}
}
