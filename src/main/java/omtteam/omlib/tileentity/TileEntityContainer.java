package omtteam.omlib.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import omtteam.omlib.util.InvUtil;
import omtteam.omlib.util.ItemStackList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by Keridos on 05/12/2015.
 * This Class is the abstract class handling Inventory Functions.
 */
@SuppressWarnings("WeakerAccess")
public abstract class TileEntityContainer extends TileEntityOwnedBlock {
    protected IItemHandlerModifiable inventory;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        NBTTagCompound inv = new NBTTagCompound();
        writeInventoryToNBT(nbtTagCompound);
        nbtTagCompound.setTag("Inventory", inv);
        return nbtTagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        NBTTagCompound inv = nbtTagCompound.getCompoundTag("Inventory");
        readInventoryFromNBT(nbtTagCompound);
    }

    private void readInventoryFromNBT(NBTTagCompound tagCompound)
    {
        if (tagCompound.getTagId("Inventory") == Constants.NBT.TAG_LIST)
        {
            NBTTagList tagList = tagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < inventory.getSlots(); i++)
            { inventory.setStackInSlot(i, ItemStack.EMPTY); }

            for (int i = 0; i < tagList.tagCount(); i++)
            {
                NBTTagCompound tag = (NBTTagCompound) tagList.get(i);
                byte slot = tag.getByte("Slot");

                if (slot < inventory.getSlots())
                {
                    inventory.setStackInSlot(slot, new ItemStack(tag));
                }
            }

            return;
        }

        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, tagCompound.getTag("Slots"));
    }

    private void writeInventoryToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setTag("Slots", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) inventory;
        }
        return super.getCapability(capability, facing);
    }

    public IItemHandlerModifiable getInventory() {
        return inventory;
    }
}
