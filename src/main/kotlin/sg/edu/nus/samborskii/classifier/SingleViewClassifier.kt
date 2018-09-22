package sg.edu.nus.samborskii.classifier

import weka.core.Instance
import weka.core.Instances

abstract class SingleViewClassifier : Classifier {

    override fun buildClassifier(datasets: List<Instances>) = buildClassifier(datasets.first())

    protected abstract fun buildClassifier(dataset: Instances)

    override fun classifyInstance(instanceList: List<Instance>): Double =
            classifyInstance(instanceList.first())

    protected abstract fun classifyInstance(instance: Instance): Double
}
