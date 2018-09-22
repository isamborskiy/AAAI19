package sg.edu.nus.samborskii.weka.selection

import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.core.Instances

class NoFeatureSelection : FeatureSelection() {

    override fun select(instances: Instances): Instances = instances

    override fun getSearcher(): ASSearch? = null

    override fun getEvaluator(): ASEvaluation? = null
}
