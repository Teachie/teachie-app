package id.teachly.utils

object DummyData {

    private val imgMale = listOf(
        "https://i.imgur.com/X92k3E9.png",
        "https://i.imgur.com/A1r6v7u.png",
        "https://i.imgur.com/M5Dpveh.png",
        "https://i.imgur.com/0w75eRr.png",
        "https://i.imgur.com/5jKGLjM.png"
    )

    private val imgFemale = listOf(
        "https://i.imgur.com/yzI1UcI.png",
        "https://i.imgur.com/MQ3BVm6.png",
        "https://i.imgur.com/jY5ocYS.png",
        "https://i.imgur.com/moQfS9x.png",
        "https://i.imgur.com/SM5YF9D.png"
    )

    fun getImg(type: Int, img: Int): String {
        return if (type == 0) imgMale[img]
        else imgFemale[img]
    }

    val interestList = listOf(
        "Masak",
        "Teknologi",
        "Fisika",
        "Kimia",
        "Biologi",
        "Android",
        "iOS",
        "Kotlin",
        "Java",
        "Berenang",
        "Menembak",
        "Makeup",
        "Masak",
        "Teknologi",
        "Fisika",
        "Kimia",
        "Biologi",
        "Android",
        "iOS",
        "Kotlin",
        "Java",
        "Berenang",
        "Menembak",
        "Makeup"
    )

}