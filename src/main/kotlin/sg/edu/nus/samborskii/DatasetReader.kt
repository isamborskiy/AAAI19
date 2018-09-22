package sg.edu.nus.samborskii

import sg.edu.nus.samborskii.weka.attributes
import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instances
import weka.core.UnassignedClassException
import weka.core.converters.CSVLoader
import java.io.File

const val ID_ATTR_NAME: String = "_id"
const val CLASS_ATTR_NAME: String = "gender"

class G2DReader(private val folder: String = "dataset") {

    fun groundTruth(): Instances = CSVLoader()
            .apply { setSource(File("$folder/gender.csv")) }.dataSet
            .apply {
                setClass(attribute(CLASS_ATTR_NAME))
                attribute(ID_ATTR_NAME)
            }
            .withStringId()

    fun gamesData(): Instances = readData("games")

    fun gamesTimeData(): Instances = readData("games_time")

    fun genresData(): Instances = readData("genres")

    fun genresTimeData(): Instances = readData("genres_time")

    fun categoriesData(): Instances = readData("categories")

    fun categoriesTimeData(): Instances = readData("categories_time")

    private fun readData(filename: String): Instances = CSVLoader()
            .apply { setSource(File("$folder/$filename.csv")) }
            .dataSet
            .withStringId()
}

private fun Instances.withStringId(): Instances {
    val idAttr = attribute(ID_ATTR_NAME)
    val otherAttrs = attributes().filter { it != idAttr }
    val idAttrStr = Attribute(ID_ATTR_NAME, true)
    val attributes = listOf(idAttrStr) + otherAttrs

    val instances = Instances(relationName(), ArrayList(attributes), size)
    instances += map { instance ->
        val fakeInstance = DenseInstance(attributes.size)
        fakeInstance.setValue(idAttrStr, instance.value(idAttr.index()).toInt().toString())
        otherAttrs.forEach { fakeInstance.setValue(it, instance.value(it)) }
        fakeInstance
    }

    try {
        val classAttr = classAttribute()
        instances.setClassIndex(instances.attribute(classAttr.name()).index())
    } catch (ignored: UnassignedClassException) {
    }

    return instances
}
