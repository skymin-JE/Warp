package kr.skymin.warp.event

import kr.skymin.warp.manager.Warp
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList

class WarpTeleportEvent(val player: Player, warp: Warp) : WarpEvent(warp), Cancellable {
	companion object {
		@JvmStatic
		val handlerList = HandlerList()
	}

	private var isCancelled = false

	override fun getHandlers(): HandlerList {
		return handlerList
	}

	override fun isCancelled(): Boolean {
		return isCancelled
	}

	fun setCancelled() {
		setCancelled(true)
	}

	override fun setCancelled(cancel: Boolean) {
		isCancelled = cancel
	}
}
