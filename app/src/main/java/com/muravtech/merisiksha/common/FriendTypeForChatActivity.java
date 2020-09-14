package com.muravtech.merisiksha.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.muravtech.merisiksha.R;
import com.muravtech.merisiksha.teacher_ui.AdminForChatActivity;
import com.muravtech.merisiksha.teacher_ui.StudentListForChatActivity;
import com.muravtech.merisiksha.utils.AppPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendTypeForChatActivity extends AppCompatActivity {
    Intent i;
    String type;
    @BindView(R.id.rl_school)
    TextView rlSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_type_for_chat);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("admin")){
            rlSchool.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.rl_school, R.id.rl_teacher, R.id.rl_parent, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_school:
                i = new Intent(FriendTypeForChatActivity.this, AdminForChatActivity.class);
                i.putExtra("type", type);
                startActivity(i);
                break;
            case R.id.rl_teacher:
                i = new Intent(FriendTypeForChatActivity.this, TeacherListForChatActivity.class);
                i.putExtra("type", type);
                startActivity(i);
                break;
            case R.id.rl_parent:
                if (type.equalsIgnoreCase("student")) {
                    i = new Intent(FriendTypeForChatActivity.this, StudentListForChatActivity.class);
                    i.putExtra("type", type);
                    i.putExtra("id", AppPreferences.getSectionId());
                } else {
                    i = new Intent(FriendTypeForChatActivity.this, ClassWiseListActivity.class);
                    i.putExtra("type", type);
                    i.putExtra("from", "chat");
                }
                startActivity(i);

                break;
        }
    }
}