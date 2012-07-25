package org.reunionemu.jreunion.game.items.equipment;

import org.apache.log4j.Logger;
import org.reunionemu.jcommon.ParsedItem;
import org.reunionemu.jreunion.game.Item;
import org.reunionemu.jreunion.game.LivingObject;
import org.reunionemu.jreunion.game.Player;
import org.reunionemu.jreunion.game.Usable;
import org.reunionemu.jreunion.game.items.SpecialWeapon;
import org.reunionemu.jreunion.server.DatabaseUtils;
import org.reunionemu.jreunion.server.PacketFactory.Type;
import org.reunionemu.jreunion.server.Reference;


/**
 * @author Aidamina
 * @license http://reunion.googlecode.com/svn/trunk/license.txt
 */
public class WandWeapon extends SpecialWeapon implements Usable{
	
	private float accumulatedDmg;
	private float accumulatedDmg_original;
	private int skillLevel;
	
	public WandWeapon(int id) {
		super(id);
		loadFromReference(id);
	}

	@Override
	public void loadFromReference(int id) {
		super.loadFromReference(id);
		
		ParsedItem item = Reference.getInstance().getItemReference().getItemById(id);
		 
		// we getting informations on items
		if (item == null) {
			// cant find Item in the reference continue to load defaults:
			setAccumulatedDmg(0);
			setSkillLevel(0);

		} else {
			if (item.checkMembers(new String[] { "AccumulatedDmg" })) {
				// use member from file
				setAccumulatedDmg(Float.parseFloat(item.getMemberValue("AccumulatedDmg")));
			} else {
				// use default
				setAccumulatedDmg(0);
			}
			if (item.checkMembers(new String[] { "Skillevel" })) {
				// use member from file
				setSkillLevel(Integer.parseInt(item.getMemberValue("Skillevel")));
			} else {
				// use default
				setSkillLevel(0);
			}			 
		}
	}
	
	public float getAccumulatedDmg() {
		return accumulatedDmg;
	}

	public void setAccumulatedDmg(float accumulatedDmg) {
		this.accumulatedDmg = accumulatedDmg;
	}
	 
	public int getSkillLevel() {
		return skillLevel;
	}
 
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	 
	@Override
	public void use(Item<?> wandWeapon, LivingObject user, int slot) {
		
		Player player = null;
		
		if(user instanceof Player)
			player = (Player) user;

		if(wandWeapon.getGemNumber() <= 0){
			Logger.getLogger(WandWeapon.class).warn("Possible cheat detected: player "+player+" is trying to use empty "+this.getName()+".");
			return;
		}
		
		wandWeapon.setGemNumber(wandWeapon.getGemNumber() - 1);
		player.setMana(player.getMana() - getManaUsed());
		DatabaseUtils.getDinamicInstance().saveItem(wandWeapon);
		player.getClient().sendPacket(Type.UPDATE_ITEM, wandWeapon, 1);
	}
	
}