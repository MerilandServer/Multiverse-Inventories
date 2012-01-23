package com.onarandombox.multiverseinventories.listener;

import com.onarandombox.multiverseinventories.MultiverseInventories;
import com.onarandombox.multiverseinventories.share.SimpleShareHandler;
import com.onarandombox.multiverseinventories.util.MVILog;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * PlayerListener for MultiverseInventories.
 */
public class MVIPlayerListener extends PlayerListener {

    private MultiverseInventories plugin;

    public MVIPlayerListener(MultiverseInventories plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player changes worlds.
     *
     * @param event The world change event.
     */
    //@EventHandler(event = PlayerChangedWorldEvent.class, priority = org.bukkit.event.EventPriority.NORMAL)
    @Override
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World fromWorld = event.getFrom();
        World toWorld = player.getWorld();

        // A precaution..  Will this ever be true?
        if (fromWorld.equals(toWorld)) {
            MVILog.debug("PlayerChangedWorldEvent fired when player travelling in same world.");
            return;
        }
        // Do nothing if dealing with non-managed worlds
        if (this.plugin.getCore().getMVWorldManager().getMVWorld(toWorld) == null
                || this.plugin.getCore().getMVWorldManager().getMVWorld(fromWorld) == null) {
            MVILog.debug("The from or to world is not managed by Multiverse!");
            return;
        }

        new SimpleShareHandler(this.plugin, player, fromWorld, toWorld).handleSharing();
    }
}
