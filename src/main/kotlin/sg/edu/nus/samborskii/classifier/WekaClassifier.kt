package sg.edu.nus.samborskii.classifier

import weka.classifiers.Classifier
import weka.core.Instance
import weka.core.Instances

open class WekaClassifier(private val classifier: Classifier) : SingleViewClassifier() {

    override fun buildClassifier(dataset: Instances) {
        classifier.buildClassifier(dataset)
    }

    override fun classifyInstance(instance: Instance): Double =
            classifier.classifyInstance(instance)
}
