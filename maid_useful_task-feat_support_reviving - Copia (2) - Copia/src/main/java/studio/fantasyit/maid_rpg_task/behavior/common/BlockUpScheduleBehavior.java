package studio.fantasyit.maid_rpg_task.behavior.common;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitEntities;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import oshi.util.tuples.Pair;
import studio.fantasyit.maid_rpg_task.memory.BlockUpContext;
import studio.fantasyit.maid_rpg_task.memory.CurrentWork;
import studio.fantasyit.maid_rpg_task.task.IMaidBlockUpTask;
import studio.fantasyit.maid_rpg_task.util.MaidUtils;
import studio.fantasyit.maid_rpg_task.util.MemoryUtil;

public class BlockUpScheduleBehavior extends Behavior<EntityMaid> {
    public BlockUpScheduleBehavior() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, InitEntities.TARGET_POS.get(), MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel p_22538_, EntityMaid p_22539_) {
        if (!switch (MemoryUtil.getCurrent(p_22539_)) {
            case BLOCKUP_UP, BLOCKUP_DOWN, BLOCKUP_DESTROY, IDLE -> true;
            default -> false;
        }) {
            return false;
        }
        return super.checkExtraStartConditions(p_22538_, p_22539_);
    }

    @Override
    protected void start(ServerLevel p_22540_, EntityMaid maid, long p_22542_) {
        BlockUpContext context = MemoryUtil.getBlockUpContext(maid);
        IMaidBlockUpTask task = (IMaidBlockUpTask) maid.getTask();
        if (context.hasTarget()) {
            if (context.getStatus() != BlockUpContext.STATUS.UP && !context.isOnLine(maid.blockPosition())) {
                context.clearStartTarget();
                MemoryUtil.setCurrent(maid, CurrentWork.IDLE);
            } else if (context.getStatus() != BlockUpContext.STATUS.IDLE && MemoryUtil.getTargetPos(maid) == null) {
                if (context.getStatus() == BlockUpContext.STATUS.DOWN) {
                    MemoryUtil.setTarget(maid, context.getTargetPos(), 0.5f);
                    MemoryUtil.setCurrent(maid, CurrentWork.BLOCKUP_DOWN);
                } else {
                    MemoryUtil.setTarget(maid, context.getStartPos(), 0.5f);
                    MemoryUtil.setCurrent(maid, CurrentWork.BLOCKUP_UP);
                }
            } else if (!context.isOnLine(maid.blockPosition()) || context.getStartPos().equals(context.getTargetPos())) {
                context.clearStartTarget();
                MemoryUtil.setCurrent(maid, CurrentWork.IDLE);
            } else if (context.getStatus() == BlockUpContext.STATUS.IDLE && !task.stillValid(maid, maid.blockPosition())) {
                maid.getBrain().setMemory(InitEntities.TARGET_POS.get(), new BlockPosTracker(context.getTargetPos()));
                context.setStatus(BlockUpContext.STATUS.DOWN);
                MemoryUtil.setCurrent(maid, CurrentWork.BLOCKUP_DOWN);
            }
        } else {
            Pair<BlockPos, BlockPos> targetPosBlockUp = task.findTargetPosBlockUp(maid, MaidUtils.getMaidRestrictCenter(maid), task.countMaxUsableBlockItems(maid));
            if (targetPosBlockUp != null) {
                context.setStartTarget(targetPosBlockUp.getA(), targetPosBlockUp.getB());
                maid.getBrain().setMemory(InitEntities.TARGET_POS.get(), new BlockPosTracker(targetPosBlockUp.getA()));
                BehaviorUtils.setWalkAndLookTargetMemories(maid, targetPosBlockUp.getA(), 0.5f, 0);
                context.setStatus(BlockUpContext.STATUS.UP);
                MemoryUtil.setCurrent(maid, CurrentWork.BLOCKUP_UP);
            }
        }
    }
}
