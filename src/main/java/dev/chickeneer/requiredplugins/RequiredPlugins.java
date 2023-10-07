package dev.chickeneer.requiredplugins;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class RequiredPlugins extends JavaPlugin implements Listener {
    public Logger LOGGER = Logger.getLogger("RequiredPlugins");
    private final Set<Set<String>> requiredPlugins = new HashSet<>();
    private final Pattern COMMA = Pattern.compile(",");
    private final Pattern PIPE = Pattern.compile("\\|");

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        String required = (String) this.getConfig().get("required-plugins");
        if (required != null) {
            for (String plugin : COMMA.split(required)) {
                Set<String> plugins = new HashSet<>();
                for (String pluginSplit : PIPE.split(plugin)) {
                    plugins.add(pluginSplit.trim());
                }
                requiredPlugins.add(plugins);
            }
        }
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        for (Set<String> plugins : requiredPlugins) {
            boolean found = false;
            for (String pluginName : plugins) {
                Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginName);
                if (plugin != null && plugin.isEnabled()) {
                    found = true;
                }
            }
            if (!found) {
                LOGGER.severe("Plugin did not load: " + String.join(", ", plugins) + " - Shutting down!");
                if (!Bukkit.getServer().isStopping()) {
                    Bukkit.getServer().shutdown();
                }
            }
        }
    }
}
