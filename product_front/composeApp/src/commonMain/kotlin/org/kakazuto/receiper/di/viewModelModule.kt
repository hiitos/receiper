package org.kakazuto.receiper.di

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.concurrent.PlatformMainDispatcher
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleStore
import cafe.adriel.voyager.core.model.rememberScreenModel
import org.kakazuto.receiper.ui.Camera.CameraScreenModel
import org.kakazuto.receiper.ui.Recipe.RecipeScreenModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

@Composable
public inline fun <reified T : ScreenModel> Screen.getScreenModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    // Koin DIフレームワークを使用して依存関係を取得
    val koin = KoinPlatform.getKoin()
    // rememberScreenModelを使用して画面の状態を保持。画面が再構築されても状態が維持される。
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}

// ViewModel層の依存関係を設定するKoinモジュール
val viewModel = module {
    // ListScreenModelのインスタンスを生成するためのファクトリを定義
    factory {
        CameraScreenModel()
    }
    // LoginScreenModelのインスタンスを生成するためのファクトリを定義
    factory {
        RecipeScreenModel()
    }
}