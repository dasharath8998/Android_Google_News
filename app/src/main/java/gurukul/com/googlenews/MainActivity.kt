package gurukul.com.googlenews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indview.view.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGetNews.setOnClickListener {
            var r :Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://newsapi.org/").build()
            var api = r.create(GetNews::class.java)
            var call : Call<Articles> = api.getNews()

            call.enqueue(object : Callback<Articles> {
                override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                    var arts : Articles? = response.body()
                    Toast.makeText(this@MainActivity,arts?.articles?.size.toString(),Toast.LENGTH_LONG).show()

                    lView.adapter = object : BaseAdapter() {
                        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                            var inflater = LayoutInflater.from(this@MainActivity)
                            var v = inflater.inflate(R.layout.indview,null)
                            v.tvTitle.text = arts?.articles!!.get(position).title
                            Glide.with(this@MainActivity).load(arts?.articles!!.get(position).urlToImage).into(v.imgView)
                            v.tvDesc.text = arts?.articles!!.get(position).description

                            lView.setOnItemClickListener { parent, view, position, id ->
                                var i = Intent(this@MainActivity,BrowserActivity::class.java)
                                i.putExtra("url","${arts?.articles!!.get(position).url}")
                                startActivity(i)
                            }

                            return v
                        }

                        override fun getItem(position: Int): Any = 0

                        override fun getItemId(position: Int): Long = 0

                        override fun getCount(): Int = arts?.articles!!.size

                    }
                }

                override fun onFailure(call: Call<Articles>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Get newses failed...",Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
