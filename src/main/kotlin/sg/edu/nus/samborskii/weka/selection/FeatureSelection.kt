package sg.edu.nus.samborskii.weka.selection

import weka.attributeSelection.ASEvaluation
import weka.attributeSelection.ASSearch
import weka.core.Instances
import weka.core.OptionHandler
import weka.filters.Filter
import weka.filters.supervised.attribute.AttributeSelection

abstract class FeatureSelection {

    protected abstract fun getSearcher(): ASSearch?

    protected abstract fun getEvaluator(): ASEvaluation?

    protected open fun getSearcherOptions(): Array<String> = emptyArray()

    protected open fun getEvaluatorOptions(): Array<String> = emptyArray()

    open fun select(instances: Instances): Instances = try {
        val searcher = getSearcher()?.apply { if (this is OptionHandler) options = getSearcherOptions() }
        val evaluation = getEvaluator()?.apply { if (this is OptionHandler) options = getEvaluatorOptions() }

        val filter = AttributeSelection().apply {
            search = searcher
            evaluator = evaluation
            setInputFormat(instances)
        }
        Filter.useFilter(instances, filter)
    } catch (ignored: Exception) {
        instances
    }

    override fun toString(): String = javaClass.simpleName
}
