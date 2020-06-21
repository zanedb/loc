package sh.zane.loc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import sh.zane.loc.Loc;

public class LocCommand implements CommandExecutor {
  private final Loc plugin;

  public LocCommand(Loc plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(
    CommandSender sender,
    Command command,
    String label,
    String[] args
  ) {
    if (!(sender instanceof Player)) {
      PluginDescriptionFile pdf = this.plugin.getDescription();
      sender.sendMessage(
        "Running Loc version " +
        pdf.getVersion() +
        ". Try /loc as a player for more."
      );
      return true;
    }

    Player player = (Player) sender;

    if (args.length == 0) {
      listLocs(player);
      return true;
    }

    switch (args[0]) {
      case "help":
        sendHelpMessage(player);
        break;
      case "":
        listLocs(player);
        break;
      default:
        sender.sendMessage("Unrecognized command. Use /loc help for help.");
        break;
    }
    return true;
  }

  private void listLocs(Player player) {
    player.sendMessage("Listing all locations...");
  }

  private void sendHelpMessage(Player player) {
    player.sendMessage(
      new String[] {
        "========== Loc Help ==========",
        "/loc [page]: List all locations.",
        "/loc save [title]: Save a location.",
        "/loc remove [title/#]: Remove a location.",
      }
    );
  }
}
