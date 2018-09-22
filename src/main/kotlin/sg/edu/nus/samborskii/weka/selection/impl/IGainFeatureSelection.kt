package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.deleteAttr
import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import sg.edu.nus.samborskii.weka.iGainStatistics
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.core.Instances

class IGainFeatureSelection(private val topN: Int) : FeatureSelection() {

    override fun select(instances: Instances): Instances = Instances(instances).apply {
        iGainStatistics(instances).drop(topN).forEach { (name, _) -> deleteAttr(name) }
    }

    override fun getSearcher(): ASSearch? = null

    override fun getEvaluator(): ASEvaluation? = null

    override fun toString(): String = "${super.toString()} ($topN)"
}
