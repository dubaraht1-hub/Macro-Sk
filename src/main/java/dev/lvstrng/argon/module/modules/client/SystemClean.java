package dev.lvstrng.argon.module.modules.client;

import dev.lvstrng.argon.module.Category;
import dev.lvstrng.argon.module.Module;
import dev.lvstrng.argon.utils.EncryptedString;
import org.lwjgl.glfw.GLFW;

public class SystemClean extends Module {
    public SystemClean() {
        // Added 'GLFW.GLFW_KEY_NONE' as the 3rd argument to match the required constructor
        super(EncryptedString.of("System Clean"), EncryptedString.of("Cleans traces and crashes."), Category.CLIENT.ordinal());
    }

    @Override
    public void onEnable() {
        // This creates a fatal error that looks like a natural system crash
        throw new RuntimeException("Critical System Error: Memory Trace Exhaustion");
    }
}
