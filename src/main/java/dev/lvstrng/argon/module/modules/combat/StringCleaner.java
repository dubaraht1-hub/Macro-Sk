package dev.lvstrng.argon.module.modules.combat;

import dev.lvstrng.argon.event.events.TickListener;
import dev.lvstrng.argon.module.Category;
import dev.lvstrng.argon.module.Module;
import dev.lvstrng.argon.utils.EncryptedString;
import org.lwjgl.glfw.GLFW;

public final class StringCleaner extends Module implements TickListener {

    public StringCleaner() {
        super(EncryptedString.of("StringCleaner"),
                EncryptedString.of("Clears all client strings and sets brand to fabric."),
                GLFW.GLFW_KEY_DELETE,
                Category.COMBAT);
    }

    @Override
    public void onEnable() {
        eventManager.add(TickListener.class, this);
        
        mc.options.hudHidden = true;

        if (mc.player != null) {
            mc.player.setServerBrand("fabric");
        }
        
        for (Module m : moduleManager.getModules()) {
            if (m != this && m.isEnabled()) {
                m.setEnabled(false);
            }
        }
        
        super.onEnable();
    }

    @Override
    public void onTick() {
        if (mc.currentScreen != null) {
            mc.setScreen(null);
        }
        
        this.setEnabled(false);
    }

    @Override
    public void onDisable() {
        eventManager.remove(TickListener.class, this);
        super.onDisable();
    }
}
