# Enchant Chaos Mod

A Minecraft Fabric 1.20.1 mod that adds random enchantments to ANY item when jumping. Maximum chaos!

## Features

- **Jump to Enchant**: When a player jumps, a random item in their inventory gets a random enchantment
- **Any Item**: Works on ANY item - dirt, stone, sticks, everything can be enchanted!
- **Any Level**: Enchantment levels from 1-100 (configurable)
- **Multiple Enchantments**: Items can accumulate multiple enchantments over multiple jumps
- **Color-Coded Messages**: Visual feedback with colors based on enchantment level
- **Jump Toggle**: Enable/disable jump enchanting (commands coming soon)

## Enchantment Level Colors
- **Gray** (§7): Level 1
- **White** (§f): Levels 2-5  
- **Yellow** (§e): Levels 6-10
- **Gold** (§6): Levels 11-20
- **Purple** (§d): Levels 21-50
- **Dark Purple** (§5): Levels 51-100

## License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

### What the License Allows
- ✅ **Free Use**: Use the mod for any purpose
- ✅ **Modification**: Modify and customize the code
- ✅ **Distribution**: Share and redistribute the mod
- ✅ **Commercial Use**: Use in commercial projects
- ⚠️ **No Warranty**: Standard disclaimer of liability

## Building the Mod

### Prerequisites

1. **Java 17** - Make sure you have Java 17 installed
2. **Gradle** - The project uses Gradle for building (included with project)

### Build Steps

1. **Navigate to the project directory:**
   ```bash
   cd path/to/your/project
   ```

2. **Build the JAR file:**
   ```bash
   ./gradlew build
   ```
   On Windows, use:
   ```bash
   gradlew.bat build
   ```

3. **Find the JAR file:**
   After building successfully, the JAR file will be located at:
   ```
   build/libs/randomenchantjump-1.0.0.jar
   ```

### Installing the Mod

1. **Install Fabric Loader** for Minecraft 1.20.1 if you haven't already
2. **Copy the JAR file** to your Minecraft mods folder:
   - Windows: `%APPDATA%\.minecraft\mods\`
   - macOS: `~/Library/Application Support/minecraft/mods/`
   - Linux: `~/.minecraft/mods/`

3. **Launch Minecraft** with the Fabric profile

## Development

### Project Structure

```
src/main/java/com/example/randomenchantjump/
├── RandomEnchantJumpMod.java      # Main mod class
└── RandomEnchantJumpModClient.java # Client entrypoint

src/main/resources/
├── fabric.mod.json                # Mod metadata
└── randomenchantjump.mixins.json  # Mixin configuration
```

### How It Works

The mod uses Fabric's server tick events to detect when a player jumps:
1. Monitors player velocity and ground state
2. When a jump is detected, selects a random enchantable item from inventory
3. Applies a random valid enchantment at a random level
4. Notifies the player of the enchantment

## License

MIT License
