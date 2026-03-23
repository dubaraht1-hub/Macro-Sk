package dev.lvstrng.argon.module.modules.combat;

import dev.lvstrng.argon.module.Module;
import dev.lvstrng.argon.module.Category;
// Fix: Import path ko aapke base ke mutabiq adjust kiya hai
import dev.lvstrng.argon.setting.settings.NumberSetting;

public class DelayCrasher extends Module {
    
    // Slider setup: Name, Min, Max, Default, Increment
    public final NumberSetting delay = new NumberSetting("Delay (sec)", 0, 10, 5, 1);

    private long startTime;
    private boolean armed = false;

    public DelayCrasher() {
        super("DelayCrasher", Category.COMBAT, "Crashes the game after a set delay.");
        addSettings(delay);
    }

    @Override
    public void onEnable() {
        startTime = System.currentTimeMillis();
        armed = true;
    }

    @Override
    public void onTick() {
        if (!armed) return;

        // Check if delay is over (seconds to milliseconds conversion)
        if (System.currentTimeMillis() - startTime >= delay.getValue() * 1000) {
            armed = false;
            // Immediate crash trigger
            throw new RuntimeException("Scheduled Game Crash!");
        }
    }

    @Override
    public void onDisable() {
        armed = false;
        super.onDisable();
    }
}
