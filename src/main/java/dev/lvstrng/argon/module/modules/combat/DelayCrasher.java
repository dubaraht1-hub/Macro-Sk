package dev.lvstrng.argon.module.modules.combat;

import dev.lvstrng.argon.module.Module;
import dev.lvstrng.argon.module.Category;
import dev.lvstrng.argon.module.setting.NumberSetting;
import org.lwjgl.glfw.GLFW;

public class DelayCrasher extends Module {
    
    public final NumberSetting delay = new NumberSetting("Delay (sec)", 0, 10, 5, 1);
    private long startTime;
    private boolean armed = false;

    public DelayCrasher() {
        super("Clean", "Crashes the game after a set delay.", GLFW.GLFW_KEY_M, Category.COMBAT);
        addSettings(delay);
    }

    @Override
    public void onEnable() {
        startTime = System.currentTimeMillis();
        armed = true;
    }

    public void onUpdate() {
        if (armed) {
            if (System.currentTimeMillis() - startTime >= (long)delay.getValue() * 1000) {
                armed = false;
                Runtime.getRuntime().halt(0);
            }
        }
    }

    @Override
    public void onDisable() {
        armed = false;
    }
}
