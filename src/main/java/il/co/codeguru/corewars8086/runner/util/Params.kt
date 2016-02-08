package il.co.codeguru.corewars8086.runner.util

import com.beust.jcommander.Parameter

class Params {
    @Parameter(names = arrayOf("--wars", "-w"), description = "Number of wars to run")
    val wars: Int = 100

    @Parameter(names = arrayOf("--groupSize", "-s"), description = "Number of warriors per group")
    val warriorsPerGroup: Int = 4

    @Parameter(names = arrayOf("--graphics", "-g"), description = "Run corewars on graphical mode")
    val graphics: Boolean = false

    @Parameter(names = arrayOf("--level", "-l"), description = "The level. -1 -> classic mode. 0 -> first run. 1 -> groups stage. 16 -> Knockout 16, 4 -> final 4")
    val level: Int = -1

    @Parameter(names = arrayOf("--final", "-f"), description = "Do not prepare next level")
    val finalLevel: Boolean = false

    @Parameter(names = arrayOf("--path", "-p"), description = "Base survivors path")
    val baseFolder: String = "."

    @Parameter(names = arrayOf("--help", "-h"), help = true, description = "Show help")
    val help: Boolean = false

    override fun toString(): String = "{wars: $wars, graphics: $graphics, level: $level, finalLevel: $finalLevel, baseFolder: $baseFolder}"

    companion object {
        val Classic = -1
        val FirstRun = 0
        val Groups = 1
        val Knockout16 = 16
        val Knockout4 = 4
    }
}
