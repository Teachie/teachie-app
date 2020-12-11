package id.teachly.data

data class Category(
    val name: String? = null,
    val img: String? = null
)

fun List<Category>.getNameOnly(): MutableList<String> {
    val nameOnly = mutableListOf<String>()
    this.forEach { nameOnly.add(it.name ?: "") }
    return nameOnly
}