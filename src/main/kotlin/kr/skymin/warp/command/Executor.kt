package kr.skymin.warp.command

import kr.skymin.warp.event.WarpTeleportEvent
import kr.skymin.warp.manager.Warp
import kr.skymin.warp.manager.WarpManager
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.math.ceil

object Executor {

	fun onCreate(player: Player, name: String, isOp: Boolean = false) {
		val result: Boolean = WarpManager.addWarp{
			warpName = name
			position = player.location
			this.isOp = isOp
		}
		if(result) {
			player.sendMessage("성공적으로 추가되었습니다.")
		} else {
			player.sendMessage("${name}은 이미 있는 워프입니다.")
		}
	}

	fun onRemove(player: Player, name: String) {
		if(WarpManager.containsWarp(name)) {
			WarpManager.removeWarp(name)
			player.sendMessage("성공적으로 삭제되었습니다.")
		} else{
			player.sendMessage("${name}은 없는 워프 입니다.")
		}
	}

	fun onTeleport(player: Player, name: String) {
		val warp: Warp? = WarpManager.getWarp(name)
		if(warp !== null && (player.isOp || !warp.isOp)) {
			val pos: Location = warp.positionParser
			pos.yaw = player.location.yaw
			pos.pitch = player.location.pitch
			if(WarpTeleportEvent(player, warp).callEvent()) {
				player.teleport(pos)
				player.sendMessage("이동되었습니다.")
			}
		} else {
			player.sendMessage("${name}은 존재 하지 않는 워프 입니다.")
		}
	}

	fun onViewList(player: Player, page: Int = 1) {
		val list: MutableList<String> = (if(player.isOp) WarpManager.warpNames else WarpManager.canUserWarps).toMutableList()
		val maxPage: Int = ceil(list.size.div(5.0)).toInt()
		val n: Int = if(page < 1) 1 else if(page > maxPage) maxPage else page
		var msg = "===== $n / $maxPage =====\n"
		val e: Int = (n - 1) * 5
		for (i: Int in e..e + 4) {
			if(i > list.lastIndex) break
			msg += "- ${list[i]}\n"
		}
		player.sendMessage(msg)
	}
}