package kr.skymin.warp.event

import kr.skymin.warp.manager.Warp
import org.bukkit.event.HandlerList

class WarpRemoveEvent(warp: Warp) : WarpEvent(warp) {
	companion object {
		@JvmStatic
		val handlerList = HandlerList()
	}

	override fun getHandlers(): HandlerList {
		return handlerList
	}
}
