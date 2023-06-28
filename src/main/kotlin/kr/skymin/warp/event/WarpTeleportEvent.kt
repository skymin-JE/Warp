package kr.skymin.warp.event

import kr.skymin.warp.manager.Warp
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList

class WarpTeleportEvent(val player: Player, warp: Warp) : WarpEvent(warp) {
	companion object {
		@JvmStatic
		val handlerList = HandlerList()
	}

	override fun getHandlers(): HandlerList {
		return handlerList
	}
}
