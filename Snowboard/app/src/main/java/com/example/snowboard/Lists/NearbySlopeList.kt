package com.example.snowboard.Lists

import com.example.snowboard.R

data class NearbySlopeList(
    val imgResort: Int,
    val nameRes: Int,
    val locationRes: Int,
    val difficultyRes: Int,
    val rating: Double,
    val skiresortSlug: String,
    val latitude: Double,
    val longitude: Double
)

// `rating` is a fallback shown until the live rating loads from skiresort.com (see
// MainScreenFragment.fetchLiveRating), sourced from skiresort.com's aggregate score as of 2026-08.
object NearbySlopeData {
    val all = listOf(
        NearbySlopeList(
            R.drawable.bansko_resort, R.string.title_ski_slopes_1, R.string.location_bansko,
            R.string.difficulty_advanced, 4.2, "bansko", 41.8377, 23.4867
        ),
        NearbySlopeList(
            R.drawable.borovets_resort, R.string.title_ski_slopes_2, R.string.location_borovets,
            R.string.difficulty_intermediate, 3.7, "borovets", 42.2667, 23.6167
        ),
        NearbySlopeList(
            R.drawable.pamporovo_resort, R.string.title_ski_slopes_3, R.string.location_pamporovo,
            R.string.difficulty_beginner, 3.6, "pamporovo", 41.6500, 24.6833
        ),
        NearbySlopeList(
            R.drawable.vitosha_resort, R.string.title_ski_slopes_4, R.string.location_vitosha,
            R.string.difficulty_beginner, 3.1, "vitosha-aleko-sofia", 42.5667, 23.2833
        ),
        NearbySlopeList(
            R.drawable.chepelare_resort, R.string.title_ski_slopes_5, R.string.location_chepelare,
            R.string.difficulty_intermediate, 3.0, "mechi-chal-chepelare", 41.7333, 24.6833
        ),
        NearbySlopeList(
            R.drawable.dobrinishte_resort, R.string.title_ski_slopes_6, R.string.location_dobrinishte,
            R.string.difficulty_beginner, 2.6, "dobrinishte", 41.8167, 23.5667
        ),
        NearbySlopeList(
            R.drawable.malyovitsa_resort, R.string.title_ski_slopes_7, R.string.location_malyovitsa,
            R.string.difficulty_advanced, 2.3, "malyovitsa", 42.2000, 23.4167
        ),
        NearbySlopeList(
            R.drawable.uzana_resort, R.string.title_ski_slopes_8, R.string.location_uzana,
            R.string.difficulty_beginner, 2.2, "uzana-uzana", 42.7667, 25.3000
        ),
        NearbySlopeList(
            R.drawable.semkovo_resort, R.string.title_ski_slopes_9, R.string.location_semkovo,
            R.string.difficulty_intermediate, 2.2, "semkovo", 41.9833, 23.6000
        ),
        NearbySlopeList(
            R.drawable.kartala_resort, R.string.title_ski_slopes_10, R.string.location_kartala,
            R.string.difficulty_advanced, 2.8, "bodrost-kartala", 41.3833, 23.3000
        )
    )
}
