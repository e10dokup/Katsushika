# Katsushika

[![CircleCI](https://circleci.com/gh/e10dokup/Katsushika.svg?style=svg)](https://circleci.com/gh/e10dokup/Katsushika)  [ ![Download](https://api.bintray.com/packages/e10dokup/maven/katsushika/images/download.svg) ](https://bintray.com/e10dokup/maven/katsushika/_latestVersion)

Katsushika is simple Kotlin-implemented ImageLoader for Android inspired by Picasso. Katsushika also uses `kotlinx.coroutines`.

![](static/katsushika_logo.png)

Katsushika supports fetching and displaying of images through the network. Katsushika's elements related to processing are modularized and can be freely implemented by developers. By default Katsushika uses OkHttp library.

# Download

Use Gradle:

``` gradle
implementation 'xyz.dokup.katsushika:katsushika:1.0.0'
```

if you want customize Katsushika's elements. import `kotlinx.coroutines` libraries.

``` gradle
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:0.20"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:0.20"
```

# Examples

### Load image from url

``` kotlin
Katsushika.with(context).load("http://example.com/image.png").into(imageView)
```

### Scale image size adjust to ImageView size

Use `.scale` method chain. Katsushika has scaler that scales image size adjust to ImageView size as default.

``` kotlin
Katsushika.with(context)
        .load("http://example.com/image.png")
        .scale(AdjustScalar(imageView))
        .into(imageView)
```

### Transform image into rounded corners

Use `.transform` method chain. Katsushika has transformer that transforms image into rounded corners as default. Multiple sets of transformers can be set.

``` kotlin
Katsushika.with(context)
        .load("http://example.com/image.png")
        .transform(RoundCornerTransformer())
        .into(imageView)
```

### Cache images

Use `.cache` method chain. Katsushika has memory/disk/combined(memory-disk) caches as default. If none are selected, Katsushika will not cache any image.

``` kotlin
Katsushika.with(context)
        .load("http://example.com/image.png")
        .cache(DefaultCombinedCache(context))
        .into(imageView)
```

### Implement scaler/transformer/cache yourself

Katsushika's scaler/transformer/cache are implementations interfaces `BitmapScaler`/`BitmapTransformer`/`BitmapCache`. So, developers can implement customized elements.

* `BitmapScaler` - Override `suspend fun scaleBitmap(byteArray: ByteArray): Bitmap`
* `BitmapTransformer` - Override `suspend fun transform(bitmap: Bitmap): Bitmap`
* `BitmapCache` - override `fun putBitmap(key: String, bitmap: Bitmap)` and `fun getBitmap(key: String): Bitmap?`

# License

```
Copyright 2018 e10dokup

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```