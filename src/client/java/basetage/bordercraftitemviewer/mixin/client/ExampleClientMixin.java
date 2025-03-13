package basetage.bordercraftitemviewer.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ExampleClientMixin {
	@Shadow @Final private static Logger LOGGER;

	@Inject(at = @At("HEAD"), method = "run")
	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftClient.run()
		LOGGER.info("Hello from client!");
	}
}