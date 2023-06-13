package kr.skymin.warp.event

import kr.skymin.warp.manager.Warp
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class WarpEvent(player: Player, private val warp: Warp): PlayerEvent(player), Cancellable {
	private val handlers = HandlerList()
	private var cancelled = false

	override fun getHandlers(): HandlerList {
		return handlers
	}

	override fun isCancelled(): Boolean {
		return cancelled
	}

	override fun setCancelled(cancel: Boolean) {
		cancelled = cancel
	}

	fun getWarp(): Warp {
		return warp
	}
}