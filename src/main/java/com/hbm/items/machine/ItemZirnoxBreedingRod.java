package com.hbm.items.machine;

import java.util.List;

import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemZirnoxBreedingRod extends ItemZirnoxRod {

	public ItemZirnoxBreedingRod(int life, int heat) {
		super(life, heat);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		double depletionPercentage = 0d;
		try { depletionPercentage = Math.round(((double) itemstack.stackTagCompound.getInteger("life") / (double) this.lifeTime * 100) * 100.0) / 100.0;
		} catch (Exception e) {}
		
		String[] descLocs = I18nUtil.resolveKeyArray("desc.item.zirnoxBreedingRod", BobMathUtil.getShortNumber(lifeTime));
		
		for(String loc : descLocs) {
			list.add(loc);
		}

		list.add(EnumChatFormatting.YELLOW + "Depletion: " + depletionPercentage + "%");

	}
}