package com.example.twolee.chatbot;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.twolee.chatbot.bottombar.BottomNavigationViewHelper;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.example.twolee.chatbot.fragments.HomeFragment;
import com.example.twolee.chatbot.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
    //처음 홈 프래그먼트가 뜨도록 초기값 설정.
    private Fragment selectedFragment;
    private FragmentTransaction transaction;
    private ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;
    // 틀 안에 무엇이 들어갈지..
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedFragment = HomeFragment.newInstance();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment); // 프래그먼트 대체
        transaction.commit();

        actionBar = getSupportActionBar();

        try {
            // 커스텀 바 추가
            if (actionBar != null) {
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar.setCustomView(R.layout.custom_action_bar);
            }

            // 버튼 이벤트 추가
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView); //이동 모드 해제
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            // 수정
                            //selectedFragment = null;
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    selectedFragment = HomeFragment.newInstance();
                                    break;
                                case R.id.action_search:
                                    selectedFragment = SearchFragment.newInstance();
                                    break;
                                case R.id.action_chatting:
                                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                    // TODO: 2018. 8. 13. 어떤 FLAG를 사용할 지 생각
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                    break;
                                case R.id.action_settings:
                                    break;
                            }
                            if (selectedFragment != null) {
                                //프레그먼트 선택할 때#
                                // TODO: 2018. 8. 14. 프래그먼트 안의 내용물 구조는 어떻게 작성할 것인가
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment); // 프래그먼트 대체
                                transaction.commit();
                            }
                            return true;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}