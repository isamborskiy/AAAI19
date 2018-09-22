package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.ConsistencySubsetEval
import weka.attributeSelection.LinearForwardSelection

class Cons_LS : FeatureSelection() {

    override fun getSearcher(): ASSearch = LinearForwardSelection()

    override fun getEvaluator(): ASEvaluation = ConsistencySubsetEval()
}
