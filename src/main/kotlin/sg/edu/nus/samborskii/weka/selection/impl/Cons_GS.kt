package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.ConsistencySubsetEval
import weka.attributeSelection.GeneticSearch

class Cons_GS : FeatureSelection() {

    override fun getSearcher(): ASSearch = GeneticSearch()

    override fun getEvaluator(): ASEvaluation = ConsistencySubsetEval()
}
