package me.zeph.zephyrus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

import me.zeph.zephyrus.abilities.AirBall;
import me.zeph.zephyrus.abilities.AirStrike;
import me.zeph.zephyrus.abilities.SpiritualProjection;



public class AbilityListener implements Listener {

	@EventHandler
	public void onSwing(PlayerAnimationEvent event) {

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (event.isCancelled() || bPlayer == null) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("AirBall")) {
			new AirBall(player);


		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("AirStrike")) {
			if (CoreAbility.hasAbility(player, AirStrike.class)) {
				CoreAbility.getAbility(player, AirStrike.class).onClick();
			}
			else {
				new AirStrike(player);
			}

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("SpiritualProjection")) {
			if (CoreAbility.hasAbility(player, SpiritualProjection.class)) {
				CoreAbility.getAbility(player, SpiritualProjection.class).remove();
			}
			else {
				new SpiritualProjection(player);
			}

		}
	}
	
	@EventHandler
	public void armorStand(PlayerArmorStandManipulateEvent event) {
		if (event.getRightClicked() == null) {
			return;
		}
		if (event.getRightClicked().hasMetadata("ArmorStand")) {
			event.setCancelled(true);
			return;
		}
	}
}








