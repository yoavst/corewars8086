package com.yoavst.codeguru.runner

import il.co.codeguru.corewars8086.runner.Directory
import il.co.codeguru.corewars8086.runner.util.Runner
import java.io.File

open class BaseRunner(val wars: Int, val warriorsPerGroup: Int, val baseFolder: String, val level: Int, val groupNumber: Int = -1) : Runner() {

    open fun onEndWars() {
    }

    init {
        competition.addCompetitionEventListener(this)
        readPlayers()
        readZombies()
    }

    override fun run() {
        competition.runCompetition(wars, warriorsPerGroup, false)
        onEndWars()
        competition.removeCompetitionEventListener(this)
    }

    protected fun readZombies() {
        competition.warriorRepository.readZombiesFileFromPath(Directory.Zombies.toAbsolutePath())
    }

    protected fun readPlayers() {
        competition.warriorRepository.readWarriorsFileFromPath(dir.toAbsolutePath())
    }

    protected fun String.toFile() = File(baseFolder, this)
    protected fun String.toAbsolutePath() = toFile().absolutePath

    protected val dir: String
        get() = Directory[level, groupNumber]

}