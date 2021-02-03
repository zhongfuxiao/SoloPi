/*
 * Copyright (C) 2015-present, Ant Financial Services Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.hulu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.hulu.R;
import com.alipay.hulu.common.utils.MiscUtil;
import com.alipay.hulu.fragment.ReplayListFragment;
import com.alipay.hulu.ui.HeadControlPanel;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by lezhou.wyl on 2018/7/30.
 */

public class NewReplayListActivity extends BaseActivity {

    private ViewPager mPager;
    private TabLayout mTabLayout;
    private HeadControlPanel mHeadPanel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_replay_list);

        mPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tab_layout);
        mHeadPanel = findViewById(R.id.head_replay_list);

        // 配置菜单信息
        LayoutInflater inflater = LayoutInflater.from(this);

        View rightTitle = inflater.inflate(R.layout.item_icon_template, mHeadPanel, false);
        ImageView icon = rightTitle.findViewById(R.id.item_icon_template_icon);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rightTitle.setLayoutParams(params);
        TextView title = rightTitle.findViewById(R.id.item_icon_template_title);
        title.setText(R.string.constant__batch_replay);
        icon.setImageResource(R.drawable.icon_batch_play);
        params.setMarginEnd(- getResources().getDimensionPixelSize(R.dimen.control_dp4));
        params.setMarginStart(getResources().getDimensionPixelSize(R.dimen.control_dp8));
        rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewReplayListActivity.this.startActivity(new Intent(NewReplayListActivity.this, BatchExecutionActivity.class));
            }
        });
        mHeadPanel.addMenuFromLeft(rightTitle);

        rightTitle = inflater.inflate(R.layout.item_icon_template, mHeadPanel, false);
        icon = rightTitle.findViewById(R.id.item_icon_template_icon);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rightTitle.setLayoutParams(params);
        title = rightTitle.findViewById(R.id.item_icon_template_title);
        title.setText(R.string.replay_icon__history);
        icon.setImageResource(R.drawable.icon_replay_history);
        params.setMarginStart(getResources().getDimensionPixelSize(R.dimen.control_dp8));
        rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewReplayListActivity.this.startActivity(new Intent(NewReplayListActivity.this, LocalReplayResultActivity.class));
            }
        });
        mHeadPanel.addMenuFromLeft(rightTitle);

        mHeadPanel.setMiddleTitle(getString(R.string.activity__case_list));
        mHeadPanel.setTitlePosition(HeadControlPanel.POSITION_LEFT);
        mHeadPanel.setBackIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.mainBlue));
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                MiscUtil.setIndicator(mTabLayout, 0, 0);
            }
        });

        ReplayPagerAdapter pagerAdapter = new ReplayPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setOffscreenPageLimit(2);

    }


    private static class ReplayPagerAdapter extends FragmentPagerAdapter {

        private static final int[] PAGES = ReplayListFragment.getAvailableTypes();

        public ReplayPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ReplayListFragment.newInstance(PAGES[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ReplayListFragment.getTypeName(PAGES[position]);
        }
        @Override
        public int getCount() {
            return PAGES.length;
        }
    }
}
