package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.CfsSubsetEval
import weka.attributeSelection.RankSearch

class CFS_RS : FeatureSelection() {

    override fun getSearcher(): ASSearch = RankSearch()

    override fun getEvaluator(): ASEvaluation = CfsSubsetEval()
}
