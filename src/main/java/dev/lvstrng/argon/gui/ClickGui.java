package dev.lvstrng.argon.gui;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.module.Category;
import dev.lvstrng.argon.module.modules.client.ClickGUI;
import dev.lvstrng.argon.utils.ColorUtils;
import dev.lvstrng.argon.utils.RenderUtils;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import static dev.lvstrng.argon.Argon.mc;

public final class ClickGui extends Screen {
	public List<Window> windows = new ArrayList<>();
	public Color currentColor;
	private static final StackWalker sw = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

	public ClickGui() {
		super(Component.empty());

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
	protected void init() {
		if (minecraft == null) {
			return;
		}
		super.init();
	}

	@Override
	public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
		if (mc.screen == this) {
			if (Argon.INSTANCE.previousScreen != null)
				Argon.INSTANCE.previousScreen.render(context, 0, 0, delta);

			if (currentColor == null)
				currentColor = new Color(10, 10, 15, 0);
			else currentColor = new Color(10, 10, 15, currentColor.getAlpha());

			if (currentColor.getAlpha() != (ClickGUI.background.getValue() ? 160 : 0))
				currentColor = ColorUtils.smoothAlphaTransition(0.06F, ClickGUI.background.getValue() ? 160 : 0, currentColor);

			if (mc.screen instanceof ClickGui)
				context.fill(0, 0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), currentColor.getRGB());

			RenderUtils.unscaledProjection();
			mouseX *= (int) Minecraft.getInstance().getWindow().getGuiScale();
			mouseY *= (int) Minecraft.getInstance().getWindow().getGuiScale();
			super.render(context, mouseX, mouseY, delta);

			for (Window window : windows) {
				window.render(context, mouseX, mouseY, delta);
				window.updatePosition(mouseX, mouseY, delta);
			}

			RenderUtils.scaledProjection();
		}
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		for (Window window : windows)
			window.keyPressed(keyCode, scanCode, modifiers);

		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		mouseX *= (int) Minecraft.getInstance().getWindow().getGuiScale();
		mouseY *= (int) Minecraft.getInstance().getWindow().getGuiScale();

		for (Window window : windows)
			window.mouseClicked(mouseX, mouseY, button);

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		mouseX *= (int) Minecraft.getInstance().getWindow().getGuiScale();
		mouseY *= (int) Minecraft.getInstance().getWindow().getGuiScale();
		for (Window window : windows)
			window.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		Minecraft mc = Minecraft.getInstance();
		mouseY *= mc.getWindow().getGuiScale();

		for (Window window : windows)
			window.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

		return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void onClose() {
		Argon.INSTANCE.getModuleManager().getModule(ClickGUI.class).setEnabledStatus(false);
		onGuiClose();
	}

	public void onGuiClose() {
		mc.setScreen(Argon.INSTANCE.previousScreen);
		currentColor = null;

		for (Window window : windows)
			window.onGuiClose();
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		mouseX *= (int) Minecraft.getInstance().getWindow().getGuiScale();
		mouseY *= (int) Minecraft.getInstance().getWindow().getGuiScale();

		for (Window window : windows)
			window.mouseReleased(mouseX, mouseY, button);

		return super.mouseReleased(mouseX, mouseY, button);
	}
}
