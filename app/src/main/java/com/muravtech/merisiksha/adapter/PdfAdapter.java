package com.muravtech.merisiksha.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muravtech.merisiksha.Bean.UpdateBean;
import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.student_ui.Notes_View;
import com.muravtech.merisiksha.student_ui.WebViewActivity;
import com.muravtech.merisiksha.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder1> {
    private List<UpdateBean.DataBean> beanPDF;
    private Context context;
    private String path;
    //RequestOptions options;

    public PdfAdapter(Context context, List<UpdateBean.DataBean> beanPDF, String path) {
        this.context = context;
        this.beanPDF = beanPDF;
        this.path = path;

    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdfmodel, viewGroup, false);
        return new ViewHolder1(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder1 holder, int position) {
        UpdateBean.DataBean item = beanPDF.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getType().equalsIgnoreCase("link")) {
                   // Intent intent = new Intent(context, WebViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.putExtra("url", item.getLink());
                    //intent.putExtra("type", item.getType());
                    //  intent.putExtra("path",path);
                    //context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, Notes_View.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("PDF_File", path + beanPDF.get(position).getFilename().get(0).getFile());
                    //intent.putExtra("title", path + beanPDF.get(position).getTitle());
                    intent.putExtra("type", item.getType());
                    //  intent.putExtra("path",path);
                    context.startActivity(intent);
                }
            }
        });

        if(item.getType().equalsIgnoreCase("image")) {
            holder.webView.setVisibility(View.GONE);
            holder.image_get.setVisibility(View.VISIBLE);

            Picasso.with(context).load(path+item.getFilename().get(0).getFile()).placeholder(R.mipmap.siksha).into(holder.image_get);

            if(item.getTitle()!=null)
               holder.textView_title.setText(Html.fromHtml(item.getTitle()));
            if(item.getCreated_at()!=null)
                holder.textview_date.setText(Html.fromHtml(Utils.DateFormate(item.getCreated_at())));


        }else if(item.getType().equalsIgnoreCase("document")) {
            holder.image_get.setVisibility(View.GONE);
            holder.webView.setVisibility(View.VISIBLE);
            if (item.getTitle() != null)
                holder.textView_title.setText(Html.fromHtml(item.getTitle()));

            if(item.getCreated_at()!=null)
                holder.textview_date.setText(Html.fromHtml(Utils.DateFormate(item.getCreated_at())));
        }
        else if (item.getType().equalsIgnoreCase("link")) {
            holder. image_get.setVisibility(View.GONE);
            holder.textView_title.setVisibility(View.GONE);
            holder.webView.setVisibility(View.VISIBLE);
            holder.webView.setText(item.getLink());
            Linkify.addLinks( holder.webView, Linkify.WEB_URLS);

            holder.textview_date.setText(Html.fromHtml(Utils.DateFormate(item.getCreated_at())));
        }


    }

    @Override
    public int getItemCount() {
        return beanPDF.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.image_get)
        ImageView image_get;
        @BindView(R.id.textView_title)
        TextView textView_title;
        @BindView(R.id.textview_date)
        TextView textview_date;
        @BindView(R.id.webView)
        TextView webView;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }
}
