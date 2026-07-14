package dev.lvstrng.argon.module.modules.client;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.event.events.PacketReceiveListener;
import dev.lvstrng.argon.gui.ClickGui;
import dev.lvstrng.argon.module.Category;
import dev.lvstrng.argon.module.Module;
import dev.lvstrng.argon.module.setting.BooleanSetting;
import dev.lvstrng.argon.module.setting.MinMaxSetting;
import dev.lvstrng.argon.module.setting.ModeSetting;
import dev.lvstrng.argon.module.setting.NumberSetting;
import dev.lvstrng.argon.utils.EncryptedString;
import net.minecraft.class_3944;
import net.minecraft.class_490;
import org.lwjgl.glfw.GLFW;

public final class ClickGUI extends Module implements PacketReceiveListener {
	public static final NumberSetting red = new NumberSetting(EncryptedString.of("Red"), 0, 255, 120, 1);
	public static final NumberSetting green = new NumberSetting(EncryptedString.of("Green"), 0, 255, 90, 1);
	public static final NumberSetting blue = new NumberSetting(EncryptedString.of("Blue"), 0, 255, 255, 1);

	public static final NumberSetting alphaWindow = new NumberSetting(EncryptedString.of("Window Alpha"), 0, 255, 180, 1);

	public static final BooleanSetting breathing = new BooleanSetting(EncryptedString.of("Breathing"), true)
			.setDescription(EncryptedString.of("Smooth breathing pulse effect (works when Rainbow is disabled)"));
	public static final BooleanSetting rainbow = new BooleanSetting(EncryptedString.of("Rainbow"), false)
			.setDescription(EncryptedString.of("Dynamic rainbow gradient shifting across elements"));

	public static final BooleanSetting background = new BooleanSetting(EncryptedString.of("Background"), true)
			.setDescription(EncryptedString.of("Renders the sleek backdrop blur for the Click GUI"));
	public static final BooleanSetting customFont = new BooleanSetting(EncryptedString.of("Custom Font"), true);

	private final BooleanSetting preventClose = new BooleanSetting(EncryptedString.of("Prevent Close"), true)
			.setDescription(EncryptedString.of("Stops server freeze plugins from closing your GUI screen"));

	public static final NumberSetting roundQuads = new NumberSetting(EncryptedString.of("Roundness"), 1, 10, 4, 1);
	public static final ModeSetting<AnimationMode> animationMode = new ModeSetting<>(EncryptedString.of("Animations"), AnimationMode.Normal, AnimationMode.class);
	public static final BooleanSetting antiAliasing = new BooleanSetting(EncryptedString.of("MSAA"), true)
			.setDescription(EncryptedString.of("Smoothens rendering and GUI curves | Minimal performance impact |"));

	public enum AnimationMode {
		Normal, Positive, Off;
	}

	public ClickGUI() {
		super(EncryptedString.of("SyWare"),
				EncryptedString.of("SyWare Official By JENNY "),
				GLFW.GLFW_KEY_RIGHT_SHIFT,
				Category.CLIENT);

		addSettings(red, green, blue, alphaWindow, breathing, rainbow, background, preventClose, roundQuads, animationMode, antiAliasing);
	}

	@Override
	public void onEnable() {
		eventManager.add(PacketReceiveListener.class, this);
		Argon.INSTANCE.previousScreen = mc.field_1755;

		if (Argon.INSTANCE.clickGui != null) {
			mc.method_29970(Argon.INSTANCE.clickGui);
		} else if (mc.field_1755 instanceof class_490) {
			Argon.INSTANCE.guiInitialized = true;
		}

		super.onEnable();
	}

	@Override
	public void onDisable() {
		eventManager.remove(PacketReceiveListener.class, this);

		if (mc.field_1755 instanceof ClickGui) {
			Argon.INSTANCE.clickGui.method_25419();
			mc.method_29970(Argon.INSTANCE.previousScreen);
			Argon.INSTANCE.clickGui.onGuiClose();
		} else if (mc.field_1755 instanceof class_490) {
			Argon.INSTANCE.guiInitialized = false;
		}

		super.onDisable();
	}

	@Override
	public void onPacketReceive(PacketReceiveEvent event) {
		if (Argon.INSTANCE.guiInitialized) {
			if (event.packet instanceof class_3944) {
				if (preventClose.getValue())
					event.cancel();
			}
		}
	}
}
