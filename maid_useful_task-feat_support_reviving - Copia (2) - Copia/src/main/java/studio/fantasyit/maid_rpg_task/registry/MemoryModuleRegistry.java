package studio.fantasyit.maid_rpg_task.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import studio.fantasyit.maid_rpg_task.MaidRpgTask;
import studio.fantasyit.maid_rpg_task.memory.*;
import studio.fantasyit.maid_rpg_task.vehicle.MaidVehicleControlType;

import java.util.Optional;

public class MemoryModuleRegistry {
    public static final DeferredRegister<MemoryModuleType<?>> REGISTER
            = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, MaidRpgTask.MODID);
    public static final RegistryObject<MemoryModuleType<BlockTargetMemory>> DESTROY_TARGET
            = REGISTER.register("block_targets", () -> new MemoryModuleType<>(Optional.of(BlockTargetMemory.CODEC)));
    public static final RegistryObject<MemoryModuleType<BlockPos>> PLACE_TARGET
            = REGISTER.register("place_target", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<BlockUpContext>> BLOCK_UP_TARGET
            = REGISTER.register("block_up", () -> new MemoryModuleType<>(Optional.of(BlockUpContext.CODEC)));
    public static final RegistryObject<MemoryModuleType<BlockValidationMemory>> BLOCK_VALIDATION
            = REGISTER.register("block_validation", () -> new MemoryModuleType<>(Optional.of(BlockValidationMemory.CODEC)));
    public static final RegistryObject<MemoryModuleType<BlockPos>> COMMON_BLOCK_CACHE
            = REGISTER.register("common_block_cache", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<ItemStack>> LOCATE_ITEM = REGISTER.register("locate_item", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<CurrentWork>> CURRENT_WORK = REGISTER.register("current_work", () -> new MemoryModuleType<>(Optional.empty()));
    public static final RegistryObject<MemoryModuleType<MaidVehicleControlType>> IS_ALLOW_HANDLE_VEHICLE = REGISTER.register("is_allow_handle_vehicle", () -> new MemoryModuleType<>(Optional.empty()));
    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
