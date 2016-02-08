package il.co.codeguru.corewars8086.runner

import com.yoavst.codeguru.runner.BaseRunner

import il.co.codeguru.corewars8086.runner.util.Params
import il.co.codeguru.corewars8086.runner.util.print
import java.io.File

class KnockoutRunner(val finalLevel: Boolean, level: Int, wars: Int, warriorsPerGroup: Int,
                     baseFolder: String) : BaseRunner(wars, warriorsPerGroup, baseFolder, level) {

    override fun onEndWars() {
        if (!finalLevel && level != Params.Knockout4) {
            val groups = competition.warriorRepository.warriorGroups.sortedByDescending { it.groupScore }.take(4) // Only 1 knockout level for now and then final.
            val newDir = Directory[level + 1].toFile()
            val dir = dir.toFile()
            if (newDir.exists()) newDir.deleteRecursively()
            newDir.mkdirs()
            groups.forEach { group ->
                group.warriors.forEach { warrior ->
                    File(dir, warrior.name).copyTo(File(newDir, warrior.name))
                }
            }
        }
        competition.warriorRepository.warriorGroups.sortedByDescending { it.groupScore }.forEachIndexed { i, warriorGroup -> warriorGroup.print(i + 1) }
    }


}