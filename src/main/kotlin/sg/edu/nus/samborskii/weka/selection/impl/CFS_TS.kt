package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.CfsSubsetEval
import weka.attributeSelection.TabuSearch

class CFS_TS : FeatureSelection() {

    override fun getSearcher(): ASSearch = TabuSearch()

    override fun getEvaluator(): ASEvaluation = CfsSubsetEval()
}
