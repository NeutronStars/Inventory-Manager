package fr.neutronstars.inventorymanager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

/**
 * @author NeutronStars
 * @since 1.0
 */

public final class InventoryManager {

	private final Map<String, AbstractInventory> inventories = Maps.newHashMap();
	private final InteractItem[] interactItems = new InteractItem[9];
	
	protected boolean cancelPlayerDrop, cancelPlayerPickup, enableHotbar;
	
	public InventoryManager(Plugin plugin){
		Bukkit.getPluginManager().registerEvents(new InventoryListener(this), plugin);
	}
	
	/**
	 * Recupère un inventaire enregistré par rapport à son titre.
	 * @param title titre de l'inventaire
	 * @return inventaire ou null.
	 */
	public final AbstractInventory getInventory(String title){
		return inventories.get(title);
	}
	
	/**
	 * Récupère tout les inventaires enregistré.
	 * @return
	 */
	public final Collection<? extends AbstractInventory> getInventories(){
		return Collections.unmodifiableCollection(inventories.values());
	}
	
	/**
	 * Recupère {@link AbstractItem} de la hotbar par rapport à son slot.
	 * @param slot emplacement de l'item.
	 * @return
	 */
	public final InteractItem getItemHotbar(int slot){
		if(slot < 0 || slot > 8) return null;
		return interactItems[slot];
	}
	
	/**
	 * Enregistre les Inventaire.
	 * @param abstractInventories
	 */
	public final void registerInventories(AbstractInventory... abstractInventories){
		for(AbstractInventory abstractInventory : abstractInventories)
			inventories.put(abstractInventory.getTitle(), abstractInventory);
	}
	
	/**
	 * Supprime les inventaires.
	 * @param abstractInventories
	 */
	public final void unregisterInventories(AbstractInventory... abstractInventories){
		for(AbstractInventory abstractInventory : abstractInventories)
			inventories.remove(abstractInventory.getTitle());
	}
	
	/**
	 * Supprime les inventaires en fonction de leur titre.
	 * @param titles
	 */
	public final void unregisterInventories(String... titles){
		for(String title : titles)
			if(inventories.containsKey(title)) inventories.remove(titles);
	}
	
	/**
	 * Active/Desactive la hotbar custom.
	 * @param enable
	 */
	public void enableHotbar(boolean enable){
		this.enableHotbar = enable;
	}
	
	/**
	 * modifie un item de la hotbar.
	 * @param slot
	 * @param material
	 * @param count
	 * @param data
	 * @param name
	 * @param lores
	 * @param glowing
	 * @param title
	 * @param actions
	 */
	public final void setItemHotbar(int slot, Material material, int count, int data, String name, List<String> lores, boolean glowing, String title, boolean log, Action...actions){
		if(slot < 0 || slot > 8) return;
		if(material == null) material = Material.AIR;
		interactItems[slot] = new InteractItem(slot, material, count, data, name, lores, glowing, title != null ? title : "", log, actions);
	}
	
	/**
	 * Annule l'event pour jetter un item.
	 * @param cancelled
	 */
	public final void setCancelledPlayerDrop(boolean cancelled){
		cancelPlayerDrop = cancelled;
	}
	
	/**
	 * Annule l'event pour recuperer un item.
	 * @param cancelled
	 */
	public final void setCancelledPlayerPickup(boolean cancelled){
		cancelPlayerPickup = cancelled;
	}
	
	/**
	 * Supprime un item de la hotbar.
	 * @param slot
	 */
	public final void removeItemHotbar(int slot){
		if(slot < 0 || slot > 8) return;
		interactItems[slot] = null;
	}
	
	/**
	 * mets la hotbar à un joueur.
	 * @param player
	 */
	public final void setPlayerHotbar(Player player){
		player.getInventory().clear();
		for(InteractItem ii : interactItems) 
			if(ii != null) player.getInventory().setItem(ii.getSlot(), ii.getItem());
	}
	
	/**
	 * Ouverture d'un inventaire au Joueur.
	 * @param player
	 * @param title
	 */
	public final void openInventory(Player player, String title, boolean log){
		AbstractInventory ai = getInventory(title);
		if(ai != null) player.openInventory(ai.getInventory());
		else if(log) System.out.println("L'inventaire "+title+" n'a pas pu etre ouvert au joueur "+player.getName());
	}
}
