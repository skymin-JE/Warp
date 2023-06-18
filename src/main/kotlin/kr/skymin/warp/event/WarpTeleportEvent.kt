package kr.skymin.warp.event

import kr.skymin.warp.manager.Warp
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

class WarpTeleportEvent(val player: Player, warp: Warp): WarpEvent(warp), Cancellable {
	private var cancelled = false

	override fun isCancelled(): Boolean {
		return cancelled
	}

	override fun setCancelled(cancel: Boolean) {
		cancelled = cancel
	}

	fun getPlayer(): Player{
		return player
	}
}