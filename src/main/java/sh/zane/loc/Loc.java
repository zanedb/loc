package sh.zane.loc;

import org.bukkit.plugin.java.JavaPlugin;
import sh.zane.loc.commands.LocCommand;

public class Loc extends JavaPlugin {

  // Runs when the server enables the plugin
  @Override
  public void onEnable() {
    // Register commands
    this.getCommand("loc").setExecutor(new LocCommand(this));
  }

  // Runs when the server stops and disables all plugins
  @Override
  public void onDisable() {}
}
