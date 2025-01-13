package tech.mcprison.prison.spigot.game;

import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.enchantments.Enchantment;

import tech.mcprison.prison.Prison;
import tech.mcprison.prison.internal.ItemStack;
import tech.mcprison.prison.internal.Player;
import tech.mcprison.prison.internal.PlayerUtil;
import tech.mcprison.prison.spigot.block.SpigotItemStack;
import tech.mcprison.prison.spigot.compat.Compatibility;
import tech.mcprison.prison.spigot.compat.SpigotCompatibility;

public class SpigotPlayerUtil
		extends PlayerUtil
{
	private SpigotPlayer spigotPlayer = null;
	
	public SpigotPlayerUtil( UUID playerUuid ) {
		super( playerUuid );
		
		Object p = Prison.get().getPlatform().getPlayer( playerUuid ).orElse( null );
		if ( p != null && p instanceof SpigotPlayer ) {
			this.spigotPlayer = (SpigotPlayer) p;
		}
	}
	public SpigotPlayerUtil( Player player ) {
		super( player.getUUID() );
		
		if ( player != null && player instanceof SpigotPlayer ) {
			this.spigotPlayer = (SpigotPlayer) player;
		}
	}

	@Override
	public boolean isActive() {
		return spigotPlayer != null;
	}
	
	@Override
	public double getHealth() {
		double results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getWrapper().getHealth();
		}
		return results;
	}

	@Override
	public double getMaxHealth() {
		double results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getMaxHealth();
		}
		return results;
	}

	@Override
	public void setMaxHealth( double maxHealth ) {
		if ( isActive() ) {
			spigotPlayer.setMaxHealth( maxHealth );
		}
	}

	@Override
	public int getMaximumAir() {
		int results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getMaximumAir();
		}
		return results;
	}

	@Override
	public int getRemainingAir() {
		int results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getRemainingAir();
		}
		return results;
	}

	@Override
	public int getFoodLevel() {
		int results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getFoodLevel();
		}
		return results;
	}

	@Override
	public void setFoodLevel( int foodLevel ) {
		if ( isActive() ) {
			spigotPlayer.getWrapper().setFoodLevel( foodLevel );
		}
	}
	
	@Override
	public double getFoodExhaustion() {
		double results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getFoodExhaustion();
		}
		return results;
	}
	
	
	/**
 	 * <p>This increments the player's exhaustion level each time they
 	 * break a block.  The exhaustion level should increase only by 
 	 * 0.005 per block.</p>
 	 * 
 	 * <p>Since the player is swinging the pickaxe, the hunger should only apply 
 	 * when they break a target block that they actually hit, not all of the
 	 * blocks that are the product of an enchantment, or an explosion.
 	 * </p>
 	 * 
 	 * https://minecraft.fandom.com/wiki/Hunger
	 */
	@Override
	public void incrementFoodExhaustionBlockBreak() {
		if ( isActive() ) {
			spigotPlayer.incrementFoodExhaustionBlockBreak();
		}
	}
	
	@Override
	public double getFoodSaturation() {
		double results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getWrapper().getSaturation();
		}
		return results;
	}

	@Override
	public double getExp() {
		double results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getExp();
		}
		return results;
	}
	
	@Override
	public int getExpToLevel() {
		int results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getWrapper().getExpToLevel();
		}
		return results;
	}

	@Override
	public int getLevel() {
		int results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getLevel();
		}
		return results;
	}

	@Override
	public double getWalkSpeed() {
		double results = 0;
		if ( isActive() ) {
			results = spigotPlayer.getExp();
		}
		return results;
	}
	
	public SpigotItemStack getItemInHand() {
		SpigotItemStack itemInHand = null;
		
		if ( isActive() && spigotPlayer.getWrapper() != null ) {
			itemInHand = SpigotCompatibility.getInstance()
								.getPrisonItemInMainHand( spigotPlayer.getWrapper() );
		}
		return itemInHand;
	}
	
	@Override
	public ItemStack getPrisonItemStack() {
		return getItemInHand();
	}
	
	@Override
	public String getItemInHandDisplayID()
	{
		String results = "";
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			results = itemStack.getBukkitStack().getType().name();
		}
		
		return results;
	}
	
	@Override
	public String getItemInHandName() {
		String results = "";
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			results = itemStack.getName();
		}
		
		return results;
	}

	@Override
	public String getItemInHandDisplayName() {
		String results = "";
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null && itemStack.getDisplayName() != null ) {
			results = itemStack.getDisplayName();
		}
		
		return results;
	}
	
	/**
	 * <p>This will return the item type of what the player is holding in their main hand, 
	 * such as pickaxe, shovel,
	 * axe, boots, bow, shears, etc...  If what is in the player's main is not one of 
	 * these item types, then it will return an empty string.
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public String getItemInHandItemType() {
		String results = "";
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			ItemType iType = ItemType.fromString( itemStack.getName() );
			if ( iType != null ) {
				results = iType.name();
			}
		}
		
		return results;
	}
	
	/*
	 * <p>The item material is the material type that an item is made from.
	 * This will only return a value if getItemInHandItemType() returns a 
	 * value, otherwise this will return an empty String.
	 * </p>
	 * 
	 */
	@Override
	public String getItemInHandItemMaterial() {
		String results = "";
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			ItemMaterial iMaterial = ItemMaterial.fromString( itemStack.getName() );
			if ( iMaterial != null ) {
				results = iMaterial.name();
			}
		}
		
		return results;
	}
	
	@Override
	public int getItemInHandDurabilityUsed() {
		int results = 0;
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			Compatibility compat = SpigotCompatibility.getInstance();
			results = compat.getDurability( itemStack );
		}
		
		return results;
	}
	
	@Override
	public int getItemInHandDurabilityMax() {
		int results = 0;
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			Compatibility compat = SpigotCompatibility.getInstance();
			results = compat.getDurabilityMax( itemStack );
		}
		
		return results;
	}

	@Override
	public int getItemInHandDurabilityRemaining() {
		int results = 0;
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			Compatibility compat = SpigotCompatibility.getInstance();
			int durabilityUsed = compat.getDurability( itemStack );
			int durabilityMax = compat.getDurabilityMax( itemStack );
			results = durabilityMax - durabilityUsed;
		}
		
		return results;
	}
	
	@Override
	public double getItemInHandDurabilityPercent() {
		double results = 0;
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null ) {
			Compatibility compat = SpigotCompatibility.getInstance();
			int durabilityUsed = compat.getDurability( itemStack );
			int durabilityMax = compat.getDurabilityMax( itemStack );
			
			results = (durabilityUsed / (double) durabilityMax) * 100.0d;
		}
		
		return results;
	}
	
	
	private int getEnchantment( String enchant ) {
		int results = 0;
		
		Enchantment enchantment = null;
		
		try
		{
			@SuppressWarnings( "unused" )
			Method methodGetKey = Enchantment.LUCK_OF_THE_SEA.getClass().getMethod( "getKey" );
			
			for ( Enchantment e : Enchantment.values() ) {
				if (e.getKey().getKey().equalsIgnoreCase( enchant ) ) {
					enchantment = e;
					break;
				}
			}
		}
		catch ( NoSuchMethodException | SecurityException e1 ) {
			// Ignore the fact that the method does not exist, which just means this is
			// spigot 1.8 or so.

			for ( Enchantment e : Enchantment.values() ) {
				if ( e.toString().toLowerCase().contains( enchant.toLowerCase() ) ) {
					enchantment = e;
				}
			}
		}
		
//		for ( Enchantment e : Enchantment.values() ) {
//			if (e.getKey().getKey().equalsIgnoreCase( enchant ) ) {
//				enchantment = e;
//				break;
//			}
//		}
		
		if ( enchantment != null ) {
			
			SpigotItemStack itemStack = getItemInHand();
			
			if ( itemStack != null && itemStack.getBukkitStack() != null) {
				try {
					if ( itemStack.getBukkitStack().containsEnchantment( enchantment ) &&
							itemStack.getBukkitStack().getEnchantments() != null ) {
						results = itemStack.getBukkitStack()
								.getEnchantmentLevel(enchantment);
					}
				}
				catch ( NullPointerException e ) {
					// Ignore. This happens when a TokeEnchanted tool is used when TE is not installed anymore.
					// It throws this exception:  Caused by: java.lang.NullPointerException: null key in entry: null=5
				}
			}
		}
		
		return results;
	}

	@Override
	public int getItemInHandEnchantmentFortune() {
		return getEnchantment( "LOOT_BONUS_BLOCKS" );
	}
	
	@Override
	public int getItemInHandEnchantmentEfficency() {
		return getEnchantment( "DIG_SPEED" );
	}
	
	@Override
	public int getItemInHandEnchantmentSilkTouch() {
		return getEnchantment( "SILK_TOUCH" );
	}
	
	@Override
	public int getItemInHandEnchantmentUnbreaking() {
		return getEnchantment( "UNBREAKING" );
	}
	
	@Override
	public int getItemInHandEnchantmentMending() {
		return getEnchantment( "MENDING" );
	}
	
	@Override
	public int getItemInHandEnchantmentLuck() {
		return getEnchantment( "LUCK" );
	}
	

//	public String getEnchantments() {
//		StringBuilder sb = new StringBuilder();
//		
//		SpigotItemStack itemStack = getItemInHand();
//		
//		if ( itemStack != null && itemStack.getBukkitStack() != null) {
//			try {
//				
//				Set<Enchantment> keys = itemStack.getBukkitStack().getEnchantments().keySet();
//				
//				for ( Enchantment key : keys ) {
//					Integer value = itemStack.getBukkitStack().getEnchantments().get( key );
//					
//					if ( value != null ) {
//						if ( sb.length() > 0 ) {
//							sb.append( ", " );
//						}
//						sb.append( key.getName() ).append( ": " ).append( value );
//						key.getItemTarget().toString()
//					}
//				}
//				
//			}
//			catch ( NullPointerException e ) {
//				// Ignore. This happens when a TokeEnchanted tool is used when TE is not installed anymore.
//				// It throws this exception:  Caused by: java.lang.NullPointerException: null key in entry: null=5
//			}
//		}
//	}
	
	public String getItemInHandLore() {
		StringBuilder sb = new StringBuilder();
		
		SpigotItemStack itemStack = getItemInHand();
		
		if ( itemStack != null && itemStack.getLore() != null && itemStack.getLore().size() > 0 ) {
			for ( String lore : itemStack.getLore() )
			{
				if ( lore.trim().length() > 0 ) {
					if ( sb.length() > 0 ) {
						sb.append( "\\" );
					}
					sb.append( lore );
				}
			}
		}
		
		return sb.toString();
	}
	
	public SpigotPlayer getSpigotPlayer() {
		return spigotPlayer;
	}
	public void setSpigotPlayer( SpigotPlayer spigotPlayer ) {
		this.spigotPlayer = spigotPlayer;
	}



}
