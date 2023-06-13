package kr.skymin.warp.manager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.skymin.warp.Loader
import java.io.File

object WarpManager {

	private val warps: MutableMap<String, Warp>

	private val config = File(Loader.INSTANCE.dataFolder.path, "Config.json")

	val warpCount: Int
		get() = warps.size

	val warpNames: MutableSet<String>
		get() = warps.keys

	val canUserWarps: MutableSet<String>
		get() {
			val warps: MutableList<String> = mutableListOf()
			this.warps.forEach {
				val warp: Warp = it.value
				if (!warp.isOp){
					warps.add(warp.name)
				}
			}
			return warps.toMutableSet()
		}

	init {
		val type = object : TypeToken<MutableMap<String, Warp>>(){}.type
		warps = Gson().fromJson(config.readText(), type)
	}

	fun save() {
		config.writeText(Gson().toJson(warps))
	}

	fun getWarp(name: String): Warp?{
		return warps[name]
	}

	fun containsWarp(name: String): Boolean {
		return  warps.containsKey(name)
	}

	fun addWarp(warp: Warp): Boolean {
		if(warps.containsKey(warp.name)){
			return false
		}
		warps[warp.name] = warp
		return true
	}

	fun removeWarp(name: String) {
		if(warps.containsKey(name)) {
			warps.remove(name)
		}
	}
}