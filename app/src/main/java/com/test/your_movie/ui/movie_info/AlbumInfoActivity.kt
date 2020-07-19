package com.test.your_movie.ui.movie_info

//class AlbumInfoActivity : AppCompatActivity() {
//
//    private var currentMovie: Movie? = null
//    private val MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_movie_info)
//
//        val bundle = intent.extras
//        if (bundle != null && bundle.containsKey("movie"))
//            currentMovie = bundle.getSerializable("movie") as Movie
//
//        init()
//    }
//
//    private fun init() {
//        val poster = findViewById<ImageView>(R.id.photo)
//        val title = findViewById<TextView>(R.id.title)
//        val originalTitle = findViewById<TextView>(R.id.original_title)
//        val date = findViewById<TextView>(R.id.date)
//        val popularity = findViewById<TextView>(R.id.popularity)
//        val description = findViewById<TextView>(R.id.description)
//        val progressBar = findViewById<ProgressBar>(R.id.progress_circular)
//        val progressText = findViewById<TextView>(R.id.progress_text)
//
//        val vote = java.lang.Double.valueOf(currentMovie!!.voteAverage)
//        val strDate = resources.getText(R.string.release_date).toString() + " " + currentMovie!!.releaseDate
//        val strPopularity = resources.getText(R.string.popularity).toString() + " " + currentMovie!!.popularity
//
//        Picasso.with(this).load(MOVIE_IMAGE_URL + currentMovie!!.posterPath)
//                .error(R.drawable.empty)
//                .placeholder(R.drawable.empty)
//                .into(poster)
//
//        title.text = currentMovie!!.movieTitle
//        originalTitle.text = currentMovie!!.movieOriginalTitle
//        description.text = currentMovie!!.overview
//        progressBar.progress = java.lang.Double.valueOf(vote * 10).toInt()
//        progressText.text = currentMovie!!.voteAverage
//
//        val spanDate = SpannableString(strDate)
//        spanDate.setSpan(TextAppearanceSpan(this, R.style.MediumText), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spanDate.setSpan(TextAppearanceSpan(this, R.style.LightText), 13, strDate.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        date.setText(spanDate, TextView.BufferType.SPANNABLE)
//
//        val spanPopularity = SpannableString(strPopularity)
//        spanPopularity.setSpan(TextAppearanceSpan(this, R.style.MediumText), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spanPopularity.setSpan(TextAppearanceSpan(this, R.style.LightText), 11, strPopularity.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        popularity.setText(spanPopularity, TextView.BufferType.SPANNABLE)
//    }
//
//    fun onClickBack(view: View) {
//        finish()
//    }
//}
