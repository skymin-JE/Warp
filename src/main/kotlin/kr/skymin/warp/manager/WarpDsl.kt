package kr.skymin.warp.manager

import kr.skymin.warp.utils.pos2str
import org.bukkit.Location

class WarpDsl {
	lateinit var warpName: String
	lateinit var position: Location
	var isOp: Boolean = false

	fun build(): Warp = Warp(warpName, pos2str(position), isOp)
}