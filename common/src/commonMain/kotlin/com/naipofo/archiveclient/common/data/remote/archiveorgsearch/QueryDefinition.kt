package com.naipofo.archiveclient.common.data.remote.archiveorgsearch

data class SortDefinition(val field: String, val type: SortType) {
    fun build() = "$field ${when(type){
        SortType.ASC -> "asd"
        SortType.DESC -> "desc"
    }}"
}

enum class SortType {
    ASC, DESC
}

sealed interface FilterGroup {
    fun build(): String = when (this){
        is Single -> this.value.build()
        is Group -> "(${first.build()}) ${when(this.type){
            groupType.AND -> "AND"
            groupType.OR -> "OR"
            groupType.AND_NOT -> "AND NOT"
        }} (${second.build()})"
    }
    data class Single(val value: SearchFilter) : FilterGroup
    data class Group(val type: groupType, val first: FilterGroup, val second: FilterGroup): FilterGroup
}

enum class groupType {
    AND, OR, AND_NOT
}

data class SearchFilter(
    val field: String,
    val filter: SearchRule
) {
    fun build(): String = "$field:${when(filter){
        is SearchRule.Exact -> filter.value
        is SearchRule.Ratio -> "[${filter.lower} TO ${filter.upper}]"
    }}"
}

sealed interface SearchRule {
    data class Exact(val value: String) : SearchRule
    data class Ratio(val lower: String, val upper: String) : SearchRule
}

sealed interface FilterValue{
    data class DateValue(val year: Int, val month: Int, val day: Int): FilterValue
    data class NumberValue(val value: Long): FilterValue
    data class StringValue(val value: String): FilterValue
}

enum class FilterValueType{
    DATE, NUMBER, STRING
}

data class FilterType(
    val title: String,
    val type: FilterValueType,
    val description: String?
)