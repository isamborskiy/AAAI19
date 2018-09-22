package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.BestFirst
import weka.attributeSelection.ConsistencySubsetEval

class Cons_SFS : FeatureSelection() {

    override fun getSearcher(): ASSearch = BestFirst()

    override fun getSearcherOptions(): Array<String> = arrayOf("-D", "1")

    override fun getEvaluator(): ASEvaluation = ConsistencySubsetEval()
}
