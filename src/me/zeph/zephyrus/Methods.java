package me.zeph.zephyrus;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class Methods {

	public static void playSphere(Location loc, double radii, double density, int amount) {
		for (double i = 0; i <= Math.PI; i += Math.PI / density) {
			double radius = Math.sin(i) * radii;
			double y = Math.cos(i) * radii;
			for (double a = 0; a < Math.PI * 2; a+= Math.PI*2 / density) {
				double x = Math.cos(a) * radius;
				double z = Math.sin(a) * radius;
				ParticleEffect.SPELL.display(loc.clone().add(x,y,z), amount);
			}
		}
	}

	public static LivingEntity getAffected(Location loc, double radii, Player player) {
		LivingEntity e = null;
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, radii)) {
			if ((entity instanceof LivingEntity) && entity.getUniqueId() != player.getUniqueId()) {
				e = (LivingEntity) entity;
			}
		}
		return e;
	}
}
