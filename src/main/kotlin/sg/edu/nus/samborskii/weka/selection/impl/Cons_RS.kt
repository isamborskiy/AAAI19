package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.ConsistencySubsetEval
import weka.attributeSelection.RankSearch

class Cons_RS : FeatureSelection() {

    override fun getSearcher(): ASSearch = RankSearch()

    override fun getEvaluator(): ASEvaluation = ConsistencySubsetEval()
}
