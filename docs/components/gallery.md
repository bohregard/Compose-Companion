# Gallery

A simple wrapper around [horizontal pager](https://google.github.io/accompanist/pager/#horizontalpager) to display images/videos.

!!! info

    Due to the way the pager works, if you specify images of different aspect ratios, the largest aspect ratio is used and will result in white space surrounding smaller aspect ratios.

## Usage

The `Gallery` composable expects a list of type `BaseGalleryItem`s which have a few super types: `WebImage`, `ResourceImage`, and `VideoItem`. `WebImage` uses [coil](https://coil-kt.github.io/coil/) to resolve the url and display the image. If `VideoItem` is specified in the list, it is up to the user to define the behavior in the `onVideoLayout` callback of `Gallery`.

If custom items are needed to render (for other Image libraries like Picasso, Glide, et cetera), you can specify custom classes that extend `BaseGalleryItem` and pass in a callback to `onCustomLayout`.

The default content scale is `ContentScale.Fit`, 

### Simple Usage

```kotlin
// Define the items to be used
val items = listOf<BaseGalleryItem>(
    WebImage(3000, 4000, "https://preview.redd.it/fqdk19afys791.jpg?width=4000&format=pjpg&auto=webp&s=822d2d2c7248b14959bf4ce9f219f779169ef9d8"),
    WebImage(4000, 3000, "https://preview.redd.it/reafmt1gys791.jpg?width=3000&format=pjpg&auto=webp&s=1c5fe37518be2d2954835027c13d364784200a0a"),
//    WebImage(600, 800, "https://picsum.photos/800/600"),
)

Gallery(
    items = items,
)
```