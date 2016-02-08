package il.co.codeguru.corewars8086.runner

import il.co.codeguru.corewars8086.runner.util.Params

object Directory {
    val Survivors = "survivors"
    val TopGroup = "cg_top"
    fun groupOf(group: Int) = "cg_group$group"
    fun knockoutOf(level: Int) = "cg_knockout$level"

    val Zombies = "zombies"

    operator fun get(level: Int, groupNumber: Int = -1): String {
        return when (level) {
            Params.Classic, Params.FirstRun -> Directory.Survivors
            Params.Groups -> Directory.groupOf(groupNumber)
            Params.Knockout16 -> Directory.knockoutOf(16)
            Params.Knockout4 -> Directory.knockoutOf(4)
            else -> throw IllegalArgumentException("No such level")
        }
    }
}