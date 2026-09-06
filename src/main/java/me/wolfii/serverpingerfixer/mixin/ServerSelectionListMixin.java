package me.wolfii.serverpingerfixer.mixin;

import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Mixin(ServerSelectionList.class)
public class ServerSelectionListMixin {
    @Mutable
    @Final
    @Shadow
    static ThreadPoolExecutor THREAD_POOL;

    @Unique
    private static final long TIMEOUT_SECONDS = 30L;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void overrideThreadPool(CallbackInfo ci) {
        ThreadFactory vanillaThreadFactory = THREAD_POOL.getThreadFactory();
        THREAD_POOL.shutdown();
        THREAD_POOL = new ThreadPoolExecutor(
            1,
            Integer.MAX_VALUE,
            TIMEOUT_SECONDS,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            vanillaThreadFactory
        );
    }
}
