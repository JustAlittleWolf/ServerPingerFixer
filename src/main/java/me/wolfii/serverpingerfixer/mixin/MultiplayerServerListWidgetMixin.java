package me.wolfii.serverpingerfixer.mixin;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Mixin(MultiplayerServerListWidget.class)
public class MultiplayerServerListWidgetMixin {
    @Mutable
    @Final
    @Shadow
    static ThreadPoolExecutor SERVER_PINGER_THREAD_POOL;

    @Unique
    private static final int PINGER_THREAD_COUNT_OVERHEAD = 5;

    @Final
    @Shadow
    private List<MultiplayerServerListWidget.ServerEntry> servers;

    @Unique
    private static boolean threadpoolInitialized = false;

    @Inject(method = "updateEntries", at = @At("HEAD"))
    private void updateEntriesInject(CallbackInfo ci) {
        if (!threadpoolInitialized) {
            threadpoolInitialized = true;
            clearServerPingerThreadPool();
        }
        if (SERVER_PINGER_THREAD_POOL.getActiveCount() >= PINGER_THREAD_COUNT_OVERHEAD) {
            clearServerPingerThreadPool();
        }
    }

    @Unique
    private void clearServerPingerThreadPool() {
        SERVER_PINGER_THREAD_POOL.shutdownNow();
        SERVER_PINGER_THREAD_POOL = new ScheduledThreadPoolExecutor(
                servers.size() + PINGER_THREAD_COUNT_OVERHEAD,
                (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build()
        );
    }
}
