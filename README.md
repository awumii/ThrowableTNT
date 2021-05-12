# ThrowableTNT
Simple Paper plugin that adds throwable tnt.

## Requirements
* [Paper 1.16.5](https://papermc.io/downloads) (or other Adventure compatible fork)
* Java 11

## Installation
Go to [Releases](https://github.com/xxneox/ThrowableTNT/releases) and download the latest jar file.  
After restarting the server, you should see the configuration file located in **plugins/ThrowableTNT/config.yml**

## Usage
Use command /throwabletnt get <amount> to give yourself some throwable tnt.  
You can also use /throwabletnt give <amount> <player> to give the throwable tnt to another player.

## Configuration
```yaml
# Settings for the TNT item.
item-type: TNT
item-name: "&cThrowable TNT!"
item-lore:
  - '&7Right click to throw!'

# Amount of ticks before the explosion
fuse-ticks: 40

# Amount of the thrown tnt's forward velocity
velocity: 1.0
```
