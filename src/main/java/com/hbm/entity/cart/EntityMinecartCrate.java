package com.hbm.entity.cart;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartCrate extends EntityMinecartContainerBase {

	public EntityMinecartCrate(World world) {
		super(world);
	}

	public EntityMinecartCrate(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z);
		if(stack.hasTagCompound()) {
			for(int i = 0; i < getSizeInventory(); i++) {
				setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
			}
		}
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, player)))
			return true;
		if(!this.worldObj.isRemote) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModItems.guiID_cart_crate, worldObj, this.getEntityId(), 0, 0);
		}

		return true;
	}

	@Override
	public Block func_145817_o() {
		return ModBlocks.crate_steel;
	}

	@Override
	public int getSizeInventory() {
		return 9 * 6;
	}

	@Override
	public void killMinecart(DamageSource p_94095_1_) {
		this.setDead();
		ItemStack itemstack = DictFrame.fromOne(ModItems.cart, EnumMinecart.CRATE);
		
		NBTTagCompound nbt = new NBTTagCompound();
			
			for(int i = 0; i < getSizeInventory(); i++) {
				
			ItemStack stack = getStackInSlot(i);
			if(stack == null)
				continue;
			
			NBTTagCompound slot = new NBTTagCompound();
			stack.writeToNBT(slot);
			nbt.setTag("slot" + i, slot);
		}
		
		if(!nbt.hasNoTags()) {
			itemstack.stackTagCompound = nbt;
		}

		if(this.func_95999_t() != null) {
			itemstack.setStackDisplayName(this.func_95999_t());
		}

		this.entityDropItem(itemstack, 0.0F);
	}

	@Override
	public ItemStack getCartItem() {
		return DictFrame.fromOne(ModItems.cart, EnumMinecart.CRATE);
	}
}