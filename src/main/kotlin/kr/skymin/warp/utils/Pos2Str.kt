package kr.skymin.warp.utils

import org.bukkit.Location

fun pos2str(pos: Location): String = "${pos.world.name}:${pos.x}:${pos.y}:${pos.z}"