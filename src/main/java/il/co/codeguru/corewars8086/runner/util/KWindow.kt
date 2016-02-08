package il.co.codeguru.corewars8086.runner.util

import il.co.codeguru.corewars8086.gui.ColumnGraph
import il.co.codeguru.corewars8086.gui.WarFrame
import il.co.codeguru.corewars8086.war.*

import java.awt.*
import java.awt.event.*
import javax.swing.*

/**
 * @author BS
 */
class KWindow(val runner: Runner, val wars: Int, val warriorsPerGroup: Int) : JFrame("CodeGuru Extreme - Competition Viewer"), ScoreEventListener, ActionListener, CompetitionEventListener {
    private val columnGraph: ColumnGraph

    // widgets
    private val runWarButton: JButton
    private val warCounterDisplay: JLabel
    private var seed: JTextField
    private var battleFrame: WarFrame? = null

    private var warCounter: Int = 0
    private var warThread: Thread? = null
    private var competitionRunning: Boolean = false

    init {
        contentPane.layout = BorderLayout()
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        runner.competition.addCompetitionEventListener(this)
        val warriorRepository = runner.competition.warriorRepository
        warriorRepository.addScoreEventListener(this)
        columnGraph = ColumnGraph(warriorRepository.groupNames)
        contentPane.add(columnGraph, BorderLayout.CENTER)
        // -------------
        val controlArea = JPanel()
        controlArea.layout = BoxLayout(controlArea, BoxLayout.Y_AXIS)
        // -------------- Button Panel
        val buttonPanel = JPanel()
        runWarButton = JButton("<html><font color=red>Start!</font></html>")
        runWarButton.addActionListener(this)
        buttonPanel.add(runWarButton)
        warCounterDisplay = JLabel("")
        buttonPanel.add(warCounterDisplay)
        buttonPanel.add(Box.createHorizontalStrut(30))
        controlArea.add(buttonPanel)
        // -------------
        controlArea.add(JSeparator(JSeparator.HORIZONTAL))
        // ------------ Control panel
        val controlPanel = JPanel()
        controlPanel.layout = FlowLayout()
        seed = JTextField(4)
        seed.text = "guru"
        controlPanel.add(JLabel("seed:"))
        controlPanel.add(seed)

        controlArea.add(controlPanel)

        // ------------
        contentPane.add(controlArea, BorderLayout.SOUTH)

        addWindowListener(object : WindowListener {
            override fun windowOpened(e: WindowEvent) {
            }

            override fun windowClosing(e: WindowEvent) {
                if (warThread != null) {
                    runner.competition.setAbort()
                }
            }

            override fun windowClosed(e: WindowEvent) {
            }

            override fun windowIconified(e: WindowEvent) {
            }

            override fun windowDeiconified(e: WindowEvent) {
            }

            override fun windowActivated(e: WindowEvent) {
            }

            override fun windowDeactivated(e: WindowEvent) {
            }
        })
    }

    /**
     * Starts a new war.
     * @return whether or not a new war was started.
     */
    fun runWar(): Boolean {
        try {
            runner.competition.setSeed(seed.text.hashCode().toLong())
            //            if (runner.competition.warriorRepository.numberOfGroups < warriorsPerGroup) {
            //                JOptionPane.showMessageDialog(this,
            //                        "Not enough survivors (got " + runner.competition.warriorRepository.numberOfGroups + " but " + warriorsPerGroup + " are needed)")
            //                return false
            //            }
            warThread = object : Thread("CompetitionThread") {
                override fun run() {
                    try {
                        runner.run()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            if (!competitionRunning) {
                warThread!!.start()
                return true
            }
        } catch (e2: NumberFormatException) {
            JOptionPane.showMessageDialog(this, "Error in configuration")
        }

        return false
    }

    override fun scoreChanged(name: String, addedValue: Float, groupIndex: Int, subIndex: Int) {
        columnGraph.addToValue(groupIndex, subIndex, addedValue)
    }

    override fun actionPerformed(e: ActionEvent) {
        if (e.source === runWarButton) {
            if (runWar()) {
                competitionRunning = true
                runWarButton.isEnabled = false
            }
        }
    }


    override fun onWarStart() {

    }



    override fun onWarEnd(reason: Int, winners: String) {
        warCounter++
        SwingUtilities.invokeLater { warCounterDisplay.text = "Sessions so far:$warCounter (out of $wars)" }
    }

    override fun onRound(round: Int) {
    }

    override fun onWarriorBirth(warriorName: String) {
    }

    override fun onWarriorDeath(warriorName: String, reason: String) {
    }

    override fun onCompetitionStart() {
        warCounter = 0
        this.runWarButton.isEnabled = false
    }

    override fun onCompetitionEnd() {
        SwingUtilities.invokeLater { warCounterDisplay.text = "The competition is over. $warCounter sessions were run." }
        warThread = null
        this.runWarButton.isEnabled = true
        runWarButton.isEnabled = true
        competitionRunning = false
    }

    override fun onEndRound() {
    }

    companion object {
        private val serialVersionUID = 1L
    }

}