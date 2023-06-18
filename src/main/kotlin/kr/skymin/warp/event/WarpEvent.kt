package kr.skymin.warp.event

import kr.skymin.warp.manager.Warp
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class WarpEvent(val warp: Warp): Event() {
	private val handlers = HandlerList()

	override fun getHandlers(): HandlerList {
		return handlers
	}

	fun getWarp() : Warp{
		return warp
	}
}