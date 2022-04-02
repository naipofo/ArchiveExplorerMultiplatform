package com.naipofo.archiveclient.common.data.remote.archiveorgsearch

val filterTypes = listOf(
    FilterType("identifier", FilterValueType.STRING, "The identifier of the item"),
    FilterType("title", FilterValueType.STRING, "The name of the item"),
    FilterType("creator", FilterValueType.STRING, "An entity primarily responsible for making the content of the resource"),
    FilterType("collection", FilterValueType.STRING, "The identifier of the parent collection"),
    FilterType("adder", FilterValueType.STRING, "Person who added the item"),
    FilterType("uploader", FilterValueType.STRING, "Person who uploaded the item"),
    FilterType("updater", FilterValueType.STRING, "Person who updated the item"),
    FilterType("language", FilterValueType.STRING, "Language of the item"),

    FilterType("downloads", FilterValueType.NUMBER, "Number of times an item was downloaded"),
    FilterType("num_reviews", FilterValueType.NUMBER, "Number of reviews an item has"),
    FilterType("item_size", FilterValueType.NUMBER, "Size of the item"),

    FilterType("publicdate", FilterValueType.DATE, "Date this item went public"),
    FilterType("createdate", FilterValueType.DATE, "Date this item was created"),
    FilterType("updatedate", FilterValueType.DATE, "Date this item was updated"),
    FilterType("oai_updatedate", FilterValueType.DATE, "Utility field merging other date filters")
)