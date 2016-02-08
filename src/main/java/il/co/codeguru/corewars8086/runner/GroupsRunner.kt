package il.co.codeguru.corewars8086.runner

import com.yoavst.codeguru.runner.BaseRunner
import il.co.codeguru.corewars8086.runner.util.Params
import il.co.codeguru.corewars8086.runner.util.Runner
import il.co.codeguru.corewars8086.runner.util.print
import java.io.File

class GroupsRunner(val wars: Int, val warriorsPerGroup: Int, val baseFolder: String) : Runner() {
    override fun run() {
        //FIXME gui
        val newDir = Directory.knockoutOf(16).toFile()
        if (newDir.exists()) newDir.deleteRecursively()
        newDir.mkdirs()
        Directory.TopGroup.toFile().listFiles().forEach { it.copyTo(File(newDir, it.name)) }
        createGroupRunner(1, newDir).run()
    }

    protected fun String.toFile() = File(baseFolder, this)

    var position = 1

    private fun createGroupRunner(groupNumber: Int, newDir: File): Runner {
        return object : BaseRunner(wars, warriorsPerGroup, baseFolder, Params.Groups, groupNumber) {
            override fun onEndWars() {
                val groups = competition.warriorRepository.warriorGroups.sortedByDescending { it.groupScore }
                println("Group $groupNumber:")
                groups.forEachIndexed { i, warriorGroup -> warriorGroup.print(i + 1) }
                groups[0].warriors.forEach { warrior ->
                    File(dir, warrior.name).copyTo(File(newDir, warrior.name))
                }
                position++
                if (position < 9) createGroupRunner(groupNumber + 1, newDir).run()
            }
        }
    }
}