package me.zeph.zephyrus.abilities;


import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;


import me.zeph.zephyrus.Methods;
import me.zeph.zephyrus.Zephyrus;



public class AirStrike extends AirAbility{

	//Config 
	private long cooldown;
	private int shots;

	//set variables
	private Location origin;
	private Location loc;
	private Vector dir;
	private int currentshots;


	public AirStrike(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this) || CoreAbility.hasAbility(player, this.getClass()) || bPlayer.isOnCooldown(this) || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			return;
		}

		setFields();
		start();
		onClick();
	}

	public void setFields() {
		
		//Config
		this.cooldown = Zephyrus.plugin.getConfig().getLong("Zephyrus.Air.AirStrike.Cooldown");
		this.shots = Zephyrus.plugin.getConfig().getInt("Zephyrus.Air.AirStrike.Shots");
		
		//Set variables
		this.origin = player.getEyeLocation();
		this.loc = origin.clone();
		this.dir = origin.getDirection();
		this.currentshots = 0;
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
		
		if (currentshots == shots) {
			this.remove();
		}
	}
	
	public void onClick() {
		new AirStrikeShot(player);
		currentshots++;
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
				+ "Shoot powerful blasts from all four limbs."; 
	}

	@Override
	public String getInstructions() {
		return "Left click to strike your opponent from a random limb.."; 
	}
}















