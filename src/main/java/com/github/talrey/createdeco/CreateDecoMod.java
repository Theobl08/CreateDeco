package com.github.talrey.createdeco;

import com.mojang.logging.LogUtils;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(CreateDecoMod.MOD_ID)
public class CreateDecoMod {
    public static final String MOD_ID = "createdeco";
    public static final String NAME = "Create Deco";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateDecoMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        CreativeTabs.register(modEventBus);
        CreateDecoMod.REGISTRATE.registerEventListeners(modEventBus);
        ItemRegistry.init();
        BlockRegistry.init();

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (CreateDecoMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class Events {
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            event.getGenerator().addProvider(true, REGISTRATE.setDataProvider(new RegistrateDataProvider(REGISTRATE, MOD_ID, event)));
        }
    }
}
