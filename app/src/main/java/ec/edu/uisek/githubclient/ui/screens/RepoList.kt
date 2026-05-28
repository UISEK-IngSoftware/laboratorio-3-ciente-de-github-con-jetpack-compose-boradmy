package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ec.edu.uisek.githubclient.ui.components.RepoItem

@Composable
fun RepoList(
    modifier: Modifier = Modifier
) {

    Column(
       modifier = modifier
           .fillMaxSize()
           .padding(all = 16.dp)
    ){
        RepoItem(
            name = "Proyecto de Django",
            description = "Un proyecto realizado en Django 5.2 para la materia de desarrollo Web",
            avatarImg = "https://impulso06.com/wp-content/uploads/2023/11/Python-y-Django-Herramientas-esenciales-para-el-desarrollo-web-moderno.png",
            language = "Python"
        )
        RepoItem(
            name = "Proyecto de React",
            description = "Un proyecto realizado en React 18.2.2 para la materia de desarrollo Web",
            avatarImg = "https://images.icon-icons.com/3660/PNG/512/programming_tecnology_react_logo_native_icon_228491.png",
            language = "Typescript"
        )
        RepoItem(
            name = "Proyecto de Android",
            description = "Un proyecto realizado en Kotlin para la materia de desarrollo Móvil",
            avatarImg = "https://unaaldia.hispasec.com/wp-content/uploads/2014/10/f94e0-android-logo.png",
            language = "Kotlin"
        )
        RepoItem(
            name = "Proyecto de iOS",
            description = "Un proyecto realizado en Swift para la materia de desarrollo Móvil",
            avatarImg = "https://play-lh.googleusercontent.com/FCcziMA1_M9nGlJo6EnguMKlJ53Yor3tNmSqDUza9w9_wXrFLiAW2cOz-kD8S-N1Vvg",
            language = "Swift"
        )
    }
}
