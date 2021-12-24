package me.zeph.zephyrus.abilities;


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


public class AirBall extends AirAbility{

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
	private Boolean hasdamaged;


	public AirBall(Player player) {
		super(player);

		if (!bPlayer.canBend(this) || CoreAbility.hasAbility(player, this.getClass()) || bPlayer.isOnCooldown(this) || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			return;
		}

		setFields();
		start();
		bPlayer.addCooldown(this);
	}

	public void setFields() {

		//Config
		this.cooldown = Zephyrus.plugin.getConfig().getLong("Zephyrus.Air.AirBall.Cooldown");
		this.damage = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirBall.Damage");
		this.range = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirBall.Range");
		this.radius = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirBall.Radius");
		this.speed = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirBall.Speed");

		//Set variables
		this.origin = player.getEyeLocation();
		this.loc = origin.clone();
		this.dir = origin.getDirection();
		this.hasdamaged = false;
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
		return "AirBall";
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

		loc.add(dir.clone().multiply(speed));
		if (e == null) {
			Methods.playSphere(loc, radius, 10, 1);
			e = Methods.getAffected(loc, radius, player);
		}
		else {
			if (!hasdamaged) {
				DamageHandler.damageEntity(e, damage, this);
				hasdamaged = true;
			}
			Methods.playSphere(loc, e.getHeight()/2, 10, 1);
			e.setVelocity(dir.clone().multiply(speed));
		}



	}

	@Override
	public void remove() {
		bPlayer.addCooldown(this);
		super.remove();
		return;
	}


	@Override
	public String getDescription() {
		return "By: __Yujiro\n"
				+ "An airbender can create a compressed ball of air by moving their hands together in a circular motion.."; 
	}

	@Override
	public String getInstructions() {
		return "Left click to shoot a ball, this will trap opponents and carry them forward."; 
	}
}















