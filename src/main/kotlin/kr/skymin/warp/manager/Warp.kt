package kr.skymin.warp.manager

import org.bukkit.Bukkit
import org.bukkit.Location

data class Warp(
	val name: String,
	private val position: String,
	val isOp: Boolean = false
){
	val positionParser: Location
		get() {
			val posParse: List<String> = position.split(':')
			return Location(
				Bukkit.getWorld(posParse[0]),
				posParse[1].toDouble(),
				posParse[2].toDouble(),
				posParse[3].toDouble()
			)
		}
}