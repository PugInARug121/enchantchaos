package com.example.randomenchantjump;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomEnchantJumpMod implements ModInitializer {
    public static final String MOD_ID = "randomenchantjump";
    
    @Override
    public void onInitialize() {
        // Mod initialization
        System.out.println("Random Enchant Jump Mod initialized!");
    }
    
    public static void enchantRandomItem(ServerPlayerEntity player) {
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
            
            // Get ALL enchantments (not just compatible ones) with crazy high levels
            List<EnchantmentLevelEntry> possibleEnchantments = new ArrayList<>();
            
            for (Enchantment enchantment : Registries.ENCHANTMENT) {
                // Allow levels from 1 to 100 (much higher than normal max of 5)
                for (int level = 1; level <= 100; level++) {
                    possibleEnchantments.add(new EnchantmentLevelEntry(enchantment, level));
                }
            }
            
            // Pick a random enchantment at a random level
            EnchantmentLevelEntry randomEnchantment = possibleEnchantments.get(random.nextInt(possibleEnchantments.size()));
            
            // Apply the enchantment (force it onto the item)
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(randomStack);
            enchantments.put(randomEnchantment.enchantment, randomEnchantment.level);
            EnchantmentHelper.set(enchantments, randomStack);
            
            // Send message to player
            String levelText = randomEnchantment.level > 10 ? "§d" : 
                              randomEnchantment.level > 5 ? "§6" : 
                              randomEnchantment.level > 1 ? "§e" : "§f";
            
            player.sendMessage(
                net.minecraft.text.Text.literal("§aYour " + randomStack.getName().getString() + 
                " got " + levelText + randomEnchantment.enchantment.getName(randomEnchantment.level).getString() + 
                " §7(Lvl " + randomEnchantment.level + ")§a!"),
                true
            );
        }
    }
}
