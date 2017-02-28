package fr.neutronstars.inventorymanager;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Maps;

/**
 * @author NeutronStars
 * @since 1.0
 */
public abstract class AbstractInventory{

	private final Inventory inventory;
	private final Map<Integer, AbstractItem> items = Maps.newHashMap();
	
	public AbstractInventory(int size, String title){
		inventory = Bukkit.createInventory(null, size, title);
	}
	
	public final Inventory getInventory() {
		return cloneInventory();
	}
	
	private final Inventory cloneInventory(){
		Inventory inventory = Bukkit.createInventory(this.inventory.getHolder(), this.inventory.getSize(), this.inventory.getName());
		inventory.setContents(this.inventory.getContents());
		return inventory;
	}
	
	public final void setItem(int slot, ItemStack item){
		setItem(slot, new AbstractItem(item) {
			public void clickItem(AbstractInventory inventory, Player player) {}
		});
	}
	
	public final void setItem(int slot, AbstractItem item){
		inventory.setItem(slot, item.getItem());
		items.put(slot, item);
	}
	
	public final void removeItem(int slot){
		inventory.setItem(slot, new ItemStack(Material.AIR));
		items.remove(slot);
	}
	
	public final String getTitle(){
		return getInventory().getTitle();
	}
	
	protected final void clickItem(Player player, int slot){
		if(items.containsKey(slot)) items.get(slot).clickItem(this, player);
	}
}
