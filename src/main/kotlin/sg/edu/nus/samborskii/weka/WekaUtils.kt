package sg.edu.nus.samborskii.weka

import sg.edu.nus.samborskii.ID_ATTR_NAME
import weka.core.Attribute
import weka.core.Instance
import weka.core.Instances
import weka.core.UnassignedClassException
import java.util.*

private const val MERGED_INSTANCES_NAME: String = "joined_instances"

fun Instances.join(instances: Instances, joinBy: String = ID_ATTR_NAME): Instances {
    val instancesCopy = Instances(instances)

    val idToInstanceLeft = associateByAttrValue(joinBy)
    val idToInstanceRight = instancesCopy.associateByAttrValue(joinBy)

    instancesCopy.deleteAttr(joinBy)

    val classAttrName = classAttrName() ?: instances.classAttrName()

    val mergedInstances = mergeInstances(idToInstanceLeft, idToInstanceRight)
    val attributes = joinAttributes(this, instancesCopy)
    return Instances(MERGED_INSTANCES_NAME, attributes, mergedInstances.size).apply {
        addAll(mergedInstances)
        if (classAttrName != null) setClass(attribute(classAttrName))
    }
}

fun Instances.classAttrName(): String? = try {
    classAttribute().name()
} catch (e: UnassignedClassException) {
    null
}

private fun Instances.associateByAttrValue(attrName: String): Map<String, Instance> {
    val attr = attribute(attrName)
    return associateBy { it.getStringValue(attr)!! }
}

private fun mergeInstances(first: Map<String, Instance>, second: Map<String, Instance>): List<Instance> {
    return first.keys
            .filter { it in second }
            .map { first[it]!!.mergeInstance(second[it]) }
}

private fun joinAttributes(vararg instancesList: Instances): ArrayList<Attribute> {
    val attributes = ArrayList<Attribute>()
    instancesList.forEach { attributes += it.attributes() }
    return ArrayList(attributes)
}

fun Instances.attributes(): List<Attribute> = (0 until numAttributes()).map { attribute(it) }

fun Instances.deleteIdAttr() = deleteAttr(ID_ATTR_NAME)

fun Instances.deleteAttr(attrName: String) = deleteAttributeAt(attribute(attrName).index())

fun Instance.getStringValue(attribute: Attribute): String? =
        try {
            when (attribute.type()) {
                Attribute.STRING, Attribute.NOMINAL -> stringValue(attribute)
                else -> value(attribute).toString()
            }
        } catch (e: NumberFormatException) {
            null
        }

fun Instances.uniformChunked(proportions: List<Int>): List<Instances> {
    val total = proportions.sum()

    val chunks = groupBy { it.classValue().toInt() }
            .map { instances ->
                val shuffledInstances = instances.value.shuffled()
                val num = shuffledInstances.size
                val chunkSize = proportions.map { it * num / total }

                val chunkBegin = proportions.map { 0 }.toMutableList()
                (1 until proportions.size).forEach { chunkBegin[it] = chunkBegin[it - 1] + chunkSize[it - 1] }
                val chunkEnd = proportions.map { num }.toMutableList()
                (0 until proportions.size - 1).forEach { chunkEnd[it] = chunkBegin[it + 1] }

                (0 until proportions.size)
                        .map { index -> shuffledInstances.subList(chunkBegin[index], chunkEnd[index]) }
            }

    return (0 until chunks.first().size).map { chunkIndex ->
        val size = chunks.map { it[chunkIndex].size }.sum()
        val newInstances = Instances("chunk$chunkIndex", ArrayList(attributes()), size)
        chunks.forEach { newInstances += it[chunkIndex] }
        newInstances
    }
}
