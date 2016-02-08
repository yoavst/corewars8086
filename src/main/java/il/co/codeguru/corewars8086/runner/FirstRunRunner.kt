package il.co.codeguru.corewars8086.runner

import com.yoavst.codeguru.runner.BaseRunner
import il.co.codeguru.corewars8086.runner.util.Params
import il.co.codeguru.corewars8086.runner.util.print

import il.co.codeguru.corewars8086.war.WarriorGroup
import java.io.File
import java.util.*

class FirstRunRunner(wars: Int, warriorsPerGroup: Int, baseFolder: String) : BaseRunner(wars, warriorsPerGroup, baseFolder, Params.FirstRun) {
    val survivorsDir = Directory.Survivors.toFile()

    override fun onEndWars() {
        var warriorGroups = competition.warriorRepository.warriorGroups.sortedBy { it.groupScore }
        warriorGroups.asReversed().forEachIndexed { i, warriorGroup -> warriorGroup.print(i + 1) }
        saveTopGroup(warriorGroups.takeLast(8))
        val ranks = createRanks(warriorGroups.subList(0, warriorGroups.size - 8))
        val groups = createGroups(ranks)
        groups.forEachIndexed { i, list -> saveGroup(list, i + 1) }

    }

    private fun createRanks(groups: List<WarriorGroup>): MutableList<MutableList<WarriorGroup>> {
        val ranks = ArrayList<MutableList<WarriorGroup>>()
        var groups1 = groups
        while (groups1.isNotEmpty()) {
            ranks += ArrayList(groups1.takeLast(8))
            groups1 = groups1.subList(0, (groups1.size - 8).let { if (it > 0) it else 0 })
        }
        return ranks
    }

    private fun createGroups(ranks: MutableList<MutableList<WarriorGroup>>): List<List<WarriorGroup>> {
        val random = Random()
        val groups = ArrayList<MutableList<WarriorGroup>>(8)
        for (i in 0..7) {
            val group = ArrayList<WarriorGroup>(ranks.size)
            ranks.forEach { rank ->
                if (rank.isNotEmpty()) {
                    val pos = random.nextInt(rank.size)
                    group += rank[pos]
                    rank.removeAt(pos)
                }
            }
            groups += group
        }
        return groups
    }

    private fun saveTopGroup(groups: List<WarriorGroup>) {
        val topGroupDir = Directory.TopGroup.toFile()
        if (topGroupDir.exists()) topGroupDir.deleteRecursively()
        topGroupDir.mkdirs()
        groups.forEach {
            it.warriors.forEach { warrior ->
                File(survivorsDir, warrior.name).copyTo(File(topGroupDir, warrior.name))
            }
        }

    }

    private fun saveGroup(groups: List<WarriorGroup>, number: Int) {
        val groupDir = Directory.groupOf(number).toFile()
        if (groupDir.exists()) groupDir.deleteRecursively()
        groupDir.mkdirs()
        groups.forEach {
            it.warriors.forEach { warrior ->
                File(survivorsDir, warrior.name).copyTo(File(groupDir, warrior.name))
            }
        }
    }
}