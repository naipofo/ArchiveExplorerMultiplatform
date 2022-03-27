import com.naipofo.archiveclient.common.data.RemoteResource
import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.ui.generic.elementFromResource
import kotlin.test.Test
import kotlin.test.assertTrue

class ResourceManagementTesting {
    object Resource

    @Test
    fun `Inlined resource management`() {
        val resource = RemoteResource.Success(Result.Success(Resource))
        var passed = false
        elementFromResource(resource, {}, {}, {
            if (it == resource.data.data) passed = true
        })
        assertTrue { passed }

        passed = false
        val stillLoading = RemoteResource.Loading
        elementFromResource<Nothing>(stillLoading, { passed = true }, {}, {})
        assertTrue { passed }

        passed = false
        val errored = RemoteResource.Success(Result.Error(Exception()))
        elementFromResource(errored, {}, {
            if (it == errored.data.exception) passed = true
        }, {})
        assertTrue { passed }
    }
}