package kr.skymin.warp.manager

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kr.skymin.warp.Loader
import kr.skymin.warp.event.WarpCreateEvent
import kr.skymin.warp.event.WarpRemoveEvent
import kr.skymin.warp.utils.pos2str
import org.bukkit.Location
import java.io.File

object WarpManager {

	private val warps: MutableMap<String, Warp>

	private val config = File(Loader.INSTANCE.dataFolder.path, "Config.json")

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
		val gson: Gson = GsonBuilder()
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.create()
		config.writeText(gson.toJson(warps))
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
		WarpCreateEvent(warp).callEvent()
		return true
	}

	fun addWarp(warpDsl: WarpDsl.() -> Unit): Boolean{
		val warp: Warp = WarpDsl().apply(warpDsl).build()
		return addWarp(warp)
	}

	fun addWarp(name: String, position: Location, isOp: Boolean): Boolean{
		return addWarp(Warp(name, pos2str(position), isOp))
	}

	fun removeWarp(name: String) {
		val warp: Warp? = warps[name]
		if(warp !== null) {
			warps.remove(name)
			WarpRemoveEvent(warp).callEvent()
		}
	}
}