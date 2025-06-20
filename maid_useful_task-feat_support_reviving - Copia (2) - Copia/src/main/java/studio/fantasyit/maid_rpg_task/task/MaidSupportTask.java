package studio.fantasyit.maid_rpg_task.task;

import com.github.tartaricacid.touhoulittlemaid.api.task.IMaidTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import studio.fantasyit.maid_rpg_task.Config;
import studio.fantasyit.maid_rpg_task.MaidRpgTask;
import studio.fantasyit.maid_rpg_task.behavior.PlayerReviveBehavior;
import studio.fantasyit.maid_rpg_task.behavior.SupportEffectBehavior;
import studio.fantasyit.maid_rpg_task.compat.PlayerRevive;
import studio.fantasyit.maid_rpg_task.menu.MaidReviveConfigGui;

import java.util.ArrayList;
import java.util.List;

public class MaidSupportTask implements IMaidTask {
    public static final ResourceLocation UID = new ResourceLocation(MaidRpgTask.MODID, "support");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public ItemStack getIcon() {
        return Items.POTION.getDefaultInstance();
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound(EntityMaid entityMaid) {
        return null;
    }

    @Override
    public boolean isEnable(EntityMaid maid) {
        return PlayerRevive.isEnable() && Config.enableReviveTask;
    }

    @Override
    public boolean enableLookAndRandomWalk(EntityMaid maid) {
        return false;
    }

    @Override
    public boolean enablePanic(EntityMaid maid) {
        return false;
    }

    @Override
    public List<Pair<Integer, BehaviorControl<? super EntityMaid>>> createBrainTasks(EntityMaid entityMaid) {
        ArrayList<Pair<Integer, BehaviorControl<? super EntityMaid>>> ret = new ArrayList<>();
        ret.add(Pair.of(1, new PlayerReviveBehavior()));
        ret.add(Pair.of(1, new SupportEffectBehavior()));
        return ret;
    }

    @Override
    public MenuProvider getTaskConfigGuiProvider(EntityMaid maid) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("");
            }

            @Override
            public AbstractContainerMenu createMenu(int index, Inventory playerInventory, Player player) {
                return new MaidReviveConfigGui.Container(index, playerInventory, maid.getId());
            }
        };
    }

}
