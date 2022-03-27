import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.*
import kotlin.test.Test
import kotlin.test.assertTrue

class ArchiveOrgSearchQueryTesting {
    @Test
    fun `Search filter building`(){
        assertTrue {
            SearchFilter("title", SearchRule.Exact("naa")).build() == "title:naa"
        }
        assertTrue {
            SearchFilter("downloads", SearchRule.Ratio("1000", "2000")).build() == "downloads:[1000 TO 2000]"
        }
    }

    @Test
    fun `Filter group building`(){
        assertTrue {
            FilterGroup.Single(SearchFilter("title", SearchRule.Exact("naa"))).build() == "title:naa"
        }
        assertTrue {
            FilterGroup.Group(
                groupType.AND,
                FilterGroup.Single(SearchFilter("title", SearchRule.Exact("naa"))),
                FilterGroup.Single(SearchFilter("title", SearchRule.Exact("naa")))
            ).build() == "(title:naa) AND (title:naa)"
        }
    }

    @Test
    fun `Sort definition building`() {
        assertTrue {
            SortDefinition("downloads", SortType.DESC).build() == "downloads desc"
        }
    }
}