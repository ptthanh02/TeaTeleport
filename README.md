# TeaTeleport

![Version](https://img.shields.io/badge/version-1.0.0-green)
![API](https://img.shields.io/badge/API-1.21-blue)
![Folia](https://img.shields.io/badge/Folia-Supported-success)

## 📝 Description

TeaTeleport is a lightweight yet powerful Minecraft plugin designed for server administrators to efficiently manage teleport locations and execute mass teleportations. Perfect for event management, server tours, or general administrative tasks, this plugin streamlines the process of moving players throughout your server.

## ✨ Features

- **Location Management**: Create, list, and delete teleport locations with ease
- **Individual Teleportation**: Teleport yourself to saved locations
- **Mass Teleportation**: Move all players at once to any saved location
- **Selective Teleportation**: Teleport specific players or entire worlds to designated locations
- **Customizable Messages**: Fully customizable messages with color support
- **Sound Effects**: Enhance user experience with sound notifications
- **Folia Support**: Compatible with the Folia server implementation

## 🔧 Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/tt set <name>` | Set a teleport location at your current position | `teateleport.set` |
| `/tt tp <name>` | Teleport to a saved location | `teateleport.tp` |
| `/tt tpall <name>` | Teleport all players to a location | `teateleport.tpall` |
| `/tt tpplayer <player> <name>` | Teleport a specific player to a location | `teateleport.tpplayer` |
| `/tt worldtp <world> <name>` | Teleport all players in a world to a location | `teateleport.worldtp` |
| `/tt list` | List all saved teleport locations | `teateleport.list` |
| `/tt delete <name>` | Delete a teleport location | `teateleport.delete` |
| `/tt reload` | Reload plugin configuration | `teateleport.reload` |
| `/tt help` | Display help information | `teateleport.use` |

## 🔒 Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `teateleport.use` | Access to base command | OP |
| `teateleport.set` | Create teleport locations | OP |
| `teateleport.tp` | Teleport to locations | OP |
| `teateleport.tpall` | Teleport all players | OP |
| `teateleport.tpplayer` | Teleport specific players | OP |
| `teateleport.worldtp` | Teleport players in a world | OP |
| `teateleport.list` | View all teleport locations | OP |
| `teateleport.delete` | Remove teleport locations | OP |
| `teateleport.reload` | Reload configuration | OP |
| `teateleport.*` | All permissions | OP |

## 🛠️ Installation

1. Download the TeaTeleport plugin JAR file
2. Place it in your server's `plugins` folder
3. Restart your server
4. Configure the plugin settings in the generated config files

## ⚙️ Configuration

TeaTeleport offers extensive configuration options through its messages.yml file. Customize colors, sounds, and the content of all plugin messages to match your server's theme.

## 💡 Usage Examples

**Creating and using a teleport point:**
```
/tt set lobby
/tt tp lobby
```

**Moving all players to an event:**
```
/tt tpall event_arena
```

**Teleporting players from a specific world:**
```
/tt worldtp world_nether event_start
```

## 📋 Support

For issues, feature requests, or assistance, please open an issue on our GitHub repository or contact the author.

---

Developed with ❤️ by Nighter