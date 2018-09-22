package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.ConsistencySubsetEval
import weka.attributeSelection.GreedyStepwise

class Cons_SWS : FeatureSelection() {

    override fun getSearcher(): ASSearch = GreedyStepwise()

    override fun getEvaluator(): ASEvaluation = ConsistencySubsetEval()
}
