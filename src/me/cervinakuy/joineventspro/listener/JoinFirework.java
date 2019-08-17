package me.cervinakuy.joineventspro.listener;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import me.cervinakuy.joineventspro.util.Config;

public class JoinFirework implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore()) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Other.Firework") && p.hasPermission("jep." + joinType.toLowerCase() + ".firework")) {
			
            Firework firework = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            FireworkEffect.Type type = FireworkEffect.Type.BALL;
             
            Random random = new Random();
            int result = random.nextInt(4) + 1;
             
            if (result == 1) {
            	type = FireworkEffect.Type.BALL;
            } else if (result == 2) {
            	type = FireworkEffect.Type.BALL_LARGE;
            } else if (result == 3) {
            	type = FireworkEffect.Type.BURST;
            } else if (result == 4) {
            	type = FireworkEffect.Type.CREEPER;
            } else if (result == 5) {
            	type = FireworkEffect.Type.STAR;
            }
             
            random.nextInt(17);
            random.nextInt(17);
             
            FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(Color.RED).withFade(Color.BLUE).with(type).trail(random.nextBoolean()).build();
            fireworkMeta.addEffect(effect);
             
            int power = random.nextInt(2) + 1;
             
            fireworkMeta.setPower(power);
            firework.setFireworkMeta(fireworkMeta);
			
		}
		
	}

}
