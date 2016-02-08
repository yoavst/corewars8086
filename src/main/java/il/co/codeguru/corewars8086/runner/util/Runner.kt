package il.co.codeguru.corewars8086.runner.util

import il.co.codeguru.corewars8086.utils.SimpleEventListener
import il.co.codeguru.corewars8086.war.Competition

abstract class Runner : SimpleEventListener() {
    var competition: Competition = Competition(false)

    abstract fun run()
}