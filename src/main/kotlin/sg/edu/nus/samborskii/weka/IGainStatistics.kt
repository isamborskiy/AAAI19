package sg.edu.nus.samborskii.weka

import sg.edu.nus.samborskii.ID_ATTR_NAME
import weka.attributeSelection.InfoGainAttributeEval
import weka.core.Instances


fun iGainStatistics(instances: Instances, idAttrName: String = ID_ATTR_NAME): List<Pair<String, Double>> {
    val classAttr = instances.classAttribute()

    val copiedInstances = Instances(instances)
    copiedInstances.deleteAttr(copiedInstances.attribute(idAttrName).name())

    val infoGainAttributeEval = InfoGainAttributeEval().apply { buildEvaluator(copiedInstances) }
    return (0 until copiedInstances.numAttributes())
            .filter { copiedInstances.attribute(it).name() != classAttr.name() }
            .map { copiedInstances.attribute(it).name() to infoGainAttributeEval.evaluateAttribute(it) }
            .sortedByDescending { it.second }
            .toList()
}
