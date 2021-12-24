package me.zeph.zephyrus.abilities.combos;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.zeph.zephyrus.Methods;
import me.zeph.zephyrus.Zephyrus;



public class AirPropulsion extends AirAbility implements ComboAbility{

	//Config 
	private long cooldown;
	private long duration;
	private double speed;


	//set variables
	private Location loc;
	private Vector dir;
	private long starttime;


	public AirPropulsion(Player player) {
		super(player);
		
		if (CoreAbility.hasAbility(player, this.getClass()) || bPlayer.isOnCooldown(this) || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())) {
			return;
		}

		
		setFields();
		start();
		bPlayer.addCooldown(this);
	}

	public void setFields() {
		//Config
		this.cooldown = Zephyrus.plugin.getConfig().getLong("Zephyrus.Air.AirPropulsion.Cooldown");
		this.duration = Zephyrus.plugin.getConfig().getLong("Zephyrus.Air.AirPropulsion.Duration");
		this.speed = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.AirPropulsion.Speed");
		
		//Set variables
		this.starttime = System.currentTimeMillis();
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
		return "AirPropulsion";
	}

	@Override
	public boolean isHarmlessAbility() {
		return true;
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline() || bPlayer.isChiBlocked() || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation()) ){
			this.remove();
		}
		
		if (System.currentTimeMillis() > (starttime + duration)) {
			this.remove();
		}
		
		this.loc = player.getEyeLocation();
		this.dir = loc.getDirection();
		
		player.setVelocity(dir.setY(0).clone().multiply(speed));
		
		AirAbility.playAirbendingParticles(GeneralMethods.getLeftSide(loc, 0.2).subtract(0,0.5,0), 3);
		ParticleEffect.SPIT.display(GeneralMethods.getLeftSide(loc, 0.2).subtract(0,0.5,0), 3);
		AirAbility.playAirbendingParticles(GeneralMethods.getRightSide(loc, 0.2).subtract(0,0.5,0), 3);
		ParticleEffect.SPIT.display(GeneralMethods.getRightSide(loc, 0.2).subtract(0,0.5,0), 3);
		
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new AirPropulsion(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		final ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("AirBurst", ClickType.LEFT_CLICK));
		combo.add(new AbilityInformation("AirScooter", ClickType.SHIFT_DOWN));
		return combo;
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
				+ "Fire yourself forward momentarily.."; 
	}

	@Override
	public String getInstructions() {
		return "AirBurst (Left click) >  AirScooter (Tap shift)."; 
	}
}















