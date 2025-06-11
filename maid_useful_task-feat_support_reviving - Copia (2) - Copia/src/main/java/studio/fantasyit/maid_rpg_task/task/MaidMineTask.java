package studio.fantasyit.maid_rpg_task.task;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.core.BlockPos;

public class MaidMineTask implements IMaidBlockDestroyTask {
    @Override
    public boolean shouldDestroyBlock(EntityMaid maid, BlockPos pos) {
        return false;
    }

    @Override
    public boolean mayDestroy(EntityMaid maid, BlockPos pos) {
        return false;
    }

}
