package com.aubrey.recepku.data.response

import com.google.gson.annotations.SerializedName


class RecipeListResponse(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

 class Brand(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

 class Unit(

	@field:SerializedName("system")
	val system: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("display_plural")
	val displayPlural: String? = null,

	@field:SerializedName("display_singular")
	val displaySingular: String? = null,

	@field:SerializedName("abbreviation")
	val abbreviation: String? = null
)

 class Nutrition(

	@field:SerializedName("carbohydrates")
	val carbohydrates: Int? = null,

	@field:SerializedName("fiber")
	val fiber: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("protein")
	val protein: Int? = null,

	@field:SerializedName("fat")
	val fat: Int? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("sugar")
	val sugar: Int? = null
)

 class TotalTimeTier(

	@field:SerializedName("tier")
	val tier: String? = null,

	@field:SerializedName("display_tier")
	val displayTier: String? = null
)

 class InstructionsItem(

	@field:SerializedName("start_time")
	val startTime: Int? = null,

	@field:SerializedName("appliance")
	val appliance: String? = null,

	@field:SerializedName("end_time")
	val endTime: Int? = null,

	@field:SerializedName("temperature")
	val temperature: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: Int? = null,

	@field:SerializedName("display_text")
	val displayText: String? = null
)

 class ComponentsItem(

	@field:SerializedName("raw_text")
	val rawText: String? = null,

	@field:SerializedName("extra_comment")
	val extraComment: String? = null,

	@field:SerializedName("ingredient")
	val ingredient: Ingredient? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: Int? = null,

	@field:SerializedName("measurements")
	val measurements: List<MeasurementsItem?>? = null
)

 class UserRatings(

	@field:SerializedName("count_positive")
	val countPositive: Int? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("count_negative")
	val countNegative: Int? = null
)

 class MeasurementsItem(

	@field:SerializedName("unit")
	val unit: Unit? = null,

	@field:SerializedName("quantity")
	val quantity: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

 class ShowItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

 class RenditionsItem(

	@field:SerializedName("container")
	val container: String? = null,

	@field:SerializedName("poster_url")
	val posterUrl: String? = null,

	@field:SerializedName("file_size")
	val fileSize: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("bit_rate")
	val bitRate: Int? = null,

	@field:SerializedName("content_type")
	val contentType: String? = null,

	@field:SerializedName("aspect")
	val aspect: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("minimum_bit_rate")
	val minimumBitRate: Any? = null,

	@field:SerializedName("maximum_bit_rate")
	val maximumBitRate: Any? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

 class TagsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

 class CreditsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

 class Ingredient(

	@field:SerializedName("updated_at")
	val updatedAt: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Int? = null,

	@field:SerializedName("display_plural")
	val displayPlural: String? = null,

	@field:SerializedName("display_singular")
	val displaySingular: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

 class SectionsItem(

	@field:SerializedName("components")
	val components: List<ComponentsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("position")
	val position: Int? = null
)

 class Show(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

 class ResultsItem(

	@field:SerializedName("nutrition_visibility")
	val nutritionVisibility: String? = null,

	@field:SerializedName("instructions")
	val instructions: List<InstructionsItem?>? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("keywords")
	val keywords: String? = null,

	@field:SerializedName("facebook_posts")
	val facebookPosts: List<Any?>? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("user_ratings")
	val userRatings: UserRatings? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("brand")
	val brand: Any? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("servings_noun_singular")
	val servingsNounSingular: String? = null,

	@field:SerializedName("show_id")
	val showId: Int? = null,

	@field:SerializedName("prep_time_minutes")
	val prepTimeMinutes: Int? = null,

	@field:SerializedName("sections")
	val sections: List<SectionsItem?>? = null,

	@field:SerializedName("brand_id")
	val brandId: Any? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItem?>? = null,

	@field:SerializedName("nutrition")
	val nutrition: Nutrition? = null,

	@field:SerializedName("compilations")
	val compilations: List<Any?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("num_servings")
	val numServings: Int? = null,

	@field:SerializedName("buzz_id")
	val buzzId: Any? = null,

	@field:SerializedName("tips_and_ratings_enabled")
	val tipsAndRatingsEnabled: Boolean? = null,

	@field:SerializedName("aspect_ratio")
	val aspectRatio: String? = null,

	@field:SerializedName("show")
	val show: Show? = null,

	@field:SerializedName("created_at")
	val createdAt: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("draft_status")
	val draftStatus: String? = null,

	@field:SerializedName("inspired_by_url")
	val inspiredByUrl: Any? = null,

	@field:SerializedName("thumbnail_url")
	val thumbnailUrl: String? = null,

	@field:SerializedName("thumbnail_alt_text")
	val thumbnailAltText: String? = null,

	@field:SerializedName("total_time_minutes")
	val totalTimeMinutes: Int? = null,

	@field:SerializedName("video_url")
	val videoUrl: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Int? = null,

	@field:SerializedName("credits")
	val credits: List<CreditsItem?>? = null,

	@field:SerializedName("approved_at")
	val approvedAt: Int? = null,

	@field:SerializedName("is_one_top")
	val isOneTop: Boolean? = null,

	@field:SerializedName("servings_noun_plural")
	val servingsNounPlural: String? = null,

	@field:SerializedName("renditions")
	val renditions: List<RenditionsItem?>? = null,

	@field:SerializedName("beauty_url")
	val beautyUrl: Any? = null,

	@field:SerializedName("is_shoppable")
	val isShoppable: Boolean? = null,

	@field:SerializedName("topics")
	val topics: List<Any?>? = null,

	@field:SerializedName("total_time_tier")
	val totalTimeTier: TotalTimeTier? = null,

	@field:SerializedName("video_ad_content")
	val videoAdContent: String? = null,

	@field:SerializedName("seo_title")
	val seoTitle: String? = null,

	@field:SerializedName("yields")
	val yields: String? = null,

	@field:SerializedName("original_video_url")
	val originalVideoUrl: String? = null,

	@field:SerializedName("canonical_id")
	val canonicalId: String? = null,

	@field:SerializedName("cook_time_minutes")
	val cookTimeMinutes: Int? = null,

	@field:SerializedName("video_id")
	val videoId: Int? = null,

	@field:SerializedName("promotion")
	val promotion: String? = null
)

 class TopicsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)

 class CompilationsItem(

	@field:SerializedName("aspect_ratio")
	val aspectRatio: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("is_shoppable")
	val isShoppable: Boolean? = null,

	@field:SerializedName("keywords")
	val keywords: Any? = null,

	@field:SerializedName("facebook_posts")
	val facebookPosts: List<Any?>? = null,

	@field:SerializedName("show")
	val show: List<ShowItem?>? = null,

	@field:SerializedName("created_at")
	val createdAt: Int? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("draft_status")
	val draftStatus: String? = null,

	@field:SerializedName("thumbnail_url")
	val thumbnailUrl: String? = null,

	@field:SerializedName("thumbnail_alt_text")
	val thumbnailAltText: String? = null,

	@field:SerializedName("video_url")
	val videoUrl: String? = null,

	@field:SerializedName("approved_at")
	val approvedAt: Int? = null,

	@field:SerializedName("canonical_id")
	val canonicalId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("beauty_url")
	val beautyUrl: Any? = null,

	@field:SerializedName("buzz_id")
	val buzzId: Any? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("video_id")
	val videoId: Int? = null,

	@field:SerializedName("promotion")
	val promotion: String? = null
)
