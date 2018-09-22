package sg.edu.nus.samborskii.weka.selection.impl

import sg.edu.nus.samborskii.weka.selection.FeatureSelection
import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.attributeSelection.Ranker
import weka.attributeSelection.ReliefFAttributeEval

/**
 * Distance feature impl algorithm.
 *
 * @author Whiplash
 */
class Relief_F : FeatureSelection() {

    override fun getSearcher(): ASSearch = Ranker()

    override fun getSearcherOptions(): Array<String> = arrayOf("-T", "0.01")

    override fun getEvaluator(): ASEvaluation = ReliefFAttributeEval()
}
