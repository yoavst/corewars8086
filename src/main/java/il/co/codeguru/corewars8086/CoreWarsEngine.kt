package il.co.codeguru.corewars8086

import com.beust.jcommander.JCommander
import il.co.codeguru.corewars8086.gui.CompetitionWindow
import il.co.codeguru.corewars8086.runner.ClassicRunner
import il.co.codeguru.corewars8086.runner.FirstRunRunner
import il.co.codeguru.corewars8086.runner.GroupsRunner
import il.co.codeguru.corewars8086.runner.KnockoutRunner
import il.co.codeguru.corewars8086.runner.util.KWindow
import il.co.codeguru.corewars8086.runner.util.Params

import java.io.IOException

object CoreWarsEngine {
    @Throws(IOException::class)
    @JvmStatic fun main(args: Array<String>) {
        if (args.isEmpty()) {
            val c = CompetitionWindow()
            c.isVisible = true
            c.pack()
        } else {
            val params = Params()
            val jCommander = JCommander(params, *args)
            if (params.help) {
                jCommander.setProgramName("codeguru.jar")
                jCommander.usage()
            } else {
                val runner = when (params.level) {
                    Params.Classic -> ClassicRunner(params.wars, params.warriorsPerGroup, params.baseFolder)
                    Params.FirstRun -> FirstRunRunner(params.wars, params.warriorsPerGroup, params.baseFolder)
                    Params.Groups -> GroupsRunner(params.wars, params.warriorsPerGroup, params.baseFolder)
                    Params.Knockout4, Params.Knockout16 -> KnockoutRunner(params.finalLevel, params.level, params.wars, params.warriorsPerGroup, params.baseFolder)
                    else -> throw IllegalArgumentException("No Such Level")
                }

                if (!params.graphics)
                    runner.run()
                else {
                    val window = KWindow(runner, params.wars, params.warriorsPerGroup)
                    window.isVisible = true
                    window.pack()
                }
            }
        }
    }
}