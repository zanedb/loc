package sh.zane.loc.commands;

import static sh.zane.loc.Loc.GRAY;
import static sh.zane.loc.Loc.PRIMARY;
import static sh.zane.loc.Loc.WHITE;

import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.World;
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
      sendWelcomeMessage(player);
      return true;
    }

    switch (args[0]) {
      case "":
        sendWelcomeMessage(player);
        break;
      case "help":
      case "h":
        sendHelpMessage(player);
        break;
      case "save":
      case "set":
      case "s":
      case "new":
      case "create":
      case "make":
        // pass on args minus args[0]
        saveLoc(player, Arrays.copyOfRange(args, 1, args.length));
        break;
      case "remove":
      case "rem":
      case "r":
      case "del":
      case "delete":
      case "destroy":
        removeLoc(player, Arrays.copyOfRange(args, 1, args.length));
        break;
      case "list":
        listLocs(player);
        break;
      default:
        sender.sendMessage(
          WHITE +
          "Unrecognized command. " +
          GRAY +
          "Use " +
          PRIMARY +
          "/loc help" +
          GRAY +
          " for help."
        );
        break;
    }
    return true;
  }

  /* COMMANDS */

  private void sendWelcomeMessage(Player player) {
    // TODO: if saved locs exist, run listLocs() instead!
    player.sendMessage(
      new String[] {
        "Loc allows you to save locations in your world.",
        "Try " +
        PRIMARY +
        "/loc save " +
        GRAY +
        "<title>" +
        WHITE +
        ", or use " +
        PRIMARY +
        "/loc help" +
        WHITE +
        " for help.",
      }
    );
  }

  private void listLocs(Player player) {
    player.sendMessage(
      WHITE +
      "You have no saved locations. " +
      GRAY +
      "Save one with " +
      PRIMARY +
      "/loc save" +
      GRAY +
      " <title>."
    );
  }

  private void saveLoc(Player player, String[] args) {
    String title = condenseArgs(args);
    Location playerLocation = player.getLocation();

    if (title.isBlank()) {
      player.sendMessage(
        WHITE +
        "You need to title this location! " +
        GRAY +
        "Usage: " +
        PRIMARY +
        "/loc save " +
        GRAY +
        "<title>" +
        WHITE +
        "."
      );
      return;
    }

    // temp
    String locationString =
      playerLocation.getBlockX() +
      ", " +
      playerLocation.getBlockY() +
      ", " +
      playerLocation.getBlockZ();
    String worldType = worldType(playerLocation.getWorld());

    player.sendMessage(
      WHITE +
      "Saved " +
      PRIMARY +
      locationString +
      WHITE +
      " (" +
      PRIMARY +
      worldType +
      WHITE +
      ")" +
      " as " +
      PRIMARY +
      title +
      WHITE +
      "."
    );
  }

  private void removeLoc(Player player, String[] args) {
    String title = condenseArgs(args);

    if (title.isBlank()) {
      player.sendMessage(
        WHITE +
        "You need to specify which location! " +
        GRAY +
        "Usage: " +
        PRIMARY +
        "/loc remove " +
        GRAY +
        "<title>" +
        WHITE +
        "."
      );
      return;
    }

    // TODO: FETCH ACTUAL LOCATION
    String locationString = "0, 0, 0";
    String worldType = "Overworld";

    player.sendMessage(
      WHITE +
      "Removed " +
      PRIMARY +
      title +
      WHITE +
      " at " +
      PRIMARY +
      locationString +
      WHITE +
      " (" +
      PRIMARY +
      worldType +
      WHITE +
      ")" +
      WHITE +
      "."
    );
  }

  private void sendHelpMessage(Player player) {
    player.sendMessage(
      new String[] {
        WHITE + "--------- " + PRIMARY + "Loc Help" + WHITE + " ---------",
        PRIMARY +
        "/loc list " +
        GRAY +
        "[page]" +
        WHITE +
        " — List all locations.",
        PRIMARY +
        "/loc save " +
        GRAY +
        "<title>" +
        WHITE +
        " — Save a location.",
        PRIMARY +
        "/loc remove " +
        GRAY +
        "<title>" +
        WHITE +
        " — Remove a location.",
        PRIMARY +
        "/loc rename " +
        GRAY +
        "<old> <new>" +
        WHITE +
        " — Rename a location.",
      }
    );
  }

  /* UTILS */

  public String condenseArgs(String[] args) {
    String stringifiedArgs = String.join("", args);
    return stringifiedArgs.replaceAll("[^a-zA-Z0-9]", "");
  }

  public String worldType(World world) {
    switch (world.getEnvironment()) {
      case NORMAL:
        return "Overworld";
      case NETHER:
        return "Nether";
      case THE_END:
        return "The End";
      default:
        return "";
    }
  }
}
