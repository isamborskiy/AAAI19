package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.CfsSubsetEval
import weka.attributeSelection.GreedyStepwise

class CFS_SWS : FeatureSelection() {

    override fun getSearcher(): ASSearch = GreedyStepwise()

    override fun getEvaluator(): ASEvaluation = CfsSubsetEval()
}
