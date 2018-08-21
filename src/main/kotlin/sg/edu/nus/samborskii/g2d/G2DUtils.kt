package sg.edu.nus.samborskii.g2d

import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instances

fun toInstances(data: Map<Int, List<Int>>, groundTruth: Map<Int, Int>, cropClassesByMin: Boolean = false): Instances {
    assert(data.keys.all { it in groundTruth })

    val classNum = groundTruth.values.groupBy { it }.size
    val minClassNum = groundTruth.values.groupBy { it }.map { it.value.size }.min()!!
    val n = if (cropClassesByMin) classNum * minClassNum else data.size

    val attributes: ArrayList<Attribute> = ArrayList(data.values.first().mapIndexed { index, _ -> Attribute("attr$index") })
    attributes += Attribute("class", groundTruth.values.distinct().map { it.toString() })

    val instances = Instances("", attributes, n).apply { setClassIndex(attributes.size - 1) }
    groundTruth.entries
            .groupBy { it.value }
            .map { (clazz, entries) -> clazz to entries.map { it.key } }
            .forEach { (_, ids) ->
                var shuffledIds = ids.shuffled()
                if (cropClassesByMin) shuffledIds = shuffledIds.take(minClassNum)
                shuffledIds.forEach { id ->
                    instances += DenseInstance(attributes.size).apply {
                        data[id]!!.forEachIndexed { index, value ->
                            setValue(attributes[index], value.toDouble())
                        }
                        setValue(attributes.last(), groundTruth[id]!!.toString())
                    }
                }
            }

    return instances
}
