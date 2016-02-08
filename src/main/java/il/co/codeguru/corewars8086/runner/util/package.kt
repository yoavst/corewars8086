package il.co.codeguru.corewars8086.runner.util

import il.co.codeguru.corewars8086.war.WarriorGroup

fun WarriorGroup.print(position: Int) {
    println("$position. $name :: $groupScore :: ${warriors.zip(scores).map { it.first.name + " " + it.second }}")
}