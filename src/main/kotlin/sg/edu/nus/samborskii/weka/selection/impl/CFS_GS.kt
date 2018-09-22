package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.CfsSubsetEval
import weka.attributeSelection.GeneticSearch

class CFS_GS : FeatureSelection() {

    override fun getSearcher(): ASSearch = GeneticSearch()

    override fun getEvaluator(): ASEvaluation = CfsSubsetEval()
}
