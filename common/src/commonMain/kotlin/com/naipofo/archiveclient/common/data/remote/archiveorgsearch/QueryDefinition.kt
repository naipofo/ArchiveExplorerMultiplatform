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
        is SearchRule.Exact<*> -> filter.value.build()
        is SearchRule.Ratio<*> -> "[${filter.lower.build()} TO ${filter.upper.build()}]"
    }}"
}

sealed interface SearchRule {
    data class Exact<T: FilterValue>(val value: T) : SearchRule
    data class Ratio<T: FilterValue>(val lower: T, val upper: T) : SearchRule
}

sealed interface FilterValue{
    fun build(): String = when (this){
        is DateValue -> "${year}-${month}-${day}"
        is NumberValue -> value.toString()
        is StringValue -> value
    }

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