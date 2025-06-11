package studio.fantasyit.maid_rpg_task.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import studio.fantasyit.maid_rpg_task.MaidRpgTask;
import studio.fantasyit.maid_rpg_task.task.IMaidVehicleControlTask;
import studio.fantasyit.maid_rpg_task.vehicle.MaidVehicleManager;

@Mod.EventBusSubscriber(modid = MaidRpgTask.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MaidTickEvent {
    @SubscribeEvent
    public static void onTick(com.github.tartaricacid.touhoulittlemaid.api.event.MaidTickEvent event) {
        if (event.getMaid().level() instanceof ServerLevel sl)
            if (event.getMaid().getTask() instanceof IMaidVehicleControlTask imvc && event.getMaid().getVehicle() != null) {
                imvc.tick(sl, event.getMaid());
                MaidVehicleManager.syncVehicleParameter(event.getMaid());
            }
    }
}
