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

    val dummyContent =
        "<h1>Ini salah satu tulisan H1</h1><div><br></div><h2>Ini salah satu tulisan h2</h2><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278837%2C%20nanoseconds%3D694000000).jpg?alt=media&token=8abc6b73-fe5e-4846-b2c1-fbabcbce5bd4\" alt=\"\" width=\"360\"></div><div><br></div><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278837%2C%20nanoseconds%3D694000000).jpg?alt=media&amp;token=8abc6b73-fe5e-4846-b2c1-fbabcbce5bd4\" alt=\"\" width=\"360\"><br></div><h3><span style=\"font-family: sans-serif;\">Ini salah satu tulisan h3</span></h3><div><span style=\"font-family: sans-serif;\"><b>Ini salah satu tulisan bold</b></span><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><b><br></b></span></div><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278874%2C%20nanoseconds%3D12000000).jpg?alt=media&amp;token=571d2bc5-2c6c-4a48-81c3-41a271971be2\" alt=\"\" width=\"360\"><span style=\"font-family: sans-serif;\"><b><br></b></span></div><div><span style=\"font-family: sans-serif;\"><b><br></b></span></div><div><span style=\"font-family: sans-serif;\"><i>Ini salah satu tulisan&nbsp;</i></span><br></div><div><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><strike>Ini salah satu tulisan&nbsp;</strike></span><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><br></span></div><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278891%2C%20nanoseconds%3D32000000).jpg?alt=media&amp;token=feb537ec-ee4f-4edf-9f8f-b1f7a5ed3398\" alt=\"\" width=\"360\"><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><u>Ini salah satu tulisan&nbsp;</u></span><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><u><br></u></span></div><div><ul><li><font face=\"sans-serif\"><u>Ini liat</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li></ul><div><ol><li><font face=\"sans-serif\"><u>Ini juga list</u></font></li><li><font face=\"sans-serif\"><u>Ini juga ini juga</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li></ol><div><font face=\"sans-serif\"><u><br></u></font></div></div></div>"
}