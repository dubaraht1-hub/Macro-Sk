package dev.lvstrng.argon.module;

import dev.lvstrng.argon.utils.EncryptedString;

public enum Category {
	COMBAT(EncryptedString.of("Combat")),
	CLIENT(EncryptedString.of("Client"));
	public final CharSequence name;

	Category(CharSequence name) {
		this.name = name;
	}
}
