import com.naipofo.archiveclient.common.ui.navigation.NavController
import kotlin.test.Test
import kotlin.test.assertTrue

class NavigationTesting {
    sealed interface Destinations {
        object First : Destinations
        object Second : Destinations
    }

    @Test
    fun `Add and remove from backstack`() {
        val navController = NavController<Destinations>(Destinations.First)
        navController.navigate(Destinations.Second)
        assertTrue { navController.currentBackStackEntry.value is Destinations.Second }
        navController.pop()
        assertTrue { navController.currentBackStackEntry.value is Destinations.First }
        navController.pop()
        assertTrue { navController.currentBackStackEntry.value is Destinations.First }
    }

    @Test
    fun `Absolute navigation`() {
        val navController = NavController<Destinations>(Destinations.First)
        navController.navigate(Destinations.Second)
        navController.absoluteNavigate(Destinations.First)
        navController.pop()
        assertTrue { navController.currentBackStackEntry.value is Destinations.First }
    }
}