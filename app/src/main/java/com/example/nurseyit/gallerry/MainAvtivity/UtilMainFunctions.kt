package com.example.nurseyit.gallerry.MainAvtivity

import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter

fun initGallaryLib(thumbnailLoader : ImageGalleryAdapter.ImageThumbnailLoader, fullScreen : FullScreenImageGalleryAdapter.FullScreenImageLoader) {
    ImageGalleryActivity.setImageThumbnailLoader(thumbnailLoader)
    FullScreenImageGalleryActivity.setFullScreenImageLoader(fullScreen)
}
