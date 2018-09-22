package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.ConsistencySubsetEval
import weka.attributeSelection.ScatterSearchV1

class Cons_SS : FeatureSelection() {

    override fun getSearcher(): ASSearch = ScatterSearchV1()

    override fun getEvaluator(): ASEvaluation = ConsistencySubsetEval()
}
