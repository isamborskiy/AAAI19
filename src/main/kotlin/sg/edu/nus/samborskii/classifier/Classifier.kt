package sg.edu.nus.samborskii.classifier

import weka.core.DenseInstance
import weka.core.Instance
import weka.core.Instances

interface Classifier {

    fun buildClassifier(datasets: List<Instances>)

    fun classifyInstance(instanceList: List<Instance>): Double
}

fun Instance.toInstances(): Instances {
    val attributes = (0 until numAttributes()).map { attribute(it) }
    val classAttr = attributes.first { it.name() == classAttribute().name() }
    return Instances("", ArrayList(attributes), 1).apply {
        setClass(classAttr)
        val fakeInstance = DenseInstance(numAttributes())
        attributes.forEach { fakeInstance.setValue(it, value(it)) }
        add(fakeInstance)
    }
}
