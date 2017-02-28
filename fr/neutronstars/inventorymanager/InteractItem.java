package fr.neutronstars.inventorymanager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

/**
 * @author NeutronStars
 * @since 1.0
 */
final class InteractItem{

	private final int slot;
	private final List<Action> actions = Lists.newArrayList();
	private final ItemStack item;
	private final String inventoryKey;
	
	protected InteractItem(int slot, Material material, int count, int data, String name, List<String> lores, boolean glowing, String title, Action...actions){
		this.slot = slot;
		item = new ItemStack(material, count, (byte)data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lores);
		if(glowing){
			im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			im.addEnchant(Enchantment.DURABILITY, 1, true);
		}
		item.setItemMeta(im);
		inventoryKey = title;
		for(Action action : actions) this.actions.add(action);
	}
	
	protected final int getSlot() {
		return slot;
	}
	
	protected final ItemStack getItem() {
		return item;
	}
	
	protected final Collection<Action> getActions(){
		return Collections.unmodifiableCollection(actions);
	}
	
	protected final String getInventoryKey() {
		return inventoryKey;
	}
}
