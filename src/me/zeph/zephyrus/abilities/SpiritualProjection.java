package me.zeph.zephyrus.abilities;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.MultiAbility;
import com.projectkorra.projectkorra.ability.SpiritualAbility;
import com.projectkorra.projectkorra.util.ActionBar;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;


import me.zeph.zephyrus.Methods;
import me.zeph.zephyrus.Zephyrus;
import net.md_5.bungee.api.ChatColor;




public class SpiritualProjection extends SpiritualAbility{

	//Config 
	private long cooldown;
	private double duration;
	private double range;


	//set variables
	private Location origin;
	private Location loc;
	private ArmorStand sitting;
	private long starttime;
	private BossBar bar;
	private double timeleft;
	private Bat hurtbox;

	public SpiritualProjection(Player player) {
		super(player);

		if (!bPlayer.canBend(this) || CoreAbility.hasAbility(this.player, this.getClass()) || bPlayer.isOnCooldown(this) || GeneralMethods.isRegionProtectedFromBuild(this, this.player.getLocation())) {
			return;
		}
		/*
		this.player.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(100000, 1));
		this.player.setInvulnerable(true);
		this.player.setAllowFlight(true);
		 */
		this.player.setGameMode(GameMode.SPECTATOR);
		this.player.setFlying(true);

		setFields();
		start();
		bPlayer.addCooldown(this);
	}

	public void setFields() {

		//Config
		this.cooldown = Zephyrus.plugin.getConfig().getLong("Zephyrus.Air.SpiritualProjection.Cooldown");
		this.duration = Zephyrus.plugin.getConfig().getLong("Zephyrus.Air.SpiritualProjection.Duration");
		this.range = Zephyrus.plugin.getConfig().getDouble("Zephyrus.Air.SpiritualProjection.Range");



		//Set variables
		this.starttime = System.currentTimeMillis();
		this.origin = player.getLocation();
		sitting = (ArmorStand) origin.getWorld().spawn(origin, ArmorStand.class);
		sitting.setLeftLegPose(new EulerAngle(Math.toRadians(270), 0, 0));
		sitting.setRightLegPose(new EulerAngle(Math.toRadians(270), 0, 0));

		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
		skullmeta.setOwner(player.getName());
		skull.setItemMeta(skullmeta);
		sitting.setHelmet(new ItemStack(skull));

		sitting.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		sitting.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		sitting.setBoots(new ItemStack(Material.LEATHER_BOOTS));

		hurtbox = (Bat) origin.getWorld().spawnEntity(origin, EntityType.BAT);
		hurtbox.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(100000, 1));
		sitting.addPassenger(hurtbox);

		AttributeInstance attribute = hurtbox.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		attribute.setBaseValue(40.0D);
		hurtbox.setHealth(40.0D);
		hurtbox.setAI(false);

		sitting.setInvulnerable(true);
		sitting.setArms(true);
		sitting.setBasePlate(false);

		sitting.setMetadata("ArmorStand", new FixedMetadataValue(ProjectKorra.plugin, this));
		this.loc = origin.clone();

		bPlayer.blockChi();
		bar = Bukkit.createBossBar("SpiritualProjection", BarColor.BLUE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
		bar.setProgress(1.0);
		bar.addPlayer(player);

	}

	@Override
	public long getCooldown() {
		return (long) cooldown;
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public String getName() {
		return "SpiritualProjection";
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
		if (player.isDead() || !player.isOnline() || GeneralMethods.isRegionProtectedFromBuild(this, player.getLocation())){
			this.remove();
		}

		timeleft = (starttime + duration) - System.currentTimeMillis();
		if (timeleft <= 0) {
			this.remove();
		}
		else {
			double ratio = timeleft/duration;
			bar.setProgress(ratio);
		}

		this.loc = player.getLocation();

		if (loc.distance(origin) > range) {
			this.remove();
		}

		ParticleEffect.SOUL.display(loc, 3, 0.5, 0.5, 0.5);
		

		if (hurtbox.getHealth() < 40) {
			ActionBar.sendActionBar(ChatColor.DARK_RED + "YOUR BODY IS UNDER ATTACK!!", player);
		}
	}

	public void onClick() {
		this.remove();
	}


	@Override
	public void remove() {
		bPlayer.addCooldown(this);
		bPlayer.unblockChi();
		/*
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.setInvulnerable(false);
		player.setAllowFlight(false);
		player.setFlying(false);
		 */
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(sitting);
		
		double damage = 40.0D - hurtbox.getHealth();
		DamageHandler.damageEntity(player, damage, this);

		sitting.remove();
		hurtbox.remove();
		bar.removeAll();
		super.remove();
		return;
	}

	@Override
	public String getDescription() {
		return "By: __Yujiro\n"
				+ "Master airbenders who have a strong connection with their spiritual side are capable of projecting their spirits into other locations."; 
	}

	@Override
	public String getInstructions() {
		return "Click to leave your body and create a projection."; 
	}
}















