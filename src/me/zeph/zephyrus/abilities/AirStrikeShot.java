package me.zeph.zephyrus.abilities;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.DamageHandler;

import me.zeph.zephyrus.Methods;
import me.zeph.zephyrus.Zephyrus;



public class AirStrikeShot extends AirAbility{

	//Config 
	private long cooldown;
	private double damage;
	private double range;
	private double radius;
	private double speed;


	//set variables
	private Location origin;
	private Location loc;
	private Vector dir;
	private Entity e;
	private enum limb{
		RP , LP , RK , LK;
	}
	private limb chosen;

	public AirStrikeShot(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)  || bPlayer.isOnCooldown(this) || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			return;
		}

		setFields();
		start();
	}

	public void setFields() {
		
		//Config 
		this.cooldown = Zephyrus.plugin.getConfig().getLong("Zephyrus.Air.AirStrike.Cooldown");
		this.damage = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirStrike.Damage");
		this.range = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirStrike.Range");
		this.radius = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirStrike.Radius");
		this.speed = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirStrike.Speed");
		
		
		this.origin = player.getEyeLocation();
		
		
		this.chosen = limb.values()[new Random().nextInt(limb.values().length)];
		
		if (chosen == limb.LK) {
			origin = GeneralMethods.getLeftSide(origin, 0.2).subtract(0,0.4,0);
		}
		if (chosen == limb.LP) {
			origin = GeneralMethods.getLeftSide(origin, 0.2);
		}
		if (chosen == limb.RK) {
			origin = GeneralMethods.getRightSide(origin, 0.2).subtract(0,0.4,0);
		}
		if (chosen == limb.RP) {
			origin = GeneralMethods.getRightSide(origin, 0.2);
		}
		
		
		this.loc = origin.clone();
		this.dir = origin.getDirection();
	}
	
	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public String getName() {
		return "AirStrike";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline() || bPlayer.isChiBlocked() || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())){
			this.remove();
		}
		
		if (loc.distance(origin) > range) {
			this.remove();
		}
		
		if (GeneralMethods.isSolid(loc.getBlock())) {
			this.remove();
		}
		
		dir = player.getEyeLocation().getDirection();
		loc.add(dir.clone().multiply(speed));
		if (e == null) {
			Methods.playSphere(loc, radius, 5, 1);
			e = Methods.getAffected(loc, radius, player);
		}
		else {
			DamageHandler.damageEntity(e, damage, this);
			this.remove();
		}
		
		
		
	}

	@Override
	public void remove() {
		super.remove();
		return;
	}
	
	@Override
	public String getDescription() {
		return "By: __Yujiro\n"
				+ "Shoot powerful blasts from all four limbs."; 
	}

	@Override
	public String getInstructions() {
		return "Left click to strike your opponent from a random limb.."; 
	}
}















