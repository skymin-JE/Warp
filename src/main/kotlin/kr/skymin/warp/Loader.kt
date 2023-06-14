package kr.skymin.warp

import kr.skymin.warp.manager.WarpManager
import io.github.monun.kommand.StringType
import io.github.monun.kommand.kommand
import kr.skymin.warp.command.Executor
import net.kyori.adventure.text.Component.text
import org.bukkit.command.Command
import org.bukkit.plugin.java.JavaPlugin
import java.lang.RuntimeException

class Loader: JavaPlugin() {

	companion object{
		private var instance: Loader? = null
		val INSTANCE: Loader
			get() {
				if(instance === null){
					throw RuntimeException("The plugin is not loaded.")
				}
				return instance!!
			}
	}

	override fun onLoad() {
		instance = this
	}

	override fun onEnable() {
		saveResource("Config.json", false)
		registerCommands()
	}

	override fun onDisable() {
		WarpManager.save()
	}

	private fun registerCommands() {
		kommand {
			register("warp"){
				requires { isPlayer }
				executes {
					var usage = "\n"
					if(player.isOp){
						usage += "/warp create <name: string> [isOp: bool] - 워프를 생성합니다.\n"
						usage += "/warp remove <name: string> - 워프를 제거 합니다.\n"
						usage += "/warp teleport_op <name: string> - 해당 워프로 순간 이동합니다.\n"
					} else {
						usage += "/warp teleport <name: string> - 해당 워프로 순간 이동 합니다.\n"
					}
					usage += "/warp list [page: int] - 이동 가능한 워프들을 확인합니다."
					player.sendMessage(usage)
				}
				// 생성
				then("create"){
					requires { isOp }
					then("name" to string(StringType.QUOTABLE_PHRASE)){
						executes {
							Executor.onCreate(player, it["name"])
						}
						then("isOp" to bool()){
							executes {
								Executor.onCreate(player, it["name"], it["isOp"])
							}
						}
					}
				}
				/** argument 생성 */
				val opWarpNames = string(StringType.QUOTABLE_PHRASE)
				opWarpNames.suggests {
					suggest(WarpManager.warpNames){
						text(it)
					}
				}
				/****************/
				// 삭제
				then("remove"){
					requires { isOp }
					then("name" to opWarpNames){
						executes {
							Executor.onRemove(player, it["name"])
						}
					}
				}
				// 이동
				then("teleport_op"){
					requires { isOp }
					then("opWarp" to opWarpNames){
						executes {
							Executor.onTeleport(player, it["opWarp"])
						}
					}
				}
				then("teleport") {
					requires { !isOp }
					val warps = string(StringType.QUOTABLE_PHRASE)
					warps.suggests {
						suggest(WarpManager.canUserWarps){
							text(it)
						}
					}
					then("name" to warps){
						executes {
							Executor.onTeleport(player, it["name"])
						}
					}
				}
				//목록
				then("list"){
					executes {
						Executor.onViewList(player)
					}
					then("page" to int()){
						executes {
							Executor.onViewList(player, it["page"])
						}
					}
				}
			}
		}
		val cmd: Command? = server.commandMap.getCommand("warp")
		if(cmd === null) return
		cmd.setDescription("워프 명령어")
	}
}