package studio.fantasyit.maid_rpg_task.util;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import studio.fantasyit.maid_rpg_task.memory.*;
import studio.fantasyit.maid_rpg_task.registry.MemoryModuleRegistry;
import studio.fantasyit.maid_rpg_task.vehicle.MaidVehicleControlType;

import java.util.List;
import java.util.Optional;

public class MemoryUtil {
    public static @Nullable BlockTargetMemory getDestroyTargetMemory(EntityMaid maid) {
        Optional<BlockTargetMemory> memory = maid.getBrain().getMemory(MemoryModuleRegistry.DESTROY_TARGET.get());
        return memory.orElse(null);
    }

    public static void setDestroyTargetMemory(EntityMaid maid, List<BlockPos> blockPosSet) {
        maid.getBrain().setMemory(MemoryModuleRegistry.DESTROY_TARGET.get(), new BlockTargetMemory(blockPosSet));
    }

    public static void clearDestroyTargetMemory(EntityMaid maid) {
        maid.getBrain().eraseMemory(MemoryModuleRegistry.DESTROY_TARGET.get());
    }

    public static void clearTarget(EntityMaid maid) {
        maid.getBrain().eraseMemory(InitEntities.TARGET_POS.get());
        maid.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    public static @Nullable BlockPos getTargetPos(EntityMaid maid) {
        Optional<PositionTracker> memory = maid.getBrain().getMemory(InitEntities.TARGET_POS.get());
        return memory.map(PositionTracker::currentBlockPosition).orElse(null);
    }

    public static @Nullable BlockPos getPlaceTarget(EntityMaid maid) {
        Optional<BlockPos> memory = maid.getBrain().getMemory(MemoryModuleRegistry.PLACE_TARGET.get());
        return memory.orElse(null);
    }

    public static void setPlaceTarget(EntityMaid maid, BlockPos blockPos) {
        maid.getBrain().setMemory(MemoryModuleRegistry.PLACE_TARGET.get(), blockPos);
    }

    public static void clearPlaceTarget(EntityMaid maid) {
        maid.getBrain().eraseMemory(MemoryModuleRegistry.PLACE_TARGET.get());
    }

    public static void setLookAt(EntityMaid maid, BlockPos pos) {
        maid.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(pos));
    }

    public static BlockUpContext getBlockUpContext(EntityMaid maid) {
        Brain<EntityMaid> brain = maid.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleRegistry.BLOCK_UP_TARGET.get())) {
            brain.setMemory(MemoryModuleRegistry.BLOCK_UP_TARGET.get(), new BlockUpContext());
        }
        return brain.getMemory(MemoryModuleRegistry.BLOCK_UP_TARGET.get()).get();
    }

    public static BlockValidationMemory getBlockValidationMemory(EntityMaid maid) {
        Brain<EntityMaid> brain = maid.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleRegistry.BLOCK_VALIDATION.get())) {
            brain.setMemory(MemoryModuleRegistry.BLOCK_VALIDATION.get(), new BlockValidationMemory());
        }
        return brain.getMemory(MemoryModuleRegistry.BLOCK_VALIDATION.get()).get();
    }

    public static void setTarget(EntityMaid maid, BlockPos targetPos, float speed) {
        maid.getBrain().setMemory(InitEntities.TARGET_POS.get(), new BlockPosTracker(targetPos));
        BehaviorUtils.setWalkAndLookTargetMemories(maid, targetPos, speed, 0);
    }

    public static CurrentWork getCurrent(EntityMaid maid) {
        return maid.getBrain().getMemory(MemoryModuleRegistry.CURRENT_WORK.get()).orElse(CurrentWork.IDLE);
    }

    public static void setCurrent(EntityMaid maid, CurrentWork currentWork) {
        maid.getBrain().setMemory(MemoryModuleRegistry.CURRENT_WORK.get(), currentWork);
    }

    public static void setCommonBlockCache(EntityMaid maid, BlockPos pos) {
        maid.getBrain().setMemory(MemoryModuleRegistry.COMMON_BLOCK_CACHE.get(), pos);
    }

    public static BlockPos getCommonBlockCache(EntityMaid maid) {
        return maid.getBrain().getMemory(MemoryModuleRegistry.COMMON_BLOCK_CACHE.get()).orElse(null);
    }

    public static void setAllowHandleVehicle(EntityMaid maid, MaidVehicleControlType allow) {
        maid.getBrain().setMemory(MemoryModuleRegistry.IS_ALLOW_HANDLE_VEHICLE.get(), allow);
    }

    public static MaidVehicleControlType getAllowHandleVehicle(EntityMaid maid) {
        return maid.getBrain().getMemory(MemoryModuleRegistry.IS_ALLOW_HANDLE_VEHICLE.get()).orElse(MaidVehicleControlType.NONE);
    }

    public static void clearCommonBlockCache(EntityMaid maid) {
        maid.getBrain().eraseMemory(MemoryModuleRegistry.COMMON_BLOCK_CACHE.get());
    }

    public static void setLocateItem(EntityMaid maid, ItemStack item) {
        maid.getBrain().setMemory(MemoryModuleRegistry.LOCATE_ITEM.get(), item);
    }
    public static ItemStack getLocateItem(EntityMaid maid) {
        return maid.getBrain().getMemory(MemoryModuleRegistry.LOCATE_ITEM.get()).orElse(ItemStack.EMPTY);
    }
}
