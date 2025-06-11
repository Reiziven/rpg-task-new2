package studio.fantasyit.maid_rpg_task.task;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import studio.fantasyit.maid_rpg_task.util.MemoryUtil;
import studio.fantasyit.maid_rpg_task.vehicle.MaidVehicleControlType;
import studio.fantasyit.maid_rpg_task.vehicle.MaidVehicleManager;

public interface IMaidVehicleControlTask {
    BlockPos findTarget(ServerLevel level, EntityMaid maid);

    default void tick(ServerLevel level, EntityMaid maid) {
        MaidVehicleControlType allowHandleVehicle = MemoryUtil.getAllowHandleVehicle(maid);
        BlockPos targetPos = findTarget(level, maid);
        MaidVehicleManager.getControllableVehicle(maid).ifPresent(vehicle -> {
            if (targetPos == null) {
                vehicle.maidStopControlVehicle(maid);
            } else {
                vehicle.maidControlVehicle(maid, allowHandleVehicle, targetPos);
            }
        });
    }
}
