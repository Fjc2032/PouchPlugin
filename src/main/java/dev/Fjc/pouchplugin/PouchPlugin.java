package dev.Fjc.pouchplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class PouchPlugin extends JavaPlugin implements Listener {
  
  private Inventory inv = null;

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(this, this);
    this.getCommand("pouch").setExecutor(this);
    getLogger().info("Initializing the GUIs. Hang tight!");
    baseInterface();
    initializeItems();
  }

  @Override
  public void onDisable() {
  }

  public void baseInterface() {
    inv = Bukkit.createInventory(null, 27, ChatColor.RED + "AURORA POUCH");
  }

  private void initializeItems() {
    inv.addItem(createItem(Material.TOTEM_OF_UNDYING, "Pouch", Chatcolor.YELLOW + "Pouch", "Line 2", "Line 3")); //Debug
  }

  protected ItemStack createItem(final Material material, final String name, final String... lore) {
    final ItemStack item = new ItemStack(material, 1);
    final ItemMeta meta = item.getItemMeta();

    meta.setDisplayName(name);
    meta.setLore(Arrays.asList(lore));
    item.setItemMeta(meta);
    return item;
  }

  public void openInv(final HumanEntity ent) {
    ent.openInventory(inv);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      baseInterface();
      openInv(player);
      return true;
  } else {
      sender.sendMessage("You have to be a player to use this");
      return false;
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onInvClick(final InventoryClickEvent event) {
    if (!event.getInventory().equals(inv)) return;
    event.setCancelled(true);

    final ItemStack clickedItem = event.getCurrentItem();
    if (clickedItem == null || clickedItem.getType().isAir()) return;

    //Unused declaration
    final Player player = (Player) event.getWhoClicked();
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onInvClick(final InventoryDragEvent event) {
    if (event.getInventory().equals(inv)) {
      event.setCancelled(true);
    }
  }
}
