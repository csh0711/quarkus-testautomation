import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode.LENIENT

/**
 * Hamcrest matcher that checks JSON.
 *
 * @see <a href="https://github.com/nt-ca-aqe/micronaut-library-app/blob/master/library-service/src/test/kotlin/utils/JsonMatcher.kt">AQE's Library App</a>
 */
class JsonMatcher(private val expectedJson: String) : TypeSafeMatcher<String>() {

    companion object {
        fun jsonEqualTo(expectedJson: String) = JsonMatcher(expectedJson)
    }

    override fun describeTo(description: Description) {
        description.appendValue(expectedJson)
    }

    override fun matchesSafely(actual: String): Boolean =
        JSONCompare.compareJSON(expectedJson, actual, LENIENT).passed()
}
