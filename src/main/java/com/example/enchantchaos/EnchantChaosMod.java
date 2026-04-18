package com.example.enchantchaos;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchantChaosMod implements ModInitializer {
    public static final String MOD_ID = "enchantchaos";
    private static boolean jumpEnchantingEnabled = true;
    private static int maxLevel = 100;
    
    @Override
    public void onInitialize() {
        // Mod initialization
        System.out.println("Enchant Chaos Mod initialized!");
        System.out.println("Commands will be added in a future update!");
    }
    
    public static void enchantRandomItem(ServerPlayerEntity player) {
        // Check if jump enchanting is enabled
        if (!jumpEnchantingEnabled) return;
        
        // Get all non-empty slots in player's inventory (any item, not just enchantable)
        List<ItemStack> inventoryItems = new ArrayList<>();
        
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (!stack.isEmpty()) {
                inventoryItems.add(stack);
            }
        }
        
        // If there are items, pick one randomly
        if (!inventoryItems.isEmpty()) {
            Random random = player.getRandom();
            ItemStack randomStack = inventoryItems.get(random.nextInt(inventoryItems.size()));
            
            // Get ALL enchantments (not just compatible ones) with configurable max level
            List<EnchantmentLevelEntry> possibleEnchantments = new ArrayList<>();
            
            for (Enchantment enchantment : Registries.ENCHANTMENT) {
                // Allow levels from 1 to maxLevel
                for (int level = 1; level <= maxLevel; level++) {
                    possibleEnchantments.add(new EnchantmentLevelEntry(enchantment, level));
                }
            }
            
            // Pick a random enchantment at a random level
            EnchantmentLevelEntry randomEnchantment = possibleEnchantments.get(random.nextInt(possibleEnchantments.size()));
            
            // Apply the enchantment (force it onto the item)
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(randomStack);
            enchantments.put(randomEnchantment.enchantment, randomEnchantment.level);
            EnchantmentHelper.set(enchantments, randomStack);
            
            // Send message to player with color coding based on level
            String levelText = randomEnchantment.level > 50 ? "§5" : 
                              randomEnchantment.level > 20 ? "§d" : 
                              randomEnchantment.level > 10 ? "§6" : 
                              randomEnchantment.level > 5 ? "§e" : 
                              randomEnchantment.level > 1 ? "§f" : "§7";
            
            player.sendMessage(
                Text.literal("§aYour " + randomStack.getName().getString() + 
                " got " + levelText + randomEnchantment.enchantment.getName(randomEnchantment.level).getString() + 
                " §7(Lvl " + randomEnchantment.level + ")§a!"),
                true
            );
        }
    }
    
    // Getter for mixin to check if enchanting is enabled
    public static boolean isJumpEnchantingEnabled() {
        return jumpEnchantingEnabled;
    }
}
