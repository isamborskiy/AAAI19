package sg.edu.nus.samborskii.g2d

import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instances

fun toInstances(data: Map<Int, List<Int>>, groundTruth: Map<Int, Int>): Instances {
    assert(data.keys.all { it in groundTruth })

    val attributes: ArrayList<Attribute> = ArrayList(data.values.first().mapIndexed { index, _ -> Attribute("attr$index") })
    attributes += Attribute("class", groundTruth.values.distinct().map { it.toString() })

    val instances = Instances("", attributes, data.size).apply { setClassIndex(attributes.size - 1) }
    data.keys.forEach { id ->
        instances += DenseInstance(attributes.size).apply {
            data[id]!!.forEachIndexed { index, value ->
                setValue(attributes[index], value.toDouble())
            }
            setValue(attributes.last(), groundTruth[id]!!.toString())
        }
    }

    return instances
}
