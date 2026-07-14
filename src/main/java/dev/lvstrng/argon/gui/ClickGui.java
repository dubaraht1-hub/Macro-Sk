package dev.lvstrng.argon.gui;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.module.Category;
import dev.lvstrng.argon.module.modules.client.ClickGUI;
import dev.lvstrng.argon.utils.ColorUtils;
import dev.lvstrng.argon.utils.RenderUtils;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

import static dev.lvstrng.argon.Argon.mc;

public final class ClickGui extends class_437 {
	public List<Window> windows = new ArrayList<>();
	public Color currentColor;
	private static final StackWalker sw = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

	public ClickGui() {
		super(class_2561.method_43473());

		int offsetX = 40;
		for (Category category : Category.values()) {
			windows.add(new Window(offsetX, 50, 240, 28, category, this));
			offsetX += 260;
		}
	}

	public boolean isDraggingAlready() {
		for(Window window : windows)
			if(window.dragging)
				return true;

		return false;
	}

	@Override
	protected void method_56131() {
		if (field_22787 == null) {
			return;
		}
		super.method_56131();
	}

	@Override
	public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
		if (mc.field_1755 == this) {
			if (Argon.INSTANCE.previousScreen != null)
				Argon.INSTANCE.previousScreen.method_25394(context, 0, 0, delta);

			if (currentColor == null)
				currentColor = new Color(10, 10, 15, 0);
			else currentColor = new Color(10, 10, 15, currentColor.getAlpha());

			if (currentColor.getAlpha() != (ClickGUI.background.getValue() ? 160 : 0))
				currentColor = ColorUtils.smoothAlphaTransition(0.06F, ClickGUI.background.getValue() ? 160 : 0, currentColor);

			if (mc.field_1755 instanceof ClickGui)
				context.method_25294(0, 0, mc.method_22683().method_4480(), mc.method_22683().method_4507(), currentColor.getRGB());

			RenderUtils.unscaledProjection();
			mouseX *= (int) class_310.method_1551().method_22683().method_4495();
			mouseY *= (int) class_310.method_1551().method_22683().method_4495();
			super.method_25394(context, mouseX, mouseY, delta);

			for (Window window : windows) {
				window.render(context, mouseX, mouseY, delta);
				window.updatePosition(mouseX, mouseY, delta);
			}

			RenderUtils.scaledProjection();
		}
	}

	@Override
	public boolean method_25404(int keyCode, int scanCode, int modifiers) {
		for (Window window : windows)
			window.keyPressed(keyCode, scanCode, modifiers);

		return super.method_25404(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean method_25402(double mouseX, double mouseY, int button) {
		mouseX *= (int) class_310.method_1551().method_22683().method_4495();
		mouseY *= (int) class_310.method_1551().method_22683().method_4495();

		for (Window window : windows)
			window.mouseClicked(mouseX, mouseY, button);

		return super.method_25402(mouseX, mouseY, button);
	}

	@Override
	public boolean method_25403(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		mouseX *= (int) class_310.method_1551().method_22683().method_4495();
		mouseY *= (int) class_310.method_1551().method_22683().method_4495();
		for (Window window : windows)
			window.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

		return super.method_25403(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		class_310 mc = class_310.method_1551();
		mouseY *= mc.method_22683().method_4495();

		for (Window window : windows)
			window.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

		return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
	}

	@Override
	public boolean method_25421() {
		return false;
	}

	@Override
	public void method_25419() {
		Argon.INSTANCE.getModuleManager().getModule(ClickGUI.class).setEnabledStatus(false);
		onGuiClose();
	}

	public void onGuiClose() {
		mc.method_29970(Argon.INSTANCE.previousScreen);
		currentColor = null;

		for (Window window : windows)
			window.onGuiClose();
	}

	@Override
	public boolean method_25406(double mouseX, double mouseY, int button) {
		mouseX *= (int) class_310.method_1551().method_22683().method_4495();
		mouseY *= (int) class_310.method_1551().method_22683().method_4495();

		for (Window window : windows)
			window.mouseReleased(mouseX, mouseY, button);

		return super.method_25406(mouseX, mouseY, button);
	}
}
