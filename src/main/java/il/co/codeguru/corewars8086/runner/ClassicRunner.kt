package il.co.codeguru.corewars8086.runner

import com.yoavst.codeguru.runner.BaseRunner

import il.co.codeguru.corewars8086.runner.util.Params
import il.co.codeguru.corewars8086.runner.util.print

class ClassicRunner(wars: Int, warriorsPerGroup: Int, baseFolder: String) : BaseRunner(wars, warriorsPerGroup, baseFolder, Params.Classic) {
    override fun onEndWars() {
        competition.warriorRepository.warriorGroups.sortedByDescending { it.groupScore }.forEachIndexed { i, warriorGroup -> warriorGroup.print(i + 1) }
    }
}