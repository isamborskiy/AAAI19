package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.BestFirst
import weka.attributeSelection.CfsSubsetEval

class CFS_SBS : FeatureSelection() {

    override fun getSearcher(): ASSearch = BestFirst()

    override fun getSearcherOptions(): Array<String> = arrayOf("-D", "0")

    override fun getEvaluator(): ASEvaluation = CfsSubsetEval()
}
