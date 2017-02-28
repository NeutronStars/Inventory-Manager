package fr.neutronstars.inventorymanager;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author NeutronStars
 * @since 1.0
 */
public abstract class AbstractItem {

	private final ItemStack item;
	
	public AbstractItem(ItemStack item) {
		this.item = item;
	}
	
	public AbstractItem(Material material, int count, int data, String name, List<String> lores, boolean glowing){
		item = new ItemStack(material, count, (byte)data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lores);
		if(glowing) im.addEnchant(Enchantment.DURABILITY, 0, false);
		item.setItemMeta(im);
	}
	
	protected final ItemStack getItem() {
		return item.clone();
	}
	
	protected abstract void clickItem(InventoryManager inventoryManager, AbstractInventory inventory, Player player);
}
