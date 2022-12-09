package com.vahitkeskin.jetpackcomposewikipediaclone.model.main_page

data class Parse(
    val categories: List<Any>,
    val displaytitle: String,
    val externallinks: List<String>,
    val images: List<String>,
    val iwlinks: List<Iwlink>,
    val langlinks: List<Langlink>,
    val links: List<Link>,
    val pageid: Int,
    val parsewarnings: List<Any>,
    val properties: List<Property>,
    val revid: Int,
    val sections: List<Section>,
    val templates: List<Template>,
    val text: Text,
    val title: String
)