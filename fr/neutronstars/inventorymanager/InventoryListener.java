package fr.neutronstars.inventorymanager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author NeutronStars
 * @since 1.0
 */
final class InventoryListener implements Listener{

	private final InventoryManager inventoryManger;
	
	protected InventoryListener(InventoryManager inventoryManger){
		this.inventoryManger = inventoryManger;
	}
	
	@EventHandler
	private void playerJoin(PlayerJoinEvent pje){
		inventoryManger.setPlayerHotbar(pje.getPlayer());
	}
	
	@EventHandler
	private void playerInteract(PlayerInteractEvent pie){
		InteractItem ii = inventoryManger.getItemHotbar(pie.getPlayer().getInventory().getHeldItemSlot());
		if(ii == null || !ii.getActions().contains(pie.getAction())) return;
		AbstractInventory ai = inventoryManger.getInventory(ii.getInventoryKey());
		if(ai == null) return;
		pie.getPlayer().openInventory(ai.getInventory());
		pie.setCancelled(true);
	}
	
	@EventHandler
	private void clickItem(InventoryClickEvent ice){
		if(ice.getInventory() == null || ice.getCurrentItem() == null) return;
		inventoryManger.getInventories().forEach(i->{
			if(ice.getInventory().getTitle().equalsIgnoreCase(i.getTitle())){
				i.clickItem(inventoryManger, (Player)ice.getWhoClicked(), ice.getSlot());
				ice.setCancelled(true);
			}
		});
	}
	
	@EventHandler
	private void dropItem(PlayerDropItemEvent pdie){
		pdie.setCancelled(true);
	}
}
