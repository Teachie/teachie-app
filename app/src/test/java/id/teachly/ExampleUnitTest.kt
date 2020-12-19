package id.teachly

import id.teachly.data.StoryContent
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testScript() {
        val listData = mutableListOf<String>()
        val dataFix = mutableListOf<StoryContent>()
        val dummyContent =
            "<h1>Ini salah satu tulisan H1</h1><div><br></div><h2>Ini salah satu tulisan h2</h2><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278824%2C%20nanoseconds%3D865000000).jpg?alt=media&amp;token=517ad5bc-e43b-42a3-8abd-7a0554138e06\" alt=\"\" width=\"360\"></div><div><br></div><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278837%2C%20nanoseconds%3D694000000).jpg?alt=media&amp;token=8abc6b73-fe5e-4846-b2c1-fbabcbce5bd4\" alt=\"\" width=\"360\"><br></div><h3><span style=\"font-family: sans-serif;\">Ini salah satu tulisan h3</span></h3><div><span style=\"font-family: sans-serif;\"><b>Ini salah satu tulisan bold</b></span><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><b><br></b></span></div><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278874%2C%20nanoseconds%3D12000000).jpg?alt=media&amp;token=571d2bc5-2c6c-4a48-81c3-41a271971be2\" alt=\"\" width=\"360\"><span style=\"font-family: sans-serif;\"><b><br></b></span></div><div><span style=\"font-family: sans-serif;\"><b><br></b></span></div><div><span style=\"font-family: sans-serif;\"><i>Ini salah satu tulisan&nbsp;</i></span><br></div><div><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><strike>Ini salah satu tulisan&nbsp;</strike></span><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><br></span></div><div><img src=\"https://firebasestorage.googleapis.com/v0/b/teachly-id.appspot.com/o/image%2Fstory%2FWvtnCLz0mDdJ0o7pA464QUpOMJA2%2FTimestamp(seconds%3D1608278891%2C%20nanoseconds%3D32000000).jpg?alt=media&amp;token=feb537ec-ee4f-4edf-9f8f-b1f7a5ed3398\" alt=\"\" width=\"360\"><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><u>Ini salah satu tulisan&nbsp;</u></span><span style=\"font-family: sans-serif;\"><br></span></div><div><span style=\"font-family: sans-serif;\"><u><br></u></span></div><div><ul><li><font face=\"sans-serif\"><u>Ini liat</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li></ul><div><ol><li><font face=\"sans-serif\"><u>Ini juga list</u></font></li><li><font face=\"sans-serif\"><u>Ini juga ini juga</u></font></li><li><font face=\"sans-serif\"><u>Ini juga</u></font></li></ol><div><font face=\"sans-serif\"><u><br></u></font></div></div></div>"

        val data = dummyContent.split("<img src=\"")


        data.forEachIndexed { index, s ->
            if (index != 0) {
                val split = s.split("\" alt=\"\" width=\"360\">")
                listData.addAll(split)

            } else listData.add(s)

        }

        listData.forEach {
            val type = if (it.substring(0, 8) == "https://") 1 else 0
            dataFix.add(StoryContent(it, type))
        }

    }
}